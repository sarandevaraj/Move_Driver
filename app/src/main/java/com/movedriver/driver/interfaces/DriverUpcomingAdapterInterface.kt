package com.movedriver.driver.interfaces

import com.movedriver.driver.data.apiData.DriverUpcomingResponse

interface DriverUpcomingAdapterInterface {
    fun updateUpcomingAdapter(data: List<DriverUpcomingResponse.PastBooking>, clickedPosition: Int)
}