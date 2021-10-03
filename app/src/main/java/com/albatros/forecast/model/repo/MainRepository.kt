package com.albatros.forecast.model.repo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.data.ForecastMain
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.*

class MainRepository(
    private val api: Api,
    private val dbRepo: DatabaseRepository,
    private val locationClient: FusedLocationProviderClient,
    private val firebaseAnalytics: FirebaseAnalytics
) {

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation() {
        val callback = object: LocationCallback() {}
        locationClient.requestLocationUpdates(LocationRequest.create(), callback, Looper.getMainLooper())
        delay(1000)
        locationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                lat = location.latitude
                lon = location.longitude
                Log.d("Got coordinates", "getCurrentLocation: $lat, $lon")
            }
        }
        locationClient.lastLocation.addOnFailureListener { exception ->
            Log.d("Error in getCurrentLocation", exception.localizedMessage ?: "undefined error")
        }
    }

    init {
        GlobalScope.launch(Dispatchers.IO) {
            getCurrentLocation()
        }
    }

    private var lat: Double = 54.9924
    private var lon: Double = 73.3686

    suspend fun getForecast(lang: String = "en_US", refresh: Boolean = false): ForecastMain {
        getCurrentLocation()
        if (_forecast == null || refresh) {
            Log.d("getForecast", "Passes location: $lat, $lon")
            val bundle = Bundle()
            bundle.putString("lat", "$lat")
            bundle.putString("lon", "$lon")
            firebaseAnalytics.logEvent("Location", bundle)
            _forecast = try { api.getForecast(lat, lon, lang) }
            catch (e1: Exception) {
                Log.d(
                    "Exception in MainRepository (net) ",
                    "getForecast: ${e1.localizedMessage ?: "undefined error"}"
                )
                try { dbRepo.collectForecastFromDatabase() }
                catch (e2: Exception) {
                    Log.d("Exception in MainRepository (db) ", "getForecast: ${e2.localizedMessage ?: "undefined error"}")
                    ForecastMain()
                }
            }
        }
        if (_forecast?.forecast?.parts?.isNotEmpty() == true && ForecastMain() != _forecast)
            dbRepo.insertForecast(_forecast!!)
        return _forecast!!
    }

    private var _forecast: ForecastMain? = null
}