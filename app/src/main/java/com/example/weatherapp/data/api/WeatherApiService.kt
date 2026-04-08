package com.example.weatherapp.data.api
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import com.example.weatherapp.data.model.WeatherResponse
interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("aqi") airQuality: String = "Yes",
        @Query("q") location: String
    ): Response<WeatherResponse>

}