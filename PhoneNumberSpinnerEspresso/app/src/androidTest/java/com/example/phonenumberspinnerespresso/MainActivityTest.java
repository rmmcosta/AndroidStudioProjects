package com.example.phonenumberspinnerespresso;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

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
public class MainActivityTest {
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
        final String PHONE = "+351914423167";
        input.perform(typeText(PHONE));
        input.check(matches(withText(PHONE)));
        AtomicReference<AtomicReferenceArray<String>> phoneTypes = new AtomicReference<>(new AtomicReferenceArray<>(new String[0]));
        activityScenarioRule.getScenario().onActivity(activity -> {
            phoneTypes.set(new AtomicReferenceArray<>(activity.getResources().getStringArray(R.array.phone_types)));

        });
        //we need to start counting from the end because the first item is already selected
        //and when clicked again it will no trigger the logic to set the text of the text view
        //with the phone number
        for (int i = phoneTypes.get().length() - 1; i > 0; i--) {
            String eachType = phoneTypes.get().get(i);
            spinner.check(matches(isDisplayed()));
            spinner.perform(click());
            onData(is(eachType)).perform(click());
            result.check(matches(withText(String.format("%s %s", PHONE, eachType))));
        }
    }

    @Test
    public void selectAnimal() {
        final String ANIMAL = "Snake";
        ViewInteraction animalSpinner = onView(withId(R.id.spinnerAnimals));
        animalSpinner.perform(click());
        onData(is(ANIMAL)).perform(click());
        ViewInteraction tvSelectedAnimal = onView(withId(R.id.tvSelectedAnimal));
        tvSelectedAnimal.check(matches(withText(ANIMAL)));
    }
}