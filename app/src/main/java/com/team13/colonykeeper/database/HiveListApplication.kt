package com.team13.colonykeeper.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HiveListApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { HiveRoomDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { HiveRepository(database.hiveDao())}
}