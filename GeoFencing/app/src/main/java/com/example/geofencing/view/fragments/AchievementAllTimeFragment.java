package com.example.geofencing.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.geofencing.R;
import com.example.geofencing.view_model.AchievementData;
import com.example.geofencing.view_model.FitHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AchievementAllTimeFragment extends Fragment {

    private AchievementData data;

    public AchievementAllTimeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement_alltime, container, false);
        view.setBackgroundColor(Color.WHITE);

        TextView dateText = view.findViewById(R.id.date_all_time);
        TextView stepsText = view.findViewById(R.id.steps_all_time);
        TextView stepsGoal = view.findViewById(R.id.steps_goal);

        this.data = FitHandler.getInstance().getUserData();

        dateText.setText(this.data.getBestPerformance().getLeft() + "");
        stepsText.setText(this.data.getBestPerformance().getRight().toString());
        stepsGoal.setText(getResources().getString(R.string.steps_goal) + AchievementData.METERS_PER_DAY);

        return view;
    }
}
