package com.example.geofencing.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.geofencing.view_model.GpsObserver;

public class GpsManager implements LocationListener {

    private GpsObserver observer;
    private LocationManager locationManager;

    private Location previousLocation;

    public GpsManager(GpsObserver observer, Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
            this.observer = observer;
        }
    }

    public void setObserver(GpsObserver observer) {
        this.observer = observer;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (this.observer == null) {
            return;
        }

        if (this.previousLocation == null) {
            this.previousLocation = location;
        }

        float distanceTraveled = this.previousLocation.distanceTo(location);

        this.observer.metersTraveled(distanceTraveled);
    }
}
