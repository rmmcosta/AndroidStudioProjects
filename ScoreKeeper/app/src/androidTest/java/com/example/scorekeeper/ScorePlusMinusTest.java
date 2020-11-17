package com.example.scorekeeper;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ScorePlusMinusTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void scorePlusMinusTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.tvTeam1Score), withText("0"),
                        withParent(allOf(withId(R.id.team1Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("0")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tvTeam2Score), withText("0"),
                        withParent(allOf(withId(R.id.team2Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("0")));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.btnTeam1Increase), withContentDescription("Team1 increase score"),
                        childAtPosition(
                                allOf(withId(R.id.team1Layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tvTeam1Score), withText("1"),
                        withParent(allOf(withId(R.id.team1Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("1")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.tvTeam2Score), withText("0"),
                        withParent(allOf(withId(R.id.team2Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView5.check(matches(withText("0")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.btnTeam2Increase), withContentDescription("Team2 increase score"),
                        childAtPosition(
                                allOf(withId(R.id.team2Layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.tvTeam2Score), withText("1"),
                        withParent(allOf(withId(R.id.team2Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView6.check(matches(withText("1")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.tvTeam1Score), withText("1"),
                        withParent(allOf(withId(R.id.team1Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView7.check(matches(withText("1")));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.btnTeam1Decrease), withContentDescription("Team1 decrease score"),
                        childAtPosition(
                                allOf(withId(R.id.team1Layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.tvTeam1Score), withText("0"),
                        withParent(allOf(withId(R.id.team1Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView8.check(matches(withText("0")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.tvTeam2Score), withText("1"),
                        withParent(allOf(withId(R.id.team2Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView9.check(matches(withText("1")));

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.btnTeam2Decrease), withContentDescription("Team2 decrease score"),
                        childAtPosition(
                                allOf(withId(R.id.team2Layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.tvTeam2Score), withText("0"),
                        withParent(allOf(withId(R.id.team2Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView10.check(matches(withText("0")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.tvTeam1Score), withText("0"),
                        withParent(allOf(withId(R.id.team1Layout),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView11.check(matches(withText("0")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
