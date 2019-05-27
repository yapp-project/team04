package yapp14th.co.kr.myplant.ui.insert

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.realm.Realm
import io.realm.RealmResults
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil
import yapp14th.co.kr.myplant.utils.getTargetDate
import java.text.SimpleDateFormat
import java.util.*

class InsertViewModel(app: Application) : AndroidViewModel(app) {
    var date: RealmResults<CDay>? = null
    var insertDate = ObservableField<String>()
    var commentStr = ObservableField<String>()

    var emotionsTitle = app.resources.getStringArray(R.array.emotions).toMutableList().apply {
        set(this.size - 1, SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last))
    }

    var emotionsColor = arrayListOf(
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_1),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_2),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_3),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_4),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_5),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_6),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_7),
            SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_8)
    )

    var currentEmotion: Int = 1

    fun setDate(year: Int, month: Int, day: Int, emotionType: Int, comment: String) {
        var calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, day)

        currentEmotion = if (emotionType == -1) 0 else emotionType
        commentStr.set(comment)

        insertDate.set(SimpleDateFormat("MM월 dd일").format(calendar.time))
    }

    fun setTargetDate(year: Int, month: Int, day: Int): Int {
        date = getTargetDate(year, month, day)
        setPosition(date)

        return date?.size ?: 0
    }

    fun setPosition(date: RealmResults<CDay>?) {
        date?.apply {
            currentEmotion = if (size == 0) 1 else this[0].emotionType.toInt()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}