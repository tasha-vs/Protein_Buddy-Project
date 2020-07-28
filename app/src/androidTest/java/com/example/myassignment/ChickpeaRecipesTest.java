package com.example.myassignment;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class ChickpeaRecipesTest {

    @Rule
    public ActivityTestRule<ChickpeaRecipes> activityTestRule = new ActivityTestRule<>(ChickpeaRecipes.class);
    private ChickpeaRecipes recipeActivity = null;
    Instrumentation.ActivityMonitor chickpeaRecipeMonitor = getInstrumentation().addMonitor(ChickpeaRecipe1.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void displayCorrectRecipe(){
        onView(withId(R.id.chickpeaRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.chickpeaRecipesList)).atPosition(0).perform(click());
        Activity tofuRecipe1Activity = getInstrumentation().waitForMonitorWithTimeout(chickpeaRecipeMonitor, 5000);
        assertNotNull(tofuRecipe1Activity);
        tofuRecipe1Activity.finish();
    }

    @Test
    public void displayDialog(){
        onView(withId(R.id.chickpeaRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.chickpeaRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
    }
}