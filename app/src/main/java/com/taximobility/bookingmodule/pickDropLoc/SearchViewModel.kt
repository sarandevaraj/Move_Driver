package com.taximobility.bookingmodule.pickDropLoc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.taximobility.bookingmodule.BookTaxiHomePage
import com.taximobility.locationSearch.PlacesData
import com.taximobility.util.PickupPlace
import kotlin.collections.ArrayList

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    var searchRepository = SearchRepository(application)

    var pickDropclick: MutableLiveData<Boolean> = MutableLiveData()

    var favClick: MutableLiveData<Boolean> = MutableLiveData()

    var pickupLoc: MutableLiveData<String> = MutableLiveData()

    var dropLoc: MutableLiveData<String> = MutableLiveData()

    var pickupDropLoc: MutableLiveData<String> = MutableLiveData()

    var recentVisible: MutableLiveData<Int> = MutableLiveData()

    var pickupVisible: MutableLiveData<Int> = MutableLiveData()

    var dropVisible: MutableLiveData<Int> = MutableLiveData()

    var recentPlace1: MutableLiveData<String> = MutableLiveData()

    var recentPlace2: MutableLiveData<String> = MutableLiveData()

    var recentPlace3: MutableLiveData<String> = MutableLiveData()

    var recentPlaceClick: MutableLiveData<Int> = MutableLiveData()

    fun pickupClick() {
        pickDropclick.value = true
    }

    fun dropClick() {
        pickDropclick.value = false
    }

    fun favIconClick(locType: String) {
        favClick.value = locType == PickupPlace
    }

    fun setPickupDropLocation(address: String, addressType: String) {
        pickupDropLoc.value = address
        if (addressType == PickupPlace) {
            pickupLoc.value = address
        } else {
            if (dropLoc.value == null || dropLoc.value!!.isEmpty()) {
                //BookTaxiHomePage.isDropSetFirstTime = true
                dropLoc.value = address
            } else {
                BookTaxiHomePage.isDropSetFirstTime = false
                dropLoc.value = address
            }

        }
    }

    fun setPickupLoc(address: String) {
        pickupLoc.value = address
    }

    fun setDropLoc(address: String) {
        if (dropLoc.value == null || dropLoc.value!!.isEmpty()) {
            //BookTaxiHomePage.isDropSetFirstTime = true
            dropLoc.value = address
        } else {
            BookTaxiHomePage.isDropSetFirstTime = false
            dropLoc.value = address
        }
    }

    fun setRecentPlaces(placeArray: ArrayList<PlacesData>) {

        val size: Int = placeArray.size
        if (BookTaxiHomePage.BOOKINGSTATE.STATE_ONE == BookTaxiHomePage.bookingState) {
            recentVisible.value = size
        } else {
            recentVisible.value = 0
        }

        when (size) {
            1 -> recentPlace1.value = placeArray[0].placeName
            2 -> {
                recentPlace1.value = placeArray[0].placeName
                recentPlace2.value = placeArray[1].placeName
            }
            else -> {
                recentPlace1.value = placeArray[0].placeName
                recentPlace2.value = placeArray[1].placeName
                recentPlace3.value = placeArray[2].placeName
            }
        }
    }

    fun getAllPastBookingPlaces() = searchRepository.getAllPastBookingPlaces()

    fun recentPlaceClick(loc: Int) {
        recentPlaceClick.value = loc
    }
}