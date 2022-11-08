package com.taximobility.bookingmodule

import android.content.Context
import com.taximobility.bookingmodule.Distance.FindDistances
import com.taximobility.bookingmodule.Interface.RouteListeners
import com.taximobility.bookingmodule.route.FindRoute
import com.taximobility.bookingmodule.utils.DistanceMatrixUtil
import com.taximobility.features.ApproximateCalculation
import com.taximobility.util.IS_BUISNESS_KEY
import com.taximobility.util.SessionSave
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


class FindETAFare(private val routeListener: RouteListeners) {

    private var mRoute = FindRoute(routeListener)
    private var mDistance = FindDistances(routeListener)
    private var approxFare: Double = 0.0
    private var speed: Int = 45
    private var approximateDistance: Double = 0.0
    private var approximateTime: Double = 0.0

    fun drawRoutePickDrop(mMap: GoogleMap?, activity: Context, pickupLatLng: LatLng, dropLatLng: LatLng, mListLatLng: ArrayList<LatLng>, isNeedToDrawRoute: Boolean) {

        if(dropLatLng.latitude == 0.0){
            routeListener.drawRoutePickToDrop(0.0, 0.0, 0.0)
            mRoute.overViewPolyLine = ""
        }else{
            if (SessionSave.getSession(IS_BUISNESS_KEY, activity, true)) {
                mRoute.setUpPolyLine(mMap!!, activity, pickupLatLng, dropLatLng, mListLatLng, isNeedToDrawRoute)
            } else {
                approximateDistance = haverSine(pickupLatLng.latitude, pickupLatLng.longitude, dropLatLng.latitude, dropLatLng.longitude, activity)
                approximateTime = calculateTime(approximateDistance)
                approxFare = ApproximateCalculation.approxFare(activity, approximateDistance, approximateTime)
                routeListener.drawRoutePickToDrop(approximateTime, approximateDistance, approxFare)
            }
        }
    }

    fun findApproximateFare(activity: Context,approximateDistance: Double,approximateTime:Double) {
        approxFare = ApproximateCalculation.approxFare(activity, approximateDistance, approximateTime)
        routeListener.drawRoutePickToDrop(approximateTime, approximateDistance, approxFare)
    }

    fun getOverviewPolyline(): String {
        return mRoute.overViewPolyLine
    }

    fun findETA(context: Context, pickLat: Double, pickLng: Double, driverLat: Double, driverLng: Double) {
        if (SessionSave.getSession(IS_BUISNESS_KEY, context, true)) {
            mDistance.getDistance(context, pickLat, pickLng, driverLat, driverLng)
        } else {
            approximateDistance = haverSine(pickLat, pickLng, driverLat, driverLng, context)
            approximateTime = calculateTime(approximateDistance)
            routeListener.getETADiverToPickup(approximateTime, approximateDistance)
        }
    }

    /**
     * This Function is used for calculate the distance travelled
     */
    @Synchronized
    fun haverSine(lat1: Double, lon1: Double,
                  lat2: Double, lon2: Double, activity: Context): Double {
        // TODO Auto-generated method stub
        //Getting both the coordinates
        val from = LatLng(lat1, lon1)
        val to = LatLng(lat2, lon2)

        //Calculating the distance in meters
        return DistanceMatrixUtil.calculateDistance(SessionSave.getSession("Metric", activity).trim { it <= ' ' }, from, to)
    }

    fun calculateTime(distance: Double): Double {
        var mTime = 0.0

        var timez: Double = distance / speed
        timez *= 3600 // time duration in seconds
        val minutes = Math.floor(timez / 60)
        timez -= minutes * 60
        val seconds1 = Math.floor(timez)
        val timeString = minutes.toInt().toString() + "." + seconds1.toInt()
        val minsFloatValue = timeString.toFloat()

        mTime = Math.round(minsFloatValue * 100.0) / 100.0

        if (mTime <= 1) {
            mTime = 1.0
        }
        return mTime
    }
}