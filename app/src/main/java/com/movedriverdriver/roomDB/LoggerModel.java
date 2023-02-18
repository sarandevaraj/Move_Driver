package com.movedriverdriver.roomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by developer on 7/5/18.
 */

@Entity(tableName = "loggerModel")
public class LoggerModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "api_type")
    public String apiType;

    public String time;
    public String requested_time;
    public String responded_time;
    public String url;
    public String request;
    public String response;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        LoggerModel loggerModel = (LoggerModel) obj;

        return loggerModel.id == this.id && loggerModel.apiType == this.apiType /*&& loggerModel.time == this.time*/;
    }

}
