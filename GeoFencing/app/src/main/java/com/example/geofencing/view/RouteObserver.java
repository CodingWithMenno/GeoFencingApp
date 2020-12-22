package com.example.geofencing.view;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public interface RouteObserver {

    void updateMetersTravelled(float totalMeters);

    void setNewRoute(List<GeoPoint> geoPoints);

    void removeRoute();

    void routeCompleted();

    void dayGoalReached();
}
