package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSearchBinding
import com.nagico.bookstore.fragments.main.SearchFragmentDirections
import com.nagico.bookstore.models.BookInfoModel
import com.nagico.bookstore.services.BookService

class SearchViewModel : ViewModel()  {
   private lateinit var mBinding: FragmentSearchBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mBookService: BookService

    val query by lazy {
        MutableLiveData<String>()
    }

    fun init(binding: FragmentSearchBinding, activity: FragmentActivity, query: String) {
        mBinding = binding
        mActivity = activity

        mBookService = BookService.instance
        this.query.value = query

        initRecyclerView()

    }

    private fun initRecyclerView(){
        mBinding.searchRecyclerContainer.linear().setup {
            addType<BookInfoModel>(R.layout.unit_book)

            onClick(R.id.book_info_layout) {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val action = SearchFragmentDirections.actionSearchFragmentToBookDetailFragment((getModel() as BookInfoModel).id)
                mBinding.root.findNavController().navigate(action)
            }
        }

        val layoutManager = HoverGridLayoutManager(mActivity, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(mBinding.searchRecyclerContainer.bindingAdapter.isHover(position)) 2 else 1
            }
        }

        mBinding.searchRecyclerContainer.layoutManager = layoutManager

        mBinding.searchRecyclerContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager!!.findViewByPosition(0) == null) {
                    mBinding.floatingActionButton.visibility = View.VISIBLE
                } else {
                    mBinding.floatingActionButton.visibility = View.INVISIBLE
                }
            }
        })

        getData()
    }

    val toTop = View.OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CONFIRM, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        mBinding.searchRecyclerContainer.smoothScrollToPosition(0)
    }

    val back = View.OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        mBinding.root.findNavController().navigateUp()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        val res = mBookService.search(query.value!!)
        if (res.isEmpty())
            mBinding.state.showEmpty()
        else
            mBinding.state.showContent()
        mBinding.searchRecyclerContainer.models = res
        mBinding.searchRecyclerContainer.adapter?.notifyDataSetChanged()
    }
}