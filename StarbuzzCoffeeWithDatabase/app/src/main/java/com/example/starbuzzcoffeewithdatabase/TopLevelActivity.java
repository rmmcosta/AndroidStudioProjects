package com.example.starbuzzcoffeewithdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starbuzzcoffeewithdatabase.adapters.OptionsListAdapter;

public class TopLevelActivity extends AppCompatActivity {
    private String[] optionsList;
    private RecyclerView rvOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        initializeOptions();
        initializeRVOptionsList();
    }

    private void initializeRVOptionsList() {
        rvOptionsList = findViewById(R.id.rvOptionsList);
        RecyclerView.Adapter optionsListAdapter = new OptionsListAdapter(this, optionsList, new OnOptionsClickListener());
        rvOptionsList.setAdapter(optionsListAdapter);
        rvOptionsList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeOptions() {
        optionsList = getResources().getStringArray(R.array.top_options);
    }

    class OnOptionsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView tvCurrent = view.findViewById(R.id.tvOptionItem);
            startSelectedActivity((String) tvCurrent.getText());
        }
    }

    private void startSelectedActivity(String option) {
        if (option.equalsIgnoreCase("Drinks")) {
            Intent intent = new Intent(this, DrinkCategoryActivity.class);
            startActivity(intent);
        } else {
            showMessage("Option not implemented!");
        }
    }

    private void showMessage(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}