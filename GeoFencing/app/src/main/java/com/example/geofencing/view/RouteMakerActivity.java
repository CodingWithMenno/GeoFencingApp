package com.example.geofencing.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;

public class RouteMakerActivity extends AppCompatActivity {

    private FitHandler fitHandler;

    private EditText placeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maker);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.placeEdit = findViewById(R.id.edit_plaats);

        this.fitHandler = FitHandler.getInstance();
    }

    public void startRouteClicked(View view) {
        String place = this.placeEdit.getText().toString();

        this.fitHandler.findQuickestPathTo(place);
    }
}