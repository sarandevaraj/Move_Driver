package com.moovex.driver.interfaces

interface DriverDistanceUpdate {
    fun onDistanceUpdate(distance: Double?, s: String)
}