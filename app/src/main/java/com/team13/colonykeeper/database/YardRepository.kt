package com.team13.colonykeeper.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class YardRepository(private val yardDao: YardDao) {
    val allYards: Flow<List<Yard>> = yardDao.getYards()

    @WorkerThread
    suspend fun insert(yard: Yard) {
        yardDao.insert(yard)
    }
}