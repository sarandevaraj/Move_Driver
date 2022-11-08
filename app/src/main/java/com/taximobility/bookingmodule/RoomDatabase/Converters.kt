package com.taximobility.bookingmodule.RoomDatabase

import androidx.room.TypeConverter
import com.taximobility.bookingmodule.LocationData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun getValueFromModel(data: List<LocationData>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun getModelFromString(data: String): List<LocationData> {
        val type = object : TypeToken<List<LocationData>>() {}.type
        return Gson().fromJson(data,type)
    }
}