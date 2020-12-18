package com.example.geofencing.repository;

import android.location.Location;

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

public class OrsApi implements Callback {

    private final String key = "5b3ce3597851110001cf6248943c489e9e1c400f86cb3e53dedc701b";

    private ApiObserver observer;

    private OkHttpClient client;

    public OrsApi(ApiObserver observer) {
        this.client = new OkHttpClient();
        this.observer = observer;
    }

    public void getFastestRouteTo(Location locationFrom, Location locationTo) {
        String prefix = "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=";
        String from = locationFrom.getLongitude() + "," + locationFrom.getLatitude();
        String to = locationTo.getLongitude() + "," + locationTo.getLatitude();

        String url = prefix + this.key + "&start=" + from + "&end=" + to;

        Request request = new Request.Builder().url(url).build();
        this.client.newCall(request).enqueue(this);
    }


    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        call.cancel();
        this.observer.callFailed();
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String jsonData = response.body().string();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.observer.orsSuccess(jsonObject);
    }
}
