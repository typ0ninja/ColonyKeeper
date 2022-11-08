package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.net.URI

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

    @WorkerThread
    suspend fun updateHivePhoto(hive_id: Int, photoURI: Uri){
        colonyDao.updateHivePhoto(hive_id, photoURI)
    }

    @WorkerThread
    suspend fun updateYardPhoto(yard_id: Int, photoURI: Uri){
        colonyDao.updateHivePhoto(yard_id, photoURI)
    }



}