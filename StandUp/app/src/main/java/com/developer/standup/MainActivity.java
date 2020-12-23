package com.developer.standup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "LOG_MainActivity";
    private static final int NOTIFICATION_WALK_ID = 1234;
    private static final int NOTIFICATION_ALARM_ID = 1222;

    private AlarmManager alarmManager;
    private Intent notificationsIntent;

    private EditText timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleAlarmToast = findViewById(R.id.toggleAlarm);
        toggleAlarmToast.setOnCheckedChangeListener(new OnToggleChange());

        ToggleButton toggleAlarmNotification = findViewById(R.id.toggleAlarmNotification);
        toggleAlarmNotification.setOnCheckedChangeListener(new OnToggleChange());

        ToggleButton toggleWalkReceiver = findViewById(R.id.toggleAlarmReceiver);
        toggleWalkReceiver.setOnCheckedChangeListener(new OnToggleChange());

        ToggleButton toggleSpecificTimeAlarm = findViewById(R.id.toggleSpecificAlarm);
        toggleSpecificTimeAlarm.setOnCheckedChangeListener(new OnToggleChange());

        timeEditText = findViewById(R.id.editTextTime);
        timeEditText.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                hideKeyboard();
            }
        });

        toggleSpecificTimeAlarm.setOnClickListener(view -> {
            hideKeyboard();
            timeEditText.clearFocus();
        });

        notificationsIntent = new Intent(this, NotificationsReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        checkToggleState(toggleWalkReceiver, NOTIFICATION_WALK_ID);
        checkToggleState(toggleSpecificTimeAlarm, NOTIFICATION_ALARM_ID);

    }

    private void hideKeyboard() {
        Log.d(LOG_TAG, "hide keyboard");
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(timeEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void checkToggleState(ToggleButton toggleButton, int notificationId) {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        boolean isChecked = PendingIntent.getBroadcast(this, notificationId, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null;
        toggleButton.setChecked(isChecked);
        String time = alarmIntent.getStringExtra(AlarmNotification.EXTRA_ALARM_TIME);
        timeEditText.setText(time);
    }

    public void showNextAlarm(View view) {

        if (alarmManager.getNextAlarmClock() != null) {
            long alarmTime = alarmManager.getNextAlarmClock().getTriggerTime();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.UK);
            String msg = simpleDateFormat.format(new Date(alarmTime));
            showMessage(msg);
        } else
            showMessage("No Alarm is defined!");
    }

    class OnToggleChange implements CompoundButton.OnCheckedChangeListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.d(LOG_TAG, "on checked changed");
            if (compoundButton.getId() == R.id.toggleAlarm) {
                showMessage(b ? getString(R.string.alarm_on_text) : getString(R.string.alarm_off_text));
                return;
            }
            if (compoundButton.getId() == R.id.toggleAlarmNotification) {
                sendNotification(b ? getString(R.string.alarm_on_text) : getString(R.string.alarm_off_text));
                return;
            }
            if (compoundButton.getId() == R.id.toggleAlarmReceiver) {
                PendingIntent pendingIntent = getWalkPendingIntent();
                if (b) {
                    long repeatInterval = 1000L;
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval, repeatInterval, pendingIntent);
                } else {
                    alarmManager.cancel(pendingIntent);
                }
                return;
            }
            if (compoundButton.getId() == R.id.toggleSpecificAlarm) {
                String time = timeEditText.getText().toString();
                PendingIntent pendingIntent = getSpecificAlarmPendingIntent(time);
                if (b) {
                    try {
                        long nextTime = DateTimeUtils.getNextDateTimeInMilli(time);
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
                    } catch (DateTimeUtils.WrongTimeException e) {
                        showMessage(e.getMessage());
                    }
                } else {
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }

    private PendingIntent getSpecificAlarmPendingIntent(String time) {
        return AlarmNotification.getBroadcastPendingIntent(this, NOTIFICATION_ALARM_ID, time);
    }

    private PendingIntent getWalkPendingIntent() {
        notificationsIntent.putExtra(AlarmNotification.EXTRA_NOTIFICATION_ID, NOTIFICATION_WALK_ID);
        notificationsIntent.putExtra(AlarmNotification.EXTRA_NOTIFICATION_TITLE, "Time to go for a walk!");
        notificationsIntent.putExtra(AlarmNotification.EXTRA_NOTIFICATION_ICON, R.drawable.ic_walk);
        return PendingIntent.getBroadcast(getApplicationContext(), NOTIFICATION_WALK_ID, notificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendNotification(String s) {
        AlarmNotification alarmNotification = new AlarmNotification(this, NOTIFICATION_WALK_ID, s, R.drawable.ic_alarm, "");
        alarmNotification.send();
    }

    private void showMessage(String msg) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(findViewById(R.id.mainLayout), msg, BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}