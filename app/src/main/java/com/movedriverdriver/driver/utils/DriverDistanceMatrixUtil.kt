package com.movedriverdriver.driver.utils

import com.google.android.gms.maps.model.LatLng
import com.movedriverdriver.util.SphericalUtil

object DriverDistanceMatrixUtil {

    fun calculateDistance(metrix: String, from: LatLng, to: LatLng): Double {
        var distance = (SphericalUtil.computeDistanceBetween(from, to).toFloat() / 1000).toDouble()
        if (metrix.equals("miles", ignoreCase = true)) distance /= 1.60934
        return distance
    }
}