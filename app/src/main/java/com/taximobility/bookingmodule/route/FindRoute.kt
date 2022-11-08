package com.taximobility.bookingmodule.route

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.view.animation.LinearInterpolator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.JointType.ROUND
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomePage
import com.taximobility.bookingmodule.Interface.RouteListeners
import com.taximobility.bookingmodule.utils.DistanceMatrixUtil
import com.taximobility.features.ApproximateCalculation
import com.taximobility.roomDB.GoogleMapModel
import com.taximobility.roomDB.MapLoggerRepository
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.util.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Draw the route to the map object .
 * Routes are drawn with attributes according to the constructor its triggered.
 */
class FindRoute(internal var routeInterface: RouteListeners) {
    private lateinit var mContext: Context
    private lateinit var mRepository: MapLoggerRepository
    private var mMap: GoogleMap? = null
    private var wayPoint = StringBuilder()
    var overViewPolyLine = ""
    private var blackPolyLine: Polyline? = null
    private var greyPolyLine: Polyline? = null
    private var listLatLng: List<LatLng>? = null
    private var pLat: Double = 0.0
    private var pLng: Double = 0.0
    private var dLat: Double = 0.0
    private var dLng: Double = 0.0
    private var wayPoints: ArrayList<LatLng> = ArrayList()
    private var approxFare: Double = 0.0
    private var isNeedToDrawRoute: Boolean = true
    private var requestedType = 0
    private var pickUp: LatLng? = null
    private var drop: LatLng? = null
    private var polyLineAnimationListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {
            val blackLatLng = blackPolyLine!!.points
            val greyLatLng = greyPolyLine!!.points
            greyLatLng.clear()
            greyLatLng.addAll(blackLatLng)
            blackLatLng.clear()
            blackPolyLine?.run {
                points = blackLatLng
                zIndex = 2f
            }
            greyPolyLine?.run {
                points = greyLatLng
            }
            drawMarker()
        }

        override fun onAnimationCancel(animator: Animator) {
        }

        override fun onAnimationRepeat(animator: Animator) {
        }
    }

    /**
     * Entry point to draw route
     *
     * @param map
     * @param mcontext
     * @param source
     * @param destination
     */
    fun setUpPolyLine(map: GoogleMap, mcontext: Context, source: LatLng?, destination: LatLng?, points: ArrayList<LatLng>, isNeedToDrawRoute: Boolean) {
        this.mMap = map
        this.mContext = mcontext
        this.isNeedToDrawRoute = isNeedToDrawRoute
        this.pickUp = source
        this.drop = destination
        requestedType = 1
        mRepository = MapLoggerRepository(mContext)
        if (source != null && destination != null)
            getGoogleRouteLog(source, destination, points).execute()
    }

    private fun getRouteFromGoogle(pLatitude: Double, pLongitude: Double, D_latitude: Double, D_longitude: Double, wayPoints: ArrayList<LatLng>) {
        val wayPointsUrl = makeDirectionUrl(pLatitude, pLongitude, D_latitude, D_longitude, wayPoints)
        val polyline = AppController.getInstance().apiManagerWithoutEncryptBaseUrl
        val coreResponse = polyline.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", pLatitude.toString() + "," + pLongitude, D_latitude.toString() + "," + D_longitude, wayPointsUrl, SessionSave.getSession(TaxiUtil.GOOGLE_KEY, mContext))
        coreResponse.enqueue(RetrofitCallbackClass(mContext, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val gson = JSONObject(response.body()!!.string())
                        if (!gson.getString("status").equals("OK", ignoreCase = true)) {
                            setFailureDistance()
                            if (gson.has("error_message")) {
                                val msg: String = gson.getString("error_message")
                                ShowToast.center(mContext, msg)
                                return
                            }
                        } else {
                            saveGoogleLog(pLatitude.toString() + "," + pLongitude + D_latitude + "," + D_longitude, gson.toString())
                            if (isNeedToDrawRoute) {
                                if (SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mContext, false)) {
                                    drawRoutePolyline(parsePolylineFromPoints(gson))
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        setFailureDistance()
//                        routeInterface.drawRoutePickToDrop(null, null, approxFare)
                    }
                } else {
                    setFailureDistance()
//                    routeInterface.drawRoutePickToDrop(null, null, approxFare)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                setFailureDistance()
//                routeInterface.drawRoutePickToDrop(null, null, approxFare)
            }
        }))
    }

    private fun setFailureDistance() {
        var distance = 0.0
        if (pickUp != null && drop != null) {
            distance = haverSine(pickUp!!.latitude, pickUp!!.longitude, drop!!.latitude, drop!!.longitude, mContext)
            calculateTime(distance)
        } else {
            routeInterface.drawRoutePickToDrop(null, null, approxFare)
        }
    }

    /**
     * Get a list of latlng from polyline by decode
     *
     * @param jObject
     * @return
     */
    private fun parsePolylineFromPoints(jObject: JSONObject): List<LatLng> {
        var path: List<LatLng> = ArrayList()
        try {
            val jRoutes: JSONArray = jObject.getJSONArray("routes")
            /** Traversing all routes  */
            val jOverviewPoly: JSONObject = (jRoutes.get(0) as JSONObject).getJSONObject("overview_polyline")
            overViewPolyLine = jOverviewPoly.getString("points")
            path = decodePoly(overViewPolyLine)
        } catch (e1: JSONException) {
            e1.printStackTrace()
        }
        return path
    }

    private fun decodePoly(encodedPath: String): List<LatLng> {
        val len = encodedPath.length
        val path: ArrayList<LatLng> = ArrayList()
        var index = 0
        var lat = 0
        var lng = 0
        while (index < len) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = encodedPath[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 31)
            lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            result = 1
            shift = 0
            do {
                b = encodedPath[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 31)
            lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            path.add(LatLng(lat.toDouble() * 1.0E-5, lng.toDouble() * 1.0E-5))
        }
        return path
    }

    private fun makeDirectionUrl(p_latitude: Double, p_longitude: Double, d_latitude: Double, d_longitude: Double, points: ArrayList<LatLng>?): String {
        wayPoint = StringBuilder()
        if (points != null) {
            if (points.size > 2) {
                pLat = points[0].latitude
                pLng = points[0].longitude
                dLat = points[points.size - 1].latitude
                dLng = points[points.size - 1].longitude
                for (i in 1 until points.size - 1) {
                    wayPoint.append(points[i].latitude)
                    wayPoint.append(',')
                    wayPoint.append(points[i].longitude)
                    if (i != points.size - 2) {
                        wayPoint.append("|")
                    }
                }
            } else {
                pLat = points[0].latitude
                pLng = points[0].longitude
                dLat = points[points.size - 1].latitude
                dLng = points[points.size - 1].longitude
            }
        } else {
            pLat = p_latitude
            pLng = p_longitude
            dLat = d_latitude
            dLng = d_longitude
        }
        return wayPoint.toString()
    }

    internal fun drawRoutePolyline(result: List<LatLng>) {
        if (BookTaxiHomePage.IS_HOME_PAGE) {
            if (BookTaxiHomePage.bookingState == BookTaxiHomePage.BOOKINGSTATE.STATE_TWO) {
                val lineOptions = PolylineOptions()
                listLatLng = ArrayList()
                this.listLatLng = result
                if (mMap != null) {
                    lineOptions.apply {
                        width(5f)
                        color(Color.GRAY)
                        startCap(SquareCap())
                        endCap(SquareCap())
                        jointType(ROUND)
                    }
                    blackPolyLine = mMap?.run {
                        addPolyline(lineOptions)
                    }
                    val greyOptions = PolylineOptions().apply {
                        width(5f)
                        color(Color.BLACK)
                        startCap(SquareCap())
                        endCap(SquareCap())
                        jointType(ROUND)
                    }
                    greyPolyLine = mMap?.run {
                        addPolyline(greyOptions)
                    }
                    animatePolyLine(1000)
                }
            }
        } else {
            val lineOptions = PolylineOptions()
            listLatLng = ArrayList()
            this.listLatLng = result
            if (mMap != null) {
                lineOptions.apply {
                    width(7f)
                    color(Color.GRAY)
                    startCap(SquareCap())
                    endCap(SquareCap())
                    jointType(ROUND)
                }
                blackPolyLine = mMap?.run {
                    addPolyline(lineOptions)
                }
                val greyOptions = PolylineOptions().apply {
                    width(7f)
                    color(Color.BLACK)
                    startCap(SquareCap())
                    endCap(SquareCap())
                    jointType(ROUND)
                }
                greyPolyLine = mMap?.run {
                    addPolyline(greyOptions)
                }
                animatePolyLine(1000)
            }
        }
    }

    private fun animatePolyLine(durations: Long) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.apply {
            duration = durations
            interpolator = LinearInterpolator()
        }
        animator.addUpdateListener { animator ->
            val latLngList = blackPolyLine!!.points
            val initialPointSize = latLngList.size
            val animatedValue = animator.animatedValue as Int
            val newPoints = animatedValue * listLatLng!!.size / 100

            if (initialPointSize < newPoints) {
                latLngList.addAll(listLatLng!!.subList(initialPointSize, newPoints))
                blackPolyLine!!.points = latLngList
            }
        }
        animator.addListener(polyLineAnimationListener)
        animator.start()
    }

    private fun drawMarker() {
        if (wayPoints.size > 2) {
            for (i in 1 until wayPoints.size - 1) {
                mMap!!.addMarker(MarkerOptions()
                        .position(wayPoints[i])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.drop_dot)))
            }
        }
    }

    private fun saveGoogleLog(latLngKey: String, routeResult: String) {
        var time = 0.0
        var distance = 0.0
        try {
            val legsArray = JSONObject(routeResult).getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
            if (legsArray != null) {
                for (i in 0 until legsArray.length()) {
                    val distanceObject = legsArray.getJSONObject(i).getJSONObject("distance")
                    val distanceString = distanceObject.getString("value")
                    if (distanceString != null && distanceString.isNotEmpty()) {
                        distance += java.lang.Double.parseDouble(distanceString)
                    }
                    val timeObject = legsArray.getJSONObject(i).getJSONObject("duration")
                    val timeString = timeObject.getString("value")
                    if (timeString != null && timeString.isNotEmpty()) {
                        time += java.lang.Double.parseDouble(timeString)
                    }
                }
            }
            val approxTravelTime = (time / 60)
            println("Time  $approxTravelTime")
            var approxTravelDist = distance / 1000
            if (SessionSave.getSession("Metric", mContext).trim { it <= ' ' }.equals("MILES", ignoreCase = true) && SessionSave.getSession(IS_BUISNESS_KEY, mContext, true)) {
                approxTravelDist /= 1.60934
            }
            val model = GoogleMapModel()
            model.fromTo = latLngKey
            model.time = approxTravelTime
            model.distance = approxTravelDist
            model.routeResult = routeResult
            model.distanceResult = ""
            mRepository.insertGoogleLog(model)
            approxFare = ApproximateCalculation.approxFare(mContext, approxTravelDist, approxTravelTime)

            println("NAAN drawRoutePickToDrop $approxTravelTime _____distance $approxTravelDist _____approxFare $approxFare")

            routeInterface.drawRoutePickToDrop(approxTravelTime, approxTravelDist, approxFare)
        } catch (e: JSONException) {
            e.printStackTrace()
            approxFare = ApproximateCalculation.approxFare(mContext, distance, time)
            routeInterface.drawRoutePickToDrop(time, distance, approxFare)
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

        var timez: Double = distance / speed.toDouble()
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
        approxFare = ApproximateCalculation.approxFare(mContext, distance, mTime)
        println("NAAN mTime $mTime _____distance $distance _____approxFare $approxFare")
        routeInterface.drawRoutePickToDrop(mTime, distance, approxFare)
        return mTime
    }

    /**
     * Check whether available in DB
     */
    private inner class getGoogleRouteLog internal constructor(source: LatLng, destination: LatLng, points: ArrayList<LatLng>) : AsyncTask<Void, Void, GoogleMapModel>() {
        private val pLatitude: Double
        private val pLongitude: Double
        private val dLatitude: Double
        private val dLongitude: Double
        private var from = ""
        private var to = ""

        init {
            this.pLatitude = source.latitude
            this.pLongitude = source.longitude
            this.dLatitude = destination.latitude
            this.dLongitude = destination.longitude
            wayPoints = points
            from = "$pLatitude,$pLongitude"
            to = "$dLatitude,$dLongitude"
        }

        override fun doInBackground(vararg voids: Void): GoogleMapModel? {
            return mRepository.getGoogleModel(from.trim { it <= ' ' } + to.trim { it <= ' ' })
        }

        override fun onPostExecute(model: GoogleMapModel?) {
            super.onPostExecute(model)
            if (model != null && model.routeResult != "") {
                try {
                    if (isNeedToDrawRoute) {
                        if (SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mContext, false)) {
                            drawRoutePolyline(parsePolylineFromPoints(JSONObject(model.routeResult)))
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                approxFare = ApproximateCalculation.approxFare(mContext, model.distance, model.time)
                routeInterface.drawRoutePickToDrop(model.time, model.distance, approxFare)
            } else {
                getRouteFromGoogle(pLatitude, pLongitude, dLatitude, dLongitude, wayPoints)
            }
        }
    }
}