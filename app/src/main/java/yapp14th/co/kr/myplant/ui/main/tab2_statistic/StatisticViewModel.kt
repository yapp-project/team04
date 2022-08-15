package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYearEmotions
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYears
import yapp14th.co.kr.myplant.utils.getCurrentMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear

class StatisticViewModel(app: Application) : BaseViewModel(app) {
    var currentYear = ObservableField<Int>()
    var currentMonth = ObservableField<Int>()

    val emotions = MutableLiveData<List<CalendarMonth>>()

    // val avEmotions = MutableLiveData<List<Pair<Int, Short>>>()
    val years = MutableLiveData<List<Int>>()

    private val repositoryImpl = HomeRepositoryImpl()

    init {
        val tempYear = getCurrentYear()
        val tempMonth = getCurrentMonth()

        currentYear.set(tempYear)
        currentMonth.set(tempMonth)

        GetYears(repositoryImpl, Schedulers.io())(
            currentYear = getCurrentYear(),
            success = { list ->
                years.value = list
            },
            error = { t ->
                println(t)
            })

        getEmotionsList(tempYear)
    }

    fun getEmotionsList(selectedYear: Int) {
        currentYear.set(selectedYear)
        GetYearEmotions(repositoryImpl, Schedulers.io())(
            year = currentYear.get() ?: 2019,
            success = { list ->
                emotions.value = list
            },
            error = { t ->
                println(t)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}
