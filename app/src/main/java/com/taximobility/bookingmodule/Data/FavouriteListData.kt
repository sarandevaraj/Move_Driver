package com.taximobility.bookingmodule.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favModel")
data class FavouriteListData(
        @PrimaryKey var _id: String = "",
        var p_location_name: String = "",
        var d_location_name: String = "",
        var latitude: Double = 0.0,
        var longtitute: Double = 0.0,
        var loction_type: String = "",
        var location_name: String = "",
        var label_name: String = "",
        var android_icon: String = "",
        var ios_icon: String = "",
        var type:String = "1"
)
