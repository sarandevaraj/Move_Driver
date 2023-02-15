package com.movedriver.driver.locationSearch

import android.content.Context
import android.os.AsyncTask
import com.google.gson.Gson
import com.movedriver.R
import com.movedriver.driver.data.DriverCommonData
import com.movedriver.driver.service.DriverRetrofitCallbackClass
import com.movedriver.driver.utils.DriverCToast
import com.movedriver.driver.utils.DriverNC
import com.movedriver.driver.utils.DriverSessionSave
import com.movedriver.util.AppController
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DriverFourSquarePlaceRepository(val mContext: Context, val listener: DriverPlaceSearchList) :
    DriverOnLocationSearched {
    private var exploreAsyncTask: ExploreAsyncTask? = null

    private var city: String = ""
    private var state: String = ""

    override fun onLocationSearched(queryString: String) {
        if (exploreAsyncTask != null) {
            exploreAsyncTask?.cancel(true)
            exploreAsyncTask = null
        }
        exploreAsyncTask = ExploreAsyncTask()
        exploreAsyncTask?.execute(queryString)
    }

    override fun onItemClicked(driverPlacesDetail: DriverPlacesDetail) {

        listener.setPlaceDetail(DriverPlacesDetail().apply {
            location_name =
                if (driverPlacesDetail.getPlaceType() == 1) driverPlacesDetail.getLocation_name()
                else "${driverPlacesDetail.getLabel_name()}, ${driverPlacesDetail.getLocation_name()}"
            label_name = driverPlacesDetail.getLabel_name()
            latitude = driverPlacesDetail.getLatitude()
            longtitute = driverPlacesDetail.getLongtitute()
            this.placeId = driverPlacesDetail.getPlaceId()
        })
    }

    private inner class ExploreAsyncTask : AsyncTask<String, Unit, Unit>() {

        override fun doInBackground(vararg params: String) {
            val client = AppController.getInstance().apiManagerWithoutEncryptBaseUrl_driver
            val url = "https://api.foursquare.com/v2/" + "venues/suggestcompletion"
            val coreResponse = client.requestExplore(
                url,
                DriverCommonData.getCurrentTimeForFourSquare(),
                DriverSessionSave.getSession(
                    DriverCommonData.SOS_LAST_LAT, mContext
                ) + "," + DriverSessionSave.getSession(DriverCommonData.SOS_LAST_LNG, mContext),
                params[0],
                DriverSessionSave.getSession("android_foursquare_api_key", mContext)
            )
            coreResponse.enqueue(DriverRetrofitCallbackClass(mContext, object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val resultList = ArrayList<DriverPlacesDetail>()
                    if (response.isSuccessful && response.body() != null) {
                        val responseResult = Gson().toJson(response.body())
                        if (responseResult != null) {
                            val jsonResponse = JSONObject(responseResult)
                            if (jsonResponse.getJSONObject("meta").getInt("code") == 200) {
                                val miniVenues = jsonResponse.getJSONObject("response")
                                    .getJSONArray("minivenues")
                                for (i in 0 until miniVenues.length()) {
                                    val placeObject = miniVenues.getJSONObject(i)
                                    val placesDetail = DriverPlacesDetail()
                                    val locationObject = placeObject.getJSONObject("location")
                                    val placeName = placeObject.getString("name")
                                    if (locationObject.has("city")) {
                                        city = locationObject.getString("city")
                                    }
                                    if (locationObject.has("state")) {
                                        state = locationObject.getString("state")
                                    }
                                    if (city.isNotEmpty() && state.isNotEmpty()) {
                                        placesDetail.setLocation_name("$city , $state")
                                    } else if (city.isNotEmpty()) {
                                        placesDetail.setLocation_name(city)
                                    } else if (state.isNotEmpty()) {
                                        placesDetail.setLocation_name(state)
                                    }
                                    placesDetail.setLabel_name(placeName)
                                    placesDetail.setPlaceId(placeObject.getString("id") ?: "")
                                    placesDetail.setPlaceType(0)
                                    placesDetail.setLatitude(
                                        locationObject.getString("lat").toDouble()
                                    )
                                    placesDetail.setLongtitute(
                                        locationObject.getString("lng").toDouble()
                                    )
                                    resultList.add(placesDetail)
                                }
                            }
                        }
                    } else DriverCToast.ShowToast(
                        mContext,
                        DriverNC.getString(R.string.server_error)
                    )
                    listener.setPlaceList(resultList)
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    t.printStackTrace()
                    DriverCToast.ShowToast(mContext, DriverNC.getString(R.string.server_error))
                    listener.setPlaceList(null)
                }
            }))
        }
    }
}