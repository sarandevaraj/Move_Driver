package com.movedriver.driver.errorLog

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(entities = [DriverApiErrorModel::class], version = 1, exportSchema = false)
@TypeConverters(DriverConverter::class)
abstract class DriverErrorLogDatabase : RoomDatabase() {
    abstract fun errorLogDao(): DriverErrorLogDao

    companion object {
        @Volatile
        private lateinit var driverErrorLogDatabase: DriverErrorLogDatabase

        @JvmStatic
        fun getDatabase(context: Context): DriverErrorLogDatabase {
            synchronized(DriverErrorLogDatabase::class.java) {
                driverErrorLogDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    DriverErrorLogDatabase::class.java,
                    "errorLogDatabase"
                ).build()
            }
            return driverErrorLogDatabase
        }
    }
}