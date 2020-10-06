package com.example.tabexperiment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializePager();
        attachTabLayout();
    }

    private void initializePager() {
        viewPager2 = findViewById(R.id.view_pager);
        final WeekDaysPageAdapter weekDaysPageAdapter = new WeekDaysPageAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(weekDaysPageAdapter);
    }

    private void attachTabLayout() {
        final TabLayout tabLayout = findViewById(R.id.tab_layout_weekDays);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getString(R.string.monday));
                        break;
                    case 1:
                        tab.setText(getString(R.string.tuesday));
                        break;
                    case 2:
                        tab.setText(getString(R.string.wednesday));
                        break;
                    default:
                }
            }
        }).attach();
    }
}