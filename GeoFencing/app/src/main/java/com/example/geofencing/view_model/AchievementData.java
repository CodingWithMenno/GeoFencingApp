package com.example.geofencing.view_model;

public class AchievementData {

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
