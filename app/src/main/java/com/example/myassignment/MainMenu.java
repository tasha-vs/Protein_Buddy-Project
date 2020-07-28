package com.example.myassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private Button btnProtein;
    private Button btnAllRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        btnProtein = findViewById(R.id.btnProteinRecipes);
        btnAllRecipes = findViewById(R.id.btnAllRecipes);

        btnProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipesByProtein();
            }
        });
        btnAllRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllRecipes();
            }
        });
    }//endof oncreate

    private void viewAllRecipes() {
        Intent allRecipesIntent = new Intent(this, RecipesListAll.class);
        startActivity(allRecipesIntent);
    }

    private void viewRecipesByProtein() {
        Intent proteinRecipesIntent = new Intent(this, RecipesByProtein.class);
        startActivity(proteinRecipesIntent);
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

