package com.developer.sqlitedatabasesimplewritedemo;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter simpleAdapter;
    TextView tvCount;
    long recordsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rvSimple);
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(this);
        simpleAdapter = new SimpleAdapter(simpleWriteSQLite.getSimpleList(), view -> {
            TextView tvId = view.findViewById(R.id.tvId);
            int id = Integer.parseInt(tvId.getText().toString());
            TextView tvName = view.findViewById(R.id.tvName);
            boolean newFavorite = tvName.getCurrentTextColor() != Color.BLUE;
            simpleWriteSQLite.updateFavorite(id, newFavorite);
            simpleAdapter.updateSimpleFavorite(id, newFavorite);
        });
        /*Cursor cursorAllRecords = simpleWriteSQLite.getAllSimpleCursor();
        String[] displayColumns = new String[]{"_id", "NAME", "DESCRIPTION", "CREATED_ON", "FAVORITE"};
        int[] textViews = new int[]{R.id.tvId, R.id.tvName, R.id.tvDescription, R.id.tvTimestamp, R.id.tvFavorite};
        recyclerView.setAdapter(new SimpleCursorAdapter(this, R.layout.simple_detail, cursorAllRecords, displayColumns, textViews, 0));*/
        recyclerView.setAdapter(simpleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recordsCount = simpleWriteSQLite.countSimple();
        tvCount = findViewById(R.id.tvRecordsCount);
        tvCount.setText(String.valueOf(recordsCount));
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
        long id = simpleWriteSQLite.insertSimple(name, description);
        String success = String.format(getString(R.string.success_insert_message), id);
        showMessage(success);
        etName.setText("");
        etDescription.setText("");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        IBinder iBinder = etName.hasFocus() ? etName.getWindowToken() : etDescription.getWindowToken();
        inputMethodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
        simpleAdapter.addSimple(new Simple(id, name, description, LocalDateTime.now().toString(), false));
        recordsCount++;
        tvCount.setText(String.valueOf(recordsCount));
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void fillSimpleTable(View view) {
        FillSimple_AsyncTask fillSimple_asyncTask = new FillSimple_AsyncTask(this, simpleAdapter);
        fillSimple_asyncTask.execute();
        recordsCount += FillSimple_AsyncTask.FILL_NUM;
        tvCount.setText(String.valueOf(recordsCount));
    }
}