package com.nagico.bookstore

import android.app.Application
import com.drake.statelayout.StateConfig
import com.nagico.bookstore.dao.DBManager


class BookstoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        DBManager.instance.init(applicationContext)

        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
        }
    }
}