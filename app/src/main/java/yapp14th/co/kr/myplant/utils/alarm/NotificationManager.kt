package yapp14th.co.kr.myplant.utils.alarm

import android.app.*
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.ui.intro.PushAgreeActivity

object NotificationManager {

    private val GROUP_DR_BORA = "Bora"

    private val smallIcon: Int
        get() = android.R.drawable.stat_notify_chat

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(context: Context) {
        val group1 = NotificationChannelGroup(GROUP_DR_BORA, GROUP_DR_BORA)
        getManager(context).createNotificationChannelGroup(group1)

        val channelMessage = NotificationChannel(yapp14th.co.kr.myplant.utils.alarm.NotificationManager.Channel.MESSAGE, "Message", android.app.NotificationManager.IMPORTANCE_DEFAULT)
        channelMessage.description = "Receive notification about new message."
        channelMessage.group = GROUP_DR_BORA
        channelMessage.lightColor = Color.GREEN
        channelMessage.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager(context).createNotificationChannel(channelMessage)

        val channelComment = NotificationChannel(yapp14th.co.kr.myplant.utils.alarm.NotificationManager.Channel.COMMENT, "Comment", android.app.NotificationManager.IMPORTANCE_DEFAULT)
        channelComment.description = "Receive notification about your post or related post\\'s comment."
        channelComment.group = GROUP_DR_BORA
        channelComment.lightColor = Color.BLUE
        channelComment.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager(context).createNotificationChannel(channelComment)

        val channelNotice = NotificationChannel(yapp14th.co.kr.myplant.utils.alarm.NotificationManager.Channel.NOTICE,
                "Notice", android.app.NotificationManager.IMPORTANCE_HIGH)
        channelNotice.description = "Receive notification about our service\\'s notice"
        channelNotice.group = GROUP_DR_BORA
        channelNotice.lightColor = Color.RED
        channelNotice.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager(context).createNotificationChannel(channelNotice)
    }

    private fun getManager(context: Context): android.app.NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteChannel(context: Context, channel: String) {
        getManager(context).deleteNotificationChannel(channel)

    }

    fun sendNotification(context: Context, id: Int, channel: String, title: String, body: String) {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(smallIcon)
                    .setAutoCancel(true)
        } else {
            Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
        }

        val notificationIntent = Intent(context, PushAgreeActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntentActivity = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        builder.setContentIntent(pendingIntentActivity)

        getManager(context).notify(id, builder.build())
    }

    object Channel {
        const val MESSAGE = "message"
        const val COMMENT = "comment"
        const val NOTICE = "notice"
    }
}