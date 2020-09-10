package com.example.twoactivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int MESSAGE_CODE = 33;
    public static final String MAIN_MESSAGE = "MainMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            TextView tvTitle = findViewById(R.id.tvTitleReply);
            TextView tvMessage = findViewById(R.id.tvReplyMessage);
            tvTitle.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            String message = data.getStringExtra(SecondActivity.REPLY_MESSAGE);
            tvMessage.setText(message);
        }
    }
}