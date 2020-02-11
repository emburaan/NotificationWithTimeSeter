package com.ematrix.alarmapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.legacy.content.WakefulBroadcastReceiver


class AlarmReceiver : WakefulBroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        fun onStartJobIntentService() {
            val mIntent = Intent(context, AlarmService::class.java)
            AlarmService.enqueueWork(context, mIntent)
        }
        val comp = ComponentName(
            context.packageName,
            AlarmService::class.java.name
        )
        onStartJobIntentService()
        startWakefulService(context, intent.setComponent(comp))
        resultCode = Activity.RESULT_OK
    }
}