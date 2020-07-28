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

public class RecipesListAllTest {

    @Rule
    public ActivityTestRule<RecipesListAll> activityTestRule = new ActivityTestRule<>(RecipesListAll.class);
    private RecipesListAll recipeActivity = null;

    Instrumentation.ActivityMonitor tofuMonitor = getInstrumentation().addMonitor(TofuRecipe1.class.getName(), null, false);
    Instrumentation.ActivityMonitor chickpeaMonitor = getInstrumentation().addMonitor(ChickpeaRecipe1.class.getName(), null, false);
    Instrumentation.ActivityMonitor eggsMonitor = getInstrumentation().addMonitor(EggsRecipe1.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void displayTofuRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(0).perform(click());
        Activity tofuRecipe1Activity = getInstrumentation().waitForMonitorWithTimeout(tofuMonitor, 5000);
        assertNotNull(tofuRecipe1Activity);
        tofuRecipe1Activity.finish();
    }

    @Test
    public void selectNonexistentTofuRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void displayChickpeaRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(2).perform(click());
        Activity chickpeaRecipe1Activity = getInstrumentation().waitForMonitorWithTimeout(chickpeaMonitor, 5000);
        assertNotNull(chickpeaRecipe1Activity);
        chickpeaRecipe1Activity.finish();
    }

    @Test
    public void selectNonexistentChickpeaRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(3).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void displayEggsRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(4).perform(click());
        Activity eggsRecipe1Activity = getInstrumentation().waitForMonitorWithTimeout(eggsMonitor, 5000);
        assertNotNull(eggsRecipe1Activity);
        eggsRecipe1Activity.finish();
    }

    @Test
    public void selectNonexistentEggRecipe(){
        onView(withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AllRecipesList)).atPosition(5).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
    }
}