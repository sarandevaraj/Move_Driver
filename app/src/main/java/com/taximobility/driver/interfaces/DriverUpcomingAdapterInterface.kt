package com.taximobility.driver.interfaces

import com.taximobility.driver.data.apiData.DriverUpcomingResponse

interface DriverUpcomingAdapterInterface {
    fun updateUpcomingAdapter(data: List<DriverUpcomingResponse.PastBooking>, clickedPosition: Int)
}