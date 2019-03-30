package yapp14th.co.kr.myplant.components

import android.content.Context
import android.util.AttributeSet
import android.widget.CalendarView
import android.widget.TextView

class CustomCalendarView(
        context: Context? = null,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0) : CalendarView(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context?) : this(context, null, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)
}