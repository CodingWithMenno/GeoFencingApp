package com.example.geofencing.view_model;

import android.location.Location;

import com.example.geofencing.repository.NominatimAPI;

import org.json.JSONObject;

public class ApiHandler implements ApiObserver {

    private FitHandler fitHandler;

    private NominatimAPI nominatimAPI;


    public ApiHandler(FitHandler fitHandler) {
        this.fitHandler = fitHandler;
        this.nominatimAPI = new NominatimAPI(this);
    }

    public void findQuickestPathTo(String city, String street, String number) {
        this.nominatimAPI.searchAddressFor(city, street, number);
    }

    private void getQuickestPathTo(Location currentLocation, Location finalLocation, boolean makeLap) {
        
    }

    @Override
    public void callFailed() {
        //TODO nog invullen
    }

    @Override
    public void nominatimSuccess(JSONObject response) {

    }
}
