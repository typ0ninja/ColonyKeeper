package com.team13.colonykeeper.database

import android.net.Uri
import android.net.Uri.parse
import androidx.room.TypeConverter
import java.net.URI
import java.time.LocalDate
import java.util.*


class Converters {
    @TypeConverter
    fun toString(photoURI: Uri): String{
        return photoURI.toString()
    }

    @TypeConverter
    fun fromURI(photoString: String): Uri? {
        return Uri.parse(photoString)
    }

    @TypeConverter
    fun toString(date: Date): String {
        return date.toString()
    }

}