package com.movedriverdriver.driver.interfaces

import com.movedriverdriver.driver.data.apiData.DriverUpcomingResponse

interface DriverUpcomingAdapterInterface {
    fun updateUpcomingAdapter(data: List<DriverUpcomingResponse.PastBooking>, clickedPosition: Int)
}