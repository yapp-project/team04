package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth

interface HomeRepository {
    fun getYears(
            currentYear: Int,
            scheduler: Scheduler,
            success: (years: List<Int>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ): Disposable

//    fun getCalendars(
//            year : Int,
//            scheduler: Scheduler,
//            success: (calendars: List<Pair<Int, Int>>) -> Unit,
//            error: (throwable: Throwable) -> Unit
//    ): Disposable

    fun getYearEmotions(
            year: Int,
            scheduler: Scheduler,
            success: (emotions: List<CalendarMonth>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ): Disposable

    fun getAllEmotions(
        scheduler: Scheduler,
        success: (emotions: List<CalendarMonth>) -> Unit,
        error: (throwable: Throwable) -> Unit
    ): Disposable

    fun getComments(
            year: Int,
            month: Int,
            scheduler: Scheduler,
            success: (emotions: List<CDayVO>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ): Disposable
}
