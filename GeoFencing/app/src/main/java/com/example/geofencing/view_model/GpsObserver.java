package com.example.geofencing.view_model;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public interface GpsObserver {

    void metersTraveled(float meters, Location currentLocation);

}
