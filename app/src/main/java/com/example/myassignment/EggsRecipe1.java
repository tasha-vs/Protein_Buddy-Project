package com.example.myassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EggsRecipe1 extends AppCompatActivity {

    private Eggs r1Eggs;

    private String ingredient0 = " 1 refrigerated pie crust";
    private String ingredient1 = " 6 large eggs";
    private String ingredient2 = "3/4 cup milk";
    private String ingredient3 = " 3/4 teaspoon salt";
    private String ingredient4 = " 1/4 teaspoon black pepper";
    private String ingredient5 = "1 1/2 cups shredded cheese";
    private String ingredient6 = " 3 tablespoons green onions";
    private String ingredient7 = "180 grams feta cheese";
    private String ingredient8 = "1 cup frozen chopped spinach, thawed & squeezed dry";

    private ListView p4r1InstructionsList;
    private Button calculateProteinButton;
    private Button viewIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eggs_recipe1);
        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        r1Eggs = new Eggs(6);
        calculateProteinButton = findViewById(R.id.btnCalculateProtein);
        calculateProteinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateProtein(r1Eggs);
            }
        });

        viewIngredients = findViewById(R.id.btnViewIngredients);
        viewIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EggIngredientsDialog dialog = new EggIngredientsDialog();
                dialog.show(getSupportFragmentManager(), "MyCustomDialog");

            }
        });

        displayInstructions();

    }//end of on create

    //creating our arraylist of ingredients specific to this recipe
    public ArrayList<String> createIngredientsList() {

        final ArrayList<String> ingredientsList = new ArrayList<String>();
        ingredientsList.add(ingredient0);
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);
        ingredientsList.add(ingredient3);
        ingredientsList.add(ingredient4);
        ingredientsList.add(ingredient5);
        ingredientsList.add(ingredient6);
        ingredientsList.add(ingredient7);
        ingredientsList.add(ingredient8);
        return ingredientsList;

    }

    private void calculateProtein(Eggs eggs) {

        double totalProtein = eggs.getWeight() * eggs.getEggProteinPerEgg();
        String recipeProtein = Double.toString(totalProtein);

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(EggsRecipe1.this);
        myBuilder.setCancelable(true);
        myBuilder.setTitle("Total Protein");
        myBuilder.setMessage("The total protein from this recipe is: " +"\n"
                +recipeProtein+ " grams");

        myBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        myBuilder.show();

    }

    private void displayInstructions() {

        p4r1InstructionsList = findViewById(R.id.p4r1DirectionsList);

        final ArrayList<String> instructionsListArray = new ArrayList<>();
        instructionsListArray.add("Preheat oven to 180 degrees celsius");
        instructionsListArray.add("Unroll pie crust and press into a 9\" pie plate, crimping the " +
                "top edges if desired");
        instructionsListArray.add("In a large bowl, whisk together eggs, milk, salt and pepper");
        instructionsListArray.add("Sprinkle feta, 1 cup of cheese, spinach, and green onions " +
                "into the pie crust and pour the egg mixture over top. Sprinkle remaining 1/2 cup cheese " +
                "on top of egg mixture");
        instructionsListArray.add("Bake for 35-40 minutes until the center is completely set. " +
                "Let cool for 5-10 minutes before slicing and serving");

        ArrayAdapter myInstructionsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instructionsListArray);
        p4r1InstructionsList.setAdapter(myInstructionsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                break;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.action_view_account:
                Intent viewAccountIntent = new Intent(this, MyAccount.class);
                startActivity(viewAccountIntent);
                break;

            case R.id.action_shopping_list:
                Intent shoppingListIntent = new Intent(this, ShoppingList.class);
                startActivity(shoppingListIntent);

            default:
                //ignore the unknown error
        }
        return super.onOptionsItemSelected(item);
    }

}
