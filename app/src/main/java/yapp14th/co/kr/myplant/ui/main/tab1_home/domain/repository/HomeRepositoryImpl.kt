package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository

import buv.co.kr.ui.login.data.HomeMockSource
import buv.co.kr.ui.login.data.HomeRemoteSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth

class HomeRepositoryImpl : HomeRepository {
    val mockHomeSource = HomeMockSource()
    val remoteHomeSource = HomeRemoteSource()

    override fun getYears(currentYear: Int, scheduler: Scheduler, success: (years: List<Int>) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return mockHomeSource.getYears(currentYear)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ years ->
                    success(years)
                })
                { t ->
                    error(t)
                }
    }

    override fun getCalendars(year: Int, scheduler: Scheduler, success: (calendars: List<Pair<Int, Int>>) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return mockHomeSource.getCalendars(year)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ calendars ->
                    success(calendars)
                })
                { t ->
                    error(t)
                }
    }

    override fun getYearEmotions(year: Int, scheduler: Scheduler, success: (emotions: List<CalendarMonth>) -> Unit, error: (throwable: Throwable) -> Unit): Disposable {
        return remoteHomeSource.getYearEmotions(year)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ emotions ->
                    success(emotions)
                })
                { t ->
                    error(t)
                }
    }
}