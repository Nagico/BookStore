package com.nagico.bookstore.viewmodels.welcome

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentSignUpBinding
import com.nagico.bookstore.fragments.welcome.SignInFragmentDirections
import com.nagico.bookstore.services.AccountService
import com.nagico.bookstore.services.exception.account.SignUpError

class SignUpViewModel : ViewModel() {
    private val mAccountService = AccountService.instance
    private lateinit var mBinding: FragmentSignUpBinding

    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val passwordConfirm: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun init(binding: FragmentSignUpBinding) {
        mBinding = binding

        mBinding.etxSignUpUsername.onFocusChangeListener = clearErrorMsg
        mBinding.etxSignUpPassword.onFocusChangeListener = clearErrorMsg
        mBinding.etxSignUpPasswordConfirm.onFocusChangeListener = clearErrorMsg

        username.observe(mBinding.lifecycleOwner!!, checkUsername)
    }

    val navSignIn = View.OnClickListener {
        val action = SignInFragmentDirections.actionGlobalSignInFragment()
        it.findNavController().navigate(action)
    }

    val signUp = View.OnClickListener {
        try {
            if (username.value.isNullOrEmpty()) throw SignUpError("username", "用户名不能为空")
            if (password.value.isNullOrEmpty()) throw SignUpError("password", "密码不能为空")
            if (password.value != passwordConfirm.value) throw SignUpError("password_confirm", "两次输入的密码不一致")

            val user = mAccountService.signUp(username.value!!, password.value!!)
            mAccountService.setDefaultUsername(mBinding.root.context, username.value!!)
            Snackbar.make(it, "注册成功", Snackbar.LENGTH_SHORT).show()
        } catch (e: SignUpError) {
            when (e.code) {
                "username" -> {
                    mBinding.etxSignUpUsernameLayout.error = e.message
                    mBinding.etxSignUpUsername.clearFocus()
                }
                "password" -> {
                    mBinding.etxSignUpPasswordLayout.error = e.message
                    mBinding.etxSignUpPassword.clearFocus()
                }
                "password_confirm" -> {
                    mBinding.etxSignUpPasswordConfirmLayout.error = e.message
                    mBinding.etxSignUpPasswordConfirm.clearFocus()
                }
                else -> {
                    Toast.makeText(mBinding.root.context, "登录失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val clearErrorMsg = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            when (v.id) {
                mBinding.etxSignUpUsername.id -> {
                    mBinding.etxSignUpUsernameLayout.error = null
                }
                mBinding.etxSignUpPassword.id -> {
                    mBinding.etxSignUpPasswordLayout.error = null
                }
                mBinding.etxSignUpPasswordConfirm.id -> {
                    mBinding.etxSignUpPasswordConfirmLayout.error = null
                }
            }
        }
    }

    private val checkUsername = Observer<String> {
        if (it.isNullOrEmpty()) {
            mBinding.etxSignUpUsernameLayout.helperText = ""
            return@Observer
        }

        if (!mAccountService.checkUsernameAvailability(it)) {
            mBinding.etxSignUpUsernameLayout.helperText = "用户名已存在"
        } else {
            mBinding.etxSignUpUsernameLayout.helperText = ""
        }
    }

}