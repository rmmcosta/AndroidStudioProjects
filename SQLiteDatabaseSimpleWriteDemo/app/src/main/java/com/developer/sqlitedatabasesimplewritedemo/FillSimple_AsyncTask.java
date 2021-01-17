package com.developer.sqlitedatabasesimplewritedemo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class FillSimple_AsyncTask extends AsyncTask<Void, Integer, Void> {
    private final Context context;
    private final SimpleAdapter simpleAdapter;
    private ProgressDialog progressDialog;
    public static final int FILL_NUM = 1000000;

    public FillSimple_AsyncTask(Context context, SimpleAdapter simpleAdapter) {
        this.context = context;
        this.simpleAdapter = simpleAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(FILL_NUM);
        progressDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Void... voids) {
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(context);
        SQLiteDatabase sqLiteDatabase = simpleWriteSQLite.getWritableDatabase();
        for (int i = 1; i <= FILL_NUM; i++) {
            String name = String.format(context.getString(R.string.auto_name), i);
            String description = String.format(context.getString(R.string.auto_description), i);
            simpleWriteSQLite.insertSimple(sqLiteDatabase, name, description);
            publishProgress(i);
        }
        sqLiteDatabase.close();
        progressDialog.dismiss();
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(context);
        simpleAdapter.setSimpleList(simpleWriteSQLite.getSimpleList());
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int currentProgress = values[0];
        progressDialog.setProgress(currentProgress);
    }
}
