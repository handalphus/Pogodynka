package com.example.pogodynka.model

import android.util.Log
import retrofit2.await
import retrofit2.awaitResponse

class WeatherRepository {

        suspend fun getWeather(): WeatherRequest?{
            return RetrofitInstance.api.weather("London,uk","2764052cd7ec75c7afe410c43740b177").awaitResponse().body()!!

    }
    suspend fun getWeather(city:String): WeatherRequest?{
        return try {
            RetrofitInstance.api.weather(city,"2764052cd7ec75c7afe410c43740b177").awaitResponse().body()!!
        } catch (e:Exception){
            WeatherRequest(cod = 404)
        }
    }
}