package com.taximobility.bookingmodule

import androidx.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import com.taximobility.R
import com.taximobility.bookingmodule.Data.NearestDriverDatas
import com.taximobility.bookingmodule.Data.SaveBookingResponse
import com.taximobility.bookingmodule.RoomDatabase.PlacesDao
import com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase
import com.taximobility.data.apiData.CheckPromoCodeData
import com.taximobility.data.apiData.DeleteFavouriteData
import com.taximobility.features.CToast
import com.taximobility.service.*
import com.taximobility.util.*
import com.google.gson.Gson
import com.taximobility.bookingmodule.Data.PassengerInfoData
import com.taximobility.interfaces.NodeAuthListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookTaxiHomeRepository(val context: Context) : NodeAuthListener {
    override fun nodeAuthListener(listener: Boolean, requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (listener) {
            callNearestApi(requestData, viewModel)
        }
    }


    val db = PlacesDatabase.getDatabase(context)
    val mDao: PlacesDao = db!!.getPlaceDao()
    var pastListData: List<LocationData> = ArrayList()


    fun callNearestApi(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {

        if (SessionSave.getSession(TaxiUtil.RUN_GO_LANG, context) == "true") {
            if (NetworkStatus.isOnline(context)) {
                nearestDriverApiCall(requestData, viewModel)
            } else {
                CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
            }
        } else {
            NodeAuth().setListener(this, requestData, viewModel)
            if (NetworkStatus.isOnline(context)) {
                if (SessionSave.getSession(TaxiUtil.NODE_TOKEN, context) != "") {
                    nearestDriverApiCall(requestData, viewModel)
                } else {
                    if (SessionSave.getSession(PASS_ID, context) != "") {
                        NodeAuth().getAuth(context)
                    }
                }
            } else {
                CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
            }
        }
    }

    private fun nearestDriverApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        var client: CoreClient? = null
//                client = NodeServiceGenerator(context, SessionSave.getSession(TaxiUtil.NODE_URL, context), 8).createService(CoreClient::class.java)
        client = AppController.getInstance().getNodeApiManagerWithTimeOut(SessionSave.getSession(TaxiUtil.NODE_URL, context), 8L)

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())
        var coreResponse = client.nodeUpdates(body, SessionSave.getSession(LANG, context))
        if (SessionSave.getSession(TaxiUtil.RUN_GO_LANG, context) == "true") {
            coreResponse = client.goLangUpdates(body, SessionSave.getSession(LANG, context))
        }
        coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<NearestDriverDatas> {
            override fun onResponse(call: Call<NearestDriverDatas>, response: Response<NearestDriverDatas>) {
                if (response.isSuccessful) {
                    try {
                        val data = response.body()
                        SessionSave.saveSession("Server_Response", Gson().toJson(data), context)

                        if (data != null) {
                            //handled with seperate api getPassengerInfoApiCall()
                            /*if (data.past_booking_places != null) {
                                if (data.past_booking_places!!.isNotEmpty()) {
                                    deleteAsyncTask(mDao).execute()
                                }
                            }
                            deleteAsyncTaskPopular(mDao).execute()
                            insertFavPlaces(data)*/
                            viewModel.nearestResponse.value = data
                        } else {
                            viewModel.nearestResponse.value = null
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        viewModel.nearestResponse.value = null
                    }
                } else {
                    viewModel.nearestResponse.value = null
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                }
            }

            override fun onFailure(call: Call<NearestDriverDatas>, t: Throwable) {
                t.printStackTrace()
                CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                viewModel.nearestResponse.value = null

            }
        }))
    }

    fun saveBookingApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
//            client = ServiceGenerator(context, TaxiUtil.API_BASE_URL, false).createService(CoreClient::class.java)
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())

            val coreResponse = client.saveBookingData(body, SessionSave.getSession(LANG, context))
            coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<SaveBookingResponse> {
                override fun onResponse(call: Call<SaveBookingResponse>, response: Response<SaveBookingResponse>) {
                    if (response.isSuccessful) {
                        try {
                            val data = response.body()
                            if (data != null) {
                                viewModel.saveBookingRes.value = data
                            } else {
                                viewModel.saveBookingRes.value = null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            viewModel.saveBookingRes.value = null
                        }
                    } else {
                        viewModel.saveBookingRes.value = null
                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }
                }

                override fun onFailure(call: Call<SaveBookingResponse>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.saveBookingRes.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }


    fun getModelApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())
            val coreResponse = client.getServiceModels(body, SessionSave.getSession(LANG, context))
            coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        try {
                            val data = response.body()
                            if (data != null) {
                                viewModel.modelDetailsRes.value = data.string()
                            } else {
                                viewModel.modelDetailsRes.value = null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            viewModel.modelDetailsRes.value = null
                        }
                    } else {
                        viewModel.modelDetailsRes.value = null
                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.modelDetailsRes.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }

    fun getPrefrenceApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())
            val coreResponse = client.getPreferences(body, SessionSave.getSession(LANG, context))
            coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        try {
                            val data = response.body()
                            if (data != null) {
                                viewModel.modelPreferenceRes.value = data.string()
                            } else {
                                viewModel.modelPreferenceRes.value = null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            viewModel.modelPreferenceRes.value = null
                        }
                    } else {
                        viewModel.modelPreferenceRes.value = null
                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.modelPreferenceRes.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }

    fun callDeleteFavourite(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl

            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())

            val coreRequest = client.deleteFavourite(body, SessionSave.getSession(LANG, context))

            coreRequest.enqueue(RetrofitCallbackClass(context, object : Callback<DeleteFavouriteData> {
                override fun onResponse(call: Call<DeleteFavouriteData>, response: Response<DeleteFavouriteData>) {
                    if (response.isSuccessful) {
                        try {
                            val data = response.body()
                            if (data != null) {
                                viewModel.deleteFavouriteData.value = data
                            } else {
                                viewModel.deleteFavouriteData.value = null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            viewModel.deleteFavouriteData.value = null
                        }

                    } else {
                        viewModel.deleteFavouriteData.value = null
                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }

                }

                override fun onFailure(call: Call<DeleteFavouriteData>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.deleteFavouriteData.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }


    fun checkPromoCodeApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
//            client = ServiceGenerator(context, TaxiUtil.API_BASE_URL, false).createService(CoreClient::class.java)
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())

            val coreResponse = client.checkProcode(body, SessionSave.getSession(LANG, context))
            coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<CheckPromoCodeData> {
                override fun onResponse(call: Call<CheckPromoCodeData>, response: Response<CheckPromoCodeData>) {
                    if (response.isSuccessful) {
                        try {
                            val data = response.body()
                            if (data != null) {
                                viewModel.checkPromocodeResponse.value = data
                            } else {
                                viewModel.checkPromocodeResponse.value = null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            viewModel.checkPromocodeResponse.value = null
                        }
                    } else {
                        viewModel.checkPromocodeResponse.value = null
                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }
                }

                override fun onFailure(call: Call<CheckPromoCodeData>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.checkPromocodeResponse.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }


    fun insertFavPlaces(data: Any) {
        if (data is NearestDriverDatas) {
            if (data.favourite_places != null) {
                if (data.favourite_places!!.isNotEmpty()) {
                    val favlistData: List<LocationData> = data.favourite_places!!
                    favlistData.map { it.type = FavouritePlaceType }
                    val wholeList = mutableListOf<LocationData>()
                    if (favlistData.isNotEmpty()) {
                        wholeList.addAll(favlistData)
                    }
                    if (data.popular_places != null) {
                        if (data.popular_places!!.isNotEmpty()) {
                            val poplistData: List<LocationData> = data.popular_places!!
                            poplistData.map { it.type = PopularPlaceType }
                            if (poplistData.isNotEmpty()) {
                                wholeList.addAll(poplistData)
                            }
                        }
                    }
                    if (data.past_booking_places != null) {
                        if (data.past_booking_places!!.isNotEmpty()) {
                            pastListData = data.past_booking_places!!
                            pastListData.map {
                                it.type = RecentPlaceType
                            }
                            wholeList.addAll(pastListData)
                        } else {
                            wholeList.addAll(pastListData)
                        }
                    }
                    insertAsyncTask(mDao, wholeList).execute()
                }
            }
        } else if (data is PassengerInfoData) {
            if (data.detail.favourite_places != null) {
                if (data.detail.favourite_places!!.isNotEmpty()) {
                    val favlistData: List<LocationData> = data.detail.favourite_places!!
                    favlistData.map { it.type = FavouritePlaceType }
                    val wholeList = mutableListOf<LocationData>()
                    if (favlistData.isNotEmpty()) {
                        wholeList.addAll(favlistData)
                    }
                    if (data.detail.popular_places != null) {
                        if (data.detail.popular_places!!.isNotEmpty()) {
                            val poplistData: List<LocationData> = data.detail.popular_places!!
                            poplistData.map { it.type = PopularPlaceType }
                            if (poplistData.isNotEmpty()) {
                                wholeList.addAll(poplistData)
                            }
                        }
                    }
                    if (data.detail.past_booking_places != null) {
                        if (data.detail.past_booking_places!!.isNotEmpty()) {
                            pastListData = data.detail.past_booking_places!!
                            pastListData.map {
                                it.type = RecentPlaceType
                            }
                            wholeList.addAll(pastListData)
                        } else {
                            wholeList.addAll(pastListData)
                        }
                    }
                    insertAsyncTask(mDao, wholeList).execute()
                }
            }
        }
    }

    fun getPassengerInfoApiCall(requestData: JSONObject, viewModel: BookTaxiHomeViewModel) {
        if (NetworkStatus.isOnline(context)) {
            val client: CoreClient = AppController.getInstance().apiManagerWithEncryptBaseUrl
//            client = ServiceGenerator(context, TaxiUtil.API_BASE_URL, false).createService(CoreClient::class.java)
            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())

            val coreResponse = client.getPassengerInfo(body, SessionSave.getSession(LANG, context))
            coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<PassengerInfoData> {
                override fun onResponse(call: Call<PassengerInfoData>, response: Response<PassengerInfoData>) {
                    if (response.isSuccessful) {
                        try {

                            val data = response.body()

                            if (data!!.detail.past_booking_places != null) {
                                if (data!!.detail.past_booking_places!!.isNotEmpty()) {
                                    deleteAsyncTask(mDao).execute()
                                }
                            }
                            deleteAsyncTaskPopular(mDao).execute()
                            insertFavPlaces(data)


                        } catch (e: Exception) {
                            e.printStackTrace()

                        }
                    } else {

                        CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    }
                }

                override fun onFailure(call: Call<PassengerInfoData>, t: Throwable) {
                    t.printStackTrace()
                    CToast.ShowToast(context, NC.getString(R.string.server_con_error))
                    viewModel.saveBookingRes.value = null
                }
            }))
        } else {
            CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
        }
    }

    fun getFavPlaces(viewModel: BookTaxiHomeViewModel): LiveData<List<LocationData>> {
        return mDao.loadFavPlaces()
    }

    fun getAllFavPopRecPlaces(): LiveData<List<LocationData>> {
        return mDao.loadAllFavouriteAndPopularAndRecent()
    }


    fun getAllFavouritePlaces(): LiveData<List<LocationData>> {
        return mDao.loadAllFavourite()
    }

    fun getFavPlaceWithFilter(str: String): LiveData<List<LocationData>> {
        return mDao.getLocationFilter(str)
    }

    fun deletFavourite(str: String) {
        deleteFavAsyncTask(mDao, str).execute()
    }

    fun deleteSavedPlaces() {
        deleteAsyncTask(mDao).execute()
    }

    private class deleteFavAsyncTask internal constructor(private val mDao: PlacesDao, val str: String) : AsyncTask<LocationData, Void, Void>() {

        override fun doInBackground(vararg params: LocationData): Void? {
            mDao.deleteFavourite(str)
            return null
        }
    }

    private class insertAsyncTask internal constructor(private val mPlaceDao: PlacesDao, val favModel: List<LocationData>) : AsyncTask<LocationData, Void, Void>() {

        override fun doInBackground(vararg params: LocationData): Void? {
            mPlaceDao.insertLog(favModel)
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val mPlaceDao: PlacesDao) : AsyncTask<LocationData, Void, Void>() {

        override fun doInBackground(vararg params: LocationData): Void? {
            mPlaceDao.deletePlace()
            return null
        }
    }


    private class deleteAsyncTaskPopular internal constructor(private val mPlaceDao: PlacesDao) : AsyncTask<LocationData, Void, Void>() {

        override fun doInBackground(vararg params: LocationData): Void? {
            mPlaceDao.deletePopular()
            return null
        }
    }

}