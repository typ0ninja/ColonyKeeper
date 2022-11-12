package com.team13.colonykeeper.database

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


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

    @TypeConverter
    fun UriListToString(uriList: Array<String>): String{
        Log.d("JSON", uriList.toString())
        val json = Gson().toJson(uriList)
        Log.d("JSON", json.toString())
        return json.toString()
    }

    @TypeConverter
    fun UriListFromString(uriString: String): Array<String>{
        val myPhotos = Gson().fromJson(uriString, Array<String>::class.java)
        Log.d("JSON", "myPhotos = ${myPhotos.size}")
        return myPhotos
    }

}