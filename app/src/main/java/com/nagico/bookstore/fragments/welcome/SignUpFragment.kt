package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.nagico.bookstore.databinding.FragmentSignUpBinding
import com.nagico.bookstore.viewmodels.welcome.SignUpViewModel

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val mBinding get() = _binding!!
    private val mModelView: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.lifecycleOwner = this
        mBinding.signUpViewModel = mModelView
        mModelView.init(mBinding, activity!!)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}