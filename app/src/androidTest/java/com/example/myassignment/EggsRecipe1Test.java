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

public class EggsRecipe1Test {

    @Rule
    public ActivityTestRule<EggsRecipe1> activityTestRule = new ActivityTestRule<>(EggsRecipe1.class);
    private EggsRecipe1 recipeActivity = null;

    @Before
    public void setUp() throws Exception {
        recipeActivity = activityTestRule.getActivity();
    }

    @Test
    public void testCorrectInstructions(){
        String instruction1 = "Preheat oven to 180 degrees celsius";
        String instruction3 = "In a large bowl, whisk together eggs, milk, salt and pepper";

        onView(withId(R.id.p4r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.p4r1DirectionsList)).atPosition(0)
                .check(matches(withText(instruction1)));
        onData(anything()).inAdapterView(withId(R.id.p4r1DirectionsList)).atPosition(2)
                .check(matches(withText(instruction3)));
    }

    @Test
    public void testCorrectIngredients(){
        String ingredient1 = " 1 refrigerated pie crust";
        String ingredient2 = " 6 large eggs";
        String ingredient3 = "3/4 cup milk";
        String ingredient4 = " 3/4 teaspoon salt";
        String ingredient5 = " 1/4 teaspoon black pepper";
        String ingredient6 = "1 1/2 cups shredded cheese";
        String ingredient7 = " 3 tablespoons green onions";
        String ingredient8 = "180 grams feta cheese";
        String ingredient9 = "1 cup frozen chopped spinach, thawed & squeezed dry";
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
        String ingredient1 = " 3/4 teaspoon salt";
        String ingredient2 = " 1/4 teaspoon black pepper";
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
        double correctProtein = 6 * 6;
        onView(withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProtein+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
    }
}