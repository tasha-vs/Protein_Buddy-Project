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

public class TofuRecipe1 extends AppCompatActivity {

    private Tofu extraFirmTofu;

    private String ingredient0 = "480g Extra Firm Tofu";
    private String ingredient1 = "2 Tbsp Light Soy Sauce";
    private String ingredient2 = "1 tsp Garlic Powder";
    private String ingredient3 = "1 tsp Onion Powder";
    private String ingredient4 = "1 tsp Ground Ginger";
    private String ingredient5 = "1 Tbsp Maple Syrup";
    private String ingredient6 = "1 Tbsp Rice Vinegar";
    private String ingredient7 = "1 Tbsp Sesame Oil (plus more for frying)";
    private String ingredient8 = "1 Tbsp Hoisin Sauce";
    private String ingredient9 = "1 tsp Cornstarch";

    private ListView p1r1InstructionsList;
    private Button calculateProteinButton;
    private Button viewIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tofu_recipe1);
        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        extraFirmTofu = new Tofu(480);
        calculateProteinButton = findViewById(R.id.btnCalculateProtein);
        calculateProteinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateProtein( extraFirmTofu );
            }
        });

        viewIngredients = findViewById(R.id.btnViewIngredients);
        viewIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TofuIngredientsDialog dialog = new TofuIngredientsDialog();
                dialog.show(getSupportFragmentManager(), "MyCustomDialog");
            }
        });

        displayInstructions();
    }

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
        ingredientsList.add(ingredient9);
        return ingredientsList;

    }

    public void calculateProtein(Tofu tofu) {

        double totalProtein = tofu.getWeight() * tofu.getTofuProteinPerGram();
        String recipeProtein = Double.toString(totalProtein);

        AlertDialog.Builder myBuilder = new AlertDialog.Builder(TofuRecipe1.this);
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

        p1r1InstructionsList = findViewById(R.id.p1r1DirectionsList);

        final ArrayList<String> instructionsListArray = new ArrayList<>();
        instructionsListArray.add("Press the tofu for 20 minutes");
        instructionsListArray.add("Once the tofu is pressed cut it into cubes");
        instructionsListArray.add("Mix all the ingredients for the tofu marinade except the " +
                "cornstarch together and pour over the tofu. Turn over all the blocks of tofu so " +
                "they have marinade on both sides. Place into the fridge and leave to marinate " +
                "for 30 minutes");
        instructionsListArray.add("Add a little sesame oil to a frying pan and then add the tofu, "+
                "leaving the tofu marinade sauce behind (don’t throw it out, you’re still going " +
                "to use it). Fry the tofu until golden brown");
        instructionsListArray.add("Mix the leftover marinade sauce with the tsp of cornstarch and "+
                "then pour over the tofu in the frying pan. Fry until the sauce thickens");
        instructionsListArray.add("Serve over basmati rice (optional) with some chopped chives " +
                "for garnish");

        ArrayAdapter myInstructionsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instructionsListArray);
        p1r1InstructionsList.setAdapter(myInstructionsAdapter);

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
