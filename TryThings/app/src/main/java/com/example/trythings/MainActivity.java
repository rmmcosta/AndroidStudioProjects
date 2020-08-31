package com.example.trythings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyLogsAt".concat(MainActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnToastJava = findViewById(R.id.buttonToastJavaId);
        btnToastJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(view);
            }
        });
        checkAndRecoverState(savedInstanceState);
    }

    private void checkAndRecoverState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG, "recover state");
            EditText inputName = findViewById(R.id.editTextTextPersonName);
            String saved = savedInstanceState.getString("editTextTextPersonName");
            inputName.setText(saved);
        }
    }

    public void showToast(View view) {
        Log.d(TAG,"entered show toast!");
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, R.string.toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On start!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On stop!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On destroy");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "On post resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "On restart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "On save instance state");
        EditText inputName = findViewById(R.id.editTextTextPersonName);
        outState.putString("editTextTextPersonName", "Saved".concat(inputName.getText().toString()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "On restore");
        checkAndRecoverState(savedInstanceState);
    }

    public void changeLang2EN(View view) {
    }

    public void changeLang2PT(View view) {

    }

    public void triggerException(View view) {
        try {
            throw new RuntimeException("My on demand exception");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}