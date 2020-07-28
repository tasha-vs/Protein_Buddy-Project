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

public class ChickpeaRecipe1 extends AppCompatActivity {

    private Chickpeas cannedChickpeas;

    private String ingredient0 = "1 can (425g) chickpeas";
    private String ingredient1 = "½ teaspoon baking soda";
    private String ingredient2 = "¼ cup lemon juice, more to taste";
    private String ingredient3 = "1 medium-to-large clove garlic, roughly chopped";
    private String ingredient4 = "½ teaspoon fine sea salt, to taste";
    private String ingredient5 = "½ cup tahini";
    private String ingredient6 = "2 to 4 tablespoons ice water, more as needed";
    private String ingredient7 = "½ teaspoon ground cumin";
    private String ingredient8 = "1 tablespoon extra-virgin olive oil";

    private ListView p2r1InstructionsList;
    private Button calculateProteinButton;
    private Button viewIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chickpea_recipe1);
        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        cannedChickpeas = new Chickpeas(425);

        calculateProteinButton = findViewById(R.id.btnCalculateProtein);
        calculateProteinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateProtein(cannedChickpeas);
            }
        });

        viewIngredients = findViewById(R.id.btnViewIngredients);
        viewIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChickpeaIngredientsDialog dialog = new ChickpeaIngredientsDialog();
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

    private void calculateProtein(Chickpeas chickpeas) {

        double totalProtein = chickpeas.getWeight() * chickpeas.getChickpeasProteinPerGram();
        String recipeProtein = Double.toString(totalProtein);

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChickpeaRecipe1.this);
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

        p2r1InstructionsList = findViewById(R.id.p2r1DirectionsList);

        final ArrayList<String> instructionsListArray = new ArrayList<>();
        instructionsListArray.add("Add all ingredients into a food processor and  blend until " +
                "the mixture is ultra smooth, pale and creamy");
        instructionsListArray.add("Taste, and adjust as necessary—I almost always add another ¼ " +
                "teaspoon salt for more overall flavor and another tablespoon of lemon juice for" +
                " extra zing.");
        instructionsListArray.add("Scrape the hummus into a serving bowl or platter, and use a " +
                "spoon to create nice swooshes on top. Top with garnishes of your choice, " +
                "and serve. Leftover hummus keeps well in the refrigerator, covered, " +
                "for up to 1 week.");

        ArrayAdapter myInstructionsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instructionsListArray);
        p2r1InstructionsList.setAdapter(myInstructionsAdapter);

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
