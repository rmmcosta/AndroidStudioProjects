package com.example.myintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReceiveData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);
        TextView email = findViewById(R.id.textViewEmail);
        Intent intent = getIntent();
        if (email != null)
            Log.i(ReceiveData.class.getSimpleName(), email.toString());
        String receivedEmail = "Nothing received";
        if (intent != null)
            receivedEmail = intent.getStringExtra("MyEmail");
        if (email != null)
            email.setText(receivedEmail);
    }
}