package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "hive_table")
data class Hive(
    @NonNull @ColumnInfo(name = "hive_name") var hiveName: String,
    //@NonNull @ColumnInfo(name = "yard") val yard: String,
    @NonNull @ColumnInfo(name = "yardID") val yardID: Int,
    @ColumnInfo(name = "photoURI" ) var photoURI: Uri? = Uri.EMPTY
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}