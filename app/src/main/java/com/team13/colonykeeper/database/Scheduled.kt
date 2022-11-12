package com.team13.colonykeeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Strings: Name, Date, time, tag, location name (basically yard or hive name) Ints: ID Bool: isNotification

@Entity(tableName = "scheduled_table")
class Scheduled (
    @ColumnInfo val name: String,
    @ColumnInfo val date: String,
    @ColumnInfo val time: String,
    @ColumnInfo val tag: String,
    @ColumnInfo val yardID: Int,
    @ColumnInfo val locName: String,
    @ColumnInfo val isNotification: Boolean
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
