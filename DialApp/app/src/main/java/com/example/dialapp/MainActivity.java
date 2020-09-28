package com.example.dialapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String LOG = "MyLog";

    private TextView.OnEditorActionListener sendListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d(LOG, "on editor action");
            Log.d(LOG, "phone: " + v.getText().toString());
            Log.d(LOG, "action id:" + actionId);
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                dial(v.getText().toString());
                return true;
            }
            return false;
        }
    };
    private EditText etPhone;

    private void dial(String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = findViewById(R.id.editTextPhone);
        etPhone.setOnEditorActionListener(sendListener);
    }
}