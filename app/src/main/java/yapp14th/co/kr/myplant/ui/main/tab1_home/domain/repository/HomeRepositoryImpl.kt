package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository

import buv.co.kr.ui.login.data.HomeMockSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class HomeRepositoryImpl : HomeRepository {
    val mockHomeSource = HomeMockSource()
    override fun getYearList(currentYear: Int, scheduler: Scheduler, success: (years: List<Int>) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return mockHomeSource.getYearList(currentYear)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ years ->
                    success(years)
                })
                { t ->
                    error(t)
                }
    }

    override fun getCalendarList(year: Int, scheduler: Scheduler, success: (calendars: List<Pair<Int, Int>>) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return mockHomeSource.getCalendarList(year)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ calendars ->
                    success(calendars)
                })
                { t ->
                    error(t)
                }
    }
}