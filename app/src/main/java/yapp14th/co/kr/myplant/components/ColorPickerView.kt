package yapp14th.co.kr.myplant.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.nio.channels.FileLock

// https://m.blog.naver.com/PostView.nhn?blogId=specialnks&logNo=10140861212&proxyReferer=https%3A%2F%2Fwww.google.com%2F 참고
class ColorPickerView(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var CENTER_X = 200      // 너비
    private var CENTER_Y = 200      // 높이
    private var CENTER_RADIUS = 64  // radius
    private var CENTER_HUE = 0.00f //채도
    private var CENTER_SATURATION = 0.00f //채도
    private var CENTER_VALUE = 0.00f //채도
    private var hsv : FloatArray = FloatArray(3)

    private var unit = 0f
    private var red = 0
    private var green = 0
    private var blue = 0

    private var PI = 3.1415926f

    private var mPaint: Paint
    private val mCenterPaint: Paint
    private val mColors: IntArray = intArrayOf(-0x10000, -0xff01, -0xffff01, -0xff0001, -0xff0100, -0x100, -0x10000)

    private var mTrackingCenter: Boolean = false
    private var mHighlightCenter: Boolean = false
    private var mSaturCenter : Boolean = false
    private var mValueCenter : Boolean = false

    private var mListener: ColorPickerView.OnColorChangedListener? = null
    private var color: Int = 0

    constructor(context: Context?) : this(context, null, 0, 0)
    constructor(context: Context?, attrs : AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context?, attrs : AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        val s = SweepGradient(0f, 0f, mColors, null)
        for(i in 0 until hsv.size){ hsv[i] = 0f }

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.shader = s
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 20f    // 원 두께

        mCenterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCenterPaint.color = color
        mCenterPaint.strokeWidth = 10f   // 가운데원 반지름
    }

    fun init(mListener: ColorPickerView.OnColorChangedListener, width: Int, height: Int, radius: Int) {
        this.mListener = mListener
        this.CENTER_X = width
        this.CENTER_Y = height
        this.CENTER_RADIUS = radius
    }
    fun changeChroma(mListener: ColorPickerView.OnColorChangedListener, hsv : FloatArray){
        this.mListener = mListener
        this.CENTER_HUE = hsv[0]
        this.CENTER_SATURATION = hsv[1]
        this.CENTER_VALUE = hsv[2]
        mSaturCenter = true
        invalidate()

    }
    fun changeValue(mListener: ColorPickerView.OnColorChangedListener, hsv : FloatArray){
        this.mListener = mListener
        this.CENTER_HUE = hsv[0]
        this.CENTER_SATURATION = hsv[1]
        this.CENTER_VALUE = hsv[2]
        mValueCenter = true
        invalidate()

    }


    interface OnColorChangedListener {
        fun colorChanged(color: Int,red : Int, green : Int, blue : Int)
    }

    override fun onDraw(canvas: Canvas) {
        val r = CENTER_X - mPaint.strokeWidth * 0.5f


        canvas.translate(CENTER_X.toFloat(), CENTER_X.toFloat())

        canvas.drawOval(RectF(-r, -r, r, r), mPaint)
        canvas.drawCircle(0f, 0f, CENTER_RADIUS.toFloat(), mCenterPaint)
        Color.RGBToHSV(red,green,blue,hsv)
        //hsv[0] =  mCenterPaint.color.toFloat()

        if (mTrackingCenter) {

            val c = mCenterPaint.color
            mCenterPaint.style = Paint.Style.STROKE

            if (mHighlightCenter) {
                mCenterPaint.alpha = 0xFF
            } else {
                mCenterPaint.alpha = 0x80
            }
            canvas.drawCircle(0f, 0f,
                    CENTER_RADIUS + mCenterPaint.strokeWidth,
                    mCenterPaint)

            mCenterPaint.style = Paint.Style.FILL
            mCenterPaint.color = c
            mListener?.colorChanged(mCenterPaint.color,Color.red(mCenterPaint.color),Color.green(mCenterPaint.color),Color.blue(mCenterPaint.color))
        }
        //채도에 따른 색 변경
        if(mSaturCenter){
            hsv[0] = CENTER_HUE
            hsv[1] = CENTER_SATURATION
            hsv[2] = CENTER_VALUE
            mCenterPaint.color =  Color.HSVToColor(hsv)
            canvas.drawCircle(0f, 0f, CENTER_RADIUS.toFloat(), mCenterPaint)
            mSaturCenter = false
            mListener?.colorChanged(mCenterPaint.color,Color.red(mCenterPaint.color),Color.green(mCenterPaint.color),Color.blue(mCenterPaint.color))
        }
        //명도에 따른 색 변경
        if(mValueCenter){
            hsv[0] = CENTER_HUE
            hsv[1] = CENTER_SATURATION
            hsv[2] = CENTER_VALUE
            mCenterPaint.color =  Color.HSVToColor(hsv)
            canvas.drawCircle(0f, 0f, CENTER_RADIUS.toFloat(), mCenterPaint)
            mValueCenter = false
            mListener?.colorChanged(mCenterPaint.color,Color.red(mCenterPaint.color),Color.green(mCenterPaint.color),Color.blue(mCenterPaint.color))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2)
    }

    private fun floatToByte(x: Float): Int {
        return Math.round(x)
    }

    private fun pinToByte(n: Int): Int {
        var n = n
        if (n < 0) {
            n = 0
        } else if (n > 255) {
            n = 255
        }
        return n
    }

    private fun ave(s: Int, d: Int, p: Float): Int {
        return s + java.lang.Math.round(p * (d - s))
    }

    private fun interpColor(colors: IntArray, unit: Float): Int {
        if (unit <= 0) {
            return colors[0]
        }
        if (unit >= 1) {
            return colors[colors.size - 1]
        }

        var p = unit * (colors.size - 1)
        val i = p.toInt()
        p -= i.toFloat()

        // now p is just the fractional part [0...1) and i is the index
        val c0 = colors[i]
        val c1 = colors[i + 1]
        val a = ave(Color.alpha(c0), Color.alpha(c1), p)
        red = ave(Color.red(c0), Color.red(c1), p)
        green = ave(Color.green(c0), Color.green(c1), p)
        blue = ave(Color.blue(c0), Color.blue(c1), p)

        return Color.argb(a, red, green, blue)
    }

    private fun rotateColor(color: Int, rad: Float): Int {
        val deg = rad * 180 / 3.1415927f
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)

        val cm = ColorMatrix()
        val tmp = ColorMatrix()

        cm.setRGB2YUV()
        tmp.setRotate(0, deg)
        cm.postConcat(tmp)
        tmp.setYUV2RGB()
        cm.postConcat(tmp)

        val a = cm.array

        val ir = floatToByte(a[0] * r + a[1] * g + a[2] * b)
        val ig = floatToByte(a[5] * r + a[6] * g + a[7] * b)
        val ib = floatToByte(a[10] * r + a[11] * g + a[12] * b)

        return Color.argb(Color.alpha(color), pinToByte(ir),
                pinToByte(ig), pinToByte(ib))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x - CENTER_X
        val y = event.y - CENTER_Y
        val inCenter = java.lang.Math.sqrt((x * x + y * y).toDouble()) <= CENTER_RADIUS

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTrackingCenter = inCenter
                if (inCenter) {
                    mHighlightCenter = true
                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE ->
                if (mTrackingCenter) {
                    if (mHighlightCenter != inCenter) {
                        mHighlightCenter = inCenter
                        invalidate()
                    }
                } else {
                    val angle = java.lang.Math.atan2(y.toDouble(), x.toDouble()).toFloat()
                    // need to turn angle [-PI ... PI] into unit [0....1]
                    unit = angle / (2 * PI)
                    if (unit < 0) {
                        unit += 1f
                    }
                    mCenterPaint.color = interpColor(mColors, unit)
                    mListener?.colorChanged(mCenterPaint.color,Color.red(mCenterPaint.color),Color.green(mCenterPaint.color),Color.blue(mCenterPaint.color))

                    invalidate()
                }
            MotionEvent.ACTION_UP ->
                if (mTrackingCenter) {
                    if (inCenter) {
                        mListener?.colorChanged(mCenterPaint.color,Color.red(mCenterPaint.color),Color.green(mCenterPaint.color),Color.blue(mCenterPaint.color))
                    }
                    mTrackingCenter = false    // so we draw w/o halo

                    invalidate()
                }
        }
        return true
    }

    companion object {





    }
}