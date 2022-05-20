package com.nagico.bookstore.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nagico.bookstore.databinding.FragmentOrderBinding
import com.nagico.bookstore.viewmodels.main.OrderViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        mBinding.lifecycleOwner = this
        mBinding.orderViewModel = mViewModel
        mViewModel.init(mBinding, activity!!)

        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}