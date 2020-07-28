package com.example.myassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private TextView loginAttempts;
    private TextView lockedOut;
    private Button loginButton;
    private Button createAccount;
    private int attemptsCounter = 3;

    public static String accountUsername;
    public static String accountPassword;

    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHandler(MainActivity.this);

        userName = (findViewById(R.id.txtboxUsername));
        userPassword = (findViewById(R.id.txtboxPassword));
        loginAttempts = (findViewById(R.id.txtAttemptsCount));
        loginButton = (findViewById(R.id.btnLogin));
        createAccount = (findViewById(R.id.btnCreateAccount));
        lockedOut = (findViewById(R.id.txtLockedOut));

        //setting these to INVISIBLE as we only want it to show IF incorrect input is entered or
        // user gets locked out
        loginAttempts.setVisibility(View.INVISIBLE);
        lockedOut.setVisibility(View.INVISIBLE);

        loginAttempts.setText("Incorrect attempts remaining: 5");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling verifylogin function with users entered
                //username and password
                verifyLogin(userName.getText().toString(), userPassword.getText().toString());
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(createAccountIntent);
            }
        });

    }//end of oncreate

    private void verifyLogin(String username, String password) {

        //grabbing our users
        ArrayList<HashMap<String, String>> userList;
        userList = db.getUsers();

        boolean usernameExists = false;

        if (username.equals("") || (password.equals(""))){
            Toast.makeText(getApplicationContext(), "Please fill out all data entries", Toast.LENGTH_SHORT).show();
        }
        else {
            for (HashMap<String, String> user : userList) {

                if (user.get("username").equals(username)) {
                    usernameExists = true;

                    accountUsername = username;

                    if (user.get("password").equals(password)) {

                        accountPassword = password;

                        Intent mainIntent = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(mainIntent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Password ", Toast.LENGTH_SHORT).show();
                        loginAttempts.setVisibility(View.VISIBLE);
                        attemptsCounter--;
                        //note: have to use the EXACT same wording within the "" as we have in our txtbox in the xml file
                        loginAttempts.setText("Incorrect attempts remaining: " + String.valueOf(attemptsCounter));

                        //if more than 3 unsuccessful login attempts, login button is not available
                        if (attemptsCounter == 0) {
                            loginAttempts.setVisibility(View.INVISIBLE);
                            loginButton.setEnabled(false);
                            lockedOut.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }//end of for loop

            if (!usernameExists) {
                Toast.makeText(getApplicationContext(), "This account doesn't exist", Toast.LENGTH_SHORT).show(); //alerting user that the account does not exist
            }

        }//end of else statement
    }
}
