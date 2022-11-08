package com.taximobility.features

import android.content.Context
import com.taximobility.bookingmodule.Data.NearestDriverDatas
import com.taximobility.util.SessionSave
import com.google.gson.Gson
import org.json.JSONException
import java.util.*

/**
 * Created by developer on 5/3/16.
 * Used to calculate the approximate fare amount
 * if distance is less than minimun km then the min fare is set as fare amount
 * else if distance is greater than minimum km and less than below_above_km value fare amount=distance_travelled * below_above_km +base fare
 * else if distance is greater than minimum km and greater than below_above_km value fare amount=distance_travelled * below_above_km +base fare
 */
object ApproximateCalculation {
    var aprrox_fare = 0.0
    private var min_km: Float = 0.toFloat()
    private var min_fare: Float = 0.toFloat()
    private var km_wise_fare: Float = 0.toFloat()
    private var additional_fare_per_km: Double = 0.toDouble()


    fun approxFare(c: Context, dist: Double, time: Double): Double {
        try {
            val fareMulti: Double?
            val belowAboveKm: Float
            println("aprrox_fare $aprrox_fare")
            val serverResponse = SessionSave.getSession("Server_Response", c)
            val dataS = Gson().fromJson(serverResponse, NearestDriverDatas::class.java)

            if (dataS != null && dataS.fare_details != null) {
                val fareJson = dataS.fare_details
                if (fareJson.min_km != null) {
                    km_wise_fare = fareJson.km_wise_fare.toFloat()
                    belowAboveKm = fareJson.below_above_km.toFloat()
                    additional_fare_per_km = fareJson.additional_fare_per_km.toDouble()
                    min_km = fareJson.min_km.toFloat()
                    min_fare = fareJson.min_fare.toFloat()
                    if (fareJson.fare_calculation_type != "2") {
                        if (dist > min_km) {
                            if (km_wise_fare == 2f) {
                                if (dist > belowAboveKm)
                                    fareMulti = fareJson.above_km
                                else
                                    fareMulti = fareJson.below_km
                                aprrox_fare = Math.round(dist * fareMulti) + fareJson.base_fare
                            } else {
                                val distanceminusmin = dist - min_km
                                aprrox_fare = distanceminusmin * additional_fare_per_km + min_fare.toDouble() + fareJson.base_fare
                                println("aprrox_fare $aprrox_fare = $distanceminusmin * $additional_fare_per_km + ${min_fare.toDouble()} + ${fareJson.base_fare}")
                            }
                        } else {
                            aprrox_fare = min_fare.toDouble()
                        }
                    } else {
                        aprrox_fare = 0.0
                    }


                    if (fareJson.fare_calculation_type == "3" || fareJson.fare_calculation_type == "2") {
                        val mmm = time * fareJson.minutes_fare
                        println("_____$mmm")
                        aprrox_fare += mmm
                    }
                    aprrox_fare = (String.format(Locale.UK, aprrox_fare.toString())).toDouble()

                    try {
                        if (fareJson.eveningfare_applicable == 1) {
                            var eveningFare = 0.0f

                            eveningFare = fareJson.evening_fare.toFloat()

                            if (eveningFare != 0f) {
                                val eveningFareAmount = (aprrox_fare * (eveningFare / 100)).toFloat()
                                aprrox_fare += eveningFareAmount
                            }
                        }
                        if (fareJson.nightfare_applicable == 1) {
                            var nightFare = 0.0f
                            try {
                                nightFare = fareJson.night_fare.toFloat()
                            } catch (e: NumberFormatException) {
                                e.printStackTrace()
                            }

                            if (nightFare != 0f) {
                                val nightFareAmount = (aprrox_fare * (nightFare / 100)).toFloat()
                                aprrox_fare += nightFareAmount
                            }
                        }


                        var tax = 0.0f
                        try {
                            tax = (SessionSave.getSession("tax", c)).toFloat()
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }

                        if (tax != 0f) {
                            val taxAmount = (aprrox_fare * (tax / 100)).toFloat()
                            aprrox_fare += taxAmount
                            println("fare_" + dist + "dd" + aprrox_fare + "__ " + taxAmount + "tax" + tax)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    return 0.0
                }
            } else {
                return 0.0
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return aprrox_fare
    }


}
