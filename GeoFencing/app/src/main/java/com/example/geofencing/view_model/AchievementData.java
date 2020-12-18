package com.example.geofencing.view_model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

public class AchievementData implements Serializable {

    public static int METERS_PER_DAY = 6500;

    private HashMap<Integer, Float> metersPerDayInMonth; //Integer is de dag van de maand; Float is het aantal meters van die dag;
    private int currentMonth;

    private float totalMetersToday;
    private int currentDayOfMonth;

    public AchievementData() {
        this.totalMetersToday = 0;
        this.currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.metersPerDayInMonth = new HashMap<>();
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
            this.totalMetersToday = 0;
        }

        if (this.currentMonth != month) {
            this.metersPerDayInMonth.clear();
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
