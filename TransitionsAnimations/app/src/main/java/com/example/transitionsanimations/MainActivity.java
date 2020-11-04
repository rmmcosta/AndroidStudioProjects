package com.example.transitionsanimations;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TRANSITION_STATE = "TRANSITION_STATE";
    private ImageView ivAndroid, ivCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivAndroid = findViewById(R.id.ivAndroid);
        ivCircle = findViewById(R.id.ivCircle);
    }

    public void explode(View view) {
        defineTransition(new Explode());
        recreateActivityWithTransition();
    }

    public void slide(View view) {
        defineTransition(new Slide());
        recreateActivityWithTransition();
    }

    private void defineTransition(Transition transition) {
        getWindow().setEnterTransition(transition);
        getWindow().setExitTransition(transition);
    }

    private void recreateActivityWithTransition() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void rotate(View view) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animation.setDuration(2000);
        animation.start();
    }

    public void swap(View view) {
        Intent intent = new Intent(this, SecondaryActivity.class);
        Pair<View, String> pAndroid = Pair.create(ivAndroid, "swap");
        Pair<View, String> pCircle = Pair.create(ivCircle, "swap");
        ActivityOptions options = ActivityOptions.
                makeSceneTransitionAnimation(this, pAndroid, pCircle);
        startActivity(intent, options.toBundle());
    }
}