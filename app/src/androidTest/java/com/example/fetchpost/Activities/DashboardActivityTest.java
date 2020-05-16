package com.example.fetchpost.Activities;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.fetchpost.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DashboardActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginToDashboard() {
        onView(withId(R.id.username)).perform(typeText("Jose"));
        onView(withId(R.id.password)).perform(typeText("12345"),closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());

    }
}