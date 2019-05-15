package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase

import buv.co.kr.base.rx.RxUseCase
import io.reactivex.Scheduler
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepository

class GetYearEmotions(val homeRepository: HomeRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(
            year: Int,
            success: (calendars: List<CalendarMonth>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ) {
        disposable = homeRepository.getYearEmotions(
                year = year,
                scheduler = scheduler,
                success = success,
                error = error
        )
    }
}