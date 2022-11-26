package com.team13.colonykeeper.database

import android.net.Uri
import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspection_table")
class Inspections(
    @ColumnInfo val name: String,
    @ColumnInfo val date: String,
    @ColumnInfo val notes: String,
    @ColumnInfo val yard_id: Int
    //@ColumnInfo val photoUri: Uri
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "photoList")
    lateinit var photoList: Array<String>
}
