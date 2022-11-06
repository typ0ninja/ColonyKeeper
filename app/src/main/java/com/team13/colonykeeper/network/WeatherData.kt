package com.team13.colonykeeper.network

import android.util.Log
import androidx.annotation.DrawableRes
import com.team13.colonykeeper.R
import kotlin.math.roundToInt

// Special thanks to team 10 for help!!!

/**
 * This data class defines [Forecast] which includes the daily_units
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class Forecast(
    val daily: Daily
) {
    fun parseToWeatherList(): List<Weather> {
        return daily.parseToWeatherList()
    }
}

/**
 * This data class defines [Daily] which includes the time, weathercode, temperature_2m_max,
 * temperature_2m_min, sunrise, and sunset
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class Daily(
    val time: List<String>,
    val weathercode: List<Int>,
    val temperature_2m_max: List<Float>,
    val temperature_2m_min: List<Float>,
    val sunrise: List<String>,
    val sunset: List<String>
) {
    fun parseToWeatherList(): List<Weather>{
        val list = mutableListOf<Weather>()
        for (index in 0..6) {
            val iconID: Int =  when (weathercode[index]) {
                0, 1, 2 -> R.drawable.ic_sunny_icon
                3, 45 -> R.drawable.ic_clouds_icon
                48, 56, 57, 66, 67, 71, 73, 75, 77, 85, 86 -> R.drawable.ic_snow_icon
                51, 53, 55, 61, 63, 65, 80, 81, 82 -> R.drawable.ic_water_icon
                95, 96, 99 -> R.drawable.ic_storm_icon
                else -> -1
            }
            val iconDesc: String = when(iconID) {
                R.drawable.ic_sunny_icon -> "Sunny weather"
                R.drawable.ic_clouds_icon -> "Cloudy/Overcast weather"
                R.drawable.ic_snow_icon -> "Snowy weather"
                R.drawable.ic_water_icon -> "Rainy weather"
                R.drawable.ic_storm_icon -> "Stormy weather"
                else -> "Error"
            }

            list.add(Weather(
                time[index],
                iconID,
                iconDesc,
                (formatTemp(temperature_2m_max[index]) + "   " + formatTemp(temperature_2m_min[index])),
                sunrise[index],
                sunset[index]
            ))
        }
        return list
    }

    private fun formatTemp(temp: Float): String {
        return temp.roundToInt().toString() + "°"
    }
}

data class Weather(
    val date: String,
    val imageResourceId: Int,
    val imageContentDescription: String,
    val tempRange: String,
    val sunrise: String,
    val sunset: String
)