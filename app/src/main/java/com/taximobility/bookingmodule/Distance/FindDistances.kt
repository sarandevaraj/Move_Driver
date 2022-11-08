package com.taximobility.bookingmodule.Distance

import android.content.Context
import android.os.AsyncTask
import com.taximobility.R
import com.taximobility.bookingmodule.Interface.RouteListeners
import com.taximobility.roomDB.GoogleMapModel
import com.taximobility.roomDB.MapLoggerRepository
import com.taximobility.roomDB.MapboxModel
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.util.*
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindDistances(var disInterface: RouteListeners) {
    internal var url: String? = null
    internal var from = ""
    internal var to = ""
    lateinit var mContext: Context

    private var mRepository: MapLoggerRepository? = null


    fun getDistance(c: Context, P_latitude: Double, P_longitude: Double,
                    D_latitude: Double, D_longitude: Double) {
        this.mContext = c
        this.from = "$P_latitude,$P_longitude"
        this.to = "$D_latitude,$D_longitude"

        mRepository = MapLoggerRepository(mContext)
        GetGoogleLog(P_latitude, P_longitude, D_latitude, D_longitude).execute()
    }

    private fun makeGoogleApiCall(P_latitude: Double, P_longitude: Double, D_latitude: Double, D_longitude: Double) {


        val baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + P_latitude + "," + P_longitude + "&destinations=" + D_latitude + "," + D_longitude + "&key=" + SessionSave.getSession(TaxiUtil.GOOGLE_KEY, mContext)
//        CoreClient polyline = new ServiceGenerator(mContext, true).createService(CoreClient.class);
        val polyline = AppController.getInstance().apiManagerWithoutEncryptBaseUrl
        polyline.getJsonbyWholeUrl("no-cache", baseUrl)
                .enqueue(RetrofitCallbackClass(mContext, object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful) {
                            val result = response.body()!!.toString()
                            var obj: JSONObject? = null
                            try {
                                obj = JSONObject(result)
                                if (obj.has("status") && !obj.getString("status").equals("OK", ignoreCase = true) && obj.has("error_message")) {
                                    val msg: String = obj.getString("error_message")
                                    ShowToast.center(mContext, msg)
                                    return
                                }
                                obj = JSONObject(result).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0)
                                val ds = obj!!.getJSONObject("distance")
                                val dis = ds.getString("value")
                                val timee = obj.getJSONObject("duration")
                                val time = timee.getString("value")
                                val times = java.lang.Double.parseDouble(time) / 60
                                val dist = java.lang.Double.parseDouble(dis) / 1000
                                saveGoogleLog(from.trim { it <= ' ' } + to.trim { it <= ' ' }, times, dist, "", result)

                                disInterface.getETADiverToPickup(times, dist)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                //ShowToast.center(mContext, NC.getString(R.string.server_con_error))
                            }

                        } else {
                            ShowToast.center(mContext, NC.getString(R.string.server_con_error))
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        ShowToast.center(mContext, t.localizedMessage)
                    }
                }))
    }

    private fun saveGoogleLog(s: String, times: Double, dist: Double, s1: String, result: String) {
        val model = GoogleMapModel()
        model.fromTo = s
        model.time = times
        model.distance = dist
        model.routeResult = s1
        model.distanceResult = result

        mRepository!!.insertGoogleLog(model)
    }

    private fun saveMapboxLog(s: String, times: Double, dist: Double, s1: String, result: String) {
        val model = MapboxModel()
        model.fromTo = s
        model.time = times
        model.distance = dist
        model.routeResult = s1
        model.distanceResult = result

        mRepository!!.insertMapboxLog(model)
    }

    private inner class GetGoogleLog(private val P_latitude: Double, private val P_longitude: Double, private val D_latitude: Double, private val D_longitude: Double) : AsyncTask<Void, Void, GoogleMapModel>() {

        override fun doInBackground(vararg voids: Void): GoogleMapModel? {

            return mRepository!!.getGoogleModel(from.trim { it <= ' ' } + to.trim { it <= ' ' })
        }

        override fun onPostExecute(model: GoogleMapModel?) {
            super.onPostExecute(model)
            if (model != null) {
                disInterface.getETADiverToPickup(model.time, model.distance)
            } else {
                makeGoogleApiCall(P_latitude, P_longitude, D_latitude, D_longitude)
            }
        }
    }
}