package com.team13.colonykeeper.model

import androidx.annotation.DrawableRes
import com.team13.colonykeeper.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

// Special thanks to team 10 for help!!!

/**
 * This data class defines [DailyUnits] which includes the time, weathercode, temperature_2m_max,
 * temperature_2m_min, sunrise, and sunset
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class DailyUnits(
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
                0 or 1 or 2 -> R.drawable.ic_sunny_icon
                3 or 45 -> R.drawable.ic_clouds_icon
                48 -> R.drawable.ic_snow_icon
                51 or 53 or 55 -> R.drawable.ic_water_icon
                56 or 57 -> R.drawable.ic_snow_icon
                61 or 63 or 65 -> R.drawable.ic_water_icon
                66 or 67 or 71 or 73 or 75 or 77 -> R.drawable.ic_snow_icon
                80 or 81 or 82 -> R.drawable.ic_water_icon
                85 or 86 -> R.drawable.ic_snow_icon
                95 or 96 or 99 -> R.drawable.ic_storm_icon
                else -> -1
            }

            list.add(Weather(
                time[index],
                iconID,
                formatTemp(temperature_2m_max[index]),
                formatTemp(temperature_2m_min[index]),
                sunrise[index],
                sunset[index]
            ))
        }
        return list
    }

    private fun formatTemp(temp: Float): String {
        return temp.roundToInt().toString()
    }
}

data class Weather(
    val date: String,
    @DrawableRes val imageResourceId: Int,
    val maxTemp: String,
    val minTemp: String,
    val sunrise: String,
    val sunset: String
)