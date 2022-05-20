package com.nagico.bookstore.views.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.IdRes
import com.nagico.bookstore.utils.ScreenUtil.dp2px

/**
 * 自定义对话框
 */
class PayDialog : Dialog {
    private var height: Int
    private var width: Int
    private var mCancelTouchout: Boolean
    private var mCancelable: Boolean
    private var view: View?
    var orderId: Long? = null



    private constructor(builder: Builder) : super(builder.context) {
        height = builder.height
        width = builder.width
        mCancelTouchout = builder.cancelTouchout
        mCancelable = builder.cancelable
        view = builder.view
    }

    private constructor(builder: Builder, resStyle: Int) : super(builder.context, resStyle) {
        height = builder.height
        width = builder.width
        mCancelTouchout = builder.cancelTouchout
        mCancelable = builder.cancelable
        view = builder.view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view!!)
        setCanceledOnTouchOutside(mCancelTouchout)
        val win = window
        val lp = win?.attributes!!
        lp.gravity = Gravity.CENTER
        lp.height = height
        lp.width = width
        win.attributes = lp
    }

    fun show(orderId: Long) {
        this.orderId = orderId
        show()
    }

    open class Builder(val context: Context) {
        var height = 0
        var width = 0
        var cancelTouchout = false
        var cancelable = false
        var view: View? = null
        private var resStyle = -1
        fun view(resView: View?): Builder {
            view = resView
            return this
        }

        fun heightpx(`val`: Int): Builder {
            height = `val`
            return this
        }

        fun widthpx(`val`: Int): Builder {
            width = `val`
            return this
        }

        fun heightdp(`val`: Int): Builder {
            height = dp2px(`val`.toFloat()).toInt()
            return this
        }

        fun widthdp(`val`: Int): Builder {
            width = dp2px(`val`.toFloat()).toInt()
            return this
        }

        fun heightDimenRes(dimenRes: Int): Builder {
            height = context.resources.getDimensionPixelOffset(dimenRes)
            return this
        }

        fun widthDimenRes(dimenRes: Int): Builder {
            width = context.resources.getDimensionPixelOffset(dimenRes)
            return this
        }

        fun style(resStyle: Int): Builder {
            this.resStyle = resStyle
            return this
        }

        fun cancelTouchout(`val`: Boolean): Builder {
            // 调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
            cancelTouchout = `val`
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            // 调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
            this.cancelable = cancelable
            return this
        }

        fun addViewOnclick(@IdRes viewRes: Int, listener: View.OnClickListener?): Builder {
            view!!.findViewById<View>(viewRes).setOnClickListener(listener)
            return this
        }

        fun build(): PayDialog {
            return if (resStyle != -1) {
                PayDialog(this, resStyle)
            } else {
                PayDialog(this)
            }
        }
    }
}