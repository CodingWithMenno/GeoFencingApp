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

import java.util.ArrayList;
import java.util.Calendar;

public class AchievementMonthFragment extends Fragment {

    private AchievementData achievementData;

    public AchievementMonthFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement_month, container, false);
        view.setBackgroundColor(Color.WHITE);

        BarChart barChart = (BarChart) view.findViewById(R.id.barchartMonth);
        barChart.clear();

        TextView textView = view.findViewById(R.id.total_steps_text_month);

        achievementData = FitHandler.getInstance().getUserData();
        int totalToday = (int) achievementData.getTotalMetersToday();
        textView.setText(getResources().getString(R.string.total_steps_today) + "" + totalToday);


        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int week = 0; week <= Calendar.getInstance().get(Calendar.WEEK_OF_MONTH); week++) {

            int metersPerWeek = 0;
            for (int day = 1; day <= 7; day++) {
                try {
                    metersPerWeek += this.achievementData.getMetersPerDayInMonth().get((week * 7) + day);
                } catch (NullPointerException e) {
                }
            }

            entries.add(new BarEntry(metersPerWeek, week));
        }


        ArrayList<String> labels = new ArrayList<>();
        for (int week = 0; week <= Calendar.getInstance().get(Calendar.WEEK_OF_MONTH); week++) {
            if (week == Calendar.getInstance().get(Calendar.WEEK_OF_MONTH)) {
                labels.add(getResources().getString(R.string.this_week));
            } else {
                labels.add("Week " + week);
            }
        }


        BarDataSet bardataset = new BarDataSet(entries, getResources().getString(R.string.weeks));
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription(getResources().getString(R.string.month_achievements));
        barChart.animateY(5000);

        return view;
    }
}
