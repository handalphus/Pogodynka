package com.example.pogodynka.model

data class WeatherRequest(
        val base: String = "",
        val clouds: Clouds = Clouds(0),
        val cod: Int,
        val coord: Coord = Coord(0.0,0.0),
        val dt: Int = 0,
        val id: Int =0,
        val main: Main = Main(.0,0,0,.0,.0,.0),
        val name: String = "",
        val sys: Sys = Sys("",0,0,0,0),
        val timezone: Int=0,
        val visibility: Int =0,
        val weather: List<Weather> = listOf(),
        val wind: Wind = Wind(0,0.0),

)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Long,
    val sunset: Long,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)