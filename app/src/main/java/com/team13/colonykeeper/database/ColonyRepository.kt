package com.team13.colonykeeper.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ColonyRepository(private val colonyDao: ColonyDao) {
    val allYards: Flow<List<Yard>> = colonyDao.getYards()
    val allHives: Flow<List<Hive>> = colonyDao.getAllHives()

    fun hivesFromYard(yardID: Int): Flow<List<Hive>> = colonyDao.getHivesFromYard(yardID)

    fun getHive(hiveID: Int): Flow<Hive> = colonyDao.getHive(hiveID)

    @WorkerThread
    suspend fun insertHive(hive: Hive) {
        colonyDao.insertHive(hive)
    }

    @WorkerThread
    suspend fun insertYard(yard: Yard) {
        colonyDao.insertYard(yard)
    }

}