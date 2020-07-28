package com.example.myassignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.myassignment.ShoppingList.shoppingListArray;

public class TofuIngredientsDialog extends DialogFragment {

    private TextView actionOK;
    public ListView displayIngredients;
    public ArrayList<String> recipeIngredientsList;

    TofuRecipe1 tofuRecipe;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_tofu_ingredients_dialog, container, false);

        tofuRecipe = new TofuRecipe1();
        displayIngredients = view.findViewById(R.id.ingredientsListDialog);
        actionOK = view.findViewById(R.id.action_ok);

        recipeIngredientsList = tofuRecipe.createIngredientsList();

        setAdapterDisplayIngredients(recipeIngredientsList);

        //when a user clicks an item on the list
        displayIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shoppingListArray.add(recipeIngredientsList.get(position));
            }
        });

        actionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                //add a notification that alerts user shopping list has been updated
            }
        });

        return view;

    }

    public void setAdapterDisplayIngredients(ArrayList<String> arrayList) {

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.
                simple_list_item_1, arrayList);
        displayIngredients.setAdapter(arrayAdapter);

    }

}