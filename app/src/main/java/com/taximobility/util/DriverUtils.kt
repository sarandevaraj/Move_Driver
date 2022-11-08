package com.taximobility.util

import android.content.Context
import com.taximobility.data.ModelDriverInfo


/**
 * Created on 10th October by developer at NDOT Technologies
 * Singleton class to get driver information like id,shift status,etc ...
 *
 * object represents singleton instance for this class
 * <b>https://kotlinlang.org/docs/reference/object-declarations.html</b>
 */
object DriverUtils {
    fun driverInfo(context: Context): ModelDriverInfo {
        val driverInfoObject = ModelDriverInfo(SessionSave.getSession(PASS_ID, context),
                SessionSave.getSession("trip_id", context)
                , SessionSave.getSession("travel_status", context),
                SessionSave.getSession("last", context).replace("null", ""))
        return driverInfoObject
    }
}