package com.example.twoactivitieswithespresso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvMessage = findViewById(R.id.tvMessage);
        String message = getIntent().getStringExtra(MainActivity.MESSAGE_SENT_CODE);
        tvMessage.setText(message);
    }
}