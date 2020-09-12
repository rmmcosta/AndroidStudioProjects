package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    public static final String REPLY_MESSAGE = "ReplyMessage";
    private static final String LOG = "MyLog " + SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG, "On create");
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "On start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, "On resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG, "On pause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG, "On save instance state");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "On stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG, "On restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "On destroy");
    }
}