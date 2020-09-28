package com.example.multiplecheckboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> selectedToppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedToppings = new ArrayList<>();
    }

    public void updateToppings(View view) {
        CheckBox checkBox = findViewById(view.getId());
        String option = checkBox.getText().toString();
        if (checkBox.isChecked()) {
            addTopping(option);
        } else {
            removeToping(option);
        }
        giveFeedback();
    }

    private void giveFeedback() {
        String message = getTopicsString();
        Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private String getTopicsString() {
        if (selectedToppings.isEmpty()) {
            return "No toppings selected";
        }
        StringBuilder message = new StringBuilder();
        message.append(getString(R.string.presentToppings));
        int count=0;
        for (String s : selectedToppings) {
            if (count++ > 0) {
                message.append(", ");
            }
            message.append(s);
        }
        return message.toString();
    }

    private void addTopping(String option) {
        if (!selectedToppings.contains(option)) {
            selectedToppings.add(option);
        }
    }

    private void removeToping(String option) {
        if (selectedToppings.contains(option)) {
            selectedToppings.remove(option);
        }
    }
}