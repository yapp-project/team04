package buv.co.kr.ui.login.data

import io.reactivex.Single
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear
import yapp14th.co.kr.myplant.utils.getMockDayEmotions

// 서버에서 데이터를 받아온다
class HomeRemoteSource : HomeDataSource {
    override fun getYears(currentYear: Int): Single<List<Int>> {
        // return getNetworkInstance().getAuthRefreshToken(Authorization = Authorization)
        return Single.create<List<Int>> {
            it.onSuccess(listOf(2019))
        }
    }

    override fun getCalendars(year: Int): Single<List<Pair<Int, Int>>> {
        return Single.create<List<Pair<Int, Int>>>{
            listOf(
                    Pair(year, 1),
                    Pair(year, 2),
                    Pair(year, 3),
                    Pair(year, 4),
                    Pair(year, 5),
                    Pair(year, 6),
                    Pair(year, 7),
                    Pair(year, 8),
                    Pair(year, 9),
                    Pair(year, 10),
                    Pair(year, 11),
                    Pair(year, 12))
        }
    }

    override fun getYearEmotions(year: Int): Single<List<CalendarMonth>> {
        return Single.create<List<CalendarMonth>> {
            val emotionsList = mutableListOf<CalendarMonth>()
            for (month in 1..12) {
                emotionsList.add(CalendarMonth(
                        year = getCurrentYear().toShort(),
                        month = month.toShort(),
                        dayList = getMockDayEmotions(year, month)
                ))
            }

            it.onSuccess(emotionsList)
        }
    }
}