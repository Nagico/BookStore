package com.nagico.bookstore.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSearchBinding
import com.nagico.bookstore.viewmodels.main.SearchViewModel

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: SearchViewModel by viewModels()
    private lateinit var bar: BottomNavigationView
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        bar = activity?.findViewById(R.id.bottom_navigation)!!
        bar.visibility = View.GONE

        mBinding.lifecycleOwner = this
        mBinding.searchViewModel = mViewModel
        mViewModel.init(mBinding, activity!!, args.query)

        return mBinding.root
    }
    override fun onStop() {
        bar.visibility = View.VISIBLE
        super.onStop()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}