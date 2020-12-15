package com.example.geofencing.view_model;

public class AchievementData {

    public static int METERS_PER_DAY = 6500;

    private float totalMetersToday;

    public AchievementData() {
        this.totalMetersToday = 0;
    }

    public float getTotalMetersToday() {
        return totalMetersToday;
    }

    public void addTotalMetersToday(float meters) {
        this.totalMetersToday += meters;
    }
}
