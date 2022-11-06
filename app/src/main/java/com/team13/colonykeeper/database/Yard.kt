package com.team13.colonykeeper.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.team13.colonykeeper.data.BeeYard

@Entity(tableName = "yard_table")
data class Yard (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo(name = "yard_name") val yardName: String
)