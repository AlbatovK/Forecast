package com.albatros.forecast.model.repo

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.data.ForecastMain
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.delay

class MainRepository(
    private val api: Api,
    private val dbRepo: DatabaseRepository,
    private val locationRepository: LocationRepository,
    private val firebaseAnalytics: FirebaseAnalytics
) {

    suspend fun getForecast(lang: String = "en_US", refresh: Boolean = false, empty: Boolean = false): ForecastMain {
        if (empty) {
            Log.d("getForecast", "Returning empty forecast")
            return ForecastMain().also {
                _forecastLive.value = it
            }
        }
        delay(2_000) /* Obviously foolish, yet practical since locationApi can't be constrained by coroutines */
        val (lat, lon) = locationRepository.getLastLocation()
        var collected = false
        if (_forecast == null || refresh) {
            Log.d("getForecast", "Passes location: $lat, $lon")
            Bundle().apply {
                putString("lat", "$lat")
                putString("lon", "$lon")
                firebaseAnalytics.logEvent("Location", this)
            }
            _forecast = try { api.getForecast(lat, lon, lang) }
            catch (e1: Exception) {
                Log.d(
                    "Exception in MainRepository (net) ",
                    "getForecast: ${e1.localizedMessage ?: "undefined error"}"
                )
                try {
                    dbRepo.collectForecastFromDatabase().also {
                        collected = true
                    }
                }
                catch (e2: Exception) {
                    Log.d("Exception in MainRepository (db) ", "getForecast: ${e2.localizedMessage ?: "undefined error"}")
                    ForecastMain()
                }
            }
        }
        if (_forecast?.forecast?.parts?.isNotEmpty() == true && ForecastMain() != _forecast && !collected) {
            dbRepo.insertForecast(_forecast!!)
        }
        _forecastLive.value = _forecast!!
        return _forecast!!
    }

    private var _forecast: ForecastMain? = null

    private var _forecastLive = MutableLiveData<ForecastMain>()

    val forecastLive: LiveData<ForecastMain> = _forecastLive
}