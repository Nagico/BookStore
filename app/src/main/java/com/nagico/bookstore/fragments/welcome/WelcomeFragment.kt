package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nagico.bookstore.databinding.FragmentWelcomeBinding

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.btnNavSignIn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()
            it.findNavController().navigate(action)
        }

        mBinding.btnNavSignUp.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}