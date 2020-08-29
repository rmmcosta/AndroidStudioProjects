package com.example.beeradviser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner beerSpinner = findViewById(R.id.beerTypeSpinnerId);
        fillBeerSpinner(beerSpinner);
        beerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object chosenItem = adapterView.getItemAtPosition(i);
                //TODO the update must be made upon clicking the button find beer
                updateBeerDescription(chosenItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillBeerSpinner(Spinner spinner) {
        //https://developer.android.com/guide/topics/ui/controls/spinner#java
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.beerTypes, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void updateBeerDescription(Object beerType)
    {
        TextView beerDescription = findViewById(R.id.beerDescriptionId);
        beerDescription.setText(beerType.toString());
    }
}
