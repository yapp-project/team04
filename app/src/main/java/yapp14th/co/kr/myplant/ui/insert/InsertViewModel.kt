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

    fun setTargetDate(realm: Realm, year: Int, month: Int, day: Int): Int {
        realm.beginTransaction();
        date = realm.where(CDay::class.java)
                .equalTo("year", year)
                .equalTo("month", month)
                .equalTo("day", day).findAll()

        realm.commitTransaction();
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

    fun realmInsertDate(realm: Realm, year: Short, month: Short, day: Short, emotionType: Short, comment: String) {
        realm.beginTransaction()

        var maxId = realm.where(CDay::class.java).max("id")
        var nextId = if (maxId == null) 1 else (maxId.toInt() + 1)

        CDay(nextId.toLong(), year, month, day, emotionType, comment).let { cDay ->
            realm.insert(cDay)
            Log.d("insert completed : ", "$cDay")
        }

        realm.commitTransaction()
        // realm.where(CDay::class.java).equalTo("owner", SharedPreferenceUtil.getStringData(SharedPreferenceUtil.email)).equalTo(fieldName, filePath).findAll().deleteAllFromRealm()
    }

    fun realmUpdateDate(realm: Realm, year: Short, month: Short, day: Short, emotionType: Short, comment: String) {
        realm.beginTransaction()

        val updateId = date!![0]?.id!!

        val item = realm.where(CDay::class.java).equalTo("id", updateId).findFirst()
        item?.emotionType = emotionType
        item?.comment = comment

        Log.d("update completed : ", "$item")

        realm.commitTransaction()
        // realm.where(CDay::class.java).equalTo("owner", SharedPreferenceUtil.getStringData(SharedPreferenceUtil.email)).equalTo(fieldName, filePath).findAll().deleteAllFromRealm()
    }
}