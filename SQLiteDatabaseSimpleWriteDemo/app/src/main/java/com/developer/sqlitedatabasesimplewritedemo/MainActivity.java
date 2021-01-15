package com.developer.sqlitedatabasesimplewritedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

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
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}