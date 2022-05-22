package com.nagico.bookstore.models

import java.util.*


class BookModel(
    var id: Long = 0,
    var title: String = "",
    var author: String = "",
    var isbn: String = "",
    var cover: String = "",
    var color: String = "",
    var score: Double = 0.0,
    var description: String = "",
    var categoryId: Long = 0,
    var price: Double = 0.0,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
) {
}