package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetCalendarList
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYearList
import yapp14th.co.kr.myplant.utils.getCurrentMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear
import java.util.*

class HomeViewModel(app: Application) : BaseViewModel(app) {
    var year = ObservableField<Int>()
    var month = ObservableField<Int>()

    var calendars = MutableLiveData<List<Pair<Int, Int>>>()
    val years = MutableLiveData<List<Int>>()

    private val repositoryImpl = HomeRepositoryImpl()

    init {
        var tempYear = getCurrentYear()
        var tempMonth = getCurrentMonth()

        year.set(tempYear)
        month.set(tempMonth)

        // 년도 spinner update
        GetYearList(repositoryImpl, Schedulers.io()).invoke(
                currentYear = getCurrentYear(),
                success = { list ->
                    years.value = list
                },
                error = { t ->
                    System.out.println(t)
                })

        getCalendarList()
    }

    fun getCalendarList(year: Int = getCurrentYear()) {
        GetCalendarList(repositoryImpl, Schedulers.io()).invoke(
                year = year,
                success = { list ->
                    calendars.value = list
                },
                error = { t ->
                    System.out.println(t)
                })
    }

    override fun onCleared() {
        super.onCleared()
    }
}