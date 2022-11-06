package com.team13.colonykeeper.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ColonyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ColonyRoomDatabase.getDatabase(this, applicationScope)}
    val colonyRepository by lazy { ColonyRepository(database.colonyDao())}
    lateinit var curYard: Yard
    lateinit var curHive: Hive

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ColonyApplication
            private set
    }
}