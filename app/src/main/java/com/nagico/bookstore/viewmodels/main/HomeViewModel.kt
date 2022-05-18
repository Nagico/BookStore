package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nagico.bookstore.databinding.FragmentHomeBinding
import com.nagico.bookstore.databinding.FragmentSignInBinding
import com.nagico.bookstore.models.BookInfoHoverHeaderModel
import com.nagico.bookstore.models.BookInfoModel
import com.nagico.bookstore.models.User
import com.nagico.bookstore.services.BookStoreService

class HomeViewModel : ViewModel() {
    private lateinit var mBinding: FragmentHomeBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity

    val bookInfos by lazy {
        MutableLiveData<ArrayList<BookInfoModel>>()
    }
    val user by lazy {
        MutableLiveData<User>()
    }

    fun init(binding: FragmentHomeBinding, activity: FragmentActivity){
        mBinding = binding
        mActivity = activity
        user.value = BookStoreService.instance.getUser(mActivity)!!

    }

    fun mock(pageIndex: Int, pageSize: Int): ArrayList<Any> {
        val data = ArrayList<Any>()
        val bar = BookInfoHoverHeaderModel()
        bar.title = "小说"
        bar.itemHover = true
        data.add(bar)
        val book1 = BookInfoModel()
        book1.title = "美丽之味"
        book1.author = "埃里克·侯麦"
        book1.price = "68.00"
        book1.score = "8.4"
        book1.cover = "https://img1.doubanio.com/view/subject/l/public/s34152667.jpg"
        book1.color = "#1f473E3D"
        data.add(book1)
        val book2 = BookInfoModel()
        book2.title = "我和妈妈的最后一年"
        book2.author = "川村元气"
        book2.price = "39.80"
        book2.score = "8.3"
        book2.cover = "https://img9.doubanio.com/view/subject/l/public/s34218104.jpg"
        book2.color = "#1fE4CEA5"
        data.add(book2)

        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(bar)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(bar)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)
        data.add(book1)
        data.add(book2)

        return data
    }

}