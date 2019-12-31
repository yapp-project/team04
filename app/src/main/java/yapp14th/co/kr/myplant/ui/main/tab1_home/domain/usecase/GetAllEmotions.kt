package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase

import buv.co.kr.base.rx.RxUseCase
import io.reactivex.Scheduler
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepository

class GetAllEmotions(val homeRepository: HomeRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(
            success: (calendars: List<CalendarMonth>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ) {
        disposable = homeRepository.getAllEmotions(
                scheduler = scheduler,
                success = success,
                error = error
        )
    }
}
