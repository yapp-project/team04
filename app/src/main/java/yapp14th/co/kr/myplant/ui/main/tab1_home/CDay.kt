package yapp14th.co.kr.myplant.ui.main.tab1_home

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CDay(
        @PrimaryKey
        var id: Long,
        var year: Short,
        var month: Short,
        var day: Short,
        var emotionType: Short,
        var comment: String
) : RealmObject() {
    constructor() : this(
            id = 0L,
            year = 2019,
            month = 4,
            day = 1,
            emotionType = 1,
            comment = "")
}