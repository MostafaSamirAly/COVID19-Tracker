package com.example.covid19_tracker

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        val desc = "Check Updates About COVID-19"
        showNotification("CORONA", desc!!)
        return Result.success()
    }

    private fun showNotification(title: String, desc: String) {
        var manager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel =
                NotificationChannel("notification", "notification", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        var builder = NotificationCompat.Builder(applicationContext, "notification")
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_corona_logo_round)
        manager.notify(100, builder.build())
    }
}