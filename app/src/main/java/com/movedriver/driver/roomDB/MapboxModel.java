package com.movedriver.driver.roomDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by developer on 29/5/18.
 */

@Entity(tableName = "mapbox_maplogger")
public class MapboxModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "from_to")
    public String fromTo;

    public double time;
    public double distance;
    public String routeResult;
    public String distanceResult;
}
