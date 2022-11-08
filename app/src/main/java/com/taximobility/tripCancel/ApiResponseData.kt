package com.taximobility.tripCancel

data class CancelTripResponseData(val status: Int, val message: String, val cancellation_amount: String, val cancellation_from: String, val wallet_amount: String?)