package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.data.api.RetrofitInstance
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    // Initialize ViewModel using viewModels delegate
    private val viewModel: WeatherViewModel by viewModels {
        // Create ViewModel with repository dependency
        WeatherViewModelFactory(
            WeatherRepository(RetrofitInstance.apiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupObservers()
        // default location search
        viewModel.fetchWeatherData("Indianapolis")
    }

    private fun initViews() {
        val btnSearch = findViewById<MaterialButton>(R.id.btnSearch)
        val etSearch = findViewById<EditText>(R.id.etSearch)

        btnSearch.setOnClickListener {
            val location = etSearch.text.toString().trim()
            if (location.isNotEmpty()) {
                // Hide keyboard
                hideKeyboard()
                // Fetch weather data for entered location
                viewModel.fetchWeatherData(location)
            } else {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        // weather data changes
        viewModel.weatherData.observe(this, Observer { weatherData ->
            weatherData?.let {
                updateWeatherUI(it)
            }
        })

        // loading state
        viewModel.isLoading.observe(this, Observer { isLoading ->
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        // error messages
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            val tvError = findViewById<TextView>(R.id.tvError)

            if (errorMessage.isNotEmpty()) {
                tvError.text = errorMessage
                tvError.visibility = View.VISIBLE
                // Hide weather card on error
                findViewById<MaterialCardView>(R.id.weatherCard).visibility = View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            } else {
                tvError.visibility = View.GONE
            }
        })
    }

    private fun updateWeatherUI(weatherResponse: WeatherResponse) {
        val weatherCard = findViewById<MaterialCardView>(R.id.weatherCard)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        val tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        val tvCondition = findViewById<TextView>(R.id.tvCondition)
        val tvFeelsLike = findViewById<TextView>(R.id.tvFeelsLike)
        val tvHumidity = findViewById<TextView>(R.id.tvHumidity)
        val tvWind = findViewById<TextView>(R.id.tvWind)

        // UI Update
        tvLocation.text = "${weatherResponse.location.name}, ${weatherResponse.location.country}"
        tvTemperature.text = "${weatherResponse.current.temp_f}°F"
        tvCondition.text = weatherResponse.current.condition.text
        tvFeelsLike.text = "${weatherResponse.current.feelslike_f}°F"
        tvHumidity.text = "${weatherResponse.current.humidity}%"
        tvWind.text = "${weatherResponse.current.wind_mph} mph"

        // Show weather card
        weatherCard.visibility = View.VISIBLE
    }
    private fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}