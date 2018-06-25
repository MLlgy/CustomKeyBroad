package com.mk.aline.customkeybroad

import android.content.Context
import android.util.TypedValue

object DensityUtil {

    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return sp
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spVal, context.resources.displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale + 0.5f
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.scaledDensity
        return pxVal / scale + 0.5f
    }
}
