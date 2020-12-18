package com.example.geofencing.view_model;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ApiObserver {

    void callFailed();
    void nominatimSuccess(JSONArray response);
    void orsSuccess(JSONObject response);
}
