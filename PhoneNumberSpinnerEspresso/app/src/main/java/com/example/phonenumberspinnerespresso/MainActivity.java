package com.example.phonenumberspinnerespresso;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etPhone;
    private TextView tvResult;

    class PhoneTypeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String phone = String.valueOf(etPhone.getText());
            if (phone.isEmpty()) {
                tvResult.setText(R.string.result_placeholder);
                return;
            }
            String[] phoneTypes = getResources().getStringArray(R.array.phone_types);
            String result = String.format("%s %s", phone, phoneTypes[i]);
            tvResult.setText(result);
            Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
            toast.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            tvResult.setText(R.string.result_placeholder);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhone = findViewById(R.id.etPhone);
        tvResult = findViewById(R.id.tvResult);
        Spinner spinnerPhoneType = findViewById(R.id.spinnerPhoneType);
        spinnerPhoneType.setOnItemSelectedListener(new PhoneTypeListener());
        ArrayAdapter<CharSequence> animalsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.animals, R.layout.support_simple_spinner_dropdown_item);
        Spinner spinnerAnimals = findViewById(R.id.spinnerAnimals);
        spinnerAnimals.setAdapter(animalsArrayAdapter);
    }
}