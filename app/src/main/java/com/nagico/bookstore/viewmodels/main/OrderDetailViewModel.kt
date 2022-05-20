package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentOrderDetailBinding
import com.nagico.bookstore.fragments.main.OrderDetailFragmentDirections
import com.nagico.bookstore.models.Order
import com.nagico.bookstore.models.OrderDetailModel
import com.nagico.bookstore.services.OrderService

class OrderDetailViewModel : ViewModel() {
    private lateinit var mBinding: FragmentOrderDetailBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mOrderService: OrderService

    val order by lazy {
        MutableLiveData<Order>()
    }

    fun init(binding: FragmentOrderDetailBinding, activity: FragmentActivity, orderId: Long) {
        mBinding = binding
        mActivity = activity
        mOrderService = OrderService.instance

        order.value = mOrderService.getOrderById(orderId)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mBinding.orderDetailRecyclerContainer.linear().setup {
            addType<OrderDetailModel>(R.layout.unit_order_detail)
            models = mOrderService.getOrderDetail(order.value!!.id)

            fun jumpToBookDetail(orderDetailModel: OrderDetailModel) {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val action = OrderDetailFragmentDirections.actionOrderDetailFragmentToBookDetailFragment(orderDetailModel.bookId)
                mBinding.root.findNavController().navigate(action)
            }

            onClick(R.id.unit_order_detail_layout) {
                jumpToBookDetail(getModel() as OrderDetailModel)
            }
        }

    }

}