package yapp14th.co.kr.myplant.ui.main.tab1_home

data class CDayVO(
        var year: Short,
        var month: Short,
        var day: Short,
        var emotionType: Short,
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