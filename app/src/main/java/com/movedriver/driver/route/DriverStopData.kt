package com.movedriver.driver.route

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

data class DriverStopData(
    var id: Int,
    var lat: Double,
    var lng: Double,
    var placeName: String,
    var tripId: String,
    var placeId: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeString(placeName)
        parcel.writeString(tripId)
        parcel.writeString(placeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DriverStopData> {
        override fun createFromParcel(parcel: Parcel): DriverStopData {
            return DriverStopData(parcel)
        }

        override fun newArray(size: Int): Array<DriverStopData?> {
            return arrayOfNulls(size)
        }
    }

    fun getLatLng(): LatLng {
        return LatLng(lat, lng)
    }
}