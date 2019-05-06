package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

interface HomeRepository {
    fun getYearList(
            currentYear: Int,
            scheduler: Scheduler,
            success: (currentYear: List<Int>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ): Disposable

    fun getCalendarList(
            year : Int,
            scheduler: Scheduler,
            success: (currentYear: List<Pair<Int, Int>>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ): Disposable
}