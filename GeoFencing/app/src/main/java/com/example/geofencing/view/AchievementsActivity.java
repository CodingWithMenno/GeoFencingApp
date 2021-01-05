package com.example.geofencing.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.geofencing.R;
import com.example.geofencing.view.fragments.FragmentAdapter;
import com.example.geofencing.view_model.AchievementData;
import com.example.geofencing.view_model.FitHandler;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class AchievementsActivity extends AppCompatActivity {

    private FitHandler fitHandler;

    private TextView totalStepsView;

    FragmentAdapter myFragmentPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setPagerAdapter();
        setTabLayout();


        //this.totalStepsView = findViewById(R.id.total_steps_text);


        this.fitHandler = FitHandler.getInstance();


        //this.totalStepsView.setText(this.fitHandler.getUserData().getTotalMetersToday() + " van de " + AchievementData.METERS_PER_DAY + " gelopen");
    }

    private void setPagerAdapter() {
        myFragmentPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("AchievementWeek");
        tabLayout.getTabAt(1).setText("AchievementMonth");
        tabLayout.getTabAt(2).setText("AchievementAllTime");
    }
}


