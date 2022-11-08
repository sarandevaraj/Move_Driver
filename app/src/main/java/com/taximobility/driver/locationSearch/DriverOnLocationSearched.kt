package com.taximobility.driver.locationSearch


interface DriverOnLocationSearched {
    fun onLocationSearched(queryString: String)
    fun onItemClicked(driverPlacesDetail: DriverPlacesDetail)
}