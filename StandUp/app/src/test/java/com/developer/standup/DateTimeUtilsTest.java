package com.developer.standup;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThrows;

@RunWith(JUnit4.class)
public class DateTimeUtilsTest extends TestCase {

    @Test
    public void getTomorrow5InMilli() throws DateTimeUtils.WrongTimeException {
        long tomorrow5 = DateTimeUtils.getNextDateTimeInMilli("05:00");
        long currentTimeInMillis = DateTimeUtils.getCurrentDateTimeInMilli();
        assertTrue(tomorrow5 > currentTimeInMillis);
    }

    @Test
    public void getWrongTimeFormatException() {
        Exception assertException = assertThrows(DateTimeUtils.WrongTimeException.class, () -> {
            DateTimeUtils.getNextDateTimeInMilli("05");
        });
        String message = "The time must be inputted with the format hour:min!";
        assertEquals(message, assertException.getMessage());
    }
}