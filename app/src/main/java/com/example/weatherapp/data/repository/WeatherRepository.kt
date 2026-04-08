package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.utilities.Result
import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.Response

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getWeatherData(location: String): Result<WeatherResponse>{
        return try {
            val response: Response<WeatherResponse> = apiService.getCurrentWeather(
                location = location,
                apiKey = "77a70e94fe27433f849194509260204"
            )
            if (response.isSuccessful){
                val weatherResponse = response.body()

                if (weatherResponse != null) {
                    Result.Success(weatherResponse)
                }

                else {
                    Result.Error(Exception("Response body is null"))
                }
            }
            else{
                Result.Error(Exception("Request failed with code: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}