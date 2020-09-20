package com.example.sendmessageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etTo, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTo = findViewById(R.id.etTo);
        etMessage = findViewById(R.id.etMessage);
    }

    public void sendMessage(String toType, String to, String messageExtra, String message) {
        Uri uri = Uri.parse(toType + ":" + to);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(messageExtra, message);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        String message = etMessage.getText().toString();
        String to = etTo.getText().toString();
        sendMessage("mailto", to, Intent.EXTRA_TEXT,message);
    }

    public void sendSms(View view) {
        String message = etMessage.getText().toString();
        String to = etTo.getText().toString();
        sendMessage("smsto", to, "sms_body",message);
    }
}