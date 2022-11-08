package com.taximobility.bookingmodule.pickDropLoc

import android.content.Context
import com.taximobility.bookingmodule.RoomDatabase.PlacesDao
import com.taximobility.bookingmodule.RoomDatabase.PlacesDatabase

class SearchRepository (val context: Context){
    val db = PlacesDatabase.getDatabase(context)
    private val mDao: PlacesDao = db!!.getPlaceDao()

    fun getAllPastBookingPlaces() = mDao.loadAllPastBookingPlaces()
}