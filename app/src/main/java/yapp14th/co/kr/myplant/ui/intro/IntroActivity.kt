package yapp14th.co.kr.myplant.ui.intro

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*
import yapp14th.co.kr.myplant.MyApplication
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.ColorPickerView

class IntroActivity : BaseActivity(), ColorPickerView.OnColorChangedListener {
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes(): Int {
        return R.layout.activity_intro
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정 (사용안할 시 반드시 false 처리할 것))
    override fun getIsUseDataBinding(): Boolean {
        return false
    }

    // TODO 필수 선언 3 (onCreate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO 필수 선언 4 (툴바 설정)
        setToolbar(R.color.transparent)

        var width = MyApplication.convertDpToPixel(90F, this@IntroActivity).toInt()
        var height = MyApplication.convertDpToPixel(90F, this@IntroActivity).toInt()
        var radius = MyApplication.convertDpToPixel(32F, this@IntroActivity).toInt()

        colorPickerView.init(this@IntroActivity, width, height, radius)

        // ...
    }

    override fun colorChanged(color: Int) {
        println("color : $color")
    }
}
