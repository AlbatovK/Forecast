package com.albatros.forecast.model.repo

import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.data.ForecastMain

class MainRepository(private val api: Api) {

    suspend fun getForecast(lat: Double = 54.99, lon: Double = 73.36, lang: String = "en_US"): ForecastMain {
        if (_forecast == null)
            _forecast = try { api.getForecast(lat, lon, lang) } catch (e: Exception) { ForecastMain() }
        return _forecast!!
    }

    private var _forecast: ForecastMain? = null
}