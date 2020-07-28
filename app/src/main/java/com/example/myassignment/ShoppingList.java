package com.example.myassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {

    public ListView ShoppingList;
    private Button mainMenu;

    public static ArrayList<String> shoppingListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);

        ShoppingList = findViewById(R.id.shoppingListListView);
        mainMenu = findViewById(R.id.btnMainMenu);

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.
                    simple_list_item_1, shoppingListArray);
         ShoppingList.setAdapter(arrayAdapter);

         ShoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 shoppingListArray.remove(position);
                 arrayAdapter.notifyDataSetChanged();
             }
         });

         mainMenu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent mainMenuIntent = new Intent(ShoppingList.this, MainMenu.class);
                 startActivity(mainMenuIntent);
             }
         });

    }//end of on create

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

            case R.id.action_shopping_list:
                Intent shoppingListIntent = new Intent(this, ShoppingList.class);
                startActivity(shoppingListIntent);
                break;

            case R.id.action_view_account:
                Intent viewAccountIntent = new Intent(this, MyAccount.class);
                startActivity(viewAccountIntent);
                break;

            default:
                //ignore the unknown error
        }
        return super.onOptionsItemSelected(item);
    }

}
