package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.models.entity.Category

class CategoryService private constructor(){
    private val categoryDao = DBManager.instance.daoSession.categoryDao

    companion object {
        val instance : CategoryService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CategoryService()
        }
    }

    fun getList(): List<Category> {
        return categoryDao.loadAll()
    }

    fun insert(category: Category) {
        categoryDao.insert(category)
    }
}