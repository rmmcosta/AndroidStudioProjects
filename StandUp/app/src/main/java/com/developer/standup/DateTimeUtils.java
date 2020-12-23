package com.developer.standup;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {
    private static final String LOG_TAG = "LOG_DateTimeUtils";


    public static long getNextDateTimeInMilli(String time) throws WrongTimeException {
        Log.d(LOG_TAG, String.format("inputted time: %s", time));
        String[] timeParts = time.split(":");
        if (timeParts.length != 2) {
            throw new WrongTimeException();
        }
        int hour = Integer.parseInt(timeParts[0]);
        Log.d(LOG_TAG, String.format("hour: %d", hour));
        int min = Integer.parseInt(timeParts[1]);
        Log.d(LOG_TAG, String.format("min: %d", min));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return getLocalNextDateTimeInMilli(hour, min);
        } else {
            //TODO develop the logic for devices running under oreo version
            return 0;
        }
    }

    public static long getCurrentDateTimeInMilli() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime currDateTime = LocalDateTime.now();
            ZonedDateTime zonedDateTime = currDateTime.atZone(ZoneId.of("Europe/Lisbon"));
            return zonedDateTime.toInstant().toEpochMilli();
        } else {
            //TODO develop the logic for devices running under oreo version
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static long getLocalNextDateTimeInMilli(int hour, int min) {
        LocalDateTime currDateTime = LocalDateTime.now();
        Log.d(LOG_TAG, String.format("now: %s", currDateTime));
        LocalDateTime nextDateTime = LocalDateTime.of(currDateTime.getYear(), currDateTime.getMonth(), currDateTime.getDayOfMonth(), hour, min);
        Log.d(LOG_TAG, String.format("next date time 1st: %s", nextDateTime));
        if (nextDateTime.isBefore(currDateTime)) {
            nextDateTime = nextDateTime.plusDays(1);
            Log.d(LOG_TAG, String.format("next date time 2nd: %s", nextDateTime));
        }
        ZonedDateTime zonedDateTime = nextDateTime.atZone(ZoneId.of("Europe/Lisbon"));
        return zonedDateTime.toInstant().toEpochMilli();
    }

    static class WrongTimeException extends Exception {
        public WrongTimeException() {
            super("The time must be inputted with the format hour:min!");
        }
    }

}
