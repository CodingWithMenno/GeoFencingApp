package com.example.geofencing.view_model;

import android.content.Context;

import com.example.geofencing.repository.GpsManager;
import com.example.geofencing.view.RouteObserver;

public class FitHandler {

//    private GpsManager gpsManager;
    private RouteObserver routeObserver;

    public void setRouteObserver(RouteObserver routeObserver) {
        this.routeObserver = routeObserver;
    }

    public void getGpsUpdates(Context context) {
//        if (this.gpsManager != null) {
//            return;
//        }
//
//        this.gpsManager = new GpsManager(this, context);
    }
}
