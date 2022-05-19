package com.nagico.bookstore.models

import android.view.View

class CartInfoModel(
    var id: Long,
    var bookId: Long,
    var title: String,
    var price: Double,
    var quantity: Int,
    var cover: String,
    var checked: Boolean
) {
    val priceDisplay: String
        get() = "￥${String.format("%.2f", price)}"
}