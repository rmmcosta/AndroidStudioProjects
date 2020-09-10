package com.example.twoactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class SecondActivity extends AppCompatActivity {
    public static final String REPLY_MESSAGE = "ReplyMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        presentMessage(getIntent());
    }

    private void presentMessage(Intent intent) {
        TextView tvReceivedMessage = findViewById(R.id.tvMessage);
        String receivedMessage = intent.getStringExtra(MainActivity.MAIN_MESSAGE);
        tvReceivedMessage.setText(receivedMessage);
    }

    public void replyMessage(View view) {
        Intent replyIntent = new Intent();
        EditText replyInput = findViewById(R.id.etReplyMessage);
        replyIntent.putExtra(REPLY_MESSAGE, replyInput.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}