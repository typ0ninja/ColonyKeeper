package com.team13.colonykeeper.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inpection_table")
class Inspections (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val date: String,
    @ColumnInfo val notes: String,
    @ColumnInfo val photoUri: Uri
)
