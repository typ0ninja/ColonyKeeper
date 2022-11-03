package com.team13.colonykeeper.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ColonyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ColonyRoomDatabase.getDatabase(this, applicationScope)}
    val yardRepository by lazy { YardRepository(database.yardDao())}
    val hiveRepository by lazy { HiveRepository(database.hiveDao())}
}