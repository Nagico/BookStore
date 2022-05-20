package com.nagico.bookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.drake.brv.utils.BRV
import com.google.android.material.textview.MaterialTextView
import com.nagico.bookstore.utils.StatusBarUtil
import com.nagico.bookstore.viewmodels.BookstoreViewModel
import com.nagico.bookstore.views.dialogs.PayDialog
import com.nagico.bookstore.views.groups.MultiLineRadioGroup

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