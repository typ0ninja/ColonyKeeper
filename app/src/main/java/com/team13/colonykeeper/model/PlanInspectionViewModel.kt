package com.team13.colonykeeper.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team13.colonykeeper.network.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

enum class WeatherApiStatus {LOADING, ERROR, DONE}

class PlanInspectionViewModel: ViewModel() {
    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _weekForcast = MutableLiveData<List<Weather>>()
    val weekForcast: LiveData<List<Weather>> = _weekForcast

    private val _dayForcast = MutableLiveData<Weather>()
    val dayForcast: LiveData<Weather> = _dayForcast

    private var dayIndex = 0

    fun getWeekForcast() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING

            val response = WeatherApi.retrofitService.getWeekForecast(39.25F, -97.75F)
            response.enqueue(object : Callback<DailyUnits> {
                override fun onResponse(call: Call<DailyUnits>, response: Response<DailyUnits>) {
                    _weekForcast.value = response.body()?.parseToWeatherList()
                    _dayForcast.value = _weekForcast.value?.get(dayIndex)
                    _status.value = WeatherApiStatus.DONE
                }

                override fun onFailure(call: Call<DailyUnits>, t: Throwable) {
                    _weekForcast.value = listOf()
                    _status.value = WeatherApiStatus.ERROR
                }
            })
        }
    }

    fun onBackArrowClicked() {
        if (dayIndex > 0) {
            dayIndex--
            _dayForcast.value = _weekForcast.value?.get(dayIndex)
        }
    }

    fun onForwardArrowClicked() {
        if (dayIndex < 6) {
            dayIndex++
            _dayForcast.value = _weekForcast.value?.get(dayIndex)
        }
    }
}