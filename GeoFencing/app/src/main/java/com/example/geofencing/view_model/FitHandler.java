package com.example.geofencing.view_model;

import android.location.Location;

import com.example.geofencing.repository.GpsManager;
import com.example.geofencing.view.MapActivity;
import com.example.geofencing.view.RouteObserver;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class FitHandler implements GpsObserver {

    private static FitHandler INSTANCE = null;

    private RouteObserver routeObserver;
    private GpsManager gpsManager;
    private AchievementData userData;

    private ApiHandler apiHandler;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private MapController mapController;


    private FitHandler(MapView mapView, MyLocationNewOverlay locationOverlay) {
        this.apiHandler = new ApiHandler(this);
        this.mapView = mapView;
        this.locationOverlay = locationOverlay;
        startMap();
    }

    public void setRouteObserver(RouteObserver routeObserver) {
        this.routeObserver = routeObserver;
    }

    private void startMap() {
        this.locationOverlay.enableMyLocation();
        this.locationOverlay.enableFollowLocation();
        this.mapView.getOverlays().add(this.locationOverlay);
        this.mapController = new MapController(this.mapView);
        this.mapController.setCenter(new GeoPoint(51.58656, 4.77596));
        this.mapController.zoomTo(19);
    }

    public void centerOnUser() {
        this.mapController.zoomTo(19);
        this.mapController.setCenter(this.locationOverlay.getMyLocation());
        this.locationOverlay.enableFollowLocation();
    }

    public void startTrackingMetersTravelled(MapActivity mapActivity) {

        //TODO inlezen van opgeslagen data en deze initializeren
        this.userData = new AchievementData();

        this.gpsManager = new GpsManager(this, mapActivity.getApplicationContext());
        this.setRouteObserver(mapActivity);
    }

    public void findQuickestPathTo(String city, String street, String number, boolean makeLap) {
        this.apiHandler.findLocationFor(city, street, number, this.locationOverlay.getLastFix(), makeLap);
    }

    @Override
    public void metersTraveled(float meters) {
        this.userData.addTotalMetersToday(meters);

        if (this.routeObserver != null) {
            this.routeObserver.updateMetersTravelled(this.userData.getTotalMetersToday());
        }
    }

    public static boolean isInstanceNull() {
        if (INSTANCE == null) {
            return true;
        }

        return false;
    }

    public static FitHandler getInstance(MapView mapView, MyLocationNewOverlay locationOverlay) {
        if (INSTANCE == null) {
            INSTANCE = new FitHandler(mapView, locationOverlay);
        }

        return INSTANCE;
    }

    public static FitHandler getInstance() {
        return INSTANCE;
    }

    public AchievementData getUserData() {
        return userData;
    }
}
