package com.team13.colonykeeper.database

import android.net.Uri
import android.net.Uri.parse
import androidx.room.TypeConverter
import java.net.URI


class Converters {
    @TypeConverter
    fun toString(photoURI: Uri): String{
        return photoURI.toString()
    }

    @TypeConverter
    fun fromURI(photoString: String): Uri? {
        return Uri.parse(photoString)
    }
}