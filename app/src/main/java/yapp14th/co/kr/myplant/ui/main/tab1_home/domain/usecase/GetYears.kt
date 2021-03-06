package yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase

import buv.co.kr.base.rx.RxUseCase
import io.reactivex.Scheduler
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepository

class GetYears(val homeRepository: HomeRepository, val scheduler: Scheduler) : RxUseCase() {
    operator fun invoke(
            currentYear: Int,
            success: (years: List<Int>) -> Unit,
            error: (throwable: Throwable) -> Unit
    ) {
        disposable = homeRepository.getYears(
                currentYear = currentYear,
                scheduler = scheduler,
                success = success,
                error = error
        )
    }
}