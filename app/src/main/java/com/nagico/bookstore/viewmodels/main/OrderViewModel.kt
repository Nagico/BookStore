package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentCartBinding
import com.nagico.bookstore.databinding.FragmentOrderBinding
import com.nagico.bookstore.databinding.UnitShoppingCartBinding
import com.nagico.bookstore.fragments.main.CartFragmentDirections
import com.nagico.bookstore.fragments.main.OrderFragmentDirections
import com.nagico.bookstore.models.CartInfoModel
import com.nagico.bookstore.models.Order
import com.nagico.bookstore.models.OrderInfoModel
import com.nagico.bookstore.models.User
import com.nagico.bookstore.services.BookStoreService
import com.nagico.bookstore.services.CartService
import com.nagico.bookstore.services.OrderService
import com.nagico.bookstore.views.dialogs.PayDialog
import com.nagico.bookstore.views.groups.MultiLineRadioGroup

class OrderViewModel : ViewModel()  {
    private lateinit var payDialog: PayDialog
    private lateinit var mBinding: FragmentOrderBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mOrderService: OrderService

    private lateinit var mUser: User

    val type by lazy {
        MutableLiveData<Int>()
    }

    fun init(binding: FragmentOrderBinding, activity: FragmentActivity){
        mBinding = binding
        mActivity = activity
        mUser = BookStoreService.instance.getUser(mActivity)!!
        mOrderService = OrderService.instance
        type.value = 0

        mBinding.orderTabLayout.addOnTabSelectedListener(
            object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                    type.value = tab?.position
                    mBinding.orderRecyclerContainer.models = mOrderService.getOrderList(mUser.id, type.value!!)
                    mBinding.orderRecyclerContainer.adapter?.notifyDataSetChanged()
                }
            }
        )

        initRecyclerView()
        initDialog()
    }

    private fun initDialog() {
        val payDialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_pay, null)
        val builder = PayDialog.Builder(mActivity)
        payDialog = builder
            .cancelTouchout(true)
            .cancelTouchout(true)
            .view(payDialogView)
            .widthpx(ViewGroup.LayoutParams.MATCH_PARENT)
            .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT)
            .style(R.style.AlertDialogStyle)
            .addViewOnclick(R.id.tv_cancel) { v ->
                Toast.makeText(mActivity, "取消支付", Toast.LENGTH_SHORT).show()
                payDialog.dismiss()
            }
            .addViewOnclick(R.id.tv_ok) { v ->
                val multiLineRadioGroup: MultiLineRadioGroup = payDialogView.findViewById(R.id.rg_pay_type)
                val checkedRadioButton: RadioButton =
                    payDialogView.findViewById(multiLineRadioGroup.checkedRadioButtonId)
                mOrderService.payOrder(payDialog.orderId!!, checkedRadioButton.text.toString())
                mOrderService.finishOrder(payDialog.orderId!!)
                Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show()
                mBinding.orderRecyclerContainer.models = mOrderService.getOrderList(mUser.id, type.value!!)
                payDialog.dismiss()
            }
            .build()
    }

    private fun initRecyclerView(){
        mBinding.orderRecyclerContainer.linear().setup {
            addType<OrderInfoModel>(R.layout.unit_order)
            models = mOrderService.getOrderList(mUser.id, 0)

            fun jumpToOrderDetail(orderInfoModel: OrderInfoModel){
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val action = OrderFragmentDirections.actionPageOrderToOrderDetailFragment(orderInfoModel.id)
                mBinding.root.findNavController().navigate(action)
            }

            onClick(R.id.unit_order_info_layout) {
                jumpToOrderDetail(getModel() as OrderInfoModel)
            }

            R.id.btn_pay.onClick {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val order = getModel() as OrderInfoModel
                payDialog.show(order.id)
            }

            R.id.btn_cancel.onClick {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                MaterialAlertDialogBuilder(context)
                    .setTitle("取消订单")
                    .setMessage("确定要取消这个订单吗？")
                    .setNegativeButton("取消") { _, _ ->
                        mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                    }
                    .setPositiveButton("确定") { _, _ ->
                        mBinding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                        mOrderService.cancelOrder((getModel() as OrderInfoModel).id)
                        mBinding.orderRecyclerContainer.models = mOrderService.getOrderList(mUser.id, type.value!!)
                    }
                    .show()
            }
        }
    }
}