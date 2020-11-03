package com.example.transitionsanimations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TRANSITION_STATE = "TRANSITION_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}