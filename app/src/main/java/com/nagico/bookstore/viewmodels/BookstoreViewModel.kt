package com.nagico.bookstore.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nagico.bookstore.models.User

class BookstoreViewModel(application: Application) : AndroidViewModel(application) {
    var user: User?= null

}