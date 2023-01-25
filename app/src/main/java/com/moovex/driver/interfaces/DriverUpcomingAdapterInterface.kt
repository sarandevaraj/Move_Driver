package com.moovex.driver.interfaces

import com.moovex.driver.data.apiData.DriverUpcomingResponse

interface DriverUpcomingAdapterInterface {
    fun updateUpcomingAdapter(data: List<DriverUpcomingResponse.PastBooking>, clickedPosition: Int)
}