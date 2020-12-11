package com.example.geofencing.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements RouteObserver {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private MapController mapController;

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

        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), this.mapView);
        this.locationOverlay.enableMyLocation();
        this.locationOverlay.enableFollowLocation();
        this.mapView.getOverlays().add(this.locationOverlay);
        this.mapController = new MapController(this.mapView);
        this.mapController.zoomTo(19);
        this.mapController.setCenter(this.locationOverlay.getMyLocation());
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
        }
    }

    public void clicked(View view) {
        this.mapController.setCenter(new GeoPoint(51.8132979, 4.6900929));
        this.mapController.animateTo(new GeoPoint(51.8132979, 4.6900929));
    }
}