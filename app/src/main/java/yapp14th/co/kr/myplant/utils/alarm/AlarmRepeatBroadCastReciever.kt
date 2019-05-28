package sisonfarmtory.co.kr.drrecipe.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import yapp14th.co.kr.myplant.utils.alarm.NotificationManager
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil
import yapp14th.co.kr.myplant.utils.alarm.Constants


open class AlarmRepeatBroadCastReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        isLaunched = true

        Log.d("", "repeat 시작")
        if (!SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.PUSH_CHECK_ENABLED)) {
            NotificationManager.sendNotification(context, 3, NotificationManager.Channel.NOTICE, "보라 님", "오늘의 감정을 기록해주세요 :)")
            // 현재 시간을 화면에 보낸다.
            saveTime(context, "Bora")
        }
    }

    private fun saveTime(context: Context, name: String) {
        val currentTime = System.currentTimeMillis()
        val intent = Intent(Constants.INTENTFILTER_BROADCAST_TIMER)
        intent.putExtra(Constants.KEY_DEFAULT, currentTime)
        intent.putExtra("sName", name)
        context.sendBroadcast(intent)
    }

    companion object {
        var isLaunched = false
    }
}
