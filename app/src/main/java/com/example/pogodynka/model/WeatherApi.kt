package com.example.pogodynka.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "APPID=2764052cd7ec75c7afe410c43740b177"
interface WeatherApi {
    @GET("weather")
    fun weather(@Query("q") city:String,@Query("APPID") key:String): Call<WeatherRequest?>
}