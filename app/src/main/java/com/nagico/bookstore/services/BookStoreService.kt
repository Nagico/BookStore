package com.nagico.bookstore.services

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.nagico.bookstore.models.entity.User
import com.nagico.bookstore.viewmodels.BookstoreViewModel

class BookStoreService private constructor(){
    companion object {
        val instance : BookStoreService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BookStoreService()
        }
    }

    fun getUser(activity: FragmentActivity) : User? {
        return ViewModelProvider(activity).get(BookstoreViewModel::class.java).user
    }

    fun setUser(activity: FragmentActivity, user: User) {
        ViewModelProvider(activity).get(BookstoreViewModel::class.java).user = user
    }
}