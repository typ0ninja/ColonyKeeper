package com.team13.colonykeeper.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColonyDao {
    @Query("SELECT * FROM hive_table ORDER BY hive_name ASC")
    fun getAllHives(): Flow<List<Hive>>

    @Query("SELECT * FROM hive_table WHERE yardID = :yardID ORDER BY hive_name ASC")
    fun getHivesFromYard(yardID: Int): Flow<List<Hive>>

    @Query("SELECT * FROM hive_table WHERE id = :hive_id")
    fun getHive(hive_id: Int) : Flow<Hive>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHive(hive: Hive)

    @Query("Update hive_table SET hive_name = :newName WHERE id = :hive_id")
    suspend fun updateHiveName(hive_id: Int, newName: String)

    @Query("DELETE FROM hive_table WHERE id = :hive_id")
    suspend fun deleteHive(hive_id: Int)

    @Query("DELETE FROM hive_table")
    suspend fun deleteAllHive()

    @Query("DELETE FROM hive_table WHERE yardID = :yard_ID")
    suspend fun deleteYardHives(yard_ID: Int)


    @Query("SELECT * FROM yard_table ORDER BY yard_name ASC")
    fun getYards(): Flow<List<Yard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertYard(yard: Yard)

    @Query("Update yard_table SET yard_name = :newName WHERE id = :yard_id")
    suspend fun updateYardName(yard_id: Int, newName: String)

    @Query("DELETE FROM yard_table WHERE id = :yard_id")
    suspend fun deleteYard(yard_id: Int)

    @Query("DELETE FROM yard_table")
    suspend fun deleteAllYard()

    @Query("SELECT * FROM yard_table JOIN hive_table ON :yard_id = hive_table.yardID")
    fun getYardHives(yard_id: Int) : Flow<List<Hive>>
}