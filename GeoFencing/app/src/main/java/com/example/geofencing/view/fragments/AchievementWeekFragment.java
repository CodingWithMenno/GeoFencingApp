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


public class AchievementWeekFragment extends Fragment {

    private AchievementData achievementData;

    public AchievementWeekFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_achievement_week, container, false);
        view.setBackgroundColor(Color.WHITE);

        BarChart barChart = (BarChart) view.findViewById(R.id.barchartWeek);

        TextView textView = view.findViewById(R.id.total_steps_text);

        achievementData = FitHandler.getInstance().getUserData();

        textView.setText((R.string.total_steps_today + "" + achievementData.getTotalMetersToday()));


        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(3f, 6));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Day1");
        labels.add("Day2");
        labels.add("Day3");
        labels.add("Day4");
        labels.add("Day5");
        labels.add("Day6");
        labels.add("Day7");


        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription("WeekAchievements");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);

        return view;
    }

}
