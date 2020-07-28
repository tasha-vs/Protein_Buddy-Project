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

public class ChickpeaRecipes extends AppCompatActivity {

    private ListView chickpeaRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chickpea_recipes);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        displayChickpeaRecipes();

    }//end of on create

    private void displayChickpeaRecipes() {

        chickpeaRecipeList = findViewById(R.id.chickpeaRecipesList);

        final ArrayList<String> chickpeaRecipeArrayList = new ArrayList<>();

        chickpeaRecipeArrayList.add("Easy Hummus Recipe");
        chickpeaRecipeArrayList.add("Coconut Chickpea Curry");

        ArrayAdapter chickpeaArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, chickpeaRecipeArrayList);
        chickpeaRecipeList.setAdapter(chickpeaArrayAdapter);

        //determining which activity needs to be opened (which recipe)
        chickpeaRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    displayP2R1();
                }
                else if (position == 1){
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChickpeaRecipes.this);

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

    public void displayP2R1() {
        Intent chickpeaRecipe1Intent = new Intent(this, ChickpeaRecipe1.class);
        startActivity(chickpeaRecipe1Intent);
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
