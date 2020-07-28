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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipesListAll extends AppCompatActivity {

    private ListView listViewRecipes;
    private String p1Recipe0 = "Marinated Tofu Recipe";                         //0
    private String p1Recipe1 = "Scrambled Tofu";                                //1
    private String p2Recipe0 = "Easy Hummus Recipe";                            //2
    private String p2Recipe1 = "Coconut Chickpea Curry";                        //3
    private String p4Recipe0 = "Easy Spinach and Feta Quiche";                  //4
    private String p4Recipe1 = "Garlic, Leek, and Brussels Sprouts Frittata";   //5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list_all);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        displayAllRecipes();
    }

    private void displayAllRecipes() {
        listViewRecipes = findViewById(R.id.AllRecipesList);

        final ArrayList<String> allRecipesArrayList = new ArrayList<>();

        allRecipesArrayList.add(p1Recipe0);
        allRecipesArrayList.add(p1Recipe1);
        allRecipesArrayList.add(p2Recipe0);
        allRecipesArrayList.add(p2Recipe1);
        allRecipesArrayList.add(p4Recipe0);
        allRecipesArrayList.add(p4Recipe1);
                                                                        //"uses unchecked or unsafe operations"???
        ArrayAdapter allRecipesArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allRecipesArrayList);
        listViewRecipes.setAdapter(allRecipesArrayAdapter);

        //determining which activity needs to be opened (which recipe)
        listViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    displayP1R1();
                }
                else if (position == 1){
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(RecipesListAll.this);

                    //allowing users to cancel out of the alert by pressing outside of the popup
                    myBuilder.setCancelable(true);
                    myBuilder.setTitle("Not yet Available");
                    myBuilder.setMessage("Sorry! Recipe coming to you soon...");

                    myBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    myBuilder.show();
                }
                else if (position == 2){
                    displayP2R1();
                }
                else if (position == 3){
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(RecipesListAll.this);

                    //allowing users to cancel out of the alert by pressing outside of the popup
                    myBuilder.setCancelable(true);
                    myBuilder.setTitle("Not yet Available");
                    myBuilder.setMessage("Sorry! Recipe coming to you soon...");

                    myBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    myBuilder.show();
                }
                else if (position == 4){
                    displayP4R1();
                }
                else if (position == 5){
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(RecipesListAll.this);

                    //allowing users to cancel out of the alert by pressing outside of the popup
                    myBuilder.setCancelable(true);
                    myBuilder.setTitle("Not yet Available");
                    myBuilder.setMessage("Sorry! Recipe coming to you soon...");

                    myBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    myBuilder.show();
                }
            }
        });
    }

    private void displayP4R1() {
        Intent eggsRecipe1Intent = new Intent(this, EggsRecipe1.class);
        startActivity(eggsRecipe1Intent);
    }

    private void displayP2R1() {
        Intent chickpeaRecipe1Intent = new Intent(this, ChickpeaRecipe1.class);
        startActivity(chickpeaRecipe1Intent);
    }

    private void displayP1R1() {
        Intent tofuRecipe2Intent = new Intent(this, TofuRecipe1.class);
        startActivity(tofuRecipe2Intent);
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
