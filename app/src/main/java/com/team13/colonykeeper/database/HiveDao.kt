package com.team13.colonykeeper.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HiveDao {
    @Query("SELECT * FROM hive_table ORDER BY hive_name ASC")
    fun getAllHives(): Flow<List<Hive>>

    @Query("SELECT * FROM hive_table WHERE yard = :yard_name ORDER BY hive_name ASC")
    fun getHivesFromYard(yard_name: String): Flow<List<Hive>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hive: Hive)

    @Query("Update hive_table SET hive_name = :newName WHERE id = :hive_id")
    suspend fun updateHiveName(hive_id: Int, newName: String)

    @Query("DELETE FROM hive_table WHERE id = :hive_id")
    suspend fun delete(hive_id: Int)

    @Query("DELETE FROM hive_table")
    suspend fun deleteAll()
}