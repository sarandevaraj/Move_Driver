package com.taximobility.bookingmodule.utils

import com.taximobility.util.SphericalUtil
import com.google.android.gms.maps.model.LatLng

object DistanceMatrixUtil {

    fun calculateDistance(metrix: String, from: LatLng, to: LatLng): Double {
        var distance = (SphericalUtil.computeDistanceBetween(from, to).toFloat() / 1000).toDouble()
        if (metrix.equals("miles", ignoreCase = true))
            distance /= 1.60934
        return distance
    }

}