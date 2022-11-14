package com.team13.colonykeeper.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.open-meteo.com/v1/"
private const val REQUEST_PATH_FIELDS_1 = "forecast?"
private const val REQUEST_PATH_FIELDS_2 = "&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset&temperature_unit=fahrenheit&windspeed_unit=mph&precipitation_unit=inch&timezone=auto"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    //@GET("${REQUEST_PATH_FIELDS_1}?latitude={lat}&longitude={long}${REQUEST_PATH_FIELDS_2}")
    //fun getWeekForecast(@Path("lat") latitude: Float, @Path("long") longitude: Float): Call<Forecast>
    @GET("forecast?latitude=30.29&longitude=-97.75&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset&temperature_unit=fahrenheit&windspeed_unit=mph&precipitation_unit=inch&timezone=auto")
    fun getWeekForecast(): Call<Forecast>
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}