package yapp14th.co.kr.myplant.utils.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ClosingService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)

        // Handle application closing
        // AlarmUtil.instance.startEightPMAlarm(this)

        // Destroy the service
        stopSelf()
    }
}
