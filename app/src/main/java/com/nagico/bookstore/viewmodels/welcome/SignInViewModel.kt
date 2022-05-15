package com.nagico.bookstore.viewmodels.welcome

import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nagico.bookstore.databinding.FragmentSignInBinding
import com.nagico.bookstore.fragments.welcome.SignInFragmentDirections
import com.nagico.bookstore.services.AccountService
import com.nagico.bookstore.services.exception.account.SignInError

class SignInViewModel : ViewModel(){
    private val mAccountService = AccountService.instance
    private lateinit var mBinding: FragmentSignInBinding

    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun init(binding: FragmentSignInBinding) {
        mBinding = binding
        username.postValue(mAccountService.getDefaultUsername(mBinding.root.context))

        mBinding.etxUsername.onFocusChangeListener = clearErrorMsg
        mBinding.etxSignInPassword.onFocusChangeListener = clearErrorMsg

    }

    val txtNavSignUpOnClickListener = View.OnClickListener {
        val action = SignInFragmentDirections.actionGlobalSignUpFragment()
        it.findNavController().navigate(action)
    }

    val signIn = View.OnClickListener {
        try {
            if (username.value.isNullOrEmpty()) throw SignInError("username", "用户名不能为空")
            if (password.value.isNullOrEmpty())  throw SignInError("password", "密码不能为空")

            val user = mAccountService.signIn(username.value!!, password.value!!)
            mAccountService.setDefaultUsername(mBinding.root.context, username.value!!)
            Snackbar.make(it, "登录成功", Snackbar.LENGTH_SHORT).show()
        }
        catch (e: SignInError) {
            when (e.code) {
                "username" -> {
                    mBinding.etxUsernameLayout.error = e.message
                    mBinding.etxUsername.clearFocus()
                }
                "password" -> {
                    mBinding.etxSignInPasswordLayout.error = e.message
                    mBinding.etxSignInPassword.clearFocus()
                }
                else -> {
                    Toast.makeText(mBinding.root.context, "登录失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val clearErrorMsg = OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            when (v.id) {
                mBinding.etxUsername.id -> {
                    mBinding.etxUsernameLayout.error = null
                }
                mBinding.etxSignInPassword.id -> {
                    mBinding.etxSignInPasswordLayout.error = null
                }
            }
        }
    }

}