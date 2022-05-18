package com.nagico.bookstore.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nagico.bookstore.databinding.FragmentOrderBinding


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}