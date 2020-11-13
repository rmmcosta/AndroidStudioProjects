package com.example.phonenumberspinnerespresso;

import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    ViewInteraction spinner;
    ViewInteraction input;
    ViewInteraction result;
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        spinner = onView(withId(R.id.spinnerPhoneType));
        input = onView(withId(R.id.etPhone));
        result = onView(withId(R.id.tvResult));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.phonenumberspinnerespresso", appContext.getPackageName());
    }

    @Test
    public void existsSpinner() {
        spinner.check(matches(isDisplayed()));
    }

    @Test
    public void isSpinnerClickable() {
        spinner.check(matches(isClickable()));
        spinner.perform(click());
    }

    @Test
    public void correctPhoneTypes() {
        String[] desiredTypes = new String[]{"Home", "Work", "Mobile", "Other"};
        activityScenarioRule.getScenario().onActivity(activity -> {
            String[] localPhoneTypes = activity.getResources().getStringArray(R.array.phone_types);
            for (int i = 0; i < localPhoneTypes.length; i++) {
                assertEquals(desiredTypes[i], localPhoneTypes[i]);
            }
        });
        AtomicReference<AtomicReferenceArray<String>> phoneTypes = new AtomicReference<>(new AtomicReferenceArray<>(new String[0]));
        activityScenarioRule.getScenario().onActivity(activity -> {
            phoneTypes.set(new AtomicReferenceArray<>(activity.getResources().getStringArray(R.array.phone_types)));

        });
        for (int i = 0; i < phoneTypes.get().length(); i++) {
            assertEquals(desiredTypes[i], phoneTypes.get().get(i));
        }
    }

    @Test
    public void existsTextViewResult() {
        result.check(matches(isDisplayed()));
    }

    @Test
    public void correctResult() {
        input.check(matches(isDisplayed()));
        final String phone = "+351914423167";
        input.perform(typeText(phone));
        result.check(matches(withText(R.string.result_placeholder)));
        AtomicReference<AtomicReferenceArray<String>> phoneTypes = new AtomicReference<>(new AtomicReferenceArray<>(new String[0]));
        activityScenarioRule.getScenario().onActivity(activity -> {
            phoneTypes.set(new AtomicReferenceArray<>(activity.getResources().getStringArray(R.array.phone_types)));

        });
        for (int i = 0; i < phoneTypes.get().length(); i++) {
            String eachType = phoneTypes.get().get(i);
            spinner.check(matches(isDisplayed()));
            spinner.perform(click());
            //onData(is(eachType)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is(eachType))).perform(click());
            result.check(matches(withText(containsString(String.format("%s %s", phone, eachType)))));
        }
    }
}