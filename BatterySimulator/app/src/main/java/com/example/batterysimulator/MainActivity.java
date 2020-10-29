package com.example.batterysimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int batteryLevel = 0;
    private static final int MAX_LEVEL = 7, MIN_LEVEL = 0;
    private ImageView ivBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivBattery = findViewById(R.id.ivBattery);
    }

    public void increaseBattery(View view) {
        if (batteryLevel < MAX_LEVEL) {
            batteryLevel++;
        }
        updateLevel(batteryLevel);
    }

    public void decreaseBattery(View view) {
        if (batteryLevel > MIN_LEVEL) {
            batteryLevel--;
        }
        updateLevel(batteryLevel);
    }

    private void updateLevel(int batteryLevel) {
        ivBattery.setImageLevel(batteryLevel);
    }
}