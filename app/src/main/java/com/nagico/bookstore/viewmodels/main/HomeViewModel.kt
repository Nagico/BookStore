package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        mBinding.homeRecyclerContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager!!.findViewByPosition(0) == null) {
                    mBinding.floatingActionButton.visibility = View.VISIBLE
                } else {
                    mBinding.floatingActionButton.visibility = View.GONE
                }
            }
        })
    }

    val toTop = View.OnClickListener {
        mBinding.homeRecyclerContainer.smoothScrollToPosition(0)
    }

}