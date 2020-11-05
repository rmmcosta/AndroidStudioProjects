package com.example.transitionsandanimations_v2;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.transition.Explode;
import android.transition.Transition;

public abstract class BaseActivity extends AppCompatActivity {
    private final String ENTER_TRANSITION = "ENTER_TRANSITION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performEnterTransition(getIntent());
    }

    private void performEnterTransition(Intent intent) {
        if (intent == null) {
            return;
        }
        AvailableTransitions availableTransitions = (AvailableTransitions) intent.getSerializableExtra(ENTER_TRANSITION);
        if (availableTransitions == null) {
            return;
        }
        defineEnterTransition(availableTransitions);
    }

    public enum AvailableTransitions {EXPLODE, FADE, SLIDE, NONE}

    public void setActivityTransition(View v, AvailableTransitions availableTransitions, Context context, BaseActivity activity, Class destinationActivityClass) {
        defineExitTransition(availableTransitions);
        v.setOnClickListener(view -> startActivityWithTransition(context, ActivityOptions.makeSceneTransitionAnimation(activity), destinationActivityClass, availableTransitions));
    }

    public void setActivityTransition(View v, Context context, BaseActivity activity, Class destinationActivityClass, String animation, View otherView, String otherAnimation) {
        Pair<View, String> p1 = Pair.create(v, animation);
        Pair<View, String> p2 = Pair.create(otherView, otherAnimation);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, p1, p2);
        v.setOnClickListener(view -> startActivityWithTransition(context, activityOptions, destinationActivityClass, AvailableTransitions.NONE));
    }

    public void setSelfTransition(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(2000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        view.setOnClickListener(View -> animator.start());
    }

    private void defineExitTransition(AvailableTransitions availableTransitions) {
        switch (availableTransitions) {
            case FADE:
                setWindowExitTransition(new Fade());
                break;
            case EXPLODE:
                setWindowExitTransition(new Explode());
                break;
        }
    }

    private void setWindowExitTransition(Transition transition) {
        getWindow().setExitTransition(transition);
    }

    private void defineEnterTransition(AvailableTransitions availableTransitions) {
        switch (availableTransitions) {
            case FADE:
                setWindowEnterTransition(new Fade());
                break;
            case EXPLODE:
                setWindowEnterTransition(new Explode());
                break;
            default:
                break;
        }
    }

    private void setWindowEnterTransition(Transition transition) {
        if (transition == null) {
            return;
        }
        getWindow().setEnterTransition(transition);
    }

    private void startActivityWithTransition(Context context, ActivityOptions activityOptions, Class destinationActivityClass, AvailableTransitions enterTransition) {
        Intent intent = new Intent(context, destinationActivityClass);
        intent.putExtra(ENTER_TRANSITION, enterTransition);
        startActivity(intent, activityOptions.toBundle());
    }
}
