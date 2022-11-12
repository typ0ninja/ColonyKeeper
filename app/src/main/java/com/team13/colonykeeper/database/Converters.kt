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
    fun UriListToString(uriList: MutableList<Uri>): String{
        Log.d("JSON", uriList.toString())
        val uriArray: Array<Uri> = uriList.toTypedArray()
        val json = Gson().toJson(uriArray.toString())
        Log.d("JSON", json.toString())
        return json.toString()//Gson().toJson( uriList, object : TypeToken<String>(){}.type )
    }

    @TypeConverter
    fun UriListFromString(uriString: String): MutableList<Uri>{
        var uriList: MutableList<Uri> = mutableListOf<Uri>()

        val json = Gson().toJson(uriString)[0]
        Log.d("JSON", "from method 0${json.toString()}")
        //TODO: finish type converter here
        return uriList
    }

}