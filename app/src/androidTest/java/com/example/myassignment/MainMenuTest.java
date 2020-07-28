package com.example.myassignment;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

public class MainMenuTest {

    @Rule
    public ActivityTestRule<MainMenu> activityTestRule = new ActivityTestRule<>(MainMenu.class);
    private MainMenu mainMenu = null;
    Instrumentation.ActivityMonitor MainActivityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor proteinMonitor = getInstrumentation().addMonitor(RecipesByProtein.class.getName(), null, false);
    Instrumentation.ActivityMonitor allRecipesMonitor = getInstrumentation().addMonitor(RecipesListAll.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mainMenu = activityTestRule.getActivity();
    }

    @Test
    public void viewByProtein(){
        Espresso.onView(withId(R.id.btnProteinRecipes)).perform(click());
        Activity mainMenuActivity = getInstrumentation().waitForMonitorWithTimeout(proteinMonitor, 5000);
        assertNotNull(mainMenuActivity);
        mainMenuActivity.finish();
    }

    @Test
    public void viewAllRecipes(){
        Espresso.onView(withId(R.id.btnAllRecipes)).perform(click());
        Activity mainMenu = getInstrumentation().waitForMonitorWithTimeout(allRecipesMonitor, 5000);
        assertNotNull(mainMenu);
        mainMenu.finish();
    }

    @Test
    public void endToEndTestPart2(){
       //clicking view by protein
        Espresso.onView(withId(R.id.btnProteinRecipes)).perform(click());
        Activity mainMenuActivity = getInstrumentation().waitForMonitorWithTimeout(proteinMonitor, 5000);
        assertNotNull(mainMenuActivity);

        //selecting tofu
        Espresso.onView(withId(R.id.btnTofuRecipes)).perform(click());
        String tofuRecipe1 = "Marinated Tofu Recipe";
        String tofuRecipe2 = "Scrambled Tofu";

        //checking correct tofu recipes are displayed
        onView(ViewMatchers.withId(R.id.tofuRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.tofuRecipesList)).atPosition(0)
                .check(matches(withText(tofuRecipe1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.tofuRecipesList)).atPosition(1)
                .check(matches(withText(tofuRecipe2)));

        //seeing if the user is alerted the recipe is not available
        onView(ViewMatchers.withId(R.id.tofuRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.tofuRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //selecting marinated tofu recipe
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.tofuRecipesList)).atPosition(0).perform(click());

        //testing correct instructions are displayed
        String instruction1 = "Press the tofu for 20 minutes";
        String instruction6 = "Serve over basmati rice (optional) with some chopped chives for " +
                "garnish";
        onView(ViewMatchers.withId(R.id.p1r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p1r1DirectionsList)).atPosition(0)
                .check(matches(withText(instruction1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p1r1DirectionsList)).atPosition(5)
                .check(matches(withText(instruction6)));

        //testing calculate protein is correct
        double correctProtein = 480 * 0.08;
        String calculatedProtein = "The total protein from this recipe is: " +correctProtein+ " grams";
        onView(ViewMatchers.withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProtein+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //testing correct ingredients are displayed
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
        onView(ViewMatchers.withId(R.id.btnViewIngredients)).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(0).check(matches(withText(ingredient1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(1).check(matches(withText(ingredient2)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(2).check(matches(withText(ingredient3)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).check(matches(withText(ingredient4)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).check(matches(withText(ingredient5)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).check(matches(withText(ingredient6)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(6).check(matches(withText(ingredient7)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(7).check(matches(withText(ingredient8)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(8).check(matches(withText(ingredient9)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(9).check(matches(withText(ingredient10)));

        //clicking on ingredients
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).perform(click());
        onView(ViewMatchers.withId(R.id.action_ok)).perform(click());

        //going to the shopping list
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Shopping List")).perform(click());

        //checking if the correct ingredients have been added
        onView(ViewMatchers.withText(ingredient4));
        onView(ViewMatchers.withText(ingredient5));
        onView(ViewMatchers.withText(ingredient6));

        //removing the items
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(0).perform(click());
        onView(ViewMatchers.withId(R.id.btnMainMenu)).perform(click());

        //viewing by protein, selecting chickpeas
        Espresso.onView(withId(R.id.btnProteinRecipes)).perform(click());
        Espresso.onView(withId(R.id.btnChickpeaRecipes)).perform(click());

        //testing unavailable recipe
        onView(ViewMatchers.withId(R.id.chickpeaRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.chickpeaRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());
        onView(ViewMatchers.withId(R.id.chickpeaRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.chickpeaRecipesList)).atPosition(0).perform(click());

        //checking correct instructions are displayed
        String chickpeaIinstruction1 = "Add all ingredients into a food processor and  blend until " +
                "the mixture is ultra smooth, pale and creamy";
        String chickpeaInstruction3 = "Scrape the hummus into a serving bowl or platter, and use a " +
                "spoon to create nice swooshes on top. Top with garnishes of your choice, " +
                "and serve. Leftover hummus keeps well in the refrigerator, covered, " +
                "for up to 1 week.";
        onView(ViewMatchers.withId(R.id.p2r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p2r1DirectionsList)).atPosition(0)
                .check(matches(withText(chickpeaIinstruction1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p2r1DirectionsList)).atPosition(2)
                .check(matches(withText(chickpeaInstruction3)));

        //testing calculated protein is correct
        double correctProtein2 = 425 * 0.19;
        onView(ViewMatchers.withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProtein2+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //testing the correct ingredients are displayed in the dialog
        String cpIngredient1 = "1 can (425g) chickpeas";
        String cpIngredient2 = "½ teaspoon baking soda";
        String cpIngredient3 = "¼ cup lemon juice, more to taste";
        String cpIngredient4 = "1 medium-to-large clove garlic, roughly chopped";
        String cpIngredient5 = "½ teaspoon fine sea salt, to taste";
        String cpIngredient6 = "½ cup tahini";
        String cpIngredient7 = "2 to 4 tablespoons ice water, more as needed";
        String cpIngredient8 = "½ teaspoon ground cumin";
        String cpIngredient9 = "1 tablespoon extra-virgin olive oil";
        onView(ViewMatchers.withId(R.id.btnViewIngredients)).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(0).check(matches(withText(cpIngredient1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(1).check(matches(withText(cpIngredient2)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(2).check(matches(withText(cpIngredient3)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).check(matches(withText(cpIngredient4)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).check(matches(withText(cpIngredient5)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).check(matches(withText(cpIngredient6)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(6).check(matches(withText(cpIngredient7)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(7).check(matches(withText(cpIngredient8)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(8).check(matches(withText(cpIngredient9)));

        //selecting ingredients
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).perform(click());
        onView(ViewMatchers.withId(R.id.action_ok)).perform(click());

        //going to the shopping list
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Shopping List")).perform(click());

        //checking if the correct ingredients have been added
        onView(ViewMatchers.withText(cpIngredient4));
        onView(ViewMatchers.withText(cpIngredient5));
        onView(ViewMatchers.withText(cpIngredient6));

        //removing the items
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(0).perform(click());

        //returning to main menu
        onView(ViewMatchers.withId(R.id.btnMainMenu)).perform(click());

        //viewing by protein, selecting eggs
        Espresso.onView(withId(R.id.btnProteinRecipes)).perform(click());
        Espresso.onView(withId(R.id.btnEggsRecipes)).perform(click());

        //testing unavailable recipe
        onView(ViewMatchers.withId(R.id.eggsRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.eggsRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //selecting quiche recipe
        onView(ViewMatchers.withId(R.id.eggsRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.eggsRecipesList)).atPosition(0).perform(click());

        //testing correct instructions
        String eggInstruction1 = "Preheat oven to 180 degrees celsius";
        String eggInstruction3 = "In a large bowl, whisk together eggs, milk, salt and pepper";
        onView(ViewMatchers.withId(R.id.p4r1DirectionsList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p4r1DirectionsList)).atPosition(0)
                .check(matches(withText(eggInstruction1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.p4r1DirectionsList)).atPosition(2)
                .check(matches(withText(eggInstruction3)));

        //testing calculated protein is correct
        double correctProteinEgg = 6 * 6;
        onView(ViewMatchers.withId(R.id.btnCalculateProtein)).perform(click());
        Espresso.onView(withText("The total protein from this recipe is: " + "\n" +correctProteinEgg+ " grams"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //testing correct ingredients displayed within dialog
        String EIngredient1 = " 1 refrigerated pie crust";
        String EIngredient2 = " 6 large eggs";
        String EIngredient3 = "3/4 cup milk";
        String EIngredient4 = " 3/4 teaspoon salt";
        String EIngredient5 = " 1/4 teaspoon black pepper";
        String EIngredient6 = "1 1/2 cups shredded cheese";
        String EIngredient7 = " 3 tablespoons green onions";
        String EIngredient8 = "180 grams feta cheese";
        String EIngredient9 = "1 cup frozen chopped spinach, thawed & squeezed dry";
        onView(ViewMatchers.withId(R.id.btnViewIngredients)).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(0).check(matches(withText(EIngredient1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(1).check(matches(withText(EIngredient2)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(2).check(matches(withText(EIngredient3)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).check(matches(withText(EIngredient4)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).check(matches(withText(EIngredient5)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).check(matches(withText(EIngredient6)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(6).check(matches(withText(EIngredient7)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(7).check(matches(withText(EIngredient8)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(8).check(matches(withText(EIngredient9)));

        //adding items to shopping list
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(3).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(4).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.ingredientsListDialog)).atPosition(5).perform(click());
        onView(ViewMatchers.withId(R.id.action_ok)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Shopping List")).perform(click());

        //checking if the correct ingredients have been added
        onView(ViewMatchers.withText(EIngredient4));
        onView(ViewMatchers.withText(EIngredient5));
        onView(ViewMatchers.withText(EIngredient6));

        //removing the items
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.shoppingListListView)).atPosition(0).perform(click());

        //returning to main menu
        onView(ViewMatchers.withId(R.id.btnMainMenu)).perform(click());

        //View all recipes
        Espresso.onView(withId(R.id.btnAllRecipes)).perform(click());

        //checking all correct recipes are displayed
        String recipe1 = "Marinated Tofu Recipe";
        String recipe2 = "Scrambled Tofu";
        String recipe3 = "Easy Hummus Recipe";
        String recipe4 = "Coconut Chickpea Curry";
        String recipe5 = "Easy Spinach and Feta Quiche";
        String recipe501 = "Spinach and Feta Quiche";
        String recipe6 = "Garlic, Leek, and Brussels Sprouts Frittata";
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(0).check(matches(withText(recipe1)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(1).check(matches(withText(recipe2)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(2).check(matches(withText(recipe3)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(3).check(matches(withText(recipe4)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(4).check(matches(withText(recipe5)));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(5).check(matches(withText(recipe6)));

        //clickng on each unavailable recipe
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(1).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        onView(ViewMatchers.withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(3).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        onView(ViewMatchers.withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(5).perform(click());
        Espresso.onView(withText("Sorry! Recipe coming to you soon..."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OKAY")).perform(click());

        //clicking on each available recipe
        onView(ViewMatchers.withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(0).perform(click());
        onView(ViewMatchers.withId(R.id.p1r1RecipeTitle)).check(matches(withText(recipe1)));
        Espresso.pressBack();

        onView(ViewMatchers.withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(2).perform(click());
        onView(ViewMatchers.withId(R.id.p2r1RecipeTitle)).check(matches(withText(recipe3)));
        Espresso.pressBack();

        onView(ViewMatchers.withId(R.id.AllRecipesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.AllRecipesList)).atPosition(4).perform(click());
        onView(ViewMatchers.withId(R.id.p4r1RecipeTitle)).check(matches(withText(recipe501)));
        Espresso.pressBack();

        //using the drop down menu to view account
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("View Account")).perform(click());

        //checking username would be displayed
        //(note this is empty as we are not interacting with the database)
        onView(ViewMatchers.withId(R.id.userAccountUsername)).check(matches(isDisplayed()));

        //checking that when edit username button is clicked, the edit text for updating username
        //is displayed
        onView(ViewMatchers.withId(R.id.btnEditUsername)).perform(click());
        onView(ViewMatchers.withId(R.id.editTextUsername)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        //checking that when edit username button is clicked, the edit text for updating password
        //is displayed
        onView(ViewMatchers.withId(R.id.btnEditPassword)).perform(click());
        onView(ViewMatchers.withId(R.id.editTextPassword)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        //checking that if delete account is pressed, a confirmation alert box is displayed,
        // then is dismissed if cancel is pressed
        onView(ViewMatchers.withId(R.id.btnDeleteAccount)).perform(click());
        Espresso.onView(withText("CANCEL")).inRoot(isDialog())
                .check(matches(isDisplayed())).perform(click());

        //checking that the user gets successfully logged out and returned to the main activity,
        // when the select logout through the drop down menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Log Out")).perform(click());
        Activity MainMenuTest = getInstrumentation().waitForMonitorWithTimeout(MainActivityMonitor, 5000);
        assertNotNull(MainMenuTest);
        MainMenuTest.finish();
    }

    @After
    public void tearDown() throws Exception {
    }

}