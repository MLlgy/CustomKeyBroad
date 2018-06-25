package com.mk.aline.customkeybroad

import android.inputmethodservice.KeyboardView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.mk.aline.customkeybroad.R.id.ll_guan
import com.mk.aline.customkeybroad.widget.EditView

class MainActivity : AppCompatActivity() {

//    @BindView(R.id.keyboard_view)
    var keyboardView: KeyboardView? = null
//    @BindView(R.id.edit_view)
    var editView: EditView? = null
//    @BindView(R.id.ll_keyboard)
    var llKeyboard: LinearLayout? = null
//    @BindView(R.id.ll_guan)
    var llGuan: LinearLayout? = null

    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        intview()
        setSubView()
        initEvent()
    }

    private fun intview() {
        keyboardView = findViewById(R.id.keyboard_view)
        editView = findViewById(R.id.edit_view)
        llKeyboard = findViewById(R.id.ll_keyboard)
        llGuan = findViewById(R.id.ll_guan)
    }

    private fun initEvent() {
        editView!!.setOnKeyboardListener(object : EditView.OnKeyboardListener {
            override fun onHide(isCompleted: Boolean) {
                if (height > 0) {
                    llGuan!!.scrollBy(0, -(height + DensityUtil.dp2px(this@MainActivity, 16F)))
                }

                if (isCompleted) {
                    Log.i("", "你点击了完成按钮")
                }
            }

            override fun onShow() {
                llGuan!!.post {
                    //pos[0]: X，pos[1]: Y
                    val pos = IntArray(2)
                    //获取编辑框在整个屏幕中的坐标
                    editView!!.getLocationOnScreen(pos)
                    //编辑框的Bottom坐标和键盘Top坐标的差
                    height = pos[1] + editView!!.getHeight() - (ScreenUtil.getScreenHeight(this@MainActivity) - keyboardView!!.height)
                    if (height > 0) {
                        //编辑框和键盘之间预留出16dp的距离
                        llGuan!!.scrollBy(0, height + DensityUtil.dp2px(this@MainActivity, 16F))
                    }
                }
            }


            override fun onPress(primaryCode: Int) {

            }
        })
    }

    private fun setSubView() {
        editView!!.setUpEditView(llKeyboard, keyboardView, true)
    }
}