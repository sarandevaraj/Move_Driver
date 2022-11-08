package com.taximobility.bookingmodule.Interface

interface RouteListeners{
    fun drawRoutePickToDrop(time: Double?, dist: Double?, approxFare:Double)
    fun getETADiverToPickup(time: Double?, dist: Double?)
}