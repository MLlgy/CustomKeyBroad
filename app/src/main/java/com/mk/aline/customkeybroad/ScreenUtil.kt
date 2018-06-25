package com.mk.aline.customkeybroad

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtil {
    fun getScreenHeight(mContext: Context): Int {
        val mScreenMananger: WindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val mDisplayMetrics = DisplayMetrics()
        mScreenMananger.defaultDisplay.getMetrics(mDisplayMetrics)
        return mDisplayMetrics.heightPixels
    }

    fun getScreenWidth(mContext: Context): Int {
        val mScreenManager: WindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val mDisplayMetrics = DisplayMetrics()
        mScreenManager.defaultDisplay.getMetrics(mDisplayMetrics)
        return mDisplayMetrics.widthPixels
    }
}
