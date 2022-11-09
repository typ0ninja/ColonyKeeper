package com.team13.colonykeeper.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.Yard

class FutureInspectionsViewModel {
    private val _all_yards = MutableLiveData<List<Yard>>()
    val all_yards: LiveData<List<Yard>> = _all_yards

    // Get list of all yards
    fun getAllYards() {
        //_all_yards.value =

    }

}