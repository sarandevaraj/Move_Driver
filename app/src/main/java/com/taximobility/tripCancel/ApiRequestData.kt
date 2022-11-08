package com.taximobility.tripCancel

data class CancelTripRequestData(val passenger_log_id: Int, val travel_status: String, val remarks: String, val pay_mod_id: String, val creditcard_cvv: String, val card_id: Int)