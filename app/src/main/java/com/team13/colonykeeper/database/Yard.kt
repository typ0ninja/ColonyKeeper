package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "yard_table")
data class Yard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo(name = "yard_name") val yardName: String,
    @ColumnInfo(name = "photoURI") var photoURI: Uri? = Uri.EMPTY
)