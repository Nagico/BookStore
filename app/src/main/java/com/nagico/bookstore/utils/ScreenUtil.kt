package com.nagico.bookstore.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager

/**
 * 屏幕单位转化
 */
object ScreenUtil {
    /**
     * dp转为px
     *
     * @param dpVal
     * @return
     */
    @JvmStatic
    fun dp2px(dpVal: Float): Float {
        val r = Resources.getSystem()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, r.displayMetrics)
    }

    /**
     * sp转为px
     *
     * @param spVal
     * @return
     */
    fun sp2px(spVal: Float): Float {
        val r = Resources.getSystem()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, r.displayMetrics)
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     */
    fun px2dp(pxValue: Float): Float {
        val r = Resources.getSystem()
        val scale = r.displayMetrics.density
        return pxValue / scale + 0.5f
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    fun px2sp(pxVal: Float): Float {
        val r = Resources.getSystem()
        return pxVal / r.displayMetrics.scaledDensity + 0.5f
    }

    /**
     * Gets the width of the display, in pixels.
     *
     * @param context
     * @return
     */
    fun screenWidthPixel(context: Context): Int {
        val sScreenWidthPixels: Int
        val windowManager = (context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager)
        val display = windowManager.defaultDisplay
        sScreenWidthPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val outPoint = Point()
            display.getRealSize(outPoint)
            outPoint.x
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val outPoint = Point()
            display.getSize(outPoint)
            outPoint.x
        } else {
            display.width
        }
        return sScreenWidthPixels
    }

    /**
     * Gets the height of the display, in pixels.
     *
     * @param context
     * @return
     */
    fun screenHeightPixel(context: Context): Int {
        val sScreenHeightPixels: Int
        val windowManager = (context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager)
        val display = windowManager.defaultDisplay
        sScreenHeightPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val outPoint = Point()
            display.getRealSize(outPoint)
            outPoint.y
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val outPoint = Point()
            display.getSize(outPoint)
            outPoint.y
        } else {
            display.height
        }
        return sScreenHeightPixels
    }

    /**
     * 测量 View
     *
     * @param measureSpec
     * @param defaultSize View 的默认大小
     * @return
     */
    fun measure(measureSpec: Int, defaultSize: Int): Int {
        var result = defaultSize
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize)
        }
        return result
    }

    /**
     * 测量文字高度
     *
     * @param paint
     * @return
     */
    fun measureTextHeight(paint: Paint): Float {
        val fontMetrics = paint.fontMetrics
        return Math.abs(fontMetrics.ascent) - fontMetrics.descent
    }

    /**
     * 计算文字的高度
     *
     * @param paint
     * @return
     */
    fun getTextHeight(paint: Paint): Int {
        val fm = paint.fontMetrics
        return Math.ceil((fm.descent - fm.ascent).toDouble()).toInt()
    }

    fun getTextFullHeight(paint: Paint): Int {
        val fm = paint.fontMetrics
        return Math.ceil((fm.bottom - fm.top).toDouble()).toInt()
    }

    fun getTextBaseLine(paint: Paint): Int {
        val fm = paint.fontMetrics
        return Math.abs(fm.bottom).toInt()
    }

    fun getScreenWidth(c: Context): Int {
        return c.resources.displayMetrics.widthPixels
    }

    /**
     * 计算圆弧长度
     *
     * @param radius 圆半径
     * @param angle  夹角度数（非弧度）
     * @return
     */
    fun getCirclePathLength(radius: Float, angle: Float): Float {
        var angle = angle
        angle = changeAngleToSingle(angle)
        return (Math.PI * radius * angle / 180).toFloat()
    }

    /**
     * 将度数转换成0～360之间的值
     *
     * @param angle
     * @return
     */
    fun changeAngleToSingle(angle: Float): Float {
        var angle = angle
        while (angle >= 360) {
            angle -= 360f
        }
        while (angle < 0) {
            angle += 360f
        }
        return angle
    }

    /**
     * 获取文字的宽度
     *
     * @param paint
     * @param str
     * @return
     */
    fun getTextWidth(paint: Paint, str: String?): Int {
        var iRet = 0
        if (str != null && str.length > 0) {
            val len = str.length
            val widths = FloatArray(len)
            paint.getTextWidths(str, widths)
            for (j in 0 until len) {
                iRet += Math.ceil(widths[j].toDouble()).toInt()
            }
        }
        return iRet
    }
}