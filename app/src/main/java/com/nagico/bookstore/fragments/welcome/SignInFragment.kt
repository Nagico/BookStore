package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.nagico.bookstore.R

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private lateinit var mTxtNavSignUp: TextView
    private lateinit var mBtnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        mTxtNavSignUp = view.findViewById(R.id.txt_nav_sign_up)
        mBtnSignIn = view.findViewById(R.id.btn_sign_in)

        mTxtNavSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_signUpFragment)
        }

        return view
    }
}