package com.nagico.bookstore.fragments.main

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentMainBinding
import java.lang.ref.WeakReference


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = mBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mBinding.bottomNavigation.setupWithNavController(activity!!.findNavController(R.id.main_host_fragment))
        val navController = activity!!.findNavController(R.id.main_host_fragment)
        mBinding.bottomNavigation.setOnItemSelectedListener {
            mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            NavigationUI.onNavDestinationSelected(it, navController)
        }
    }
}