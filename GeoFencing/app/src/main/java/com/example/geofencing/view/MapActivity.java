package com.example.geofencing.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;


import com.example.geofencing.R;
import com.example.geofencing.view_model.AchievementData;
import com.example.geofencing.view_model.FitHandler;
import com.example.geofencing.view_model.Maths;
import com.progress.progressview.ProgressView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements RouteObserver {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private Polyline route;
    private Marker routeMarker;

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

    public void routeMakerButtonClicked(View view) {
        Intent achievementsIntent = new Intent(this, RouteMakerActivity.class);
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

        Bitmap icon = Maths.convertToBitmap(getResources().getDrawable(R.drawable.ic_userarrow), 80, 90);
        this.locationOverlay.setPersonIcon(icon);
        this.locationOverlay.setDirectionArrow(icon, icon);

        this.fitHandler = FitHandler.getInstance(this.mapView, this.locationOverlay);
        this.fitHandler.startTrackingMetersTravelled(this);
    }

    @Override
    public void updateMetersTravelled(float totalMeters) {
        float percentage = (totalMeters / AchievementData.METERS_PER_DAY) * 100;
        float mappedValue = percentage / 100;

        this.progressMeterView.post(() -> {
           this.progressMeterView.setProgress(mappedValue);
        });

        if (this.route != null) {
            this.fitHandler.removeRoutePointsCloseToUser(this.route, this.locationOverlay.getMyLocation());
        }
    }

    @Override
    public void setNewRoute(List<GeoPoint> geoPoints) {
        if (this.route != null) {
            this.mapView.getOverlayManager().remove(this.route);
            this.mapView.getOverlayManager().remove(this.routeMarker);
        }

        this.routeMarker = new Marker(this.mapView);
        this.routeMarker.setPosition(geoPoints.get(geoPoints.size() - 1));
        this.routeMarker.setIcon(getResources().getDrawable(R.drawable.ic_baseline_location));
        this.routeMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        this.mapView.getOverlayManager().add(this.routeMarker);

        this.route = new Polyline();
        this.route.setPoints(geoPoints);
        Paint paint = this.route.getOutlinePaint();
        paint.setARGB(255, 103, 230, 236);
        this.mapView.getOverlayManager().add(this.route);
    }

    @Override
    public void removeRoute() {
        this.mapView.getOverlayManager().remove(this.route);
        this.mapView.getOverlayManager().remove(this.routeMarker);
        this.route = null;
        this.routeMarker = null;
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

        if (this.fitHandler != null) {
            this.fitHandler.updatePersonalRecord();
            this.fitHandler.saveUserData();
        }
    }
}