package com.example.rbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Spinner spPhoneType;

    private AdapterView.OnItemSelectedListener spPhoneTypeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String optionSelected = parent.getItemAtPosition(position).toString();
            Snackbar snackbar = Snackbar.make(view.getRootView(), optionSelected, BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spPhoneType = findViewById(R.id.spPhoneType);
        spPhoneType.setOnItemSelectedListener(spPhoneTypeListener);
    }

    public void chooseDeliveryMethod(View view) {
        RadioButton rbDeliveryMethod = findViewById(view.getId());
        String deliverOption = rbDeliveryMethod.getText().toString();
        Toast toast = Toast.makeText(MainActivity.this, deliverOption, Toast.LENGTH_SHORT);
        toast.show();
    }
}