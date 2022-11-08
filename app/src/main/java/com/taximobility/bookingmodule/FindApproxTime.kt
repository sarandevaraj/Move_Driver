//package com.taximobility.bookingmodule
//
//import android.app.Activity
//import android.os.AsyncTask
//import com.taximobility.bookingmodule.Interface.ApproxTimeListener
//import com.taximobility.features.ApproximateCalculation.approxFare
//
//object FindApproxTime {
//
//    val speed = "45"
//
//    private var approxFare = 0.0
//
//    lateinit var listener: ApproxTimeListener
//
//    private var DISTANCE_TYPE_FOR_ETA: Int = 1
//    private var DISTANCE_TYPE_FOR_BOOK_LATER: Int = 2
//    private var DISTANCE_TYPE_FOR_FARE: Int = 3
//
//    private var approxTravelTime: String = "0"
//    private var approxTravelDist: String = "0"
//
//    fun calculateApproxTime(approxTimeListener: ApproxTimeListener, context: Activity, dDistance: Double, sMetric: String, type: Int) {
//        this.listener = approxTimeListener
//        ApproximateTime(context, dDistance, sMetric, type)
//    }
//
//    /**
//     * Class to calculate ETA and Trip distance if user does not have buisness key
//     */
//    private class ApproximateTime constructor(val context: Activity, var Ddistance: Double, var Smetric: String, var type: Int) : AsyncTask<Void, Void, Void>() {
//        var time: Double = 0.toDouble()
//
//        override fun doInBackground(vararg params: Void): Void? {
//
//            try {
//                time = calculateTime(context, Ddistance, Smetric, type)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            return null
//        }
//
//        override fun onPostExecute(result: Void) {
//            super.onPostExecute(result)
////                if (bookingState == BOOKINGSTATE.STATE_TWO && sf.getPickuplatlng() != null) {
////                    if (pickupMarker != null)
////                        pickupMarker.remove()
////                    if (TaxiUtil.mDriverdata.size > 0) {
////                        if (E_time == 0.0) {
////                            E_time = 1.0
////                        }
////                    }
////                    val b = CustomMarker.getMarkerBitmapFromView(E_time.toInt().toString(), context, sf.getPickupLocTxt())        //                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
////                    pickupMarker = map.addMarker(MarkerOptions()
////                            .position(sf.getPickuplatlng())
////                            .icon(BitmapDescriptorFactory.fromBitmap(b)))
////                    pickupMarker.setTag("pickup")
////                    pickupMarker.setAnchor(0.0f, 1f)
////                }
//            if (type == DISTANCE_TYPE_FOR_FARE) {
//                approxTravelTime = time.toString()
//                approxTravelDist = Ddistance.toString()
//                approxFare = approxFare(context, Ddistance, time)
//                listener.approxTimeFareType(type, time.toString(), Ddistance.toString(), approxFare)
////                    if (sf.getDroplat() != 0.0 && pickupApproxFare != null) {
////                        if (zone_fare_applicable) {
////                            pickupApproxFare.setText(SessionSave.getSession("Currency", context) + decimalFormat.format(java.lang.Double.parseDouble(String.format(Locale.UK, zone_zone_fare.toString()))))
////                            approxFare = zone_zone_fare
////                        } else
////                            pickupApproxFare.setText(SessionSave.getSession("Currency", context) + decimalFormat.format(java.lang.Double.parseDouble(String.format(Locale.UK, approxFare.toString()))))
////                    } else if (pickupApproxFare != null) {
////                        pickupApproxFare.setText("")
////                    }
//            }
//        }
//    }
//
//
//    /*
//     * this method is used to calculate time trip time
//     * */
//    fun calculateTime(context: Activity, distance: Double, metric: String, type: Int): Double {
//        var distanceM = 0.0
//
//        try {
//            var timeZ = 0.0
//            timeZ = distance / java.lang.Double.parseDouble(speed)
//            timeZ *= 3600 // time duration in seconds
//            val minutes = Math.floor(timeZ / 60)
//            timeZ -= minutes * 60
//            val seconds1 = Math.floor(timeZ)
//            val timeString = minutes.toInt().toString() + "." + seconds1.toInt()
//            val minsfloatValue = java.lang.Float.parseFloat(timeString)
//
//            distanceM = Math.round(minsfloatValue * 100.0) / 100.0
//
//            if (distanceM <= 1) {
//                distanceM = 1.0
//            }
//            if (type == DISTANCE_TYPE_FOR_ETA) {
//                listener.approxTimeETAType(type, distanceM)
//            } else if (type == DISTANCE_TYPE_FOR_BOOK_LATER) {
//                approxTravelTime = distanceM.toString()
//                approxTravelDist = distance.toString()
//                listener.approxTimeBookLater(type, approxTravelDist, approxTravelTime)
//            } else if (type == DISTANCE_TYPE_FOR_FARE) {
//                approxTravelTime = distanceM.toString()
//                approxTravelDist = distance.toString()
//                approxFare = approxFare(context, distance, distanceM)
//                listener.approxTimeFareType(type, approxTravelTime, approxTravelDist, approxFare)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return distanceM
//    }
//
//
//}