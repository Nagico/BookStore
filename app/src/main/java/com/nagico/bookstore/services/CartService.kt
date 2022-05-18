package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.models.OrderItem
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

    fun getCart(userId: Long): List<OrderItem> {
        val user = userDao.load(userId)
        return user.cart
    }

    fun addToCart(userId: Long, bookId: Long, quantity: Int) {
        val user = userDao.load(userId)
        val book = DBManager.instance.daoSession.bookDao.load(bookId)
        val orderItem = OrderItem(
            null, null, userId, bookId, quantity, 0.0, Date(), Date()
        )
        user.cart.add(orderItem)
        userDao.update(user)
    }

    fun removeFromCart(userId: Long, orderItemId: Long) {
        val user = userDao.load(userId)
        val orderItem = orderItemDao.load(orderItemId)
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

}