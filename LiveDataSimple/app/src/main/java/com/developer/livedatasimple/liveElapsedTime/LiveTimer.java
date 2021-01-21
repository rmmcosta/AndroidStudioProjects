package com.developer.livedatasimple.liveElapsedTime;

import android.os.SystemClock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;

public class LiveTimer {
    private final MutableLiveData<Long> liveTimer;
    private static final long DELAY = 1000;
    private final long initialTime;
    private long currentTime;

    public LiveTimer() {
        Timer timer = new Timer();
        initialTime = SystemClock.elapsedRealtime();
        liveTimer = new MutableLiveData<>();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTime = (SystemClock.elapsedRealtime() - initialTime) / 1000;
                liveTimer.postValue(currentTime);
            }
        }, DELAY, DELAY);
    }

    public LiveData<Long> getLiveTimer() {
        return liveTimer;
    }
}
