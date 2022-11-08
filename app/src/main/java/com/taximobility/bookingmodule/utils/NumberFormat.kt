package com.taximobility.bookingmodule.utils

import java.text.DecimalFormat

object NumberFormat{
    private var decimalFormat: DecimalFormat? = null
    fun convertDouble(value:Double) : String{
        decimalFormat = DecimalFormat("####0.00")
        return decimalFormat!!.format(value)
    }
    @JvmStatic
    fun convertFloat(value:Float) : Float{
        decimalFormat = DecimalFormat("####0.000")
        return decimalFormat!!.format(value).toFloat()
    }


}