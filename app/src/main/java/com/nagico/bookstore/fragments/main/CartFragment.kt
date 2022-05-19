package com.nagico.bookstore.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentCartBinding
import com.nagico.bookstore.databinding.FragmentHomeBinding
import com.nagico.bookstore.viewmodels.main.CartViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        mBinding.lifecycleOwner = this
        mBinding.cartViewModel = mViewModel
        mViewModel.init(mBinding, activity!!)

        return mBinding.root
    }
}