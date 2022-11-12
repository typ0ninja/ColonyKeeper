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
    //@ColumnInfo val photoUri: Uri
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
    @ColumnInfo(name = "photoList")
    var photoList: MutableList<Uri> = emptyList<Uri>() as MutableList<Uri>
}
