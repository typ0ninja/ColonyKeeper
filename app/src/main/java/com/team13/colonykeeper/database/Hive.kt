package com.team13.colonykeeper.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hive_table")
data class Hive (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo(name = "hive_name") val hiveName: String,
    //@NonNull @ColumnInfo(name = "yard") val yard: String,
    @NonNull @ColumnInfo(name = "yardID") val yardID: Int
)