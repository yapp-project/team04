package yapp14th.co.kr.myplant.components

import android.content.Context
import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil

class EventDecorator(private val context: Context, private val dates: ArrayList<CalendarDay>, private val position : Int) : DayViewDecorator {
    private var currentMonth = 0
    private var currentDay = 0

    override fun shouldDecorate(day: CalendarDay): Boolean {
        currentMonth = day.month
        currentDay = day.day

        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        // 날자밑에 점 넣기
        view.addSpan(DotSpan(10f, Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_$position"))))
    }
}
