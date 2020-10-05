package com.example.newsapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NewsAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;

    public NewsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public NewsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("MyLog", "createFragment: " + position);
        switch (position) {
            case 0:
                return new TopStoriesFragment();
            case 1:
                return new TechNewsFragment();
            case 2:
                return new CookingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
