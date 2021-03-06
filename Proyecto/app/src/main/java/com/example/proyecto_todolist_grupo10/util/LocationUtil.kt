package com.example.proyecto_todolist_grupo10.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.example.proyecto_todolist_grupo10.model.LocationObject
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationUtil(context: Context) : LiveData<LocationObject>() {

    private var fusedLocation = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
        fusedLocation.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocation.lastLocation.addOnSuccessListener { location: Location? ->
            location?.also {
                setLocationData(it)
            }
            startLocationUpdate()
        }
    }

    private fun setLocationData(location: Location) {
        value = LocationObject(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        fusedLocation.requestLocationUpdates(locationResquest, locationCallback, null)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            p0 ?: return
            p0.locations.forEach {
                setLocationData(it)
            }
        }
    }

    companion object {
        val locationResquest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }


}