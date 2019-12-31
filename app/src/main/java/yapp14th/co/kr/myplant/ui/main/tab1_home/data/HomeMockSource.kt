package buv.co.kr.ui.login.data

import androidx.databinding.library.baseAdapters.BR.year
import io.reactivex.Single
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.getCurrentMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear
import yapp14th.co.kr.myplant.utils.getMockDayEmotions
import java.util.*

// 더미 데이터를 받아온다
class HomeMockSource : HomeDataSource {
    override fun getYears(year: Int): Single<List<Int>> {
        var yearList = mutableListOf<Int>()
        var calendar = Calendar.getInstance()
        var currentYear = calendar.get(Calendar.YEAR)

        for (i in 2019..currentYear) {
            yearList.add(i)
        }

        return Single.create<List<Int>> {
            it.onSuccess(yearList)
        }
    }

//    override fun getCalendars(year: Int): Single<List<Pair<Int, Int>>> {
//        return Single.create<List<Pair<Int, Int>>> {
//            it.onSuccess(
//                    listOf(
//                            Pair(year, 1),
//                            Pair(year, 2),
//                            Pair(year, 3),
//                            Pair(year, 4),
//                            Pair(year, 5),
//                            Pair(year, 6),
//                            Pair(year, 7),
//                            Pair(year, 8),
//                            Pair(year, 9),
//                            Pair(year, 10),
//                            Pair(year, 11),
//                            Pair(year, 12)))
//        }
//    }

    override fun getYearEmotions(year: Int): Single<List<CalendarMonth>> {
        return Single.create<List<CalendarMonth>> {
            val emotionsList = mutableListOf<CalendarMonth>()
            for (month in 1..12) {
                emotionsList.add(
                    CalendarMonth(
                        _year = getCurrentYear().toShort(),
                        _month = month.toShort(),
                        _dayList = getMockDayEmotions(year, month)
                    )
                )
            }

            it.onSuccess(emotionsList)
        }
    }

    override fun getAllEmotions(): Single<List<CalendarMonth>> {
        return Single.create<List<CalendarMonth>> {
            val yearList = mutableListOf<Int>().apply {
                val currentYear = getCurrentYear()

                for (i in 2019..currentYear) {
                    add(i)
                }
            }

            val emotionsList = mutableListOf<CalendarMonth>()

            for (year in yearList) {
                for (month in 1..12) {
                    emotionsList.add(
                        CalendarMonth(
                            _year = getCurrentYear().toShort(),
                            _month = month.toShort(),
                            _dayList = getMockDayEmotions(year, month)
                        )
                    )
                }
            }

            it.onSuccess(emotionsList)
        }
    }


    override fun getComments(year: Int, month: Int): Single<List<CDayVO>> {
        return Single.create<List<CDayVO>> {
            val comments = mutableListOf<CDayVO>()

            arrayListOf(1, 3, 6, 7, 9).forEach { day ->
                comments.add(
                    CDayVO(
                        year = getCurrentYear().toShort(),
                        month = getCurrentMonth().toShort(),
                        day = day.toShort(),
                        emotionType = ((Math.random() * 7) + 1).toShort(),
                        comment = "abcd"
                    )
                )
            }

            it.onSuccess(comments)
        }
    }
}
