package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentHomeBinding
import com.nagico.bookstore.fragments.main.HomeFragmentDirections
import com.nagico.bookstore.models.BookInfoHoverHeaderModel
import com.nagico.bookstore.models.BookInfoModel
import com.nagico.bookstore.models.User
import com.nagico.bookstore.services.BookService
import com.nagico.bookstore.services.BookStoreService

class HomeViewModel : ViewModel() {
    private lateinit var mBinding: FragmentHomeBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mBookService: BookService

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
        mBookService = BookService.instance

        initRecyclerView()

    }

    private fun initRecyclerView(){
        mBinding.homeRecyclerContainer.linear().setup {
            addType<BookInfoModel>(R.layout.unit_book)
            addType<BookInfoHoverHeaderModel>(R.layout.unit_book_hover_header)
            models = mBookService.getBookInfoListWithCategory()

            onClick(R.id.book_info_layout) {
                val action = HomeFragmentDirections.actionPageHomeToBookDetailFragment()
                mBinding.root.findNavController().navigate(action)
            }

            onHoverAttachListener = object : OnHoverAttachListener {
                // 黏住顶部的时候, v表示指定悬停的itemView对象
                override fun attachHover(v: View) {
                    ViewCompat.setElevation(v, 10F)
                }

                // 从顶部分离的时候
                override fun detachHover(v: View) {
                    ViewCompat.setElevation(v, 0F)
                }
            }

        }

        val layoutManager = HoverGridLayoutManager(mActivity, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(mBinding.homeRecyclerContainer.bindingAdapter.isHover(position)) 2 else 1
            }
        }
        mBinding.homeRecyclerContainer.layoutManager = layoutManager
    }

    private fun mock(): ArrayList<Any> {
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