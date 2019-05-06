package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase

import buv.co.kr.base.rx.RxUseCase
import io.reactivex.Scheduler
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepository

class GetCalendars(val homeRepository: HomeRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(
            year: Int,
            success: (calendars: List<Pair<Int, Int>>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ) {
        disposable = homeRepository.getCalendars(
                year = year,
                scheduler = scheduler,
                success = success,
                error = error
        )
    }
}