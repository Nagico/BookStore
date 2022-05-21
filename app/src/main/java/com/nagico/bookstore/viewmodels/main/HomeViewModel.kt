package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
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
import com.nagico.bookstore.models.entity.User
import com.nagico.bookstore.services.BookService
import com.nagico.bookstore.services.BookStoreService


class HomeViewModel : ViewModel() {
    private lateinit var mBinding: FragmentHomeBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mBookService: BookService

    val user by lazy {
        MutableLiveData<User>()
    }

    fun init(binding: FragmentHomeBinding, activity: FragmentActivity){
        mBinding = binding
        mActivity = activity
        user.value = BookStoreService.instance.getUser(mActivity)!!
        mBookService = BookService.instance

        initRecyclerView()

        mBinding.searchText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (mBinding.searchText.text.isNullOrEmpty()) {
                   Toast.makeText(mActivity, "请输入搜索关键词", Toast.LENGTH_SHORT).show()
                   return@setOnEditorActionListener false
                }
                mBinding.searchText.clearFocus()
                val action = HomeFragmentDirections.actionPageHomeToSearchFragment(mBinding.searchText.text.toString())
                mBinding.root.findNavController().navigate(action)
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener false
        }

    }

    private fun initRecyclerView(){
        mBinding.homeRecyclerContainer.linear().setup {
            addType<BookInfoModel>(R.layout.unit_book)
            addType<BookInfoHoverHeaderModel>(R.layout.unit_book_hover_header)
            models = mBookService.getBookInfoListWithCategory()

            onClick(R.id.book_info_layout) {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val action = HomeFragmentDirections.actionPageHomeToBookDetailFragment((getModel() as BookInfoModel).id)
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
                    mBinding.floatingActionButton.visibility = View.INVISIBLE
                }
            }
        })
    }

    val toTop = View.OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CONFIRM, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        mBinding.homeRecyclerContainer.smoothScrollToPosition(0)
    }

}