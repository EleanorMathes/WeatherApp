package com.example.weatherapp.data.model
data class WeatherResponse(
    val location: Location,
    val current: Current
)
data class Location(
    val name: String,        // City name
    val country: String      // Country name
)
data class Current(
    val temp_f: Double,           // Temperature in Celsius
    val condition: Condition,     // Weather condition (text and icon)
    val humidity: Int,            // Humidity percentage
    val wind_mph: Double,         // Wind speed in km/h
    val feelslike_f: Double       // "Feels like" temperature
)
data class Condition(
    val text: String,        // Weather description (e.g., "Partly cloudy")
    val icon: String         // Weather icon URL
)