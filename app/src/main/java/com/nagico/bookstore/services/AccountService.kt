package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.dao.UserDao
import com.nagico.bookstore.models.User
import com.nagico.bookstore.services.exception.account.SignInError
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

    fun SignIn(username: String, password: String): User {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique() ?: throw SignInError("用户名不存在")
        if (user.password != EncryptionUtil.getEncryptedPassword(password, salt)) throw SignInError("密码错误")
        return user
    }

    fun SignUp(username: String, password: String): User {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique()
        if (user != null) throw SignInError("用户名已存在")
        val newUser = User(null, username, EncryptionUtil.getEncryptedPassword(password, salt), Date(), Date())
        dao.insert(newUser)
        return newUser
    }

    fun CheckUsernameAvailability(username: String): Boolean {
        val user = dao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique()
        return user == null
    }
}