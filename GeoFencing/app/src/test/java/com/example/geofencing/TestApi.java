package com.example.geofencing;

import com.example.geofencing.repository.NominatimAPI;
import com.example.geofencing.view_model.ApiObserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestApi {
    private static boolean goodResult;

    @Test
    public void responseFromApiGood() throws InterruptedException {
        goodResult = false;

        NominatimAPI api = new NominatimAPI(new ApiObserver() {
            @Override
            public void callFailed() {
                //do nothing
            }

            @Override
            public void nominatimSuccess(JSONArray response) {
                goodResult = true;
            }

            @Override
            public void orsSuccess(JSONObject response) {
                //do nothing
            }
        });

        api.searchAddressFor("iroko");

        Thread.sleep(3000);

        Assert.assertTrue(goodResult);
    }
}