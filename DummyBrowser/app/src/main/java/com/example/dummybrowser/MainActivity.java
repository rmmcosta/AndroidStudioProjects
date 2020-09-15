package com.example.dummybrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndShowIntent();
    }

    private void checkAndShowIntent() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getData() != null) {
                Uri uri = intent.getData();
                TextView textView = findViewById(R.id.tvReceivedUri);
                textView.setText(uri.toString());
            }
        }
    }
}