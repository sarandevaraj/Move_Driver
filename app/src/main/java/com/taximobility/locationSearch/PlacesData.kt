package com.taximobility.locationSearch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PlacesData(var id: Int, var lat: Double,
                      var lng: Double, var placeName: String, var placeId: String = "", var placeType: String? = "", var favPlaceType: String? = "0", var android_icon: String? = "") : Parcelable