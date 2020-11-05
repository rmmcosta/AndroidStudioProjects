package com.example.transitionsandanimations_v2;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivCircle = findViewById(R.id.ivCircle);
        ImageView ivRectangle = findViewById(R.id.ivRectangle);
        ImageView ivDroid = findViewById(R.id.ivDroid);
        ImageView ivSquare = findViewById(R.id.ivSquare);

        setActivityTransition(ivCircle, AvailableTransitions.EXPLODE, this, this, this.getClass());
        setActivityTransition(ivRectangle, AvailableTransitions.FADE, this, this, this.getClass());
        setSelfTransition(ivSquare);
        setActivityTransition(ivDroid, this.getBaseContext(), this, SecondaryActivity.class, getString(R.string.droid_animation_name), ivCircle, getString(R.string.circle_animation_name));
    }
}