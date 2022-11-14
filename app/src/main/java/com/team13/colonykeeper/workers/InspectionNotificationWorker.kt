package com.team13.colonykeeper.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.team13.colonykeeper.MainActivity
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyApplication.Companion.CHANNEL_ID

class InspectionNotificationWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {
    private val NOTIFICATION_ID = 1

    override fun doWork(): Result {
        Log.d("Worker", "In Do Work!")
        val intent = Intent(context, MainActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_baseline_hive_24)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_hive_24)
            .setLargeIcon(icon)
            .setContentTitle(inputData.getString("title").toString())
            .setContentText(inputData.getString("message").toString())
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        return Result.success()
    }
}