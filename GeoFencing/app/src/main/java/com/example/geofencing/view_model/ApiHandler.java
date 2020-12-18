package com.example.geofencing.view_model;

import android.location.Location;
import android.util.Log;

import com.example.geofencing.repository.NominatimAPI;
import com.example.geofencing.repository.OrsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiHandler implements ApiObserver {

    private final FitHandler fitHandler;

    private final NominatimAPI nominatimAPI;
    private final OrsApi orsApi;
    private Location currentLocation;

    public ApiHandler(FitHandler fitHandler) {
        this.fitHandler = fitHandler;
        this.nominatimAPI = new NominatimAPI(this);
        this.orsApi = new OrsApi(this);
    }

    public void findLocationFor(String city, String street, String number, Location currentLocation) {
        this.currentLocation = currentLocation;
        this.nominatimAPI.searchAddressFor(city, street, number);
    }

    private void calculatePathTo(Location finalLocation) {
        this.orsApi.getFastestRouteTo(this.currentLocation, finalLocation);
    }

    @Override
    public void callFailed() {
        Log.d(ApiHandler.class.getName(), "Something went wrong with the API calls/parsing");
    }

    @Override
    public void nominatimSuccess(JSONArray response) {
        try {
            JSONObject object = response.getJSONObject(0);
            double latitude = object.getDouble("lat");
            double longitude = object.getDouble("lon");

            Location location = new Location("");
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            calculatePathTo(location);
        } catch (JSONException e) {
            Log.d(ApiHandler.class.getName(), "No address found");
            callFailed();
        }
    }

    @Override
    public void orsSuccess(JSONObject response) {
        List<GeoPoint> geoPointsOnRoute = new ArrayList<>();

        try {
            JSONArray features = response.getJSONArray("features");
            JSONObject featuresObject = features.getJSONObject(0);
            JSONObject geometry = featuresObject.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");

            int coordinateCounter = 0;
            while (true) {
                JSONArray coordinate = coordinates.getJSONArray(coordinateCounter);
                double longitude = coordinate.getDouble(0);
                double latitude = coordinate.getDouble(1);
                geoPointsOnRoute.add(new GeoPoint(latitude, longitude));
                coordinateCounter++;
            }

        } catch (JSONException e) {
            //The code will always go in here
        } finally {
            if (!geoPointsOnRoute.isEmpty()) {
                this.fitHandler.getRouteObserver().setNewRoute(geoPointsOnRoute);
            }
        }
    }
}
