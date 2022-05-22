package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.nagico.bookstore.databinding.FragmentBookDetailBinding
import com.nagico.bookstore.models.BookModel
import com.nagico.bookstore.models.entity.Book
import com.nagico.bookstore.services.BookService
import com.nagico.bookstore.services.BookStoreService
import com.nagico.bookstore.services.CartService

class BookDetailViewModel : ViewModel() {
    private lateinit var mBinding: FragmentBookDetailBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mBookService: BookService
    private lateinit var mCartService: CartService
    private lateinit var mBookStoreService: BookStoreService

    val book by lazy {
        MutableLiveData<BookModel>()
    }

    fun init(binding: FragmentBookDetailBinding, activity: FragmentActivity, bookId: Long){
        mBinding = binding
        mActivity = activity

        mBookService = BookService.instance
        mCartService = CartService.instance
        mBookStoreService = BookStoreService.instance
        book.value = mBookService.getBookDetail(bookId)
    }

    fun addCart() = OnClickListener {
        mCartService.addToCart(mBookStoreService.getUser(mActivity)!!.id, book.value!!.id)
        it.performHapticFeedback(HapticFeedbackConstants.CONFIRM, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        Toast.makeText(mActivity, "成功加入购物车", Toast.LENGTH_SHORT).show()
    }

    fun back() = OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        mBinding.root.findNavController().navigateUp()
    }
}