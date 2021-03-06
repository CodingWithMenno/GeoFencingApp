package com.example.geofencing.view_model;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;

public class AchievementData implements Serializable {

    public static int METERS_PER_DAY = 6500;

    private boolean dayGoalReached;

    private HashMap<Integer, Float> metersPerDayInMonth; //Integer is de dag van de maand; Float is het aantal meters van die dag;

    private int currentMonth;

    private float totalMetersToday;

    private int currentDayOfMonth;

    private Pair<String, Float> bestPerformance;

    public AchievementData() {
        this.dayGoalReached = false;
        this.totalMetersToday = 0;
        this.currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.metersPerDayInMonth = new HashMap<>();
        this.bestPerformance = Pair.of(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).toString(), this.totalMetersToday);
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
            try {
                this.totalMetersToday = this.metersPerDayInMonth.get(day);
            } catch (NullPointerException e) {
                this.totalMetersToday = 0;
            }
            this.dayGoalReached = false;
            System.out.println("day reset");
        }

        if (this.currentMonth != month) {
            this.metersPerDayInMonth.clear();
            this.totalMetersToday = 0;
            System.out.println("month reset");
        }

        this.currentMonth = month;
        this.currentDayOfMonth = day;
    }

    public void checkForRecord() {
        if (this.totalMetersToday > this.bestPerformance.getValue()) {
            this.bestPerformance = Pair.of(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).toString(), this.totalMetersToday);
        }
    }

    public HashMap<Integer, Float> getMetersPerDayInMonth() {
        return metersPerDayInMonth;
    }

    public void setMetersPerDayInMonth(HashMap<Integer, Float> metersPerDayInMonth) {
        this.metersPerDayInMonth = metersPerDayInMonth;
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public float getTotalMetersToday() {
        return totalMetersToday;
    }

    public void setTotalMetersToday(float totalMetersToday) {
        this.totalMetersToday = totalMetersToday;
    }

    public void addTotalMetersToday(float meters) {
        this.totalMetersToday += meters;
    }

    public boolean isDayGoalReached() {
        return dayGoalReached;
    }

    public void setDayGoalReached(boolean dayGoalReached) {
        this.dayGoalReached = dayGoalReached;
    }

    public Pair<String, Float> getBestPerformance() {
        return bestPerformance;
    }

    public void setBestPerformance(Pair<String, Float> bestPerformance){
        this.bestPerformance = bestPerformance;
    }
}
