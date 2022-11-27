package com.team13.colonykeeper.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team13.colonykeeper.R
import com.team13.colonykeeper.database.Yard
import com.team13.colonykeeper.model.WeatherApiStatus
import java.text.SimpleDateFormat

// -------- Binding Adapters for activity_schedule_inspection --------

/**
 * This binding adapter displays the [WeatherApiStatus] of the network request in an image view.
 * When the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: WeatherApiStatus?) {
    when(status) {
        WeatherApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        WeatherApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
        }
    }
}

/**
 * This binding adapter displays the weather icon based on the given resource id
 * If not a valid key, displays error image
 */
@BindingAdapter("weatherIcon")
fun bindWeatherIcon(weatherImageView: ImageView, id: Int){
    if (id != -1 && id != 0) {
        weatherImageView.setImageResource(id)
    } else {
        weatherImageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
    }
}

@BindingAdapter("time")
fun bindFormattedTime(textView: TextView, unformattedText: String?) {
    val stringLength = unformattedText?.length ?: 0
    if (stringLength > 10) {
        textView.text = unformattedText!!.substring(11)
    }
}

@BindingAdapter("date")
fun bindFormattedDate(textView: TextView, unformattedText: String?) {
    val stringLength = unformattedText?.length ?: 0
    if (stringLength > 1) {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("EEEE MMM d")
        textView.text = formatter.format(parser.parse(unformattedText!!))
    }
}