package com.taximobility.tripCancel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCreditCard(vararg models: CreditCardData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCreditCards(models: List<CreditCardData>)

    @Query("SELECT * From CardDetails")
    fun loadAllCards(): LiveData<List<CreditCardData>>

    @Query("DELETE From CardDetails")
    fun deleteAllCards()
}