package com.team13.colonykeeper.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class HiveRepository(private val hiveDao: HiveDao) {
    val allHives: Flow<List<Hive>> = hiveDao.getAllHives()

    fun hivesFromYard(yardID: Int): Flow<List<Hive>> = hiveDao.getHivesFromYard(yardID)

    fun getHive(hiveID: Int): Flow<Hive> = hiveDao.getHive(hiveID)

    @WorkerThread
    suspend fun insert(hive: Hive) {
        hiveDao.insert(hive)
    }
}