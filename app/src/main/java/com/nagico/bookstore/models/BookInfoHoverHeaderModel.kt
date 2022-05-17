package com.nagico.bookstore.models

import com.drake.brv.item.ItemHover

class BookInfoHoverHeaderModel : ItemHover {
    // 返回值决定是否悬停
    override var itemHover: Boolean = true

    var title: String = ""
}