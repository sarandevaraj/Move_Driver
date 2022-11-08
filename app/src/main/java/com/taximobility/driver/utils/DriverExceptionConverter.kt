package com.taximobility.driver.utils

object DriverExceptionConverter{

    fun buildStackTraceString(elements: Array<StackTraceElement>?): String {
        val sb = StringBuilder()
        if (elements != null && elements.isNotEmpty()) {
            for (element in elements) {
                sb.append(element.toString())
            }
        }
        return sb.toString()
    }
}