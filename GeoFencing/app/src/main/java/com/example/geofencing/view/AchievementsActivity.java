package com.example.geofencing.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geofencing.R;
import com.example.geofencing.view_model.AchievementData;
import com.example.geofencing.view_model.FitHandler;

public class AchievementsActivity extends AppCompatActivity {

    private FitHandler fitHandler;

    private TextView totalStepsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        this.totalStepsView = findViewById(R.id.total_steps_text);

        this.fitHandler = FitHandler.getInstance();

        this.totalStepsView.setText(this.fitHandler.getUserData().getTotalMetersToday() + " van de " + AchievementData.METERS_PER_DAY + " gelopen");
    }
}
