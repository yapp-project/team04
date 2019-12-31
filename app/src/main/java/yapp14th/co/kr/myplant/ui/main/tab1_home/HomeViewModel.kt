package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.reactivex.schedulers.Schedulers
import yapp14th.co.kr.myplant.base.BaseViewModel
import yapp14th.co.kr.myplant.components.SingleLiveEvent
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.repository.HomeRepositoryImpl
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYearEmotions
import yapp14th.co.kr.myplant.ui.main.tab1_home.domain.usecase.GetYears
import yapp14th.co.kr.myplant.utils.*

class HomeViewModel(app: Application) : BaseViewModel(app) {
    var currentYear = ObservableField<Int>()
    var currentMonth = ObservableField<Int>()

    val isFlip = ObservableField<Boolean>()
    val isFlipLive = MutableLiveData<Boolean>()

    // var calendars = MutableLiveData<List<Pair<Int, Int>>>()
    val year = MutableLiveData<Int>()
    val years = MutableLiveData<List<Int>>()
    val emotions = MutableLiveData<List<CalendarMonth>>()

    private val repositoryImpl = HomeRepositoryImpl()

    init {
        currentYear.set(getCurrentYear())
        currentMonth.set(getCurrentMonth())
        Log.d("DEBUG init", "${currentYear.get()} ${currentMonth.get()}")

        // 년도 spinner update
        var yearUseCase = GetYears(repositoryImpl, Schedulers.io())
        year.value = getCurrentYear()

        yearUseCase(
                currentYear = year.value ?: getCurrentYear(),
                success = { list ->
                    years.value = list
                },
                error = { t ->
                    println(t)
                })

        getEmotionsList()
    }

//    fun getCalendarList(year: Int = getCurrentYear()) {
//        GetCalendars(repositoryImpl, Schedulers.io()).invoke(
//                year = year,
//                success = { list ->
//                    calendars.value = list
//                },
//                error = { t ->
//                    System.out.println(t)
//                })
//    }

    fun flipAndFlop(isStartFlip: Boolean) {
        isFlip.set(isStartFlip)
        emotions.value?.forEach { emotion ->
            emotion.init = false
            emotion.fliped = isStartFlip
        }

        isFlipLive.value = isStartFlip
    }

    fun clearFlipAndFlop() {
        emotions.value?.forEach { emotion ->
            emotion.init = true
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("DEBUG onCleared()", "${currentYear.get()} ${currentMonth.get()}")
        // years.value = listOf()
        // emotions.value = listOf()
    }

    fun getEmotionsList() {
        GetYearEmotions(repositoryImpl, Schedulers.io())(
                year = year.value ?: getCurrentYear(),
                success = { list ->
                    emotions.value = list

                    isFlip.set(false)
                    isFlipLive.value = false
                },
                error = { t ->
                    println(t)
                }
        )
    }

    fun getCurrentMonthData() = currentMonth.get()!! - 1
    fun getCurrentMonthEmotions() = emotions.value?.get(getCurrentMonthData())
    fun getCalendarDays(targetEmotions: ArrayList<CDayVO>): ArrayList<CalendarDay> =
            ArrayList<CalendarDay>().apply {
                targetEmotions.forEach { targetEmotion ->
                    add(CalendarDay.from(targetEmotion.year.toInt(), targetEmotion.month.toInt(), targetEmotion.day.toInt()))
                }
            }

    fun setEmotions() {
        // 달 계산으로 변경
        GetYearEmotions(repositoryImpl, Schedulers.io())(
                year = currentYear.get() ?: getCurrentYear(),
                success = { list ->
                    emotions.value?.get(currentMonth.get()
                            ?: getCurrentRefinedMonth())?.dayList = list[currentMonth.get()
                            ?: getCurrentRefinedMonth()].dayList
                },
                error = { t ->
                    System.out.println(t)
                }
        )
    }


    fun getBiggestEmotionImage(month: Int): Int {
        val monthDays = emotions.value!![month].dayList
        val array = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in monthDays.indices) {
            array[monthDays[i].emotionType.toInt()]++
        }

        var maxIndex = 0
        for (emotionNum in 0 until array.size - 1) {
            maxIndex = if (array[maxIndex] < array[emotionNum + 1]) emotionNum + 1 else maxIndex
        }

        return getcalendarResources()[maxIndex]
    }

    fun getSecondEmotionFilter(month: Int): Int {
        val monthDays = emotions.value!![month].dayList
        val array = arrayOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0), intArrayOf(3, 0), intArrayOf(4, 0), intArrayOf(5, 0), intArrayOf(6, 0), intArrayOf(7, 0), intArrayOf(8, 0))
        for (i in monthDays.indices) {
            array[monthDays[i].emotionType.toInt()][1]++
        }

        JavaUtil.sort(array, 0, array.size - 1)
        val maxIndex = array[0][0]
        var secondIndex = array[1][0]
        if (array[1][1] == 0)
            secondIndex = maxIndex

        return adjustAlpha(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_$secondIndex")), 0.4f)
//        var array = arrayOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0), Pair(6, 0), Pair(7, 0), Pair(8, 0))
//        emotions.value!![month].dayList.forEach { cDayVO ->
//            val newPair = array[cDayVO.emotionType.toInt()].apply {
//                copy(first = this.first, second = this.second + 1)
//            }
//            array[cDayVO.emotionType.toInt()] = newPair
//        }
//
//        var newList = array.sortedWith(compareBy { it.second }).reversed()
//
//        val maxIndex = if(newList[1].second == 0) 0 else newList[1].first
//        Log.d("${month + 1}월 : ", "EMOTION_$maxIndex")
//
//        return adjustAlpha(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_$maxIndex")), 0.4f)
    }

//    fun releasePrevPosition() {
//        emotionsColor.value?.get(getCurrentMonthData())?.init = true
//    }
}
