package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.dao.OrderDao
import com.nagico.bookstore.dao.OrderItemDao
import com.nagico.bookstore.models.entity.Order
import com.nagico.bookstore.models.OrderDetailModel
import com.nagico.bookstore.models.OrderInfoModel
import com.nagico.bookstore.models.entity.OrderItem
import java.util.*

class OrderService private constructor(){
    private val orderDao = DBManager.instance.daoSession.orderDao
    private val orderItemDao = DBManager.instance.daoSession.orderItemDao

    companion object {
        val instance : OrderService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OrderService()
        }
    }

    fun getAllOrders() : List<Order> {
        return orderDao.loadAll()
    }

    fun getOrderById(id : Long) : Order? {
        return orderDao.load(id)
    }

    fun createOrder(order : Order) : Long {
        return orderDao.insert(order)
    }

    private fun convertOrderToOrderInfoModel(order : Order) : OrderInfoModel {
        val items = orderItemDao.queryBuilder().where(OrderItemDao.Properties.OrderId.eq(order.id)).list()
        val size = items.size
        val cover1 = items[0].book.cover
        val cover2 = if (size > 1) items[1].book.cover else ""
        val totalPrice = items.sumOf { it.price }
        val totalQuantity = items.sumOf { it.quantity }
        return OrderInfoModel(
            order.id,
            cover1,
            cover2,
            order.status,
            totalPrice,
            totalQuantity,
            size,
            order.createdAt
        )
    }

    fun getOrderList(userId: Long, type: Int): List<OrderInfoModel> {
        val result = if (type == 0) {
            orderDao.queryBuilder()
                .where(OrderDao.Properties.UserId.eq(userId))
                .orderDesc(OrderDao.Properties.UpdatedAt)
                .list()
        } else {
            orderDao.queryBuilder()
                .where(OrderDao.Properties.UserId.eq(userId))
                .where(OrderDao.Properties.Status.eq(type))
                .orderDesc(OrderDao.Properties.UpdatedAt)
                .list()
        }

        return result.map { convertOrderToOrderInfoModel(it) }
    }

    private fun convertOrderItemToOrderDetailModel(orderItem : OrderItem) : OrderDetailModel {
        return OrderDetailModel(
            orderItem.id,
            orderItem.book.id,
            orderItem.book.title,
            orderItem.book.author,
            orderItem.quantity,
            orderItem.price,
            orderItem.book.cover
        )
    }

    fun getOrderDetail(orderId : Long) : List<OrderDetailModel> {
        val items = orderItemDao.queryBuilder()
            .where(OrderItemDao.Properties.OrderId.eq(orderId))
            .orderDesc(OrderItemDao.Properties.UpdatedAt)
            .list()
        return items.map { convertOrderItemToOrderDetailModel(it) }
    }

    fun cancelOrder(orderId : Long) {
        val order = orderDao.load(orderId)
        order?.status = Order.Status.CANCELLED.ordinal
        orderDao.update(order)
    }

    fun payOrder(orderId : Long, paymentMethod: String) {
        val order = orderDao.load(orderId)!!
        val items = orderItemDao.queryBuilder()
            .where(OrderItemDao.Properties.OrderId.eq(orderId))
            .orderDesc(OrderItemDao.Properties.UpdatedAt)
            .list()
        order.status = Order.Status.DELIVERING.ordinal
        order.paymentMethod = paymentMethod
        order.paymentAmount = items.sumOf { it.price * it.quantity }
        order.paidAt = Date()
        order.updatedAt = Date()
        orderDao.update(order)
    }

    fun finishOrder(orderId : Long) {
        val order = orderDao.load(orderId)!!
        order.status = Order.Status.DELIVERED.ordinal
        order.updatedAt = Date()
        orderDao.update(order)
    }

}