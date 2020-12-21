package com.developer.standup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

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

        notificationsIntent = new Intent(getApplicationContext(), NotificationsReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        checkToggleState(toggleWalkReceiver, NOTIFICATION_WALK_ID);
        checkToggleState(toggleSpecificTimeAlarm, NOTIFICATION_ALARM_ID);

    }

    private void checkToggleState(ToggleButton toggleButton, int notificationId) {
        boolean isChecked = PendingIntent.getBroadcast(this, notificationId, notificationsIntent, PendingIntent.FLAG_NO_CREATE) != null;
        toggleButton.setChecked(isChecked);
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
                PendingIntent pendingIntent = getSpecificAlarmPendingIntent();
                if (b) {
                    EditText timeEditText = findViewById(R.id.editTextTime);
                    String time = timeEditText.getText().toString();
                    String[] timeParts = time.split(":");
                    if (timeParts.length != 2) {
                        showMessage("wrong inputted time!");
                        return;
                    }
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    Date currDateTime = new Date();
                    Date inputtedDateTime = new Date(currDateTime.getYear(), currDateTime.getMonth(), currDateTime.getDay(), hour, minute);
                    if (inputtedDateTime.compareTo(currDateTime) < 0) {
                        inputtedDateTime = new Date(currDateTime.getYear(), currDateTime.getMonth(), currDateTime.getDay() + 1, hour, minute);
                    }
                    Calendar calendar = GregorianCalendar.getInstance(Locale.UK);
                    calendar.setTime(inputtedDateTime);
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }

    private PendingIntent getSpecificAlarmPendingIntent() {
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_ID, NOTIFICATION_ALARM_ID);
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_TITLE, "Time to wake up!");
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_ICON, R.drawable.ic_alarm);
        return PendingIntent.getBroadcast(getApplicationContext(), NOTIFICATION_ALARM_ID, notificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getWalkPendingIntent() {
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_ID, NOTIFICATION_WALK_ID);
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_TITLE, "Time to go for a walk!");
        notificationsIntent.putExtra(NotificationsReceiver.EXTRA_NOTIFICATION_ICON, R.drawable.ic_walk);
        return PendingIntent.getBroadcast(getApplicationContext(), NOTIFICATION_WALK_ID, notificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendNotification(String s) {
        AlarmNotification.send(this, NOTIFICATION_WALK_ID, s, R.drawable.ic_alarm);
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