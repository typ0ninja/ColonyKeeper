package com.team13.colonykeeper.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "yard_table")
data class Yard (
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "yard_name") val yardName: String
)