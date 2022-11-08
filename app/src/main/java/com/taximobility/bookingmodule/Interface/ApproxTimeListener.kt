package com.taximobility.bookingmodule.Interface

interface ApproxTimeListener{
    /**
     *   private final int DISTANCE_TYPE_FOR_ETA = 1;
        private final int DISTANCE_TYPE_FOR_BOOK_LATER = 2;
         private final int DISTANCE_TYPE_FOR_FARE = 3;
     */
    fun approxTimeFareType(type:Int,time:String,dist:String,approxFare:Double)
    fun approxTimeETAType(type:Int,eTA:Double)
    fun approxTimeBookLater(type:Int,time:String,dist:String)
}