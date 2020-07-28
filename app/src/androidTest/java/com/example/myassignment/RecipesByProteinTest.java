package com.example.myassignment;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class RecipesByProteinTest {

    @Rule
    public ActivityTestRule<RecipesByProtein> activityTestRule = new ActivityTestRule<>(RecipesByProtein.class);
    private RecipesByProtein recipesByProtein = null;
    Instrumentation.ActivityMonitor tofuMonitor = getInstrumentation().addMonitor(TofuRecipes.class.getName(), null, false);
    Instrumentation.ActivityMonitor chickpeasMonitor = getInstrumentation().addMonitor(ChickpeaRecipes.class.getName(), null, false);
    Instrumentation.ActivityMonitor eggsMonitor = getInstrumentation().addMonitor(EggRecipes.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        recipesByProtein = activityTestRule.getActivity();
    }

    @Test
    public void viewTofuRecipes(){
        Espresso.onView(withId(R.id.btnTofuRecipes)).perform(click());
        Activity tofuActivity = getInstrumentation().waitForMonitorWithTimeout(tofuMonitor, 5000);
        assertNotNull(tofuActivity);
        tofuActivity.finish();
    }

    @Test
    public void viewChickpeasRecipes(){
        Espresso.onView(withId(R.id.btnChickpeaRecipes)).perform(click());
        Activity chickpeasActivity = getInstrumentation().waitForMonitorWithTimeout(chickpeasMonitor, 5000);
        assertNotNull(chickpeasActivity);
        chickpeasActivity.finish();
    }

    @Test
    public void viewEggRecipes(){
        Espresso.onView(withId(R.id.btnEggsRecipes)).perform(click());
        Activity eggssActivity = getInstrumentation().waitForMonitorWithTimeout(eggsMonitor, 5000);
        assertNotNull(eggssActivity);
        eggssActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
    }
}