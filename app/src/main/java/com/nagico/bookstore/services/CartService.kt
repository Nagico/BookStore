package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.dao.OrderItemDao
import com.nagico.bookstore.models.CartInfoModel
import com.nagico.bookstore.models.entity.Order
import com.nagico.bookstore.models.entity.OrderItem
import java.util.Date

class CartService private constructor(){
    private val userDao = DBManager.instance.daoSession.userDao
    private val orderDao = DBManager.instance.daoSession.orderDao
    private val orderItemDao = DBManager.instance.daoSession.orderItemDao

    companion object {
        val instance : CartService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CartService()
        }
    }

    private fun convertOrderItemToCartInfoModel(orderItem: OrderItem): CartInfoModel {
        val book = orderItem.book
        return CartInfoModel(
            id = orderItem.id,
            bookId = book.id,
            title = book.title,
            price = book.price,
            quantity = orderItem.quantity,
            cover = book.cover,
            checked = false
        )
    }

    fun getCartList(userId: Long): List<CartInfoModel> {
        val cart = orderItemDao.queryBuilder()
            .where(OrderItemDao.Properties.CartId.eq(userId))
            .where(OrderItemDao.Properties.OrderId.isNull)
            .orderDesc(OrderItemDao.Properties.UpdatedAt)
            .list()
        return cart.map { convertOrderItemToCartInfoModel(it) }
    }

    fun addToCart(userId: Long, bookId: Long) {
        val user = userDao.load(userId)
        val orderItem = orderItemDao.queryBuilder()
            .where(OrderItemDao.Properties.BookId.eq(bookId))
            .where(OrderItemDao.Properties.CartId.eq(userId))
            .unique()
        if (orderItem != null) {
            orderItem.quantity += 1
            orderItem.updatedAt = Date()
            orderItemDao.update(orderItem)
        } else {
            val newItem = OrderItem(
                null, null, userId, bookId, 1, 0.0, Date(), Date()
            )
            orderItemDao.insert(newItem)
            user.cart.add(newItem)
            userDao.update(user)
        }
    }

    fun removeFromCart(userId: Long, orderItemId: Long) {
        val user = userDao.load(userId)
        val orderItem = orderItemDao.load(orderItemId)
        orderItemDao.delete(orderItem)
        user.cart.remove(orderItem)
        userDao.update(user)
    }

    fun updateQuantity(userId: Long, orderItemId: Long, quantity: Int) {
        val user = userDao.load(userId)
        val orderItem = orderItemDao.load(orderItemId)
        orderItem.quantity = quantity
        orderItem.updatedAt = Date()
        user.cart.remove(orderItem)
        user.cart.add(orderItem)
        userDao.update(user)
    }

    fun checkout(userId: Long, itemIds: List<Long>): Long {
        val order = Order()
        val user = userDao.load(userId)
        order.userId = userId
        order.status = Order.Status.UNPAID.ordinal
        order.createdAt = Date()
        order.updatedAt = Date()
        orderDao.insert(order)
        itemIds.forEach {
            val orderItem = orderItemDao.load(it)
            val book = orderItem.book
            val newItem = OrderItem(
                null, order.id, null, orderItem.bookId, orderItem.quantity,
                book.price, orderItem.createdAt, orderItem.updatedAt
            )
            user.cart.remove(orderItem)
            orderItemDao.delete(orderItem)
            orderItemDao.insert(newItem)
            order.orderItems.add(newItem)
        }
        orderDao.update(order)
        userDao.update(user)

        return order.id
    }

}