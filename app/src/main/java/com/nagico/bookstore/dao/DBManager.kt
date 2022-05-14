package com.nagico.bookstore.dao


import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DBManager private constructor() {
    private lateinit var mHelper: DaoMaster.DevOpenHelper
    private lateinit var mDaoMaster: DaoMaster
    lateinit var daoSession: DaoSession

    companion object{
        const val DB_NAME = "bookstore.db"
        val instance : DBManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            DBManager()
        }
    }

    fun init(context: Context) {
        mHelper = DaoMaster.DevOpenHelper(context, DB_NAME, null)
        mDaoMaster = DaoMaster(getWritableDatabase())
        daoSession = mDaoMaster.newSession()
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