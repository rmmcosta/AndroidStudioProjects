package com.example.recyclerviewwithwords;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.recyclerviewwithwords.TestUtils.atPosition;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTestingWithRecyclerActions {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test(expected = PerformException.class)
    public void itemWithText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.rvWords))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("dummy"))
                ));
    }

    @Test
    public void scrollToLastItem() {
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.rvWords))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(String.format("Word %d", MainActivity.INITIAL_WORDS_NUM - 1)))
                ));
    }

    @Test
    public void clickItem() {
        ViewInteraction rvWords = onView(withId(R.id.rvWords));
        rvWords.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        rvWords.perform(RecyclerViewActions.scrollTo(hasDescendant(withSubstring("Clicked"))));
        rvWords.check(matches(atPosition(0, hasDescendant(withSubstring("Clicked")))));
        int lastItem = MainActivity.INITIAL_WORDS_NUM-1;
        rvWords.perform(RecyclerViewActions.scrollToPosition(lastItem));
        rvWords.check(matches(atPosition(lastItem, hasDescendant(withText(String.format("Word %d", lastItem))))));
        rvWords.perform(RecyclerViewActions.actionOnItemAtPosition(lastItem, click()));
        rvWords.check(matches(atPosition(lastItem, hasDescendant(withSubstring("Clicked")))));
    }
}
