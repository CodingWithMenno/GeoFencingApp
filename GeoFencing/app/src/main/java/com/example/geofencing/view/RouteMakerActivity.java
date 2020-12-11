package com.example.geofencing.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.geofencing.R;

public class RouteMakerActivity extends AppCompatActivity {

    private EditText cityEdit;
    private EditText streetEdit;
    private EditText numberEdit;
    private SwitchCompat lapSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maker);

        this.cityEdit = findViewById(R.id.edit_plaats);
        this.streetEdit = findViewById(R.id.edit_straat);
        this.numberEdit = findViewById(R.id.edit_nummer);
        this.lapSwitch = findViewById(R.id.switch_make_lap);
    }

    public void startRouteClicked(View view) {
        //TODO vind een route naar de opgegeven plek
    }
}