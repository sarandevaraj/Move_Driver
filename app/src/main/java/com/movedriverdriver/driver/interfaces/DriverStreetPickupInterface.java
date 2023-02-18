package com.movedriverdriver.driver.interfaces;

import android.location.Location;

/**
 * Created by developer on 28/9/17.
 */

public interface DriverStreetPickupInterface {
    void updateFare(String distanceFare, Location latLng);
}