package com.example.myintents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go2ImplicitIntents(View view) {
        Intent intent = new Intent(this, ImplicitIntents.class);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        TextView inputEmail = findViewById(R.id.editTextEmailAddress);
        String myEmail = String.valueOf(inputEmail.getText());
        Intent intent = new Intent(this, ReceiveData.class);
        intent.putExtra("MyEmail", myEmail);
        startActivity(intent);
    }

    public void goGetText(View view) {
        Intent intent = new Intent(this, EnterAndBack.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String myName = data.getStringExtra("MyName");
            Toast toast = Toast.makeText(getApplicationContext(), myName, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}