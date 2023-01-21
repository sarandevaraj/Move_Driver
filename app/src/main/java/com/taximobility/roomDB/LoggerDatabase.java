package com.taximobility.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by developer on 10/5/18.
 */
@Database(entities = {LoggerModel.class}, version = 1, exportSchema = false)
public abstract class LoggerDatabase extends RoomDatabase {

    public abstract LoggerDao loggerDao();

    private static LoggerDatabase INSTANCE;

    static LoggerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LoggerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LoggerDatabase.class, "logger_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
