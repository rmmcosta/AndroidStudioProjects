package com.developer.sqlitedatabasesimplewritedemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvSimple);
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(this);
        SQLiteDatabase sqLiteDatabase = simpleWriteSQLite.getReadableDatabase();
        simpleAdapter = new SimpleAdapter(simpleWriteSQLite.getSimpleList(sqLiteDatabase), view -> {
            TextView tvId = view.findViewById(R.id.tvId);
            int id = Integer.parseInt(tvId.getText().toString());
            TextView tvName = view.findViewById(R.id.tvName);
            boolean newFavorite = tvName.getCurrentTextColor() != Color.BLUE;
            simpleWriteSQLite.updateFavorite(sqLiteDatabase, id, newFavorite);
            simpleAdapter.updateSimpleFavorite(id, newFavorite);
        });
        recyclerView.setAdapter(simpleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveData(View view) {
        EditText etName = findViewById(R.id.etName);
        EditText etDescription = findViewById(R.id.etDescription);
        String name = etName.getText().toString();
        if (name.isEmpty()) {
            showMessage("It is mandatory to input the name!");
            return;
        }
        String description = etDescription.getText().toString();
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(this);
        SQLiteDatabase sqLiteDatabase = simpleWriteSQLite.getWritableDatabase();
        long id = simpleWriteSQLite.insertSimple(sqLiteDatabase, name, description);
        String success = String.format(getString(R.string.success_insert_message), id);
        showMessage(success);
        etName.setText("");
        etDescription.setText("");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        IBinder iBinder = etName.hasFocus() ? etName.getWindowToken() : etDescription.getWindowToken();
        inputMethodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
        simpleAdapter.addSimple(new Simple(id, name, description, LocalDateTime.now().toString(), false));
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}