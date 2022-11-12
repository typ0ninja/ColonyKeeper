package com.team13.colonykeeper.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.team13.colonykeeper.MainActivity
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.ColonyApplication

class InspectionNotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    // Arbitrary id number
    val notificationId = 17

    override fun doWork(): Result {
//        val intent = Intent(applicationContext, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent = PendingIntent
//            .getActivity(applicationContext, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(applicationContext, ColonyApplication.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_baseline_hive_24)
//            .setContentTitle("Inspection Time!")
//            .setContentText("It's time to inspect your hives")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(applicationContext)) {
//            notify(notificationId, builder.build())
//        }

        return Result.success()
    }
}