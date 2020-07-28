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

public class TofuRecipesTest {

    @Rule
    public ActivityTestRule<TofuRecipes> activityTestRule = new ActivityTestRule<>(TofuRecipes.class);
    private TofuRecipes recipeActivity = null;
    Instrumentation.ActivityMonitor tofuRecipeMonitor = getInstrumentation().addMonitor(TofuRecipe1.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void displayCorrectRecipe(){
        onView(withId(R.id.tofuRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.tofuRecipesList)).atPosition(0).perform(click());
        Activity tofuRecipe1Activity = getInstrumentation().waitForMonitorWithTimeout(tofuRecipeMonitor, 5000);
        assertNotNull(tofuRecipe1Activity);
        tofuRecipe1Activity.finish();
    }

    @Test
    public void displayDialog(){
        onView(withId(R.id.tofuRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.tofuRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
    }
}