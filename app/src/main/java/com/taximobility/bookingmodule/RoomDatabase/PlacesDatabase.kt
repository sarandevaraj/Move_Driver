package com.taximobility.bookingmodule.RoomDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.taximobility.bookingmodule.LocationData


@Database(entities = [LocationData::class], version = 2)
@TypeConverters(Converters::class)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlacesDao

    companion object {

        private var INSTANCE: PlacesDatabase? = null


        internal fun getDatabase(context: Context): PlacesDatabase? {
            if (INSTANCE == null) {
                synchronized(PlacesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                PlacesDatabase::class.java, "Favdatabase")
                                .build()

                    }
                }
            }
            return INSTANCE
        }
    }
}