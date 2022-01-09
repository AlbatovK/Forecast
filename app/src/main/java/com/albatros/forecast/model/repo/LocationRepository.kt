package com.albatros.forecast.model.repo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LocationRepository(locationClient: FusedLocationProviderClient, context: Context) {

    private var lat: Double = 54.9924
    private var lon: Double = 73.3686

    fun getLastLocation() = Pair(lat, lon)

    init {
        val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val granted = permissions.all { ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
        if (!granted) {
            Log.d("Got coordinates", "Not granted")
        }
        if (granted) {
            val callback = object: LocationCallback() { }
            locationClient.requestLocationUpdates(LocationRequest.create(), callback, Looper.getMainLooper())
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    lat = location.latitude
                    lon = location.longitude
                    Log.d("Got coordinates", "getCurrentLocation: $lat, $lon")
                }
            }
            locationClient.lastLocation.addOnFailureListener { exception ->
                Log.d("Error in Location", exception.localizedMessage ?: "undefined error")
            }
        }
    }
}