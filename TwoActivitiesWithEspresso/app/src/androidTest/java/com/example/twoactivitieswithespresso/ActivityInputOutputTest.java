package com.example.twoactivitieswithespresso;

import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {
    private ViewInteraction inputMain, btnSend, tvMessageTitle, tvMessage;
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        inputMain = onView(withId(R.id.inputEditText));
        btnSend = onView(withId(R.id.btnSend));
        tvMessageTitle = onView(withId(R.id.tvMessageTitle));
        tvMessage = onView(withId(R.id.tvMessage));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.twoactivitieswithespresso", appContext.getPackageName());
    }

    @Test
    public void mainActivityReady() {
        inputMain.check(matches(isDisplayed()));
        btnSend.check(matches(isDisplayed())).check(matches(isClickable()));
    }

    @Test
    public void sendMessage() {
        String message = "my message to the world";
        inputMain.perform(typeText(message));
        btnSend.perform(click());
        tvMessageTitle.check(matches(isDisplayed())).check(matches(withText(R.string.message_received)));
        tvMessage.check(matches(isDisplayed())).check(matches(withText(message)));
    }
}