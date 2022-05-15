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
    private var _viewBinding: FragmentWelcomeBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = viewBinding.root

        viewBinding.btnNavSignIn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionGlobalSignInFragment()
            it.findNavController().navigate(action)
        }

        viewBinding.btnNavSignUp.setOnClickListener {
            val action = WelcomeFragmentDirections.actionGlobalSignUpFragment()
            it.findNavController().navigate(action)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}