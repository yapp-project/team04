package yapp14th.co.kr.myplant.ui.main.tab1_home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import yapp14th.co.kr.myplant.BR

data class CalendarMonth(
        @Bindable var _year: Short,
        @Bindable var _month: Short,
        @Bindable var _fliped: Boolean = false,
        @Bindable var _init: Boolean = true,
        @Bindable var _dayList: List<CDayVO>) : BaseObservable() {

    var year: Short
        get() = _year
        set(value) {
            _year = value
        }
    var month: Short
        get() = _month
        set(value) {
            _month = value
        }
    var fliped: Boolean
        get() = _fliped
        set(value) {
            _fliped = value
            notifyPropertyChanged(BR.fliped)
        }
    var init: Boolean
        get() = _init
        set(value) {
            _init = value
            notifyPropertyChanged(BR.init)
        }
    var dayList: List<CDayVO>
        get() = _dayList
        set(value) {
            _dayList = value
        }
}