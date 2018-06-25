package com.mk.aline.customkeybroad.widget

import android.annotation.TargetApi
import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.mk.aline.customkeybroad.R
import com.mk.aline.customkeybroad.SystemUtil
import java.util.*


/**
 * Creater: liguoying
 * Time: 2018/6/21 0021 16:44
 */
class EditView : EditText, KeyboardView.OnKeyboardActionListener {

    private var mKeyboardView: KeyboardView? = null
    private var mEnglishKeyBoard: Keyboard? = null
    private var mNumberKeyboard: Keyboard? = null
    private var isNumber: Boolean = false //是否为数字键盘
    private var isCapital: Boolean = false //是否为大写键盘
    private var mContext: Context? = null
    private var mViewGroup: LinearLayout? = null
    private var mOnKeyboardListener: OnKeyboardListener? = null

    constructor(mContext: Context) : this(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs,0)

    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr) {
        this.mContext = mContext
        initView()

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        mNumberKeyboard = Keyboard(mContext, R.xml.keyboard_number)
        mEnglishKeyBoard = Keyboard(mContext, R.xml.keyboard_english)
        requestFocus()
    }

    /**
     * 设置键盘
     */
    fun setUpEditView(mViewGroup: LinearLayout?, mKeyboardView: KeyboardView?, isNumber: Boolean) {
        this.mViewGroup = mViewGroup
        this.mKeyboardView = mKeyboardView;
        this.isNumber = isNumber
        when (isNumber) {
            true -> mKeyboardView!!.keyboard = mNumberKeyboard
            false -> mKeyboardView!!.keyboard = mEnglishKeyBoard
        }
        mKeyboardView.isEnabled = true
        mKeyboardView.setOnKeyboardActionListener(this)
    }

    private fun changeEnOrNumBroad() {
        when (isNumber) {
            true -> mKeyboardView!!.keyboard = mEnglishKeyBoard
            false -> mKeyboardView!!.keyboard = mNumberKeyboard
        }
        isNumber = !isNumber
    }

    /**
     * 大小写切换
     */
    private fun changeENCapital() {

        for (it in mEnglishKeyBoard!!.keys) {
            if (it.label != null && isKey(it.label.toString()))
                when (isCapital) {
                    true -> {
                        it.label = it.label.toString().toLowerCase()
                        it.codes[0] = it.codes[0] + 32
                    }
                    false -> {
                        it.label = it.label.toString().toUpperCase()
                        it.codes[0] = it.codes[0] - 32
                    }
                }
        }
        isCapital = !isCapital
    }

    private fun isKey(key: String): Boolean {
        val string: String = "abcdefghijklmnopqrstuvwxyz"
        if (string.indexOf(key.toLowerCase()) > -1) {
            return false
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        requestFocus()
        requestFocusFromTouch()
        SystemUtil.closesoftInputBorad(this)
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (!isShow()) {
                showShoftInput()
            }
        }
        return true

    }

    private fun isShow(): Boolean {
        return mKeyboardView!!.visibility == View.VISIBLE
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        SystemUtil.closesoftInputBorad(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        SystemUtil.closesoftInputBorad(this)
        mKeyboardView = null
    }


    override fun onPress(primaryCode: Int) {
        mOnKeyboardListener!!.onPress(primaryCode)
        if (isNumber) {
            return
        }
        setPreviewEnale(primaryCode)
    }

    /**
     * 设置是否开启预览
     */
    private fun setPreviewEnale(primaryCode: Int) {
        val list = Arrays.asList(Keyboard.KEYCODE_MODE_CHANGE, Keyboard.KEYCODE_DELETE, Keyboard.KEYCODE_SHIFT, Keyboard.KEYCODE_DONE, 32)
        when (list.contains(primaryCode)) {
            false -> mKeyboardView!!.isPreviewEnabled = true
            true -> mKeyboardView!!.isPreviewEnabled = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            hide(false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showShoftInput() {
        val visiable = mKeyboardView!!.visibility
        when (visiable) {
            View.INVISIBLE, View.GONE -> {
                mKeyboardView!!.visibility = View.VISIBLE
                mViewGroup!!.visibility = View.VISIBLE
            }

        }
        mOnKeyboardListener!!.onShow()
    }

    private fun hide(mHide: Boolean) {
        val visibility = mKeyboardView!!.getVisibility()
        if (visibility == View.VISIBLE) {
            mKeyboardView!!.visibility = View.INVISIBLE
            if (mViewGroup != null) {
                mViewGroup!!.visibility = View.GONE
            }
        }
        mOnKeyboardListener!!.onHide(mHide)
    }

    override fun onRelease(primaryCode: Int) {
        when (primaryCode) {
            Keyboard.KEYCODE_DONE -> hide(true)
            else -> {
            }
        }
    }

    override fun swipeRight() {
    }

    override fun swipeLeft() {
    }

    override fun swipeUp() {
    }

    override fun swipeDown() {
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val mEditTable: Editable = text
        val start: Int = selectionStart
        when (primaryCode) {
        //-2 英文和数字的切换
            Keyboard.KEYCODE_MODE_CHANGE -> changeEnOrNumBroad()
        //-5
            Keyboard.KEYCODE_DELETE -> {
                if (mEditTable != null && mEditTable.length > 0 && start > 0) {
                    mEditTable.delete(start - 1, start)
                }
            }
        //-1 大小写切换
            Keyboard.KEYCODE_SHIFT -> {
                changeENCapital()
                mKeyboardView!!.keyboard = mEnglishKeyBoard
            }
            else -> {
                mEditTable.insert(start, primaryCode.toChar().toString())
            }
        }
    }

    override fun onText(text: CharSequence?) {
    }

    interface OnKeyboardListener {

        fun onHide(isCompleted: Boolean)
        fun onShow()
        fun onPress(primaryCode: Int)
    }

    fun setOnKeyboardListener(mOnKeyboardListener: OnKeyboardListener) {
        this.mOnKeyboardListener = mOnKeyboardListener
    }
}