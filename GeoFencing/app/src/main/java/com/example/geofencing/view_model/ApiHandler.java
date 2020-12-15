package com.example.geofencing.view_model;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.geofencing.repository.NominatimAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiHandler implements ApiObserver {

    private FitHandler fitHandler;

    private NominatimAPI nominatimAPI;
    private boolean makeLap;
    private Location currentLocation;

    public ApiHandler(FitHandler fitHandler) {
        this.fitHandler = fitHandler;
        this.nominatimAPI = new NominatimAPI(this);
    }

    public void findLocationFor(String city, String street, String number, Location currentLocation, boolean makeLap) {
        this.currentLocation = currentLocation;
        this.makeLap = makeLap;
        this.nominatimAPI.searchAddressFor(city, street, number);
    }

    private void calculatePathTo(Location finalLocation) {
        //TODO OrsApi gebruiken
        System.out.println("Afstand naar nieuwe locatie is: " + currentLocation.distanceTo(finalLocation) + "meter");
    }

    @Override
    public void callFailed() {
        //TODO nog invullen
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
            //TODO iets doen als er geen adres is gevonden
        }
    }
}
