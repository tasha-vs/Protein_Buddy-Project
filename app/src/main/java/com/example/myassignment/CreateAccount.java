package com.example.myassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnCreateAccount;
    private Button btnBack;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = new DbHandler(CreateAccount.this);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        btnCreateAccount = findViewById(R.id.btnCreateUserAccount);
        btnBack = findViewById(R.id.btnBack);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    private void createNewAccount() {
        String usernameField = username.getText().toString();
        String passwordField = password.getText().toString();

        ArrayList<HashMap<String, String>> userList;
        userList = db.getUsers();

        boolean usernameDuplicate = false;
        for (HashMap<String, String> user : userList) {     //looping through each user in our user list
            if (user.get("username").equals(usernameField)) {
                usernameDuplicate = true;
            }
        }

        if (usernameDuplicate) {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(CreateAccount.this);
            myBuilder.setCancelable(false);
            myBuilder.setTitle("Invalid Username");
            myBuilder.setMessage("This username already exists" + "\n" + "Please enter a new one.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            myBuilder.show();
            username.setText("");
            usernameDuplicate = false;

        }
        else if (usernameField.equals("") || passwordField.equals("")){

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(CreateAccount.this);
            myBuilder.setCancelable(false);
            myBuilder.setTitle("Invalid Entry");
            myBuilder.setMessage("Please make sure all data has been entered.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            myBuilder.show();
            }
            else if (passwordField.length() < 6){
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(CreateAccount.this);

                myBuilder.setCancelable(false);
                myBuilder.setTitle("Invalid Password");
                myBuilder.setMessage("Your password must contain at least 6 digits.");

                myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                myBuilder.show();
                password.setText("");
                }
                else {
                    DbHandler dbHandler = new DbHandler(CreateAccount.this);
                    dbHandler.insertUserDetails(usernameField, passwordField);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(CreateAccount.this);

                    myBuilder.setCancelable(true);
                    myBuilder.setTitle("Account Created");
                    myBuilder.setMessage("Your account has been successfully created.");

                    myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent backIntent = new Intent(CreateAccount.this, MainActivity.class);
                            startActivity(backIntent);
                        }
                    });
                    myBuilder.show();

                    username.setText("");
                    password.setText("");
                }
    }
}
