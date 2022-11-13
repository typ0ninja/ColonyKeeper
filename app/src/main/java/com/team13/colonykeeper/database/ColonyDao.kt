package com.team13.colonykeeper.database

import android.net.Uri
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.net.URI

@Dao
interface ColonyDao {

    //Hive Calls
    @Query("SELECT * FROM hive_table ORDER BY hive_name ASC")
    fun getAllHives(): Flow<List<Hive>>

    @Query("SELECT * FROM hive_table WHERE yardID = :yardID ORDER BY hive_name ASC")
    fun getHivesFromYard(yardID: Int): Flow<List<Hive>>

    @Query("SELECT * FROM hive_table WHERE id = :hive_id")
    fun getHive(hive_id: Int): Flow<Hive>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHive(hive: Hive)

    @Query("Update hive_table SET hive_name = :newName WHERE id = :hive_id")
    suspend fun updateHiveName(hive_id: Int, newName: String)

    @Query("DELETE FROM hive_table WHERE id = :hive_id")
    suspend fun deleteHive(hive_id: Int)

    @Query("DELETE FROM hive_table")
    suspend fun deleteAllHive()

    @Query("Update hive_table SET photoURI = :photoURI WHERE id = :hive_id")
    suspend fun updateHivePhoto(hive_id: Int, photoURI: Uri)

    //Yard Calls
    @Query("DELETE FROM hive_table WHERE yardID = :yard_ID")
    suspend fun deleteYardHives(yard_ID: Int)

    @Query("SELECT * FROM yard_table ORDER BY yard_name ASC")
    fun getYards(): Flow<List<Yard>>

    @Query("SELECT * FROM yard_table WHERE id = :yard_id")
    fun getYard(yard_id: Int): Flow<Yard>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertYard(yard: Yard)

    @Query("Update yard_table SET yard_name = :newName WHERE id = :yard_id")
    suspend fun updateYardName(yard_id: Int, newName: String)

    @Query("DELETE FROM yard_table WHERE id = :yard_id")
    suspend fun deleteYard(yard_id: Int)

    @Query("DELETE FROM yard_table")
    suspend fun deleteAllYard()

    @Query("SELECT * FROM yard_table JOIN hive_table ON :yard_id = hive_table.yardID")
    fun getYardHives(yard_id: Int): Flow<List<Hive>>

    @Query("Update yard_table SET photoURI = :photoURI WHERE id = :yard_id")
    suspend fun updateYardPhoto(yard_id: Int, photoURI: Uri)

    @Query("Update yard_table SET latitude = :latitude AND longitude = :longitude WHERE id = :id")
    suspend fun updateYardGPS(id: Int, latitude: Double, longitude: Double)

    //Scheduled Inspections:

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun scheduleInspection(scheduled: Scheduled)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInspection(scheduled: Scheduled)

    @Query("SELECT * FROM scheduled_table ORDER BY date ASC")
    fun getAllScheduled(): Flow<List<Scheduled>>

    @Query("SELECT * FROM scheduled_table WHERE id = :scheduled_id")
    fun getScheduled(scheduled_id: Int): Flow<Scheduled>

    @Delete
    suspend fun deleteScheduled(scheduled: Scheduled)

    @Query("DELETE FROM scheduled_table WHERE id = :scheduled_id")
    suspend fun deleteScheduled(scheduled_id: Int)

    @Query("SELECT * FROM scheduled_table where yardID = :yard_id")
    fun getYardScheduled(yard_id: Int): Flow<List<Scheduled>>

    @Query("SELECT * FROM scheduled_table where tag = :tag")
    fun getTagScheduled(tag: String): Flow<List<Scheduled>>

    @Query("DELETE FROM scheduled_table where yardID = :yard_id")
    suspend fun deleteYardScheduled(yard_id: Int)

    @Query("DELETE FROM scheduled_table where tag = :tag")
    suspend fun deleteTagScheduled(tag: String)

    //Previous Inspections:

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInspection(newInspection: Inspections)

    @Delete
    suspend fun deleteInspection(inspection: Inspections)

    @Query("SELECT * FROM inspection_table")
    fun getInspections(): Flow<List<Inspections>>


}