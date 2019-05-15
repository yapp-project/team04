package yapp14th.co.kr.myplant.ui.intro

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intro_colorpick.*
import yapp14th.co.kr.myplant.MyApplication
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.components.ColorPickerView
import android.widget.SeekBar



class IntroColorPickActivity : BaseActivity(), ColorPickerView.OnColorChangedListener {
    var redcolor : Int = 0
    var greencolor : Int = 0
    var bluecolor : Int = 0
    var width : Int = 0
    var height : Int = 0
    var radius : Int = 0
    //var hsv = FloatArray(3)
    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes(): Int {
        return yapp14th.co.kr.myplant.R.layout.activity_intro_colorpick
    }

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정 (사용안할 시 반드시 false 처리할 것))
    override fun getIsUseDataBinding(): Boolean {
        return false
    }

    // TODO 필수 선언 3 (onCreate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      
        width = MyApplication.convertDpToPixel(90F, this@IntroColorPickActivity).toInt()
        height = MyApplication.convertDpToPixel(90F, this@IntroColorPickActivity).toInt()
        radius = MyApplication.convertDpToPixel(25F, this@IntroColorPickActivity).toInt()

        init()

        // TODO 필수 선언 4 (툴바 설정)

        // ...
    }
    public fun init(){


        colorPickerView_test.init(this@IntroColorPickActivity, width, height, radius)
        //colorPickerView_test.init(this, 200,200,64)
        //명도 변경
        intro_sb_brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                var hsv = FloatArray(3)
                Color.RGBToHSV(redcolor, greencolor, bluecolor, hsv)
                hsv[2] = (intro_sb_brightness.getProgress())/100.toFloat()
                Log.d("intro/value",hsv[1].toString())
                colorPickerView_test.changeValue(this@IntroColorPickActivity,hsv)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        //채도 변경
        intro_sb_chroma.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                var hsv = FloatArray(3)
                Color.RGBToHSV(redcolor, greencolor, bluecolor, hsv)
                hsv[1] = (intro_sb_chroma.getProgress())/100.toFloat()
                Log.d("intro/saturation",hsv[1].toString())
                colorPickerView_test.changeChroma(this@IntroColorPickActivity,hsv)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    override fun colorChanged(color: Int,red : Int, green : Int, blue : Int) {
        println("color : $color")
        redcolor= red;
        greencolor = green
        bluecolor = blue
        //Color.RGBToHSV(redcolor, greencolor, bluecolor, hsv)
        R_et.setText(red.toString())
        G_et.setText(green.toString())
        B_et.setText(blue.toString())
        hex_code_et.setText(String.format("#%06X", color))
        intro_sb_brightness.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN );
        intro_sb_chroma.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN );
    }
}