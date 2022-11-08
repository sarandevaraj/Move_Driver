package com.taximobility.interfaces

import com.taximobility.bookingmodule.BookTaxiHomeViewModel
import org.json.JSONObject

interface NodeAuthListener {
    fun nodeAuthListener(listener:Boolean,requestData:JSONObject,viewModel: BookTaxiHomeViewModel)
}