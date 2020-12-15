package com.example.geofencing.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;
import com.progress.progressview.ProgressView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements RouteObserver {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;

    private ProgressView progressMeterView;

    private FitHandler fitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplication(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map);

        this.progressMeterView = findViewById(R.id.progress_meter_view);

        if (FitHandler.isInstanceNull()) {
            makeOsmMap();
        } else {
            this.fitHandler = FitHandler.getInstance();
        }

    }

    public void centerButtonClicked(View view) {
        this.fitHandler.centerOnUser();
    }

    public void meterBarClicked(View view) {
        Intent achievementsIntent = new Intent(this, AchievementsActivity.class);
        startActivity(achievementsIntent);
    }

    private void makeOsmMap() {
        this.mapView = findViewById(R.id.map_view);
        this.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        this.mapView.setMultiTouchControls(true);
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSIONS_REQUEST_CODE);

        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), this.mapView);

        this.fitHandler = FitHandler.getInstance(this.mapView, this.locationOverlay);
        this.fitHandler.startTrackingMetersTravelled(this);
    }

    @Override
    public void updateMetersTravelled(float totalMeters) {
        float percentage = (totalMeters / FitHandler.METERS_PER_DAY) * 100;
        float mappedValue = percentage / 100;

        this.progressMeterView.post(() -> {
           this.progressMeterView.setProgress(mappedValue);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.mapView != null) {
            this.mapView.onResume();
        }

        if (this.locationOverlay != null) {
            this.locationOverlay.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (this.mapView != null) {
            this.mapView.onPause();
        }

        if (this.locationOverlay != null) {
            this.locationOverlay.onPause();
        }
    }
}