package yapp14th.co.kr.myplant.components

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter


class MaterialColorAdapter(entries: MutableList<String>) : WheelArrayAdapter<String>(entries) {
    private val entries : List<String> = entries.toList()

    override fun getDrawable(position: Int): Drawable {
        var data = Color.parseColor(entries[position])
        return createOvalDrawable(data)
    }

    private fun createOvalDrawable(color: Int): Drawable {
        val shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.paint.color = color
        return shapeDrawable
    }
}