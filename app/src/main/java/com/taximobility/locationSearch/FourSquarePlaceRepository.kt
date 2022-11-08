package com.taximobility.locationSearch

import android.content.Context
import android.os.AsyncTask
import com.google.gson.Gson
import com.taximobility.R
import com.taximobility.data.apiData.PlacesDetail
import com.taximobility.features.CToast
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.util.AppController
import com.taximobility.util.NC
import com.taximobility.util.SessionSave
import com.taximobility.util.TaxiUtil
import com.taximobility.util.TaxiUtil.getCurrentTimeForFourSquare
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FourSquarePlaceRepository(val mContext: Context, val listener: PlaceSearchList) : OnLocationSearched {
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

    override fun onItemClicked(placesDetail: PlacesDetail) {
        TaxiUtil.Latitude = placesDetail.getLatitude()
        TaxiUtil.Longitude = placesDetail.getLongtitute()
        if (placesDetail.getPlaceType() == 0) {
            TaxiUtil.Address = placesDetail.getLocation_name()
        } else {
            TaxiUtil.Address = "${placesDetail.getLabel_name()}, ${placesDetail.getLocation_name()}"
        }

        listener.setPlaceDetail(PlacesDetail().apply {
            if (placesDetail.getPlaceType() == 0)
                location_name = placesDetail.getLocation_name()
            else
                location_name = "${placesDetail.getLabel_name()}, ${placesDetail.getLocation_name()}"
            label_name = placesDetail.getLabel_name()
            latitude = placesDetail.getLatitude()
            longtitute = placesDetail.getLongtitute()
            this.placeId = placesDetail.getPlaceId()
        })
    }

    private inner class ExploreAsyncTask : AsyncTask<String, Unit, Unit>() {

        override fun doInBackground(vararg params: String) {
//            val client = ServiceGenerator(mContext, "https://api.foursquare.com/v2/", true).createService(CoreClient::class.java)
            val coreResponse = AppController.getInstance().apiManagerWithoutEncryptBaseUrl.requestExplore("https://api.foursquare.com/v2/", getCurrentTimeForFourSquare(),
                    SessionSave.getSession("PLAT", mContext) + "," + SessionSave.getSession("PLNG", mContext), params[0], SessionSave.getSession("android_foursquare_api_key", mContext))
            coreResponse.enqueue(RetrofitCallbackClass(mContext, object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    val resultList = ArrayList<PlacesDetail>()
                    if (response.isSuccessful && response.body() != null) {
                        val responseResult = Gson().toJson(response.body())
                        if (responseResult != null) {
                            val jsonResponse = JSONObject(responseResult)
                            if (jsonResponse.getJSONObject("meta").getInt("code") == 200) {
                                val miniVenues = jsonResponse.getJSONObject("response").getJSONArray("minivenues")
                                for (i in 0 until miniVenues.length()) {
                                    val placeObject = miniVenues.getJSONObject(i)
                                    val placesDetail = PlacesDetail()
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
                                    placesDetail.setLatitude(locationObject.getString("lat").toDouble())
                                    placesDetail.setLongtitute(locationObject.getString("lng").toDouble())
                                    resultList.add(placesDetail)
                                }
                            }
                        }
                    } else
                        CToast.ShowToast(mContext, NC.getString(R.string.server_error))

                    listener.setPlaceList(resultList)
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(mContext, NC.getString(R.string.server_error))
                    listener.setPlaceList(null)
                }
            }))
        }

    }
}