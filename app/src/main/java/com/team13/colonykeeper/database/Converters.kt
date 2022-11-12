package com.team13.colonykeeper.database

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun UriListToString(uriList: MutableList<Uri>): String{
        return Gson().toJson( uriList, object : TypeToken<String>(){}.type )
    }

    @TypeConverter
    fun UriListFromString(uriString: String): MutableList<Uri>{
        return Gson().fromJson( uriString, object : TypeToken<MutableList<Uri>>(){}.type )
    }

}