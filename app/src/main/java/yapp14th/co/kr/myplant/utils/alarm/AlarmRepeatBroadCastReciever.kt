package yapp14th.co.kr.myplant.utils.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil
import yapp14th.co.kr.myplant.utils.getTargetDate
import java.util.*


open class AlarmRepeatBroadCastReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        isLaunched = true
        Log.d("", "repeat 시작")

        // 1. Push 허용 유무(true) && 색깔 선택 다했는지(true) 확인
        if (SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.PUSH_CHECK_ENABLED)
                && SharedPreferenceUtil.getBooleanData(SharedPreferenceUtil.COLOR_PICK_FINISHED)) {

            // 2. 오늘 등록된 감정이 있는지 확인
            val today = Calendar.getInstance()
            val result = getTargetDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DATE))

            // 2-1. 오늘 등록된 감정이 없다면 알람
            if (result.size == 0) {
                NotificationManager.sendNotification(context, 3, NotificationManager.Channel.NOTICE, "보라 님", "오늘의 감정을 기록해주세요 :)")
                // 현재 시간을 화면에 보낸다.
                saveTime(context, "Bora")
            }
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
