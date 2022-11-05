package com.team13.colonykeeper.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface YardDao {
    @Query("SELECT * FROM yard_table ORDER BY yard_name ASC")
    fun getYards(): Flow<List<Yard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(yard: Yard)

    @Query("Update yard_table SET yard_name = :newName WHERE id = :yard_id")
    suspend fun updateYardName(yard_id: Int, newName: String)

    @Query("DELETE FROM yard_table WHERE id = :yard_id")
    suspend fun delete(yard_id: Int)

    @Query("DELETE FROM yard_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM yard_table JOIN hive_table ON :yard_id = hive_table.yardID")
    fun getYardHives(yard_id: Int) : Flow<List<Hive>>
}