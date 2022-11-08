package com.taximobility.interfaces

interface CancelPayment {

    fun onCancelPay(trip_id: Int, reason: String?, cancelFare: String?, type: String?,order_id:String?)

}