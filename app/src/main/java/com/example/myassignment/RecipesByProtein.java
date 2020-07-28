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

public class RecipesByProtein extends AppCompatActivity {

    private Button btnTofu;
    private Button btnLegumes;
    private Button btnEggs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_by_protein);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        btnTofu = findViewById(R.id.btnTofuRecipes);
        btnLegumes = findViewById(R.id.btnChickpeaRecipes);
        btnEggs = findViewById(R.id.btnEggsRecipes);

        btnTofu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTofuRecipes();
            }
        });

        btnLegumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLegumesRecipes();
            }
        });

        btnEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEggsRecipes();
            }
        });

    }

    private void showEggsRecipes() {
        Intent showEggRecipesIntent = new Intent(this, EggRecipes.class);
        startActivity(showEggRecipesIntent);
    }

    private void showLegumesRecipes() {
        Intent showLegumesRecipesIntent = new Intent(this, ChickpeaRecipes.class);
        startActivity(showLegumesRecipesIntent);
    }

    private void showTofuRecipes() {
        Intent showTofuRecipesIntent = new Intent(this, TofuRecipes.class);
        startActivity(showTofuRecipesIntent);
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

