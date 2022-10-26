package com.team13.colonykeeper.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class YardListApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { YardRoomDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { YardRepository(database.yardDao())}
}