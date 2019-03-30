package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.app.Application
import androidx.databinding.ObservableField
import yapp14th.co.kr.myplant.base.BaseViewModel
import java.util.*

class HomeViewModel(app: Application) : BaseViewModel(app) {
    var year = ObservableField<Int>()
    var month = ObservableField<Int>()
    var list = ArrayList<Pair<Int, Int>>()

    init {
        var tempYear = Calendar.getInstance().get(Calendar.YEAR)
        var tempMonth = Calendar.getInstance().get(Calendar.MONTH)

        year.set(tempYear)
        month.set(tempMonth)

        list = arrayListOf(
                Pair(tempYear, 1),
                Pair(tempYear, 2),
                Pair(tempYear, 3),
                Pair(tempYear, 4),
                Pair(tempYear, 5),
                Pair(tempYear, 6),
                Pair(tempYear, 7),
                Pair(tempYear, 8),
                Pair(tempYear, 9),
                Pair(tempYear, 10),
                Pair(tempYear, 11),
                Pair(tempYear, 12))
    }

    override fun onCleared() {
        super.onCleared()
    }
}