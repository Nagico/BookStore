package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nagico.bookstore.R

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    private lateinit var mBtnNavSignIn: Button
    private lateinit var mBtnNavSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_welcome, container, false)
        mBtnNavSignIn = view.findViewById(R.id.btn_nav_sign_in)
        mBtnNavSignUp = view.findViewById(R.id.btn_nav_sign_up)

        mBtnNavSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_signInFragment)
        }

        mBtnNavSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_signUpFragment)
        }

        return view
    }
}