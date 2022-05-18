import re
import json
import colorsys
import asyncio
import aiohttp
import requests
from io import BytesIO
from PIL import Image
from bs4 import BeautifulSoup
from multiprocessing import Pool

urls = [
    {
        "id": 1,
        "title": "小说",
        "url": "https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4"
    },
    {
        "id": 2,
        "title": "随笔",
        "url": "https://book.douban.com/tag/%E9%9A%8F%E7%AC%94"
    },
    {
        "id": 3,
        "title": "历史",
        "url": "https://book.douban.com/tag/%E5%8E%86%E5%8F%B2"
    },
    {
        "id": 4,
        "title": "旅行",
        "url": "https://book.douban.com/tag/%E6%97%85%E8%A1%8C"
    },
    {
        "id": 5,
        "title": "管理",
        "url": "https://book.douban.com/tag/%E7%AE%A1%E7%90%86"
    },
    {
        "id": 6,
        "title": "科普",
        "url": "https://book.douban.com/tag/%E7%A7%91%E6%99%AE"
    },
]


async def get_url(url, session):
    try:
        async with session.get(url) as resposne:
            res = await resposne.text()
            return res
    except Exception as e:
        return None


async def analysis_book(category, url, session):
    """
    解析url
    :param url:
    :return:
    """
    try:
        page = await get_url(url, session)
        if page is None:
            print('failed', url)
        soup = BeautifulSoup(page, 'lxml')
        title = soup.select('#wrapper > h1 > span')[0].get_text().strip()
        author = soup.find('span', text=re.compile(r'^\s?作者:?\s?$')).next_sibling.next_sibling.getText().strip()
        score = eval(soup.find('strong', attrs={'class': 'll rating_num'}).getText().strip())
        isbn = soup.find('span', text=re.compile(r'^\s?ISBN:?\s?$')).next_sibling.getText().strip()
        cover = soup.find('a', class_='nbg')['href']

        description = soup.find('div', attrs={'class': 'indent', 'id': 'link-report'}).findAll('div', class_='intro')[
            -1] \
            .getText(separator='\n').strip()
        price = eval(soup.find('span', text=re.compile(r'^\s?定价:?\s?$')).next_sibling.getText().strip().strip('元'))

        book = {
            "title": title,
            "author": author,
            "score": score,
            "isbn": isbn,
            "cover": cover,
            "description": description,
            "price": price,
            "category": category
        }
        print('success', url)
        return book
    except Exception as e:
        print('failed', url)
        return None


async def analysis_category(category, session):
    url = category['url']
    page = await get_url(url, session)
    if page is None:
        print('failed', url)
    soup = BeautifulSoup(page, 'lxml')
    books = soup.findAll('li', class_='subject-item')
    book_url = [book.find('a')['href'] for book in books]
    return [(category['id'], url) for url in book_url]


def get_dominant_color(image):
    bytes_stream = BytesIO(image)
    image = Image.open(bytes_stream)
    image = image.convert('RGBA')
    image.thumbnail((400, 400))
    max_score = 0
    dominant_color = None
    for count, (r, g, b, a) in image.getcolors(image.size[0] * image.size[1]):
        # 跳过纯黑色
        if a == 0:
            continue
        saturation = colorsys.rgb_to_hsv(r / 255.0, g / 255.0, b / 255.0)[1]
        y = min(abs(r * 2104 + g * 4130 + b * 802 + 4096 + 131072) >> 13, 235)
        y = (y - 16.0) / (235 - 16)
        # 忽略高亮色
        if y > 0.9:
            continue
        # Calculate the score, preferring highly saturated colors.
        # Add 0.1 to the saturation so we don't completely ignore grayscale
        # colors by multiplying the count by zero, but still give them a low
        # weight.
        score = (saturation + 0.1) * count
        if score > max_score:
            max_score = score
            dominant_color = (r, g, b)
    (r, g, b) = dominant_color
    hex_r = hex(r)[2:].upper()
    hex_g = hex(g)[2:].upper()
    hex_b = hex(b)[2:].upper()
    hex_r0 = hex_r.zfill(2)
    hex_g0 = hex_g.zfill(2)
    hex_b0 = hex_b.zfill(2)
    return hex_r0 + hex_g0 + hex_b0


async def spider():
    book_urls = []
    async with aiohttp.ClientSession() as session:
        for category in urls:
            book_urls.extend(await analysis_category(category, session))
        tasks = [asyncio.ensure_future(analysis_book(category, url, session)) for category, url in book_urls]
        books = await asyncio.gather(*tasks)
    books = [book for book in books if book is not None]
    with open('books.json', 'w', encoding='utf-8') as f:
        json.dump(books, f, ensure_ascii=False, indent=4)


def handel_color_single(book):
    try:
        data = requests.get(url=book['cover']).content
        color = get_dominant_color(data)
        book['color'] = color
        print(book['title'], book['color'])
        return book
    except:
        print(book['title'], 'failed')
        return None


def handle_color():
    with open('books.json', 'r', encoding='utf-8') as f:
        books = json.load(f)
    results = []
    pool = Pool(processes=12)
    for book in books:
        results.append(pool.apply_async(handel_color_single, args=(book,)))
    pool.close()
    pool.join()
    res = [i.get() for i in results]
    with open('books2.json', 'w', encoding='utf-8') as f:
        json.dump(res, f, ensure_ascii=False, indent=4)


def gen_code():
    codes = []
    with open('books2.json', 'r', encoding='utf-8') as f:
        books = json.load(f)
    for book in [book for book in books if book is not None]:
        title = book['title']
        author = book['author']
        author = author.replace('【', '[').replace('】', ']')\
            .replace('（', '(').replace('）', ')')\
            .replace('［', '[').replace('］', ']')\
            .replace('] ', ']')
        isbn = book['isbn']
        cover = book['cover']
        description = book['description']
        description = description.replace('\n', '\\n')
        price = book['price']
        score = book['score']
        category = book['category']
        color = book['color']
        codes.append(
            f'book = Book(null, "{title}", "{author}", "{isbn}", "{cover}", '
            f'"{color}", {"%.1f" % score}, "{description}", '
            f'{category}, {"%.2f" % price}, date, date)'
        )
        codes.append(f"bookService.insert(book)")
    with open('code.txt', 'w', encoding='utf-8') as f:
        f.write('\n'.join(codes))


if __name__ == "__main__":
    # asyncio.run(spider())
    # handle_color()
    gen_code()
