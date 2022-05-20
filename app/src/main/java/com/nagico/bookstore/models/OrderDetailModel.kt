package com.nagico.bookstore.models

class OrderDetailModel(
    val id: Long = 0,
    val bookId: Long = 0,
    val title: String = "",
    val author: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0,
    val cover: String = ""
) {
    val totalPrice: Double
        get() = quantity * price
}