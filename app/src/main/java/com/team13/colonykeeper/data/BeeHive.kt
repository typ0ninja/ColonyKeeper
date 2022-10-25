package com.team13.colonykeeper.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BeeHive(
    var name: String,
    @DrawableRes val imageResourceId: Int,
    //val gpsData: GPSData,
    //val queenDate: String,
    //@StringRes val notesResourceId: Int,
    //var pushData: PushData,
    //val inspectionData: InspectionData
) {

    // Returns queenDateFormatted
    fun getFormattedDate(){}
}