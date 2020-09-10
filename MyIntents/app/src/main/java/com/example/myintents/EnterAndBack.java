package com.example.myintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EnterAndBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_and_back);
    }

    public void goBack(View view) {
        Intent replyIntent = new Intent();
        TextView inputMyName = findViewById(R.id.editTextTextPersonName);
        String myName = inputMyName.getText().toString();
        replyIntent.putExtra("MyName", myName);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}