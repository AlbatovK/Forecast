package com.albatros.forecast.model.repo

import android.util.Log
import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.data.ForecastMain

class MainRepository(private val api: Api, private val dbRepo: DatabaseRepository) {

    suspend fun getForecast(lat: Double = 54.9924, lon: Double = 73.3686, lang: String = "en_US", refresh: Boolean = false): ForecastMain {
        if (_forecast == null || refresh)
            _forecast = try {
                api.getForecast(lat, lon, lang)
            } catch (e1: Exception) {
                Log.d("Exception in MainRepository (net) ", "getForecast: ${e1.localizedMessage ?: "undefined error"}")
                try { dbRepo.collectForecastFromDatabase()  } catch (e2: Exception) {
                    Log.d("Exception in MainRepository (db) ", "getForecast: ${e2.localizedMessage ?: "undefined error"}")
                    ForecastMain()
                }
            }
        if (_forecast?.forecast?.parts?.isNotEmpty() == true && ForecastMain() != _forecast)
            dbRepo.insertForecast(_forecast!!)
        return _forecast!!
    }

    private var _forecast: ForecastMain? = null
}