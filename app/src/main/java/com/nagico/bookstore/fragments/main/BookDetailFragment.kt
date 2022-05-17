package com.nagico.bookstore.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentBookDetailBinding


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class BookDetailFragment : Fragment() {
    private var _binding: FragmentBookDetailBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var bar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)

        bar = activity?.findViewById(R.id.bottom_navigation)!!
        bar.visibility = View.GONE

        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bar.visibility = View.VISIBLE
    }


}