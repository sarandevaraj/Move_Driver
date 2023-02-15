package com.movedriver.driver.errorLog

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movedriver.driver.data.DriverModelDriverInfo
import org.json.JSONObject

@Entity(tableName = "apiErrorModel")
data class DriverApiErrorModel(
    @PrimaryKey(autoGenerate = true) val ids: Int = 0,
    val timeStamp: String,
    val apiCase: String,
    val error: String,
    val driverDataDriver: DriverModelDriverInfo,
    val inputParams: JSONObject,
    val classContext: String,
    val sendStatus: Int
)