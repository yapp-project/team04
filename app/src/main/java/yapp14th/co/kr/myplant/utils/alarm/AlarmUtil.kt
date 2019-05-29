package yapp14th.co.kr.myplant.utils.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*
import android.util.Log


class AlarmUtil {
    var TEST = (60 * 1000).toLong()
    var TIME_24H = (24 * 60 * 60 * 1000).toLong()

    fun startEightPMAlarm(context: Context) {
        // AlarmOneMinuteBroadcastReceiver 초기화
        val alarmIntent = Intent(context, AlarmRepeatBroadCastReciever::class.java)
        alarmIntent.putExtra("sName", "OneMinute")

        // 알람이 있는지 확인   출처: https://migom.tistory.com/10 [devlog.gitlab.io]
        val findIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, PendingIntent.FLAG_NO_CREATE)
        if (findIntent == null) {
            // TODO: 이미 설정된 알람이 없는 경우
            Log.d("startEightPMAlarm ", "설정된 알람이 없음")

            // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
            var todayCalendar = Calendar.getInstance()
            var tomorrow8PMCalendar = Calendar.getInstance()
            tomorrow8PMCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DATE), 20, 0, 0)

            // 이미 알람 시간을 지났을 경우
            if (tomorrow8PMCalendar.timeInMillis - Calendar.getInstance().timeInMillis < 0){
                Log.d("startEightPMAlarm ", "이미 알람 시간을 지났을 경우")
                tomorrow8PMCalendar.set(Calendar.DATE, tomorrow8PMCalendar.get(Calendar.DATE) + 1)
                Log.d("startEightPMAlarm ", "다음날 알람 등록 완료")
            }
            startRepeat(context, tomorrow8PMCalendar.timeInMillis, AlarmManager.INTERVAL_DAY)
            Log.d("startEightPMAlarm ", "알람 새로 등록")

        } else {
            // TODO: 이미 설정된 알람이 있는 경우
            Log.d("startEightPMAlarm ", "설정된 알람이 있음")
            Log.d("startEightPMAlarm ", "알람 등록할 필요 없음")
//            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            am.cancel(findIntent)
//            findIntent.cancel()
        }
    }

    fun clearAlarm(context: Context) {
        val alarmIntent = Intent(context, AlarmRepeatBroadCastReciever::class.java)
        alarmIntent.putExtra("sName", "OneMinute")

        val findIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, PendingIntent.FLAG_NO_CREATE)
        if (findIntent == null) {
            // TODO: 이미 설정된 알람이 없는 경우
            Log.d("clearAlarm ", "설정된 알람이 없음")
            Log.d("clearAlarm ", "clear 할 필요 없음")
        } else {
            // TODO: 이미 설정된 알람이 있는 경우
            Log.d("clearAlarm ", "설정된 알람이 있음")
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(findIntent)
            findIntent.cancel()
            Log.d("clearAlarm ", "clear 완료")
        }
    }


    fun startAlarm(context: Context, delay: Int) {
        // AlarmManager 호출
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(context, AlarmRepeatBroadCastReciever::class.java)
        alarmIntent.putExtra("sName", "OneMinute")
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)
        }
    }

    fun startRepeat(context: Context, dateTime: Long, delay: Long) {
        val alarmIntent = Intent(context, AlarmRepeatBroadCastReciever::class.java)
        alarmIntent.putExtra("sName", "Repeat")
        val pendingIntent = PendingIntent.getBroadcast(context, 1001, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dateTime, delay, pendingIntent)
    }

    companion object {
        private val ONE_MINUES = 60 * 1000

        private var _instance: AlarmUtil? = null

        val instance: AlarmUtil
            get() {
                if (_instance == null)
                    _instance = AlarmUtil()
                return _instance as AlarmUtil
            }
    }
}