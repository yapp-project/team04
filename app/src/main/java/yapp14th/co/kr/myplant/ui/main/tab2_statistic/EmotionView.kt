package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout

class EmotionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var paint : Paint
    private lateinit var rect : RectF

    init {
        paint = Paint()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        rect = RectF(0f, 0f.toFloat(), right.toFloat(),(bottom-top).toFloat())
        Log.e("EmotionView", "."+ left + ","+top+","+right+","+bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawRoundRect(rect, 10f, 10f, paint)
    }

    fun setColor(color : Int){
        paint.color = color
        invalidate()
    }
}