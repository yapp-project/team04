package yapp14th.co.kr.myplant.ui.main.tab1_home.data

import buv.co.kr.ui.login.data.HomeDataSource
import io.reactivex.Single
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.getTargetMonthDates
import yapp14th.co.kr.myplant.utils.getTargetYearEmotions

// 서버에서 데이터를 받아온다
class HomeRemoteSource : HomeDataSource {
    override fun getYears(currentYear: Int): Single<List<Int>> {
        // return getNetworkInstance().getAuthRefreshToken(Authorization = Authorization)
        return Single.create<List<Int>> {
            // 추후 년도 계산 로직 필요
            it.onSuccess(listOf(2019))
        }
    }

//    override fun getCalendars(year: Int): Single<List<Pair<Int, Int>>> {
//        return Single.create<List<Pair<Int, Int>>>{
//            listOf(
//                    Pair(year, 1),
//                    Pair(year, 2),
//                    Pair(year, 3),
//                    Pair(year, 4),
//                    Pair(year, 5),
//                    Pair(year, 6),
//                    Pair(year, 7),
//                    Pair(year, 8),
//                    Pair(year, 9),
//                    Pair(year, 10),
//                    Pair(year, 11),
//                    Pair(year, 12))
//        }
//    }

    override fun getYearEmotions(year: Int): Single<List<CalendarMonth>> {
        return Single.create<List<CalendarMonth>> {
            var emotions = getTargetYearEmotions(year)

            it.onSuccess(emotions)
        }
    }

    override fun getComments(year: Int, month: Int): Single<List<CDayVO>> {
        return Single.create<List<CDayVO>> {
            var monthDates = getTargetMonthDates(year, month)

            mutableListOf<CDayVO>().apply {
                monthDates?.forEach { cDay ->
                    add(CDayVO(
                            year = cDay.year,
                            month = cDay.month,
                            day = cDay.day,
                            emotionType = cDay.emotionType,
                            comment = cDay.comment
                    ))
                }

                it.onSuccess(this)
            }
        }
    }
}