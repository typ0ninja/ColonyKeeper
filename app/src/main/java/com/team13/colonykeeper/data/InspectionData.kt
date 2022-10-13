package com.team13.colonykeeper.data

import androidx.annotation.StringRes

data class InspectionData(
    val id: Int,
    val date: String,
    val inspectionPhotos: MutableList<Int>,
    @StringRes val notesResourceId: Int
) {
}