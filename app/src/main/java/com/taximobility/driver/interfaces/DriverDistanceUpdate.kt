package com.taximobility.driver.interfaces

interface DriverDistanceUpdate {
    fun onDistanceUpdate(distance: Double?, s: String)
}