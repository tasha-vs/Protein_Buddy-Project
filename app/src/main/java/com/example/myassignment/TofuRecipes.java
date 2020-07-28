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

public class TofuRecipes extends AppCompatActivity {

    private ListView tofuRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tofu_recipes);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        displayTofuRecipes();

    }//end of on create

    private void displayTofuRecipes() {

        tofuRecipeList = findViewById(R.id.tofuRecipesList);

        final ArrayList<String> tofuRecipeArrayList = new ArrayList<>();

        tofuRecipeArrayList.add("Marinated Tofu Recipe");
        tofuRecipeArrayList.add("Scrambled Tofu");

        ArrayAdapter tofuArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tofuRecipeArrayList);
        tofuRecipeList.setAdapter(tofuArrayAdapter);

        //determining which activity needs to be opened (which recipe)
        tofuRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    displayP1R1();
                }
                else if (position == 1){
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(TofuRecipes.this);

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

    public void displayP1R1() {
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
