package com.taximobility.driver.locationSearch

import java.util.*

interface DriverPlaceSearchList {
    fun setPlaceList(placeDetailResultDriver: ArrayList<DriverPlacesDetail>?)
    fun setPlaceDetail(placeDetailDriver: DriverPlacesDetail)
}