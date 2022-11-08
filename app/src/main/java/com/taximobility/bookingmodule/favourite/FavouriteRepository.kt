package com.taximobility.bookingmodule.favourite

import android.content.Context
import android.os.AsyncTask
import com.taximobility.R
import com.taximobility.bookingmodule.LocationData
import com.taximobility.bookingmodule.RoomDatabase.PlacesDao
import com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase
import com.taximobility.data.apiData.AddFavouriteData
import com.taximobility.features.CToast
import com.taximobility.service.CoreClient
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.util.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class FavouriteRepository(val mContext: Context) {


    val db = PlacesDatabase.getDatabase(mContext)
    val mDao: PlacesDao = db!!.getPlaceDao()

    fun callAddFavApi(requestData: JSONObject, viewModel: FavouriteViewModel) {
        if (NetworkStatus.isOnline(mContext)) {
            val datas: ArrayList<LocationData> = ArrayList(2)
            val locationData = LocationData(
                    Random().nextInt(),
                    requestData.getString("p_favourite_place"),
                    "",
                    requestData.getString("p_fav_latitude").toDouble(),
                    requestData.getString("p_fav_longtitute").toDouble(),
                    requestData.getString("p_fav_latitude").toDouble(),
                    requestData.getString("p_fav_longtitute").toDouble(),
                    requestData.getString("p_fav_locationtype"),
                    requestData.getString("p_favourite_place"),
                    requestData.getString("p_fav_locationtype"),
                    "http://mongo.taximobility.com/public/localtest/android/static_image/favourite_popular/OFFICE.png",
                    "http://mongo.taximobility.com/public/localtest/iOS/static_image/favourite_popular/OFFICE.png"
            )
            datas.add(locationData)
            var client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())
            val coreRequest = client.addFavourite(requestBody, SessionSave.getSession(LANG, mContext))
            coreRequest.enqueue(RetrofitCallbackClass(mContext, object : Callback<AddFavouriteData> {
                override fun onResponse(call: Call<AddFavouriteData>, response: Response<AddFavouriteData>) {
                    try {
                        if (response.isSuccessful) {
                            val data = response.body()
                            if (data != null) {
                                viewModel.setFavDataValue(data)
                                if(data.status == 1)
                                insertFavPlaces(datas)
                            } else {
                                CToast.ShowToast(mContext, NC.getString(R.string.server_error))
                            }
                        } else {
                            CToast.ShowToast(mContext, NC.getString(R.string.server_error))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<AddFavouriteData>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(mContext, NC.getString(R.string.server_error))
                }
            }))
        } else {
            CToast.ShowToast(mContext, NC.getString(R.string.check_internet_connection))
        }
    }


    fun insertFavPlaces(data: List<LocationData>) {
        val favlistData: List<LocationData> = data
        favlistData.map { it.type = FavouritePlaceType }
        val wholeList = mutableListOf<LocationData>()
        if (favlistData.isNotEmpty()) {
            wholeList.addAll(favlistData)
        }
        insertAsyncTask(mDao, wholeList).execute()
    }


    private class insertAsyncTask internal constructor(private val mPlaceDao: PlacesDao, val favModel: List<LocationData>) : AsyncTask<LocationData, Void, Void>() {

        override fun doInBackground(vararg params: LocationData): Void? {
            mPlaceDao.insertLog(favModel)
            return null
        }
    }
}