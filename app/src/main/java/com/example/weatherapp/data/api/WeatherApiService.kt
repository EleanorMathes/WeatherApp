package com.example.weatherapp.data.api
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import com.example.weatherapp.data.model.WeatherResponse
interface WeatherApiService {
    // https://api.weatherapi.com/v1/current.json?key=77a70e94fe27433f849194509260204&q=Indianapolis&aqi=no

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "Yes"

    ): Response<WeatherResponse>

}