package com.nagico.bookstore.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentHomeBinding
import com.nagico.bookstore.models.BookInfoHoverHeaderModel
import com.nagico.bookstore.models.BookInfoModel
import com.nagico.bookstore.viewmodels.main.HomeViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.lifecycleOwner = this
        mBinding.homeViewModel = mViewModel
        mViewModel.init(mBinding, activity!!)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.homeRecyclerContainer.linear().setup {
            addType<BookInfoModel>(R.layout.unit_book)
            addType<BookInfoHoverHeaderModel>(R.layout.unit_book_hover_header)
            models = mViewModel.mock(1,20)

            onClick(R.id.book_info_layout) {
                val action = HomeFragmentDirections.actionPageHomeToBookDetailFragment()
                findNavController().navigate(action)
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

        val layoutManager = HoverGridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(mBinding.homeRecyclerContainer.bindingAdapter.isHover(position)) 2 else 1 // 具体的业务逻辑由你确定
            }
        }
        mBinding.homeRecyclerContainer.layoutManager = layoutManager

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}