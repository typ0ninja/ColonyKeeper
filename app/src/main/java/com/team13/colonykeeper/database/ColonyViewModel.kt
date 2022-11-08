package com.team13.colonykeeper.database

import android.net.Uri
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.net.URI

class ColonyViewModel(private val repository: ColonyRepository): ViewModel() {
    //Yard Items
    fun allYards(): LiveData<List<Yard>> = repository.allYards.asLiveData()

    fun insertYard(yard: Yard) = viewModelScope.launch {
        repository.insertYard(yard)
    }

    //Hive Items
    fun allHives(): LiveData<List<Hive>> = repository.allHives.asLiveData()

    fun getHive(hiveID: Int): LiveData<Hive> = repository.getHive(hiveID).asLiveData()

    fun hivesFromYard(yardID: Int): LiveData<List<Hive>> = repository.hivesFromYard(yardID).asLiveData()

    fun insertHive(hive: Hive) = viewModelScope.launch {
        repository.insertHive(hive)
    }

    suspend fun updateHivePhoto(hive_id: Int, photoURI: Uri){
        repository.updateHivePhoto(hive_id, photoURI)
    }

    suspend fun updateYardPhoto(yard_id: Int, photoURI: Uri){
        repository.updateHivePhoto(yard_id, photoURI)
    }

}

class ColonyViewModelFactory(private val repository: ColonyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColonyViewModel::class.java)){
            return ColonyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}