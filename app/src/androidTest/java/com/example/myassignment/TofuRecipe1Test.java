package com.example.myassignment;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class TofuRecipe1Test {

    @Rule
    public ActivityTestRule<TofuRecipe1> activityTestRule = new ActivityTestRule<>(TofuRecipe1.class);
    private TofuRecipe1 recipeActivity = null;

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void testCorrectInstructions(){
        String instruction1 = "Press the tofu for 20 minutes";
        String instruction6 = "Serve over basmati rice (optional) with some chopped chives for " +
                "garnish";

        onView(withId(R.id.p1r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.p1r1DirectionsList)).atPosition(0)
                .check(matches(withText(instruction1)));
        onData(anything()).inAdapterView(withId(R.id.p1r1DirectionsList)).atPosition(5)
                .check(matches(withText(instruction6)));
    }

    @Test
    public void testCorrectIngredients(){
        String ingredient1 = "480g Extra Firm Tofu";
        String ingredient2 = "2 Tbsp Light Soy Sauce";
        String ingredient3 = "1 tsp Garlic Powder";
        String ingredient4 = "1 tsp Onion Powder";
        String ingredient5 = "1 tsp Ground Ginger";
        String ingredient6 = "1 Tbsp Maple Syrup";
        String ingredient7 = "1 Tbsp Rice Vinegar";
        String ingredient8 = "1 Tbsp Sesame Oil (plus more for frying)";
        String ingredient9 = "1 Tbsp Hoisin Sauce";
        String ingredient10 = "1 tsp Cornstarch";
        onView(withId(R.id.btnViewIngredients)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(0).check(matches(withText(ingredient1)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(1).check(matches(withText(ingredient2)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(2).check(matches(withText(ingredient3)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(3).check(matches(withText(ingredient4)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(4).check(matches(withText(ingredient5)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(5).check(matches(withText(ingredient6)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(6).check(matches(withText(ingredient7)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(7).check(matches(withText(ingredient8)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(8).check(matches(withText(ingredient9)));
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(9).check(matches(withText(ingredient10)));

    }

    @Test
    public void testAddingIngredientsToShoppingList(){
        String ingredient1 = "1 tsp Onion Powder";
        String ingredient2 = "1 tsp Ground Ginger";
        onView(withId(R.id.btnViewIngredients)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(3).perform(click());
        onData(anything()).inAdapterView(withId(R.id.ingredientsListDialog)).atPosition(4).perform(click());
        onView(withId(R.id.action_ok)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Shopping List")).perform(click());
        onData(anything()).inAdapterView(withId(R.id.shoppingListListView)).atPosition(0).check(matches(withText(ingredient1)));
        onData(anything()).inAdapterView(withId(R.id.shoppingListListView)).atPosition(1).check(matches(withText(ingredient2)));
    }

    @Test
    public void testCalculateProtein(){
        double correctProtein = 480 * 0.08;
        //String calculatedProtein = "The total protein from this recipe is: " +correctProtein+ " grams";
        onView(withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProtein+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
    }
}