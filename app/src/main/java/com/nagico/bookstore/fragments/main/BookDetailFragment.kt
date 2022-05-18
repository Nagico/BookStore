package com.nagico.bookstore.fragments.main

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentBookDetailBinding
import com.nagico.bookstore.viewmodels.main.BookDetailViewModel


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: BookDetailViewModel by viewModels()
    private lateinit var bar: BottomNavigationView
    val args: BookDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)

        bar = activity?.findViewById(R.id.bottom_navigation)!!
        bar.visibility = View.GONE

        mBinding.lifecycleOwner = this
        mBinding.bookDetailViewModel = mViewModel
        mViewModel.init(mBinding, activity!!, args.bookId)

        return mBinding.root
    }

    override fun onStop() {
        super.onStop()

    }
    override fun onDestroyView() {
        bar.visibility = View.VISIBLE
        super.onDestroyView()
        _binding = null
    }


}