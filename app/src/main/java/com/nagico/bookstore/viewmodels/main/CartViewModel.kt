package com.nagico.bookstore.viewmodels.main

import android.annotation.SuppressLint
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
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
import com.nagico.bookstore.R
import com.nagico.bookstore.databinding.FragmentCartBinding
import com.nagico.bookstore.databinding.UnitShoppingCartBinding
import com.nagico.bookstore.fragments.main.CartFragmentDirections
import com.nagico.bookstore.models.CartInfoModel
import com.nagico.bookstore.models.entity.User
import com.nagico.bookstore.services.BookStoreService
import com.nagico.bookstore.services.CartService
import com.nagico.bookstore.services.OrderService
import com.nagico.bookstore.views.dialogs.PayDialog
import com.nagico.bookstore.views.groups.MultiLineRadioGroup

class CartViewModel : ViewModel() {
    private lateinit var mBinding: FragmentCartBinding
    @SuppressLint("StaticFieldLeak")
    private lateinit var mActivity: FragmentActivity
    private lateinit var mCartService: CartService
    private lateinit var mOrderService: OrderService
    private lateinit var payDialog: PayDialog

    private lateinit var mUser: User

    val totalChecked by lazy {
        MutableLiveData<Boolean>()
    }

    val totalPrice by lazy {
        MutableLiveData<Double>()
    }

    val totalQuantity by lazy {
        MutableLiveData<Int>()
    }
    fun init(binding: FragmentCartBinding, activity: FragmentActivity){
        mBinding = binding
        mActivity = activity
        mUser = BookStoreService.instance.getUser(mActivity)!!
        mCartService = CartService.instance
        mOrderService = OrderService.instance
        totalChecked.value = false
        totalPrice.value = 0.0
        totalQuantity.value = 0

        initRecyclerView()
        initDialog()

        mBinding.cbCartAll.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            if (mBinding.cbCartAll.isChecked) {
                selectAll()
            } else {
                unselectAll()
            }
        }
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
                payDialog.dismiss()
            }
            .build()
    }

    private fun initRecyclerView(){
        mBinding.cartRecyclerContainer.linear().setup {
            addType<CartInfoModel>(R.layout.unit_shopping_cart)

            fun jumpToBookDetail(cartInfoModel: CartInfoModel){
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val action = CartFragmentDirections.actionPageCartToBookDetailFragment(cartInfoModel.bookId)
                mBinding.root.findNavController().navigate(action)
            }

            onClick(R.id.img_cover1) {
                jumpToBookDetail(getModel() as CartInfoModel)
            }

            onClick(R.id.txt_title) {
                jumpToBookDetail(getModel() as CartInfoModel)
            }

            onClick(R.id.txt_price) {
                jumpToBookDetail(getModel() as CartInfoModel)
            }

            R.id.btn_clear.onClick {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                MaterialAlertDialogBuilder(context)
                    .setTitle("删除商品")
                    .setMessage("确定要删除这个商品吗？")
                    .setNegativeButton("取消") { _, _ ->
                        mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                    }
                    .setPositiveButton("确定") { _, _ ->
                        mBinding.root.performHapticFeedback(HapticFeedbackConstants.CONFIRM, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                        mutable.removeAt(adapterPosition)
                        mCartService.removeFromCart(mUser.id, (getModel() as CartInfoModel).id)
                        adapter.notifyItemRemoved(adapterPosition)
                        calTotalAttrs()
                    }
                    .show()
            }

            R.id.btn_add.onClick {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val cartInfoModel = getModel() as CartInfoModel
                mCartService.updateQuantity(mUser.id, cartInfoModel.id, cartInfoModel.quantity + 1)
                cartInfoModel.quantity += 1
                adapter.notifyItemChanged(adapterPosition)
                calTotalAttrs()
            }

            R.id.btn_del.onClick {
                mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                val cartInfoModel = getModel() as CartInfoModel
                if(cartInfoModel.quantity > 1){
                    mCartService.updateQuantity(mUser.id, cartInfoModel.id, cartInfoModel.quantity - 1)
                    cartInfoModel.quantity -= 1
                    adapter.notifyItemChanged(adapterPosition)
                    calTotalAttrs()
                }
            }

            onBind {
                getBinding<UnitShoppingCartBinding>().txtUnitCartQuantity.setOnEditorActionListener { _, actionId, _ ->
                    try{
                        if(actionId == EditorInfo.IME_ACTION_DONE){
                            val cartInfoModel = getModel() as CartInfoModel
                            val quantity = it.findViewById<TextInputEditText>(R.id.txt_unit_cart_quantity).text.toString().toInt()
                            if(quantity > 0){
                                mCartService.updateQuantity(mUser.id, cartInfoModel.id, quantity)
                                cartInfoModel.quantity = quantity
                                adapter.notifyItemChanged(adapterPosition)
                                calTotalAttrs()
                                return@setOnEditorActionListener false
                            } else {
                                Toast.makeText(context, "数量不能小于1", Toast.LENGTH_SHORT).show()
                                adapter.notifyItemChanged(adapterPosition)
                                return@setOnEditorActionListener false
                            }

                        }
                        return@setOnEditorActionListener false
                    } catch (e: Exception){
                        Toast.makeText(context, "请输入正确的数量", Toast.LENGTH_SHORT).show()
                        adapter.notifyItemChanged(adapterPosition)
                        return@setOnEditorActionListener false
                    }

                }

                getBinding<UnitShoppingCartBinding>().cbBook.setOnClickListener {
                    mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                    calTotalAttrs()
                    var allChecked = true
                    mBinding.cartRecyclerContainer.models?.forEach {
                        if(it is CartInfoModel && !it.checked){
                            allChecked = false
                        }
                    }
                    totalChecked.postValue(allChecked)
                }
            }

        }

        getData()
    }

    private fun calTotalAttrs() {
        var newTotalPrice = 0.0
        var newTotalQuantity = 0
        mBinding.cartRecyclerContainer.models?.forEach {
            if(it is CartInfoModel){
                if (it.checked) {
                    newTotalPrice += it.quantity * it.price
                    newTotalQuantity += it.quantity
                }
            }
        }
        totalPrice.postValue(newTotalPrice)
        totalQuantity.postValue(newTotalQuantity)
    }

    private fun selectAll(){
        mBinding.cartRecyclerContainer.models?.forEach {
            if(it is CartInfoModel){
                it.checked = true
            }
        }
        mBinding.cartRecyclerContainer.adapter?.notifyDataSetChanged()
        calTotalAttrs()
    }

    private fun unselectAll(){
        mBinding.cartRecyclerContainer.models?.forEach {
            if(it is CartInfoModel){
                it.checked = false
            }
        }
        mBinding.cartRecyclerContainer.adapter?.notifyDataSetChanged()
        calTotalAttrs()
    }

    fun checkout() = View.OnClickListener {
        mBinding.root.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        val checkoutIds = mutableListOf<Long>()
        mBinding.cartRecyclerContainer.models?.forEach {
            if(it is CartInfoModel){
                if(it.checked){
                    checkoutIds.add(it.id)
                }
            }
        }
        if(checkoutIds.isEmpty()){
            Toast.makeText(mActivity, "请选择要结算的商品", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        val orderId = mCartService.checkout(mUser.id, checkoutIds)

        payDialog.show(orderId)

        getData()
        totalPrice.postValue(0.0)
        totalQuantity.postValue(0)
        totalChecked.postValue(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        val res = mCartService.getCartList(mUser.id)
        if (res.isEmpty())
            mBinding.state.showEmpty()
        else
            mBinding.state.showContent()
        mBinding.cartRecyclerContainer.models = res
        mBinding.cartRecyclerContainer.adapter?.notifyDataSetChanged()
    }

}