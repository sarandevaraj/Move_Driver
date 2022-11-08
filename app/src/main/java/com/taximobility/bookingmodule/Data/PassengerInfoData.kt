package com.taximobility.bookingmodule.Data

import com.google.gson.annotations.SerializedName
import com.taximobility.bookingmodule.LocationData

data class PassengerInfoData(
        @SerializedName("detail")
        var detail: PassengerInfoDetail,
        var status: Int = 0,
        var message: String = "")

data class PassengerInfoDetail(
        @SerializedName("favorite_place")
        var favourite_places: List<LocationData>?,
        @SerializedName("popular_place")
        var popular_places: List<LocationData>?,
        @SerializedName("past_bookings")
        var past_booking_places: List<LocationData>?
)

