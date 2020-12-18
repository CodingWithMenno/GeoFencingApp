package com.example.geofencing.view_model;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;

public class AchievementData implements Serializable {

    public static int METERS_PER_DAY = 6500;

    private HashMap<Integer, Float> metersPerDayInMonth; //Integer is de dag van de maand; Float is het aantal meters van die dag;
    private int currentMonth;

    private float totalMetersToday;
    private int currentDayOfMonth;

    private Pair<Long, Float> bestPerformance;

    public AchievementData() {
        this.totalMetersToday = 0;
        this.currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.metersPerDayInMonth = new HashMap<>();
        this.bestPerformance = Pair.of(System.currentTimeMillis(), this.totalMetersToday);
    }

    /**
     * Checks if the current day and month is still the right value and if not
     * the currentDay and totalMeters and/or currentMonth will be reset.
     */
    public void checkCurrentDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);

        if (this.currentDayOfMonth != day) {
            this.metersPerDayInMonth.put(this.currentDayOfMonth, this.totalMetersToday);
            this.currentDayOfMonth = day;
            this.totalMetersToday = this.metersPerDayInMonth.get(day);
        }

        if (this.currentMonth != month) {
            this.metersPerDayInMonth.clear();
            this.totalMetersToday = 0;
        }
    }

    public void checkForRecord() {
        if (this.totalMetersToday > this.bestPerformance.getValue()) {
            this.bestPerformance = Pair.of(System.currentTimeMillis(), this.totalMetersToday);
        }
    }

    public HashMap<Integer, Float> getMetersPerDayInMonth() {
        return metersPerDayInMonth;
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    public float getTotalMetersToday() {
        return totalMetersToday;
    }

    public void addTotalMetersToday(float meters) {
        this.totalMetersToday += meters;
    }
}
