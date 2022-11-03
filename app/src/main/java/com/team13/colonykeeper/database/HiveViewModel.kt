package com.team13.colonykeeper.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class HiveViewModel(private val repository: HiveRepository): ViewModel() {
    fun allHives(): LiveData<List<Hive>> = repository.allHives.asLiveData()

    fun hivesFromYard(yard: String): LiveData<List<Hive>> = repository.hivesFromYard(yard).asLiveData()

    fun insert(hive: Hive) = viewModelScope.launch {
        repository.insert(hive)
    }
}

class HiveViewModelFactory(private val repository: HiveRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HiveViewModel::class.java)){
            return HiveViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}