package com.example.geofencing.repository;

import com.example.geofencing.view_model.ApiHandler;
import com.example.geofencing.view_model.ApiObserver;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NominatimAPI implements Callback {

    private ApiObserver observer;

    private OkHttpClient client;

    public NominatimAPI(ApiObserver observer) {
        this.client = new OkHttpClient();
        this.observer = observer;
    }

    public void searchAddressFor(String place) {
        this.client = new OkHttpClient();

        String url = makeURL(place);
        Request request = new Request.Builder().url(url).build();

        this.client.newCall(request).enqueue(this);
    }

    private String makeURL(String place) {
        String prefix = "https://nominatim.openstreetmap.org/search/";
        String postfix = "?format=json&addressdetails=1&limit=1&polygon_svg=1";

        place = place.trim();
        place = place.replace(" ", "%20");

        return prefix + place + postfix;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        call.cancel();
        this.observer.callFailed();
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonData = response.body().string();

        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.observer.nominatimSuccess(jsonObject);
    }
}
