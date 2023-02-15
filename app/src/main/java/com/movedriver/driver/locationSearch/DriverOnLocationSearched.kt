package com.movedriver.driver.locationSearch


interface DriverOnLocationSearched {
    fun onLocationSearched(queryString: String)
    fun onItemClicked(driverPlacesDetail: DriverPlacesDetail)
}