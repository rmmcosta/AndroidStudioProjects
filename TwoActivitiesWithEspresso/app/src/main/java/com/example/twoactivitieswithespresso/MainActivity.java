package com.example.twoactivitieswithespresso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String MESSAGE_SENT_CODE = "MESSAGE_SENT";
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = findViewById(R.id.inputEditText);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String message = String.valueOf(etInput.getText());
        intent.putExtra(MESSAGE_SENT_CODE, message);
        startActivity(intent);
    }
}