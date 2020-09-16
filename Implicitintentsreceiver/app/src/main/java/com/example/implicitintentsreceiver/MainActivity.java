package com.example.implicitintentsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processIntent(getIntent());
    }

    private void processIntent(Intent intent) {
        if (intent.getData() != null) {
            TextView textView = findViewById(R.id.tvIntentData);
            textView.setText(intent.getData().toString());
        }
    }
}