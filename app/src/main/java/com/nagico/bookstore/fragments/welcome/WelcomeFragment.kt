package com.nagico.bookstore.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nagico.bookstore.databinding.FragmentWelcomeBinding
import com.nagico.bookstore.utils.DataUtil

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = mBinding.root

        mBinding.btnNavSignIn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()
            it.findNavController().navigate(action)
        }

        mBinding.btnNavSignUp.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }

        mBinding.txtInitDb.setOnClickListener {
            MaterialAlertDialogBuilder(context!!)
                .setTitle("初始化数据库")
                .setMessage("初始化后，数据库将被清空，请谨慎操作！\n初始用户名: co\n初始密码: 123")
                .setNegativeButton("取消") { dialog, which ->

                }
                .setPositiveButton("确认") { dialog, which ->
                    DataUtil.init()
                    Toast.makeText(context, "初始化成功", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}