package com.nagico.bookstore.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager


/**
 * @author Martin-harry
 * @date 2021/8/10
 * @address
 * @Desc 系统状态栏颜色
 */
object StatusBarUtil {
    fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
        try {
            //获取sdk的api，如果大于等于5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window
                window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) //状态栏颜色（6.0以上才可以）
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorResId)
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setWindowStatusBarColorText(activity: Activity, colorResId: Int) {
        try {
            //获取sdk的api，如果大于等于5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window
                //                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);//状态栏颜色（6.0以上才可以）
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorResId)
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setWindowStatusBarColor(dialog: Dialog, colorResId: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = dialog.window!!
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = dialog.context.resources.getColor(colorResId)
                window.navigationBarColor = Color.TRANSPARENT
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
