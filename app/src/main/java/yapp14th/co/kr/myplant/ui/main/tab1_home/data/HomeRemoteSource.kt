package yapp14th.co.kr.myplant.ui.main.tab1_home.data

import buv.co.kr.ui.login.data.HomeDataSource
import io.reactivex.Single
import io.realm.Realm
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.getGeneratedDayEmotions

// 서버에서 데이터를 받아온다
class HomeRemoteSource : HomeDataSource {
    override fun getYears(currentYear: Int): Single<List<Int>> {
        // return getNetworkInstance().getAuthRefreshToken(Authorization = Authorization)
        return Single.create<List<Int>> {
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

            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val emotions = mutableListOf<CalendarMonth>()
            for (month in 1..12) {
                var monthData = realm.where(CDay::class.java)
                        .equalTo("year", year.toShort())
                        .equalTo("month", month)
                        .findAllSorted("emotionType")

                emotions.add(CalendarMonth(
                        _year = year.toShort(),
                        _month = month.toShort(),
                        _dayList = getGeneratedDayEmotions(monthData)
                ))
            }
            realm.commitTransaction()
            it.onSuccess(emotions)
        }
    }

    override fun getComments(year: Int, month: Int): Single<List<CDayVO>> {
        return Single.create<List<CDayVO>> {

            val realm = Realm.getDefaultInstance()

            val comments = mutableListOf<CDayVO>()
            realm.beginTransaction()
            var monthData = realm.where(CDay::class.java)
                    .equalTo("year", year)
                    .equalTo("month", month).findAllSorted("day")

            monthData.forEach { cDay ->
                comments.add(CDayVO(
                        year = cDay.year,
                        month = cDay.month,
                        day = cDay.day,
                        emotionType = cDay.emotionType,
                        comment = cDay.comment
                ))
            }
            realm.commitTransaction()
            it.onSuccess(comments)
        }
    }
}