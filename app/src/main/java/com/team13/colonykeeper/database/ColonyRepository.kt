package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.WorkerThread
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.net.URI

class ColonyRepository(private val colonyDao: ColonyDao) {
    val allYards: Flow<List<Yard>> = colonyDao.getYards()
    val allHives: Flow<List<Hive>> = colonyDao.getAllHives()

    fun hivesFromYard(yardID: Int): Flow<List<Hive>> = colonyDao.getHivesFromYard(yardID)

    fun getHive(hiveID: Int): Flow<Hive> = colonyDao.getHive(hiveID)

    fun getYard(yard_id: Int): Flow<Yard> = colonyDao.getYard(yard_id)

    //scheduled inspections

    fun getAllScheduled(): Flow<List<Scheduled>> = colonyDao.getAllScheduled()

    fun getScheduled(scheduled_id: Int): Flow<Scheduled> = colonyDao.getScheduled(scheduled_id)

    fun getYardScheduled(yard_id: Int): Flow<List<Scheduled>> = colonyDao.getYardScheduled(yard_id)

    fun getTagScheduled(tag: String): Flow<List<Scheduled>> = colonyDao.getTagScheduled(tag)

    fun getInspections(): Flow<List<Inspections>> = colonyDao.getInspections()

    @WorkerThread
    suspend fun deleteScheduled(scheduled: Scheduled) = colonyDao.deleteScheduled(scheduled)

    @WorkerThread
    suspend fun deleteScheduled(scheduled_id: Int) = colonyDao.deleteScheduled(scheduled_id)

    @WorkerThread
    suspend fun deleteYardScheduled(yard_id: Int) = colonyDao.deleteYardScheduled(yard_id)

    @WorkerThread
    suspend fun deleteTagScheduled(tag: String) = colonyDao.deleteTagScheduled(tag)

    @WorkerThread
    suspend fun insertHive(hive: Hive) {
        colonyDao.insertHive(hive)
    }

    @WorkerThread
    suspend fun insertYard(yard: Yard) {
        colonyDao.insertYard(yard)
    }

    @WorkerThread
    suspend fun updateHiveName(hive_id: Int, newName: String){
        colonyDao.updateHiveName(hive_id, newName)
    }

    @WorkerThread
    suspend fun updateYardName(yard_id: Int, newName: String){
        colonyDao.updateYardName(yard_id, newName)
    }

    @WorkerThread
    suspend fun updateHivePhoto(hive_id: Int, photoURI: Uri){
        colonyDao.updateHivePhoto(hive_id, photoURI)
    }

    @WorkerThread
    suspend fun updateYardPhoto(yard_id: Int, photoURI: Uri){
        colonyDao.updateYardPhoto(yard_id, photoURI)
    }

    @WorkerThread
    suspend fun scheduleInspection(scheduled: Scheduled){
        colonyDao.scheduleInspection(scheduled)
    }

    @WorkerThread
    suspend fun updateInspection(newName: String, isNotif: Boolean, id: Int){
        colonyDao.updateInspection(newName, isNotif, id)
    }

    @WorkerThread
    suspend fun addInspection(newInspection: Inspections){
        colonyDao.addInspection(newInspection)
    }

    @WorkerThread
    suspend fun deleteInspection(inspection: Inspections){
        colonyDao.addInspection((inspection))
    }


}