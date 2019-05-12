package yapp14th.co.kr.myplant.components

import android.R
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(private val context: Context, private val dates: HashSet<CalendarDay>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        // 날자밑에 점 넣기
        view.addSpan(DotSpan(5f, Color.parseColor("#3F51B335")))
    }
}
