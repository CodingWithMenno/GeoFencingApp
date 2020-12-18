package com.example.geofencing.view;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.geofencing.R;
import com.example.geofencing.view_model.FitHandler;

public class RouteMakerActivity extends AppCompatActivity {

    private FitHandler fitHandler;

    private EditText cityEdit;
    private EditText streetEdit;
    private EditText numberEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maker);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.cityEdit = findViewById(R.id.edit_plaats);
        this.streetEdit = findViewById(R.id.edit_straat);
        this.numberEdit = findViewById(R.id.edit_nummer);

        this.fitHandler = FitHandler.getInstance();
    }

    public void startRouteClicked(View view) {
        String city = this.cityEdit.getText().toString();
        String street = this.streetEdit.getText().toString();
        String number = this.numberEdit.getText().toString();

        this.fitHandler.findQuickestPathTo(city, street, number);
    }
}