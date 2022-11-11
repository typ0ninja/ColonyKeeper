package com.team13.colonykeeper.database

import android.net.Uri
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
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

    fun getAllScheduled(): LiveData<List<Scheduled>> = repository.getAllScheduled().asLiveData()

    fun getScheduled(scheduled_id: Int): LiveData<Scheduled> = repository.getScheduled(scheduled_id).asLiveData()

    fun insertHive(hive: Hive) = viewModelScope.launch {
        repository.insertHive(hive)
    }

    fun getYardScheduled(yard_id: Int): LiveData<List<Scheduled>> = repository.getYardScheduled(yard_id).asLiveData()

    fun getTagScheduled(tag: String): LiveData<List<Scheduled>> = repository.getTagScheduled(tag).asLiveData()

    suspend fun deleteScheduled(scheduled: Scheduled) = viewModelScope.launch {
        repository.deleteScheduled(scheduled)
    }

    suspend fun deleteScheduled(scheduled_id: Int) = viewModelScope.launch {
        repository.deleteScheduled(scheduled_id)
    }

    suspend fun deleteYardScheduled(yard_id: Int) = viewModelScope.launch {
        repository.deleteYardScheduled(yard_id)
    }

    suspend fun deleteTagScheduled(tag: String) = viewModelScope.launch {
        repository.deleteTagScheduled(tag)
    }

    suspend fun updateHivePhoto(hive_id: Int, photoURI: Uri) = viewModelScope.launch {
        repository.updateHivePhoto(hive_id, photoURI)
    }

    suspend fun updateYardPhoto(yard_id: Int, photoURI: Uri) = viewModelScope.launch {
        repository.updateHivePhoto(yard_id, photoURI)
    }

    suspend fun scheduleInspection(scheduled: Scheduled) = viewModelScope.launch {
        repository.scheduleInspection(scheduled)
    }

    suspend fun updateInspection(scheduled: Scheduled) = viewModelScope.launch {
        repository.updateInspection(scheduled)
    }

    suspend fun addInspection(newInspection: Inspections) = viewModelScope.launch {
        repository.addInspection(newInspection)
    }

    suspend fun deleteInspection(inspection: Inspections) = viewModelScope.launch {
        repository.deleteInspection(inspection)
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