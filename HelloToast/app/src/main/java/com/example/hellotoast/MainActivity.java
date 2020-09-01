package com.example.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void incCount(View view) {
        TextView countText = findViewById(R.id.textCount);
        int count = Integer.parseInt(countText.getText().toString());
        countText.setText(String.valueOf(++count));
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Hello toast!", Toast.LENGTH_LONG);
        toast.show();
    }
}