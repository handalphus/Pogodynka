package com.example.pogodynka.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
object RetrofitInstance{
    private val httpLoggingInterceptor =HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofit by lazy{
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: WeatherApi by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WeatherApi::class.java)
    }
    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }
}

//        "weather?q=London,uk&APPID=2764052cd7ec75c7afe410c43740b177"
