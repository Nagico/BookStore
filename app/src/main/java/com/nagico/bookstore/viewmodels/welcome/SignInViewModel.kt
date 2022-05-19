package com.nagico.bookstore.viewmodels.welcome

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nagico.bookstore.databinding.FragmentSignInBinding
import com.nagico.bookstore.fragments.welcome.SignInFragmentDirections
import com.nagico.bookstore.services.AccountService
import com.nagico.bookstore.services.BookStoreService
import com.nagico.bookstore.services.exception.account.SignInError
import com.nagico.bookstore.viewmodels.BookstoreViewModel

class SignInViewModel : ViewModel(){
    private val mAccountService = AccountService.instance
    private lateinit var mBinding: FragmentSignInBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity

    val username by lazy {
        MutableLiveData<String>()
    }
    val password by lazy {
        MutableLiveData<String>()
    }

    fun init(binding: FragmentSignInBinding, activity: FragmentActivity){
        mBinding = binding
        mActivity = activity
        username.postValue(mAccountService.getDefaultUsername(mBinding.root.context))
        mBinding.etxUsername.onFocusChangeListener = clearErrorMsg
        mBinding.etxSignInPassword.onFocusChangeListener = clearErrorMsg

    }

    val txtNavSignUpOnClickListener = View.OnClickListener {
        it.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        it.findNavController().navigate(action)
    }

    val signIn = View.OnClickListener {
        try {
            if (username.value.isNullOrEmpty()) throw SignInError("username", "用户名不能为空")
            if (password.value.isNullOrEmpty())  throw SignInError("password", "密码不能为空")

            val user = mAccountService.signIn(username.value!!, password.value!!)
            BookStoreService.instance.setUser(mActivity, user)
            mAccountService.setDefaultUsername(mBinding.root.context, username.value!!)
            it.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment())
        }
        catch (e: SignInError) {
            it.performHapticFeedback(HapticFeedbackConstants.REJECT, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
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