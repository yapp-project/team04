package yapp14th.co.kr.myplant.ui.main.tab1_home

data class CDayVO(
        var year: Int,
        var month: Int,
        var day: Int,
        var emotionType: Int,
        var comment: String
)

fun generateCalendarMonth(calendarMonth: CDay): CDayVO {
    return CDayVO(
            year = calendarMonth.year,
            month = calendarMonth.month,
            day = calendarMonth.day,
            emotionType = calendarMonth.emotionType,
            comment = calendarMonth.comment)
}