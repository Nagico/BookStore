package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSignInBinding
import com.nagico.bookstore.services.AccountService
import com.nagico.bookstore.viewmodels.welcome.SignInViewModel

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val mBinding get() = _binding!!
    private val mModelView: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        val view = mBinding.root

        mBinding.lifecycleOwner = this
        mBinding.signUpViewModel = mModelView
        mModelView.init(mBinding)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}