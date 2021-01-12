package com.example.geofencing.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.geofencing.R;
import com.example.geofencing.view_model.GpsObserver;

public class GpsManager implements LocationListener {

    private final float MAX_SPEED = 4;

    private GpsObserver observer;
    private LocationManager locationManager;

    private Location previousLocation;

    private Context context;

    private boolean isGpsOn = true;

    public GpsManager(GpsObserver observer, Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        this.observer = observer;
        this.context = context;

        getLocationUpdates();
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

        float distanceTraveled = this.previousLocation.distanceTo(location) / 2;

        if (location.getSpeed() <= MAX_SPEED) {
            this.observer.metersTraveled(distanceTraveled, location);
        }
    }

    private void getLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
        }
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            this.locationManager.removeUpdates(this);
            Toast.makeText(context, context.getResources().getString(R.string.gps_off), Toast.LENGTH_LONG).show();
            this.isGpsOn = false;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            getLocationUpdates();
            if (!this.isGpsOn) {
                Toast.makeText(context, context.getResources().getString(R.string.gps_on), Toast.LENGTH_LONG).show();
                this.isGpsOn = true;
            }
        }
    }

    public Location getLocation() {
        return previousLocation;
    }
}
