package com.example.myassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {

    public static final String TAG = "DbHandler";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "userdb";     //name of our schema
    private static final String TABLE_Users = "userdetails";    //name of our table

    //name of our 3 columns
    private static final String KEY_ID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating our table and columns
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: user table created");

    }//end of onCreate()

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {

        //deleting older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        Log.d(TAG, "onUpgrade: dropping table if an older one exists");

        //creating new table
        onCreate(db);
        Log.d(TAG, "onUpgrade: creating new table");

    }//end of onUpgrade()


    //inserting our user details
    public void insertUserDetails( String username, String password){

        SQLiteDatabase db = this.getWritableDatabase();     //getting the data repository in write mode

        //creating a new map of the values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_USERNAME, username);
        cValues.put(KEY_PASSWORD, password);

        //inserting the new row, returning the primary key values of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        Log.d(TAG, "insertUserDetails: inserting new user details");
        db.close();

    }

    public ArrayList<HashMap<String, String>> getUsers(){

        SQLiteDatabase db = this.getWritableDatabase();

        //creating an array list that contains hashmap for the return type
        ArrayList<HashMap<String, String>> userAccountList = new ArrayList<>();

        //creating query selecting columns from our user table
        String query = "SELECT userid, username, password FROM "+ TABLE_Users;

        //passing the rawquery result to a cursor instance
        Cursor myCursor = db.rawQuery(query, null);
        Log.d(TAG, "getUsers: starting to add users to array");

        //to read everything
        while (myCursor.moveToNext()){
            HashMap<String, String> user = new HashMap<>();
            user.put("userid", myCursor.getString(myCursor.getColumnIndex((KEY_ID))));
            user.put("username", myCursor.getString(myCursor.getColumnIndex(KEY_USERNAME)));
            user.put("password", myCursor.getString(myCursor.getColumnIndex(KEY_PASSWORD)));
            userAccountList.add(user);
        }
        Log.d(TAG, "getUsers: all users added to array");

        return userAccountList;

    }

    //deleting all useraccounts (used for testing)
    public void deleteAllAccounts() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_Users);
        Log.d(TAG, "deleteAllAccounts: all user accounts deleted from table");

    }

    //updating our account username
    public int updateUserAccountUsername( String username, String id ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);

        Log.d(TAG, "updateUserAccountUsername: updating username");
        int count = db.update(TABLE_Users, contentValues, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        Log.d(TAG, "updateUserAccountUsername: username updated");

        db.close();     //added this to resolve the issue for the update
                        //did not fix the issue

        return count;

    }

    //updating our account password
    public int updateUserAccountPassword( String password, String id ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PASSWORD, password);
        Log.d(TAG, "updateUserAccountUsername: updating username");

        int count = db.update(TABLE_Users, contentValues, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        Log.d(TAG, "updateUserAccountUsername: username updated");
        db.close();

        return count;

    }

    //deleting our useraccount
    public void deleteUser(String userId){

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteUser: deleting user");
        db.delete(TABLE_Users, KEY_ID+" = ?", new String[]{String.valueOf(userId)});
        Log.d(TAG, "deleteUser: user deleted");
        db.close();

    }
}
