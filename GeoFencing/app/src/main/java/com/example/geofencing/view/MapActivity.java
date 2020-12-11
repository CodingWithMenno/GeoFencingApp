package com.example.geofencing.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.os.Bundle;

import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements RouteObserver {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;

    private FitHandler fitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplication(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_map);

        this.mapView = findViewById(R.id.map_view);
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissions(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mapView.onPause();
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
        } else {
            if (this.fitHandler == null) {
                this.fitHandler = new FitHandler();
                this.fitHandler.setRouteObserver(this);
                this.fitHandler.getGpsUpdates(getApplicationContext());
            }
        }
    }

    @Override
    public void updateUserLocation(double latitude, double longitude) {
        //TODO update user location on map
    }
}