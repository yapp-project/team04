package yapp14th.co.kr.myplant.ui.main.tab1_home

data class CalendarMonth(
        var year: Short,
        var month: Short,
        var fliped: Boolean = false,
        var dayList: List<CDayVO>
)