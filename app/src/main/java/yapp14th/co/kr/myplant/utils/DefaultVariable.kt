package yapp14th.co.kr.myplant.utils

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import filaroid.co.kr.filaroid.components.OnSnapPositionChangeListener
import filaroid.co.kr.filaroid.components.SnapOnScrollListener
import io.realm.RealmResults
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import java.util.*

fun getMonthDay(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 ->
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
                29
            else
                28
        else -> -1
    }
}

fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)

fun getCurrentMonth() = Calendar.getInstance().get(Calendar.MONTH)
fun getCurrentRefinedMonth() = Calendar.getInstance().get(Calendar.MONTH) + 1
fun getRefinedMonth(month: Short) = (month + 1).toShort()

fun getCurrentDate() = Calendar.getInstance().get(Calendar.DATE)

fun getcalendarResources() = arrayOf(
        R.drawable.ic_calendar_background_pleasure,     // 기쁨
        R.drawable.ic_calendar_background_happy,        // 행복
        R.drawable.ic_calendar_background_excitement,   // 신남
        R.drawable.ic_calendar_background_peace,        // 평화
        R.drawable.ic_calendar_background_sadness,      // 슬픔
        R.drawable.ic_calendar_background_anxiety,      // 불안
        R.drawable.ic_calendar_background_anger,        // 분노
        R.drawable.ic_calendar_background_custom        // 짜증
)

fun getMockDayEmotions(year: Int, month: Int): List<CDayVO> {
    var maximumDay = getMonthDay(year, month)
    val list = mutableListOf<CDayVO>()

    for (i in 1..maximumDay) {
        list.add(CDayVO(
                year = year.toShort(),
                month = month.toShort(),
                day = i.toShort(),
                emotionType = (Math.random() * 8).toShort(),
                comment = ""
        ))
    }

    return list
}

fun getGeneratedDayEmotions(datas: RealmResults<CDay>): List<CDayVO> {
    val list = mutableListOf<CDayVO>()

    datas.forEach { data ->
        list.add(CDayVO(
                year = data.year,
                month = data.month,
                day = data.day,
                emotionType = data.emotionType,
                comment = data.comment))
    }

    return list
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun RecyclerView.attachSnapHelperWithListener(
        snapHelper: SnapHelper,
        behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
        onSnapPositionChangeListener: OnSnapPositionChangeListener) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener = SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

fun adjustAlpha(color: Int, factor: Float): Int {
    val alpha = Math.round(Color.alpha(color) * factor)
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}
