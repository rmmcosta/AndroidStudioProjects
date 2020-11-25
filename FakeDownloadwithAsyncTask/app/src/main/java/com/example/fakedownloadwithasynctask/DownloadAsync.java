package com.example.fakedownloadwithasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DownloadAsync extends AsyncTask<Void, Integer, String> {
    private static final String LOG_TAG = "LOG_DOWNLOAD_ASYNC";
    private final Context mContext;
    private final int mNumFiles;
    private ProgressDialog progressDialog;

    public DownloadAsync(Context context, int numFiles) {
        this.mContext = context;
        this.mNumFiles = numFiles;
    }

    @Override
    protected void onPreExecute() {
        //show progress dialog
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(mNumFiles);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(LOG_TAG, "clicked cancel");
                dialogInterface.cancel();
                cancel(true);
            }
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            for (int i = 1; i <= mNumFiles; i++) {
                Thread.sleep(5);
                Log.i(LOG_TAG, String.format(mContext.getString(R.string.download_progress), i));
                publishProgress(i);
            }
            return String.format(mContext.getString(R.string.download_finish), mNumFiles);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return "Error";
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
        toast.show();
    }
}
