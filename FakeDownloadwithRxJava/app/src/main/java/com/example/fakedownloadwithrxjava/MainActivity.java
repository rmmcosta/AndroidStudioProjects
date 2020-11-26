package com.example.fakedownloadwithrxjava;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN_ACTIVITY";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final int SLEEP_DURATION = 1000;
    private static final int NUM_FILES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void download(View view) {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG, "onComplete()");
                        showToastDownloadFinish(NUM_FILES);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(LOG_TAG, "onNext(" + value + ")");
                    }
                }));
    }

    private void showToastDownloadFinish(int numFiles) {
        Toast toast = Toast.makeText(this, getString(R.string.download_finish, numFiles), Toast.LENGTH_LONG);
        toast.show();
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(() -> {
            // Do some long running operation
            for (int i = 1; i <= NUM_FILES; i++) {
                Thread.sleep(SLEEP_DURATION);
            }
            return Observable.just("Success");
        });
    }

    public void downloadWithProgress(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        Disposable downloadObserver = Observable.create(emitter -> {
            for (int i = 1; i <= NUM_FILES; i++) {
                String logMsg = String.format("still counting %d", i);
                Log.i(LOG_TAG, logMsg);
                try {
                    if (emitter.isDisposed()) {
                        return;
                    }
                    Thread.sleep(SLEEP_DURATION);
                } catch (Exception e) {
                    if (!emitter.isDisposed()) {
                        throw new Exception(e);
                    }
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    // update progress
                    progressDialog.setProgress((Integer) o);
                }, t -> {
                    // on error
                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }, () -> {
                    // on complete
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();
                });
        progressDialog.setMax(NUM_FILES);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                downloadObserver.dispose();
            }
        });
        progressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}