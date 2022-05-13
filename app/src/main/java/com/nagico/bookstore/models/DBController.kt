package com.nagico.bookstore.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.nagico.bookstore.models.dao.*

class DBController(context: Context) {
    private val dbName = "bookstore.db"
    private val mContext = context
    private val mHelper = DaoMaster.DevOpenHelper(mContext, dbName, null)
    private val mDaoMaster = DaoMaster(getWritableDatabase())
    private val mDaoSession: DaoSession = mDaoMaster.newSession()

    val userDao: UserDao = mDaoSession.userDao
    val bookDao: BookDao = mDaoSession.bookDao
    val orderDao: OrderDao = mDaoSession.orderDao
    val orderItemDao: OrderItemDao = mDaoSession.orderItemDao
    val categoryDao: CategoryDao = mDaoSession.categoryDao

    companion object {
        private var instance: DBController? = null

        /**
         * 获取单例
         *
         * @param context 上下文
         * @return DBController
         */
        fun getInstance(context: Context): DBController {
            if (instance == null) {
                synchronized(DBController::class.java) {
                    if (instance == null) {
                        instance = DBController(context)
                    }
                }
            }
            return instance as DBController
        }
    }



    /**
     * 获取可读数据库
     */
    private fun getReadableDatabase(): SQLiteDatabase {
        return mHelper.readableDatabase
    }

    /**
     * 获取可写数据库
     */
    private fun getWritableDatabase(): SQLiteDatabase {
        return mHelper.writableDatabase
    }

}