package com.movedriver.driver.interfaces

interface DriverDistanceUpdate {
    fun onDistanceUpdate(distance: Double?, s: String)
}