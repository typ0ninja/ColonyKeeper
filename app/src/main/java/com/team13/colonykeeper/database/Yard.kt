package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "yard_table")
data class Yard(
    @NonNull @ColumnInfo(name = "yard_name") var yardName: String,
    @ColumnInfo(name = "photoURI") var photoURI: Uri? = Uri.EMPTY
){
    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0
    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}