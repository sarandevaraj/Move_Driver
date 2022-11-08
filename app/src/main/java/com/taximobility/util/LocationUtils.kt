package com.taximobility.util

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object LocationUtils {

    fun calculateDistanceWithMetrics(metrix: String, from: LatLng, to: LatLng): Double {
        var distance = 0.0
        if (from != null && to != null) {
            val distancearray = FloatArray(2)
            Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, distancearray)
            distance = distancearray[0].toDouble()
            if (metrix.equals("miles", ignoreCase = true))
                distance /= 1.60934

        }
        return distance
    }

    fun calculateDistanceInMeter(from: LatLng, to: LatLng): Double {
        var distance = 0.0
        if (from != null && to != null) {
            val distancearray = FloatArray(2)
            Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, distancearray)
            distance = distancearray[0].toDouble()


        }
        return distance
    }

}