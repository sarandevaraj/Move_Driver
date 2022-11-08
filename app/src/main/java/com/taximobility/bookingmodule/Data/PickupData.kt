package com.taximobility.bookingmodule.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PickupData(var id: Int, var lat: Double,
                      var lng: Double, var placeName: String, var placeId: String = "", var placeType: String = "") : Parcelable