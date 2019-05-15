package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYearEmotions
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYears
import yapp14th.co.kr.myplant.utils.getCurrentMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear

class HomeViewModel(app: Application) : BaseViewModel(app) {
    var currentYear = ObservableField<Int>()
    var currentMonth = ObservableField<Int>()

    val isFlip = ObservableField<Boolean>()
    val isFlipLive = MutableLiveData<Boolean>()

    var calendars = MutableLiveData<List<Pair<Int, Int>>>()
    val years = MutableLiveData<List<Int>>()
    val emotions = MutableLiveData<List<CalendarMonth>>()

    private val repositoryImpl = HomeRepositoryImpl()

    init {
        var tempYear = getCurrentYear()
        var tempMonth = getCurrentMonth()

        currentYear.set(tempYear)
        currentMonth.set(tempMonth)

        // 년도 spinner update
        GetYears(repositoryImpl, Schedulers.io()).invoke(
                currentYear = getCurrentYear(),
                success = { list ->
                    years.value = list
                },
                error = { t ->
                    System.out.println(t)
                })

        getEmotionsList()
    }

//    fun getCalendarList(year: Int = getCurrentYear()) {
//        GetCalendars(repositoryImpl, Schedulers.io()).invoke(
//                year = year,
//                success = { list ->
//                    calendars.value = list
//                },
//                error = { t ->
//                    System.out.println(t)
//                })
//    }

    fun flipAndFlop(isStartFlip: Boolean) {
        isFlip.set(isStartFlip)
        emotions.value?.forEach { emotion ->
            emotion.init = false
            emotion.fliped = isStartFlip
        }
        isFlipLive.value = isStartFlip
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getEmotionsList(year: Int = getCurrentYear()) {
        GetYearEmotions(repositoryImpl, Schedulers.io()).invoke(
                year = year,
                success = { list ->
                    emotions.value = list
                },
                error = { t ->
                    System.out.println(t)
                }
        )
    }

    fun getCurrentMonthData() = currentMonth.get()!! - 1
    fun getCurrentMonthEmotions() = emotions.value?.get(getCurrentMonthData())
    fun getCalendarDays(year: Int, month: Int, maximumDay: Int): HashSet<CalendarDay> =
            HashSet<CalendarDay>().apply {
                for (i in 1..maximumDay) {
                    add(CalendarDay.from(year, month, i))
                }
            }


//    fun releasePrevPosition() {
//        emotionsColor.value?.get(getCurrentMonthData())?.init = true
//    }
}