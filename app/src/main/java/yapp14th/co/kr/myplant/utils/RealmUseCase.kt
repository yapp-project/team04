package yapp14th.co.kr.myplant.utils

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDay
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth

// 특정 년도의 모든 emotions를 뽑아옴
fun getTargetYearEmotions(year: Int): MutableList<CalendarMonth> {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    val emotions = mutableListOf<CalendarMonth>()

    for (month in 1..12) {
        var monthData = realm.where(CDay::class.java)
                .equalTo("year", year.toShort())
                .equalTo("month", month)
                .findAllSorted("emotionType")

        emotions.add(CalendarMonth(
                _year = year.toShort(),
                _month = month.toShort(),
                _dayList = getGeneratedDayEmotions(monthData)))

    }

    realm.commitTransaction()

    return emotions
}

// 특정 달의 모든 Comment를 가져옴
fun getTargetMonthDates(year: Int, month: Int): RealmResults<CDay>? {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    val comments = mutableListOf<CDayVO>()

    var monthData = realm.where(CDay::class.java)
            .equalTo("year", year)
            .equalTo("month", month).findAllSorted("day")

    realm.commitTransaction()
    return monthData
}

// 특정 일의 데이터를 가져옴
fun getTargetDate(year: Int, month: Int, day: Int): RealmResults<CDay> {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    var date = realm.where(CDay::class.java)
            .equalTo("year", year)
            .equalTo("month", month)
            .equalTo("day", day).findAll()

    realm.commitTransaction()
    return date
}


fun insertDate(year: Short, month: Short, day: Short, emotionType: Short, comment: String) {
    val realm = Realm.getDefaultInstance()
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

fun updateDate(year: Short, date: RealmResults<CDay>?, month: Short, day: Short, emotionType: Short, comment: String) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    date?.let { date ->
        val updateId = date[0]?.id!!

        val item = realm.where(CDay::class.java).equalTo("id", updateId).findFirst()
        item?.emotionType = emotionType
        item?.comment = comment

        Log.d("update completed : ", "$item")

        realm.commitTransaction()
    }
    // realm.where(CDay::class.java).equalTo("owner", SharedPreferenceUtil.getStringData(SharedPreferenceUtil.email)).equalTo(fieldName, filePath).findAll().deleteAllFromRealm()
}

fun getEmotionsCount(): Int {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    val abcd = realm.where(CDay::class.java).findAll()

    realm.commitTransaction()
    return abcd.size
}

fun getAlbumsCount(): Int {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()

    val abcd = realm.where(CDay::class.java).findAll()

    realm.commitTransaction()
    return abcd.size
}