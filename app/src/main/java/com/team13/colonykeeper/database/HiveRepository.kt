package com.team13.colonykeeper.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class HiveRepository(private val hiveDao: HiveDao) {
    val allHives: Flow<List<Hive>> = hiveDao.getAllHives()

    fun hivesFromYard(yard: String): Flow<List<Hive>> = hiveDao.getHivesFromYard(yard)

    @WorkerThread
    suspend fun insert(hive: Hive) {
        hiveDao.insert(hive)
    }
}