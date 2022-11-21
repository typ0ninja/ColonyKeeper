package com.team13.colonykeeper.database

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.team13.colonykeeper.R
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ColonyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ColonyRoomDatabase.getDatabase(this, applicationScope)}
    val colonyRepository by lazy { ColonyRepository(database.colonyDao())}
    lateinit var curYard: Yard
    lateinit var curHive: Hive

    val DEFAULT_URI = Uri.parse("android.resource://com.team13.colonykeeper/" + R.drawable.beehive_temp)

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "inspection_channel"
            val descriptionText = "inspection_reminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val inspectionChannel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(inspectionChannel)
        }
    }

    companion object {
        lateinit var instance: ColonyApplication
            private set
        const val CHANNEL_ID = "water_reminder_id"
    }
}