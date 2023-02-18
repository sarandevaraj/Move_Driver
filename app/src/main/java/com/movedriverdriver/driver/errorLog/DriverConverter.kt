package com.movedriverdriver.driver.errorLog

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movedriverdriver.driver.data.DriverModelDriverInfo
import org.json.JSONObject

class DriverConverter {
    @TypeConverter
    fun driverInfotoString(driverInfoDriver: DriverModelDriverInfo): String {
        return Gson().toJson(driverInfoDriver)
    }

    @TypeConverter
    fun stringToDriverInfo(value: String): DriverModelDriverInfo {
        val type = object : TypeToken<DriverModelDriverInfo>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun latLngToString(latLng: LatLng): String {
        return Gson().toJson(latLng)
    }

    @TypeConverter
    fun stringToLatLng(latLng: String): LatLng {
        val type = object : TypeToken<LatLng>() {}.type
        return Gson().fromJson(latLng, type)
    }

    @TypeConverter
    fun inputParamsToJson(inputData: String): JSONObject {
        return JSONObject(inputData)
    }


    @TypeConverter
    fun inputParamsToString(inputData: JSONObject): String {
        return inputData.toString()
    }

}