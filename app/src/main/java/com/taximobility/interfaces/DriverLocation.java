package com.taximobility.interfaces;

/**
 * Created by developer on 9/14/16.
 * Interface to update driver location receive from getpassengerupdate
 */
public interface DriverLocation {
    void driverLocationUpdate(double lat, double lng);
}
