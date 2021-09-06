package com.albatros.forecast.model.repo

import com.albatros.forecast.model.api.Api

class MainRepository(private val api: Api) {
    suspend fun getForecast(lat: Double = 54.99, lon: Double = 73.36) = api.getForecast(lat, lon)
}