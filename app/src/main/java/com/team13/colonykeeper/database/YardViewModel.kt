package com.team13.colonykeeper.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class YardViewModel(private val repository: YardRepository): ViewModel() {
    fun allYards(): LiveData<List<Yard>> = repository.allYards.asLiveData()

    fun insert(yard: Yard) = viewModelScope.launch {
        repository.insert(yard)
    }
}

class YardViewModelFactory(private val repository: YardRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HiveViewModel::class.java)){
            return YardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}