package com.taximobility.bookingmodule.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taximobility.bookingmodule.LocationData

@Dao
interface PlacesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(models: List<LocationData>)

    @Query("SELECT * From favModel WHERE type = 1 or type = 2 ORDER BY type ASC LIMIT 3 ")
    fun loadFavPlaces(): LiveData<List<LocationData>>

    @Query("DELETE FROM favModel")
    fun deletePlace()

    @Query("SELECT * From favModel WHERE type=1")
    fun loadAllFavourite(): LiveData<List<LocationData>>

    @Query("SELECT * From favModel")
    fun loadAllFavouriteAndPopularAndRecent(): LiveData<List<LocationData>>

    @Query("SELECT * FROM favModel WHERE location_name LIKE :values || '%'")
    fun getLocationFilter(values: String): LiveData<List<LocationData>>

    @Query("SELECT * From favModel WHERE type=3")
    fun loadAllPastBookingPlaces(): LiveData<List<LocationData>>

    @Query("DELETE FROM favModel WHERE location_name LIKE :str || '%'")
    fun deleteFavourite(str:String)

    @Query("DELETE FROM favModel WHERE type=2 or type=1")
    fun deletePopular()
}