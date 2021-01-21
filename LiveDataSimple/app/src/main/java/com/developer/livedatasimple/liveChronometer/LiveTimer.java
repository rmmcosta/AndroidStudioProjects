package com.developer.livedatasimple.liveChronometer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;

public class LiveTimer {
    private Timer timer;
    private MutableLiveData<Long> liveTimer;
    private static final long DELAY = 1000;
    private long currentTime = 0;

    public LiveTimer() {
        timer = new Timer();
        liveTimer = new MutableLiveData<>();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTime += DELAY;
                liveTimer.postValue(currentTime);
            }
        }, DELAY, DELAY);
    }

    public LiveData<Long> getLiveTimer() {
        return liveTimer;
    }
}
