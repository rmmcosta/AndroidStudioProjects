package com.example.workoutfragments;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Stopwatch implements Serializable {
    private int seconds;
    private int minutes;
    private int hours;
    private static final int MAX_SECONDS = 59;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_HOURS = 99;
    private boolean isCounting;
    private boolean wasCounting;

    public Stopwatch(int seconds, int minutes, int hours) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public void incSeconds() {
        if (seconds == MAX_SECONDS) {
            seconds = 0;
            incMinutes();
        } else {
            seconds++;
        }
    }

    public void incMinutes() {
        if (minutes == MAX_MINUTES) {
            minutes = 0;
            incHours();
        } else {
            minutes++;
        }
    }

    public void incHours() {
        if (hours == MAX_HOURS) {
            reset();
        } else {
            hours++;
        }
    }

    public void reset() {
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public boolean isCounting() {
        return isCounting;
    }

    public void startCounting() {
        wasCounting = isCounting;
        isCounting = true;
    }

    public void stopCounting() {
        wasCounting = isCounting;
        isCounting = false;
    }

    public void resume() {
        isCounting = wasCounting;
    }

    @NonNull
    @Override
    public String toString() {
        String currentWatch = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return currentWatch;
    }
}
