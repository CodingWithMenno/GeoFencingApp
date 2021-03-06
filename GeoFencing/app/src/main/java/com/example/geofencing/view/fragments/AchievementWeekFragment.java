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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


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
        barChart.clear();

        TextView textView = view.findViewById(R.id.total_steps_text);

        achievementData = FitHandler.getInstance().getUserData();
        int totalToday = (int) achievementData.getTotalMetersToday();
        textView.setText(getResources().getString(R.string.total_steps_today) + "" + totalToday);


        HashMap<Integer, Float> metersPerDayInMonth = achievementData.getMetersPerDayInMonth();
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        ArrayList<BarEntry> entries = new ArrayList<>();
        int counter = 0;
        for (int dayCounter = currentDay - 7; dayCounter < currentDay; dayCounter++) {

            float metersOpDeDag = 0.0f;
            try {
                 metersOpDeDag = metersPerDayInMonth.get(dayCounter);
            } catch (NullPointerException e) {
                //Gaat hierin als het de vorige maand was
            }

            entries.add(new BarEntry(metersOpDeDag, counter));
            counter++;
        }

        ArrayList<String> labels = new ArrayList<>();
        for (int dayBackCounter = currentDay - 7; dayBackCounter < currentDay; dayBackCounter++) {
            try {
                labels.add(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH) + 1,
                        dayBackCounter).toString());
            } catch (DateTimeException e) {
                //Gaat hierin als het de vorige maand was
                labels.add(getResources().getString(R.string.previous_month));
            }

        }

        BarDataSet bardataset = new BarDataSet(entries, getResources().getString(R.string.days));
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription(getResources().getString(R.string.week_achievements));
        barChart.animateY(5000);

        return view;
    }

}
