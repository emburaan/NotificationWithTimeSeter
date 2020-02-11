package com.ematrix.alarmapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var pendingIntent: PendingIntent
    lateinit var alarmManager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager;


  /*      cancel.setOnClickListener {
            cancelAlarm()
            //this.alarmManager.cancel(pendingIntent)

            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            //  alarmManager.cancel()
        }*/

    }

    fun OnToggleClicked(view: View) {
        var time: Long
        if ((view as ToggleButton).isChecked) {
            Toast.makeText(this@MainActivity, "ALARM ON", Toast.LENGTH_SHORT).show()
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour())
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute())
            val intent = Intent(this, AlarmReceiver::class.java)
           ContextCompat.startForegroundService(this,intent)
            pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent,0)
            time = calendar.getTimeInMillis() - calendar.getTimeInMillis() % 60000
            if (System.currentTimeMillis() > time) {
                time =
                    if (Calendar.AM_PM === 0) time + 1000 * 60 * 60 * 12 else time + 1000 * 60 * 60 * 24
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)

        } else {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this@MainActivity, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
        /*cancel.setOnClickListener {
            alarmManager.cancel(pendingIntent)
        }*/
    }



    fun cancelAlarm() {
        if (alarmManager != null) {
            Log.e("alarm", "ringing")
            if (pendingIntent != null) {
                Log.e("alarm", "pending intent")
                this.alarmManager.cancel(pendingIntent)
            }
        }
//        val intent = Intent(this, AlarmReceiver::class.java)
//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        alarmManager =  getSystemService(ALARM_SERVICE) as AlarmManager;
//        alarmManager.cancel(pendingIntent)
    }
}
