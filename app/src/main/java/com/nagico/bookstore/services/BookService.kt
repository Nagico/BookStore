package com.nagico.bookstore.services

import com.nagico.bookstore.dao.BookDao
import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.models.entity.Book
import com.nagico.bookstore.models.BookInfoHoverHeaderModel
import com.nagico.bookstore.models.BookInfoModel
import com.nagico.bookstore.models.BookModel

class BookService private constructor(){
    private val bookDao = DBManager.instance.daoSession.bookDao
    private val categoryDao = DBManager.instance.daoSession.categoryDao

    companion object {
        val instance : BookService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BookService()
        }
    }

    fun getList() : List<Book> {
        return bookDao.loadAll()
    }

    fun get(id : Long) : Book? {
        return bookDao.load(id)
    }

    fun insert(book : Book) : Long {
        return bookDao.insert(book)
    }

    fun update(book : Book) {
        bookDao.update(book)
    }

    fun delete(book : Book) {
        bookDao.delete(book)
    }

    fun search(query : String) : List<BookInfoModel> {
        return bookDao.queryBuilder().whereOr(
                BookDao.Properties.Title.like("%$query%"),
                BookDao.Properties.Author.like("%$query%")
        ).build().list().map { convertBookToBookInfoModel(it) }
    }

    private fun convertBookToBookInfoModel(it: Book): BookInfoModel {
        val bookInfo = BookInfoModel()
        bookInfo.id = it.id
        bookInfo.title = it.title
        bookInfo.author = it.author
        bookInfo.price = String.format("%.2f",it.price)
        bookInfo.cover = it.cover
        bookInfo.score = String.format("%.1f",it.score)
        bookInfo.color = "#1F" + it.color
        return bookInfo
    }

    fun getBookInfoListWithCategory(): List<Any> {
        val result = mutableListOf<Any>()
        val categories = categoryDao.loadAll()
        categories.forEach { category ->
            val header = BookInfoHoverHeaderModel()
            header.title = category.name
            result.add(header)
            val books = bookDao.queryBuilder().where(BookDao.Properties.CategoryId.eq(category.id)).build().list()
            books.forEach {
                result.add(convertBookToBookInfoModel(it))
            }
        }
        return result
    }

    private fun covertBookToBookModel(it: Book): BookModel {
        return BookModel(
            it.id,
            it.title,
            it.author,
            it.isbn,
            it.cover,
            it.color,
            it.score,
            it.description,
            it.categoryId,
            it.price,
            it.createdAt,
            it.updatedAt
                )
    }

    fun getBookDetail(id : Long) : BookModel {
        return covertBookToBookModel(bookDao.load(id))
    }
}