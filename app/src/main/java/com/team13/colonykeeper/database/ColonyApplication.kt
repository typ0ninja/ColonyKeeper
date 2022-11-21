package com.team13.colonykeeper.database

import android.app.Application
import android.net.Uri
import com.team13.colonykeeper.R
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
    }

    companion object {
        lateinit var instance: ColonyApplication
            private set
    }
}