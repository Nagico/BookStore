package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSignUpBinding

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.txtNavSignIn.setOnClickListener {
            val action = SignUpFragmentDirections.actionGlobalSignInFragment()
            it.findNavController().navigate(action)
        }

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
        }


        return view
    }

}