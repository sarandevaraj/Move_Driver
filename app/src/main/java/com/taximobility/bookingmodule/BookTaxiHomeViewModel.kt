package com.taximobility.bookingmodule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taximobility.bookingmodule.Data.NearestDriverDatas
import com.taximobility.bookingmodule.Data.SaveBookingResponse
import com.taximobility.data.apiData.CheckPromoCodeData
import com.taximobility.data.apiData.DeleteFavouriteData
import org.json.JSONObject

class BookTaxiHomeViewModel(application: Application) : AndroidViewModel(application) {

    var bookTaxiHomeRepository = BookTaxiHomeRepository(application)

    var skipDropClick: MutableLiveData<Boolean> = MutableLiveData()

    var nearestResponse: MutableLiveData<NearestDriverDatas> = MutableLiveData()

    var saveBookingRes: MutableLiveData<SaveBookingResponse> = MutableLiveData()

    var checkPromocodeResponse: MutableLiveData<CheckPromoCodeData> = MutableLiveData()

    var deleteFavouriteData: MutableLiveData<DeleteFavouriteData> = MutableLiveData()

    var carLayClick: MutableLiveData<Boolean> = MutableLiveData()


    var cashCardClick: MutableLiveData<Boolean> = MutableLiveData()

    var payType: MutableLiveData<String> = MutableLiveData()

    var cashCardEnable: MutableLiveData<Boolean> = MutableLiveData()

    var modelDetailsRes: MutableLiveData<String> = MutableLiveData()

    var modelPreferenceRes: MutableLiveData<String> = MutableLiveData()

    fun skipDropLocClick(loc: Int) {
        skipDropClick.value = loc == 1
    }

    fun callNearestApiCall(requestData: JSONObject) = bookTaxiHomeRepository.callNearestApi(requestData, this)

    fun callSaveBookingApi(requestData: JSONObject) = bookTaxiHomeRepository.saveBookingApiCall(requestData, this)

    fun callGetPassengerInfoApi(requestData: JSONObject) = bookTaxiHomeRepository.getPassengerInfoApiCall(requestData, this)

    fun deleteFavouriteApiCall(requestData: JSONObject) = bookTaxiHomeRepository.callDeleteFavourite(requestData, this)

    fun callModelDetailsApi(requestData: JSONObject) = bookTaxiHomeRepository.getModelApiCall(requestData, this)

    fun callModelPrefrenceApi(requestData: JSONObject) = bookTaxiHomeRepository.getPrefrenceApiCall(requestData, this)


    fun getFavouritePlaces(): LiveData<List<LocationData>> {
        return bookTaxiHomeRepository.getFavPlaces(this)
    }

    fun getAllFavPopRecPlaces(): LiveData<List<LocationData>> {
        return bookTaxiHomeRepository.getAllFavPopRecPlaces()
    }


    fun loadAllFavourite(): LiveData<List<LocationData>> {
        return bookTaxiHomeRepository.getAllFavouritePlaces()
    }

    fun callCheckPromoCode(requestData: JSONObject) = bookTaxiHomeRepository.checkPromoCodeApiCall(requestData, this)

    fun deleteFavourite(str: String) = bookTaxiHomeRepository.deletFavourite(str)

    fun cashCardClick() {
        cashCardClick.value = true
    }

    fun setPaymentType(str: String) {
        payType.value = str
    }

    fun cashCardEnable(enable: Boolean) {
        cashCardEnable.value = enable
    }

}