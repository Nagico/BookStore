package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.models.Order

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

}