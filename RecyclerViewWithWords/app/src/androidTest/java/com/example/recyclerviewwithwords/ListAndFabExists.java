package com.example.recyclerviewwithwords;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListAndFabExists {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void listAndFabExists() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.tvWordListItem), withText("Word 0"),
                        withParent(withParent(withId(R.id.RecyclerView))),
                        isDisplayed()));
        textView.check(matches(withText("Word 0")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tvWordListItem), withText("Word 1"),
                        withParent(withParent(withId(R.id.RecyclerView))),
                        isDisplayed()));
        textView2.check(matches(withText("Word 1")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tvWordListItem), withText("Word 10"),
                        withParent(withParent(withId(R.id.RecyclerView))),
                        isDisplayed()));
        textView3.check(matches(withText("Word 10")));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.fab),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));
    }
}
