package yapp14th.co.kr.myplant.ui.comment

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetComments

class CommentViewModel(app: Application) : BaseViewModel(app) {
    var comments = MutableLiveData<List<CDayVO>>()

    var year = ObservableField<Int>()
    var month = ObservableField<Int>()

    var repository = HomeRepositoryImpl()

    fun setInit(year: Int, month: Int) {
        this.year.set(year)
        this.month.set(month)

        GetComments(repository, Schedulers.io()).invoke(year, month, success = { list ->
            comments.value = list
        }, error = { throwable ->
            System.out.println(throwable)
        })
    }
}