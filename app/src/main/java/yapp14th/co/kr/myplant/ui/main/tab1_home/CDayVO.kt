package yapp14th.co.kr.myplant.ui.main.tab1_home

import yapp14th.co.kr.myplant.utils.getEmotionsList

data class CDayVO(
        var year: Short,
        var month: Short,
        var day: Short,
        var emotionType: Short,
        var comment: String
) {
    fun getEmotionStr(emotionType: Short): String {
        return getEmotionsList()[emotionType - 1]
    }
}
