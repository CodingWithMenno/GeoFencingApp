package com.example.geofencing.view_model;

import android.Manifest;
import android.content.Context;

import com.example.geofencing.R;
import com.example.geofencing.view.RouteObserver;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class FitHandler {

    private RouteObserver routeObserver;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private MapController mapController;

    public FitHandler(MapView mapView, MyLocationNewOverlay locationOverlay) {
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
}
