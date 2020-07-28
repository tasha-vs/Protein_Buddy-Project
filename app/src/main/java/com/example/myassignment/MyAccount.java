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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAccount extends AppCompatActivity {

    private TextView accountUsername;
    private EditText editUsername;
    private Button btnEditUsername;
    private Button btnUpdateUsername;

    private TextView accountPassword;
    private EditText editPassword;
    private Button btnEditPassword;
    private Button btnUpdatePassword;
    private Button btnDeleteAccount;
    private Button btnMainMenu;
    //the below variable is used so i am not quite sure why it says it is not?
    private String accountName;

    boolean usernameDuplicate = false;
    boolean updatedUsername = false;

    DbHandler db;
    MainActivity mainActivity;
    ArrayList<HashMap<String, String>> userList;
    String userID = "";

    static String newUsernameTxt;
    static String newPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        db = new DbHandler(MyAccount.this);
        mainActivity = new MainActivity();

        Toolbar myToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);


        accountUsername = findViewById(R.id.userAccountUsername);
        editUsername = findViewById(R.id.editTextUsername);
        btnEditUsername = findViewById(R.id.btnEditUsername);
        btnUpdateUsername = findViewById(R.id.btnUpdateUsername);

        accountPassword = findViewById(R.id.userAccountPassword);
        editPassword = findViewById(R.id.editTextPassword);
        btnEditPassword = findViewById(R.id.btnEditPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);

        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnMainMenu = findViewById(R.id.btnMainMenu);

        getUserInfo();

        //grabbing our current user id
        userList = db.getUsers();
        for (HashMap<String, String> user : userList) {
            if (user.get("username").equals(accountUsername.getText().toString())) {
                //grabbing our user id to identify the correct account we want to update
                userID = user.get("userid");
            }
        }

        btnEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!editUsername.getText().toString().equals("")){
                editUsername.setText("");
            }

            editUsername.setVisibility(View.VISIBLE);
            btnUpdateUsername.setVisibility(View.VISIBLE);
            }
        });

        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserName(editUsername.getText().toString());
            }
        });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassword.setVisibility(View.VISIBLE);
                btnUpdatePassword.setVisibility(View.VISIBLE);
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePassword(editPassword.getText().toString());
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(MyAccount.this, MainMenu.class);
                startActivity(mainMenuIntent);
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);

                myBuilder.setCancelable(true);
                myBuilder.setTitle("Delete Account");
                myBuilder.setMessage("Are you sure you want to delete your account?" + "\n"
                        + "Once an account has been deleted, it can never be accessed again.");

                myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUser(userID);
                    }

                });

                myBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                myBuilder.show();
            }
        });
    }

    private void getUserInfo(){

        if (!updatedUsername) {

            accountUsername.setText(mainActivity.accountUsername);
            accountName = accountUsername.getText().toString();

        } else {
            accountUsername.setText(newUsernameTxt);
        }

        accountPassword.setText(mainActivity.accountPassword);

    }

    private void validateUserName(String newUsername) {

        if(editUsername.getText().toString().equals(accountUsername.getText().toString())) {

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);
            myBuilder.setCancelable(false);
            myBuilder.setTitle("Invalid Username");
            myBuilder.setMessage("Please enter a different Username.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            myBuilder.show();

        } else if(newUsername.equals("")){

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);
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

        } else {

            updateUsername(newUsername);

        }
    }

    private void updateUsername(String newUsername) {

        //refreshing our userlist
        userList = db.getUsers();
        for (HashMap<String, String> user : userList) {
            //checking to see if the user is trying to update their account name to one that already exists
            if (user.get("username").equals(editUsername.getText().toString())) {

                usernameDuplicate = true;

            }
        }

        if (!usernameDuplicate){

            db.updateUserAccountUsername(newUsername, userID);

            newUsernameTxt = newUsername;
            accountName = newUsername;

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);
            myBuilder.setCancelable(false);
            myBuilder.setTitle("Account Created");
            myBuilder.setMessage("Your account has been successfully updated.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editUsername.setVisibility(View.INVISIBLE);
                    btnUpdateUsername.setVisibility(View.INVISIBLE);
                    dialogInterface.cancel();
                }
            });

            accountUsername.setText(newUsername);
            myBuilder.show();

        }
        else {

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);
            myBuilder.setCancelable(false);
            myBuilder.setTitle("Invalid Entry");
            myBuilder.setMessage("This username is already in use, please enter a new one.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            myBuilder.show();
            usernameDuplicate = false;

        }
    }

    private void validatePassword(String newPassword) {

        if(newPassword.equals("")){
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);
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
        else if (newPassword.length() < 6) {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);

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
            editPassword.setText("");
        }
        else if (newPassword.equals(accountPassword.getText())){
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);

            myBuilder.setCancelable(false);
            myBuilder.setTitle("Invalid Password");
            myBuilder.setMessage("Please enter a different password.");

            myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            myBuilder.show();
        }
        else{

            updatePassword(newPassword);

        }
    }

    private void updatePassword(String newPassword) {

        db.updateUserAccountPassword(newPassword, userID);

        newPasswordTxt = newPassword;

        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(MyAccount.this);

        myBuilder.setCancelable(true);
        myBuilder.setTitle("Account Updated");
        myBuilder.setMessage("Your account has been successfully updated.");

        myBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        accountPassword.setText(newPassword);
        myBuilder.show();

        editPassword.setVisibility(View.INVISIBLE);
        btnUpdatePassword.setVisibility(View.INVISIBLE);
        editPassword.setText("");

    }

    private void deleteUser(String userID) {

        db.deleteUser(userID);
        Intent mainActivityIntent = new Intent(MyAccount.this, MainActivity.class);
        startActivity(mainActivityIntent);

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
