package com.nagico.bookstore.models

import java.util.*

class OrderInfoModel(
    var id: Long = 0L,
    var cover1: String = "",
    var cover2: String = "",
    var status: Int = 0,
    var totalPrice: Double = 0.0,
    var totalQuantity: Int = 0,
    val size: Int = 0,
    var orderDate: Date? = null,
) {
    val statusString: String
        get() = when (status) {
            1 -> "待付款"
            2 -> "待收货"
            3 -> "已完成"
            4 -> "已取消"
            else -> "未知"
        }

    val orderDateString: String
        get() = orderDate?.let {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            sdf.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
            sdf.format(it)
        } ?: "未知"
}