package com.taximobility.bookingmodule.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.taximobility.data.apiData.AddFavouriteData
import org.json.JSONObject

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private var favRepository = FavouriteRepository(application)
    var favData: MutableLiveData<AddFavouriteData> = MutableLiveData()

    fun callAddFavApiCall(requestData: JSONObject) {
        favRepository.callAddFavApi(requestData,this)
    }

    fun setFavDataValue(data : AddFavouriteData){
        favData.value=data
    }
}