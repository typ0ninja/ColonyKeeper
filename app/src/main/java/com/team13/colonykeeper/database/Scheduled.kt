package com.team13.colonykeeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//Strings: Name, Date, time, tag, location name (basically yard or hive name) Ints: ID Bool: isNotification

@Entity(tableName = "scheduled_table")
class Scheduled (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val date: String,
    @ColumnInfo val tag: String,
    @ColumnInfo val yardID: Int,
    @ColumnInfo val locName: String,
    @ColumnInfo val isNotification: Boolean
)