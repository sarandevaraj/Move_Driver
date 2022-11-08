package com.taximobility.tripCancel

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [CreditCardData::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao

    companion object {
        @Volatile
        private var appDataBase: AppDataBase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDataBase? {
            if (appDataBase == null) {
                synchronized(AppDataBase::class.java) {
                    if (appDataBase == null) {
                        appDataBase = Room.databaseBuilder(context.applicationContext,
                                AppDataBase::class.java, "appDatabase")
                                .build()
                    }
                }
            }
            return appDataBase
        }
    }
}
