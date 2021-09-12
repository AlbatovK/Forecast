package com.albatros.forecast.model.api

import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.module.apiKey
import com.albatros.forecast.model.module.getPath
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    /**
     * Header - personal Yandex api key,
     * Path - V2 Yandex.Api destination,
     * @param lat latitude
     * @param lon longitude
     * @param lang query language
     * @return Forecast from Yandex.Weather Api
     * @see ForecastMain
     * @see com.albatros.forecast.model.repo.MainRepository.getForecast
     */
    @Headers(value = [apiKey])
    @GET(value = getPath)
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): ForecastMain
}