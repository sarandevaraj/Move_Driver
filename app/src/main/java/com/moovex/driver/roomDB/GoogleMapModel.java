package com.moovex.driver.roomDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "google_maplogger")
public class GoogleMapModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "from_to")
    public String fromTo;

    public double time;
    public double distance;
    public String routeResult;
    public String distanceResult;
}
