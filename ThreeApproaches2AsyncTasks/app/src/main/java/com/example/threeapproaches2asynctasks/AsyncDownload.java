package com.example.threeapproaches2asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncDownload extends AsyncTask<Void, Integer, String> {
    private final Context mContext;
    private final int mNumFiles;
    private static final String LOG_TAG = "ASYNC_DOWNLOAD_LOG";
    private ProgressDialog progressDialog;

    public AsyncDownload(Context mContext, int mNumFiles) {
        this.mContext = mContext;
        this.mNumFiles = mNumFiles;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMax(mNumFiles);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, mContext.getString(R.string.cancel_download_btn), (dialogInterface, i) -> {
            dialogInterface.cancel();
            //cancel thread (async task)
            cancel(true);
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            for (int i = 1; i <= mNumFiles; i++) {
                Thread.sleep(100);
                String logMsg = String.format(mContext.getString(R.string.progress_log), i);
                Log.i(LOG_TAG, logMsg);
                publishProgress(i);
            }
            progressDialog.dismiss();
            return String.format(mContext.getString(R.string.download_files_success), mNumFiles);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
        toast.show();
    }
}
