package com.example.geofencing.view_model;

import org.json.JSONObject;

public interface ApiObserver {

    void callFailed();
    void nominatimSuccess(JSONObject response);

}
