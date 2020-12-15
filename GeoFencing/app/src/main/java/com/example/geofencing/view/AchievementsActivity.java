package com.example.geofencing.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;

public class AchievementsActivity extends AppCompatActivity {

    private FitHandler fitHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        this.fitHandler = FitHandler.getInstance();
    }
}
