package com.taximobility.bookingmodule.Interface

interface PickDropSetListener {

    fun pickUpSet(latitude: Double, longtitue: Double,address:String)
    fun dropSet(latitude: Double, longtitue: Double,address:String,focus:String)

    fun pickupListener()
    fun dropListener()

    fun kmRestrictListener()
}