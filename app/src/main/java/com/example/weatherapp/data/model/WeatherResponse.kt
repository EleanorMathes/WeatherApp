package com.example.weatherapp.data.model



data class WeatherResponse(
    val location: Location,
    val current: Current

)
// name of our location and country
data class Location(
    val name: String,
    val country: String
)

//Current condition (humidity, wind speed, temperature in f)
data class Current(
    val temp_f: Double,
    val condition: Condition,
    val humidity: Int,
    val wind_mph: Double,
    val feelslike_f: Double,

)
// Condition description and icon
data class Condition(
    val text: String,
    val icon: String
)
