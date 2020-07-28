package com.example.myassignment;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class ChickpeaRecipe1Test {

    @Rule
    public ActivityTestRule<ChickpeaRecipe1> activityTestRule = new ActivityTestRule<>(ChickpeaRecipe1.class);
    private ChickpeaRecipe1 recipeActivity = null;

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void testCorrectInstructions(){
        String instruction1 = "Add all ingredients into a food processor and  blend until " +
                "the mixture is ultra smooth, pale and creamy";
        String instruction3 = "Scrape the hummus into a serving bowl or platter, and use a " +
                "spoon to create nice swooshes on top. Top with garnishes of your choice, " +
                "and serve. Leftover hummus keeps well in the refrigerator, covered, " +
                "for up to 1 week.";

        onView(withId(R.id.p2r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.p2r1DirectionsList)).atPosition(0)
                .check(matches(withText(instruction1)));
        onData(anything()).inAdapterView(withId(R.id.p2r1DirectionsList)).atPosition(2)
                .check(matches(withText(instruction3)));
    }

    @Test
    public void testCorrectIngredients(){
        String ingredient1 = "1 can (425g) chickpeas";
        String ingredient2 = "½ teaspoon baking soda";
        String ingredient3 = "¼ cup lemon juice, more to taste";
        String ingredient4 = "1 medium-to-large clove garlic, roughly chopped";
        String ingredient5 = "½ teaspoon fine sea salt, to taste";
        String ingredient6 = "½ cup tahini";
        String ingredient7 = "2 to 4 tablespoons ice water, more as needed";
        String ingredient8 = "½ teaspoon ground cumin";
        String ingredient9 = "1 tablespoon extra-virgin olive oil";
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
    }

    @Test
    public void testAddingIngredientsToShoppingList(){
        String ingredient1 = "1 medium-to-large clove garlic, roughly chopped";
        String ingredient2 = "½ teaspoon fine sea salt, to taste";
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
        double correctProtein = 425 * 0.19;
        onView(withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProtein+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
    }
}