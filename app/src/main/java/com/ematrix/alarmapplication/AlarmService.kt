package com.ematrix.alarmapplication

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat


class AlarmService : JobIntentService() {


    companion object {
        @JvmStatic
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, AlarmService::class.java, 1, intent)
        }
    }


    override fun onHandleWork(intent: Intent) {
        showNotification(applicationContext, "hello", "Wake Up! Wake Up!", intent)
    }

    fun showNotification(
        context: Context,
        title: String?,
        body: String?,
        intent: Intent?
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle(title)
            .setContentText(body)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent: PendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, mBuilder.build())
    }


/*    private fun sendNotification(msg: String) {
        Log.d("AlarmService", "Preparing to send notification...: $msg")
        alarmNotificationManager = this
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), 0
        )

        val alamNotificationBuilder = NotificationCompat.Builder(
            this
        ).setContentTitle("Alarm").setSmallIcon(R.drawable.ic_btn_speak_now)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
            .setContentText(msg)
        alamNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager!!.notify(1, alamNotificationBuilder.build())
        Log.d("AlarmService", "Notification sent.")
    }*/


}