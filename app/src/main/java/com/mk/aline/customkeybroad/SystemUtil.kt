package com.mk.aline.customkeybroad

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Creater: liguoying
 * Time: 2018/6/21 0021 17:05
 */
object SystemUtil {

    fun closesoftInputBorad(mEditText: EditText) {
        val mInputManager: InputMethodManager = mEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputManager.hideSoftInputFromWindow(mEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

