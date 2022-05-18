package com.nagico.bookstore.services

import android.content.Context
import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.dao.UserDao
import com.nagico.bookstore.models.User
import com.nagico.bookstore.services.exception.account.SignInError
import com.nagico.bookstore.services.exception.account.SignUpError
import com.nagico.bookstore.utils.EncryptionUtil
import java.util.*

class AccountService private constructor(){
    private val dao: UserDao = DBManager.instance.daoSession.userDao
    private val salt = "salt\$a123sdj/?salt"

    companion object {
        val instance : AccountService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AccountService()
        }
    }

    fun getList(): List<User> {
        return dao.loadAll()
    }

    fun signIn(username: String, password: String): User {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique() ?: throw SignInError("username", "用户名不存在")
        if (user.password != EncryptionUtil.getEncryptedPassword(password, salt)) throw SignInError("password", "密码错误")

        return user
    }

    fun signUp(username: String, password: String): User {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique()
        if (user != null) throw SignUpError("username", "用户名已存在")
        val newUser = User(null, username, EncryptionUtil.getEncryptedPassword(password, salt), Date(), Date())

        dao.insert(newUser)
        return newUser
    }

    fun checkUsernameAvailability(username: String): Boolean {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique()
        return user == null
    }

    fun getDefaultUsername(context: Context): String {
        val mSharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return mSharedPreferences.getString("default_username", "") ?: ""
    }

    fun setDefaultUsername(context: Context, username: String) {
        val mSharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        mSharedPreferences.edit().putString("default_username", username).apply()
    }

}