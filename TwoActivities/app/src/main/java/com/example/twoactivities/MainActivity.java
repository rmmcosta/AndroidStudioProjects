package com.example.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_MESSAGE = "MainMessage";
    private static final String IS_REPLY_VISIBLE = "IsReplyVisible";
    private static final String REPLY_MESSAGE = "ReplyMessage";
    private final int MESSAGE_CODE = 33;
    private final String LOG = "MyLog " + MainActivity.class.getSimpleName();
    private TextView tvTitle;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG, "On create");
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tvTitleReply);
        tvMessage = findViewById(R.id.tvReplyMessage);
        validateSavedInstanceState(savedInstanceState);
    }

    private void validateSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(LOG, "Recover saved instance state");
            if (savedInstanceState.getBoolean(IS_REPLY_VISIBLE)) {
                tvTitle.setVisibility(View.VISIBLE);
                tvMessage.setVisibility(View.VISIBLE);
            }
            tvMessage.setText(savedInstanceState.getString(REPLY_MESSAGE));
        }
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.etMessage);
        String message = editText.getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(MAIN_MESSAGE, message);
        editText.setText("");
        startActivityForResult(intent, MESSAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MyLog", "OnResult, requestCode:" + requestCode + " resultCode:" + resultCode + " data:" + data.getStringExtra("ReplyMessage"));
        if (requestCode == MESSAGE_CODE && resultCode == RESULT_OK) {
            tvTitle.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            String message = data.getStringExtra(SecondActivity.REPLY_MESSAGE);
            tvMessage.setText(message);
        }
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
        //lets save the information about the reply
        outState.putBoolean(IS_REPLY_VISIBLE, tvTitle.getVisibility()==View.VISIBLE);
        outState.putString(REPLY_MESSAGE, tvMessage.getText().toString());
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