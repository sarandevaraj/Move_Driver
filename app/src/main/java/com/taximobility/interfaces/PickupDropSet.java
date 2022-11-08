package com.taximobility.interfaces;

/**
 * Created by developer on 14/3/17.
 */
public interface PickupDropSet {
    void pickUpSet(double latitude, double longtitue);

    void dropSet(double latitude, double longtitue);

    void requestPickupAddress();
}
