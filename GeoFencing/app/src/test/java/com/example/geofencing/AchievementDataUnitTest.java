package com.example.geofencing;

import com.example.geofencing.view_model.AchievementData;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

public class AchievementDataUnitTest {

    private final AchievementData achievementData = new AchievementData();


    @Test
    public void testCheckCurrentDay() {
        final int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int month = Calendar.getInstance().get(Calendar.MONTH);

        achievementData.checkCurrentDay();
        Assert.assertEquals(day, achievementData.getCurrentDayOfMonth());
        Assert.assertEquals(month, achievementData.getCurrentMonth());
    }

    @Test
    public void testBestPerformance() {

        achievementData.setTotalMetersToday(15000f);
        achievementData.setBestPerformance(Pair.of(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).toString(), 5000f));
        achievementData.checkForRecord();
        final float bestPerformance = achievementData.getBestPerformance().getRight();
        Assert.assertEquals(15000f , bestPerformance, 0);

    }

    @Test
    public void testMetersPerDayInMonth() {
        final HashMap<Integer, Float> metersPerDay = new HashMap<>();
        metersPerDay.put(achievementData.getCurrentDayOfMonth(), achievementData.getTotalMetersToday());
        achievementData.setMetersPerDayInMonth(metersPerDay);
        Assert.assertEquals(metersPerDay, achievementData.getMetersPerDayInMonth());
    }

    @Test
    public void testGetterSetterTotalMetersToday() {
        final float totalMeters = 8f;
        achievementData.setTotalMetersToday(totalMeters);
        Assert.assertEquals(totalMeters, achievementData.getTotalMetersToday(),0);
    }

    @Test
    public void testDayGoalReached() {
        final boolean goalReached = true;
        achievementData.setDayGoalReached(goalReached);
        Assert.assertTrue(achievementData.isDayGoalReached());
    }

    @Test
    public void testAddTotalMetersToday() {
        final float totalMetersToday = achievementData.getTotalMetersToday();
        final float metersToAdd = 10f;
        achievementData.addTotalMetersToday(10f);
        Assert.assertEquals(totalMetersToday + metersToAdd, achievementData.getTotalMetersToday(),0);
    }
}
