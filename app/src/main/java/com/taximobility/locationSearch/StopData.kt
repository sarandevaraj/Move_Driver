package com.taximobility.locationSearch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class StopData(var id: Int, var lat: Double,
                    var lng: Double, var placeName: String, var placeId: String = "") : Parcelable