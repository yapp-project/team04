package yapp14th.co.kr.myplant.utils.alarm

import android.content.Context
import android.content.Intent
import android.util.Log
import sisonfarmtory.co.kr.drrecipe.ui.main.AlarmRepeatBroadCastReciever

class AlarmOneMinuteBroadcastReceiver : AlarmRepeatBroadCastReciever() {
    // 알람을 받음
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        Log.d("", "not repeat 시작")
        // 알람 스타트
        AlarmUtil.instance.startAlarm(context, 60000)

        // if (!SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.eventDateClicked))
        NotificationManager.sendNotification(context, 3, NotificationManager.Channel.NOTICE, "보라 님", "오늘의 감정을 기록해주세요 :)")
    }
}