package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetCalendars
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

    fun getCalendarList(year: Int = getCurrentYear()) {
        GetCalendars(repositoryImpl, Schedulers.io()).invoke(
                year = year,
                success = { list ->
                    calendars.value = list
                },
                error = { t ->
                    System.out.println(t)
                })
    }

    fun flipAndFlop(isStartFlip : Boolean){
        isFlip.set(isStartFlip)
        isFlipLive.value = isStartFlip
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getEmotionsList(year : Int = getCurrentYear()) {
        GetYearEmotions(repositoryImpl, Schedulers.io()).invoke(
                year = year,
                success = {list ->
                    emotions.value = list
                },
                error = {t ->
                    System.out.println(t)
                }
        )
    }
}