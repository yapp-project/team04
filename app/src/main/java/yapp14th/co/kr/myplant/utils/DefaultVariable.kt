package yapp14th.co.kr.myplant.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import filaroid.co.kr.filaroid.components.OnSnapPositionChangeListener
import filaroid.co.kr.filaroid.components.SnapOnScrollListener
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
fun getEmotionsList() = arrayListOf(
        "기쁨",
        "행복",
        "신남",
        "평화",
        "슬픔",
        "불안",
        "분노",
        ""
)

fun getMockDayEmotions(year: Int, month: Int): List<CDayVO> {
    var maximumDay = getMonthDay(year, month)
    val list = mutableListOf<CDayVO>()

    for (i in 1..maximumDay) {
        list.add(CDayVO(
                year = year.toShort(),
                month = month.toShort(),
                day = i.toShort(),
                emotionType = (Math.random() * getEmotionsList().size).toShort(),
                comment = ""
        ))
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