package buv.co.kr.ui.login.data

import io.reactivex.Single

// 하나의 관련데이터를 가져온다. (Interface를 통한 remote / dummy 처리)
interface HomeDataSource {
    fun getYearList(
            currentYear: Int
    ): Single<List<Int>>

    fun getCalendarList(
            year: Int
    ): Single<List<Pair<Int, Int>>>
}