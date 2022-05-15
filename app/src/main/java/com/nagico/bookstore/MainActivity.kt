package com.nagico.bookstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nagico.bookstore.viewmodels.BookstoreViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide action bar
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        ViewModelProvider(this).get(BookstoreViewModel::class.java).user = null
    }
}