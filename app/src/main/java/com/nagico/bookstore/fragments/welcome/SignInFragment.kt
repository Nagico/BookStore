package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSignInBinding

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.txtNavSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionGlobalSignUpFragment()
            it.findNavController().navigate(action)
        }

        binding.btnSignIn.setOnClickListener {
            Toast.makeText(context, "Sign in", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}