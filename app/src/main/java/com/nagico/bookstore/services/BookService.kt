package com.nagico.bookstore.services

import com.nagico.bookstore.dao.DBManager
import com.nagico.bookstore.models.Book

class BookService {
    private val bookDao = DBManager.instance.daoSession.bookDao

    companion object {
        val instance : BookService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BookService()
        }
    }

    fun getAllBooks() : List<Book> {
        return bookDao.loadAll()
    }

    fun getBookById(id : Long) : Book? {
        return bookDao.load(id)
    }

    fun insertBook(book : Book) : Long {
        return bookDao.insert(book)
    }

    fun updateBook(book : Book) {
        bookDao.update(book)
    }

    fun deleteBook(book : Book) {
        bookDao.delete(book)
    }
}