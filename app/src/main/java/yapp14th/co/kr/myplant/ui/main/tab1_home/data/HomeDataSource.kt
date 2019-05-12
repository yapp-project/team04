package buv.co.kr.ui.login.data

import io.reactivex.Single
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth

// 하나의 관련데이터를 가져온다. (Interface를 통한 remote / dummy 처리)
interface HomeDataSource {
    fun getYears(
            currentYear: Int
    ): Single<List<Int>>

//    fun getCalendars(
//            year: Int
//    ): Single<List<Pair<Int, Int>>>

    fun getYearEmotions(
            year: Int
    ): Single<List<CalendarMonth>>
}