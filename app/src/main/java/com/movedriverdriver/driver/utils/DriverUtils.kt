package com.movedriverdriver.driver.utils

import android.content.Context
import com.movedriverdriver.driver.data.DriverCommonData
import com.movedriverdriver.driver.data.DriverModelDriverInfo
import com.movedriverdriver.driver.service.LocationUpdate.*

/**
 * Created on 10th October by developer at NDOT Technologies
 * Singleton class to get driver information like id,shift status,etc ...
 *
 * object represents singleton instance for this class
 * <b>https://kotlinlang.org/docs/reference/object-declarations.html</b>
 */
object DriverUtils {
    fun driverInfo(context: Context): DriverModelDriverInfo {
        val driverLastLocation = "$currentLatitude,${currentLongtitude}"
        val driverLocationAccuracy = "$currentAccuracy"
        return DriverModelDriverInfo(
            DriverSessionSave.getSession("Id", context),
            DriverSessionSave.getSession("trip_id", context),
            "$driverLastLocation,$driverLocationAccuracy",
            DriverSessionSave.getSession("shift_status", context),
            DriverSessionSave.getSession("travel_status", context),
            DriverSessionSave.getSession("service_status", context, false),
            DriverSessionSave.getSession(DriverCommonData.DRIVER_LOCATION_STATIC, context)
                .replace("null", "")
        )
    }
}