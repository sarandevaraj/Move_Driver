package com.taximobility.bookingmodule.Data

data class SaveBookingResponse(var message: String, var status: Int, var detail: Details, var trip_id: Int)


data class Details(
        var passenger_tripid: Int,
        var notification_time: String,
        var total_request_time: Int,
        var credit_card_status: Int)



