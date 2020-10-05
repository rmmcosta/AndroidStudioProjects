package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);
        inflateToolbar();
        initializeAdapter();
    }

    private void initializeAdapter() {
        final NewsAdapter newsAdapter = new NewsAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(newsAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getString(R.string.tab_1st));
                        break;
                    case 1:
                        tab.setText(getString(R.string.tab_2nd));
                        break;
                    case 2:
                        tab.setText(getString(R.string.tab_3rd));
                        break;
                    default:
                        tab.setText("N/A");
                        break;
                }
            }
        }).attach();
    }

    private void inflateToolbar() {
        setSupportActionBar((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar));
    }
}