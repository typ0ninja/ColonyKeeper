package com.team13.colonykeeper.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hive_table")
data class Hive (
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "hive_name") val hiveName: String,
    @NonNull @ColumnInfo(name = "yard") val yard: String
)