package com.nagico.bookstore

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.drake.brv.utils.BRV
import com.nagico.bookstore.utils.StatusBarUtil
import com.nagico.bookstore.viewmodels.BookstoreViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide action bar
        supportActionBar?.hide()

        StatusBarUtil.setWindowStatusBarColor(this, R.color.theme_bg_white)

        BRV.modelId = BR.m

        setContentView(R.layout.activity_main)
        ViewModelProvider(this).get(BookstoreViewModel::class.java).user = null
    }
}