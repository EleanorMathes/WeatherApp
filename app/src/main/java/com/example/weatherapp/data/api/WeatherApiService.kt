package com.example.weatherapp.data.api
import retrofit2.http.GET

interface WeatherApiService {
    // http://api.weatherapi.com/v1/current.json?key=77a70e94fe27433f849194509260204&q=Indianapolis&aqi=no

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "Yes"

    )

}