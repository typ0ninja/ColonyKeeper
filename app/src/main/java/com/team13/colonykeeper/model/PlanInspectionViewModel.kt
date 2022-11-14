package com.team13.colonykeeper.model

import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.team13.colonykeeper.database.ColonyApplication
import com.team13.colonykeeper.database.ColonyViewModel
import com.team13.colonykeeper.database.ColonyViewModelFactory
import com.team13.colonykeeper.database.Scheduled
import com.team13.colonykeeper.network.Forecast
import com.team13.colonykeeper.network.Weather
import com.team13.colonykeeper.network.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

enum class WeatherApiStatus {LOADING, ERROR, DONE}

class PlanInspectionViewModel: ViewModel(){

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _weekForcast = MutableLiveData<List<Weather>>()
    val weekForcast: LiveData<List<Weather>> = _weekForcast

    private val _dayForcast = MutableLiveData<Weather>()
    val dayForcast: LiveData<Weather> = _dayForcast

    private var dayIndex = 0

    private val _hour = MutableLiveData<Int>()
    val hour: LiveData<Int> = _hour

    private val _minute = MutableLiveData<Int>()
    val minute: LiveData<Int> = _minute

    private val _checked = MutableLiveData<Boolean>()
    val checked: LiveData<Boolean> = _checked

    fun getWeekForecast() {
        viewModelScope.launch {
            Log.d("PlanInspectionViewModel", "In getWeekForecast")
            _status.value = WeatherApiStatus.LOADING

            val lat: Double = ColonyApplication.instance.curYard.latitude
            val long: Double = ColonyApplication.instance.curYard.longitude

            Log.d("GPS", "Lat: ${lat}")

            //val response = WeatherApi.retrofitService.getWeekForecast(lat.toFloat(), long.toFloat())
            val response = WeatherApi.retrofitService.getWeekForecast()

            response.enqueue(object : Callback<Forecast> {
                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    Log.d("PlanInspectionViewModel", "In Response callback")
                    _weekForcast.value = response.body()?.parseToWeatherList()
                    _dayForcast.value = _weekForcast.value?.get(dayIndex)
                    _status.value = WeatherApiStatus.DONE
                }

                override fun onFailure(call: Call<Forecast>, t: Throwable) {
                    Log.d("PlanInspectionViewModel", "In Failure callback")
                    _weekForcast.value = listOf()
                    _status.value = WeatherApiStatus.ERROR
                }
            })
        }
    }

    fun onTimeChanged(newHour: Int, newMinute: Int){
        _hour.value = newHour
        _minute.value = newMinute
    }

    fun onCheckBoxToggled(isChecked: Boolean) {
        _checked.value = isChecked
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

    fun returnTime(): String {
        return _hour.value.toString() + ":" + _minute.value.toString()
    }

    fun isLoaded(): Boolean {
        return _status.value == WeatherApiStatus.DONE
    }
}