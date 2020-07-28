package com.example.myassignment;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.KeyEvent;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;
    Instrumentation.ActivityMonitor mainActivityMonitor = getInstrumentation().addMonitor(MainMenu.class.getName(), null, false);
    Instrumentation.ActivityMonitor createAccountMonitor = getInstrumentation().addMonitor(CreateAccount.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        mainActivity = activityTestRule.getActivity();
    }

    @Test
    public void testVerifyLoginWithoutUserName(){
        String password = "password";

        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("Please fill out all data entries"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testVerifyLoginWithoutPassword(){
        String username = "username";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("Please fill out all data entries"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testVerifyLoginWithNonExistingAccount(){
        String username = "username";
        String password = "password";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(username));
        //must always close keyboard after performing typing action
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("This account doesn't exist"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testVerifyLoginWithExistingAccount(){
        String username = "admin";
        String password = "password";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        assertNotNull(mainActivity.findViewById(R.id.btnLogin));
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        Activity mainMenuActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000);
        assertNotNull(mainMenuActivity);
        mainMenuActivity.finish();
    }

    @Test
    public void endToEndTestPart1(){

        //!!!!!!!!!!!!!CREATE ACCOUNT!!!!!!!!!!!!!!//
        Espresso.onView(withId(R.id.btnCreateAccount)).perform(click());
        Activity createAccountActivity = getInstrumentation().waitForMonitorWithTimeout(createAccountMonitor, 5000);
        assertNotNull(createAccountActivity);

        //input only a password
        String password = "password";
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Please make sure all data has been entered."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).perform(click());

        //remove password
        Espresso.onView(withId(R.id.editTextPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //input only a username
        String accountName = "123456789";
        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(accountName));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Please make sure all data has been entered."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).perform(click());

        //removing username
        Espresso.onView(withId(R.id.editTextUsername)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();


        //entering account name in use
        //note for this to test successfully, an admin account must exist.
        String accountNameInUse = "admin";
        String password2 = "password";

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(accountNameInUse));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("This username already exists" + "\n" + "Please enter a new one."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).perform(click());

        //removing username
        Espresso.onView(withId(R.id.editTextUsername)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //removing password
        Espresso.onView(withId(R.id.editTextPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();


        //input short password
        String username2 = "username2";
        String shortPassword = "123";
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(shortPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(username2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Your password must contain at least 6 digits."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).perform(click());


        //succesfully create account
        String fullpassword = "123456";
        String username3 = "username8";

        //removing password
        Espresso.onView(withId(R.id.editTextPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //removing username
        Espresso.onView(withId(R.id.editTextUsername)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(fullpassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(username3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Your account has been successfully created.")).inRoot(isDialog())
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed())).perform(click());


        //!!!!!!!!!!!!!LOGIN TO ACCOUNT!!!!!!!!!!!!!!//

        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("Please fill out all data entries"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        //entering only password
        String password3 = "123456";

        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(password3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("Please fill out all data entries"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        //deleting password
        // for some reason when it clicks on password, it doesnt click on the end like all the rest?
        //and it only deleted 3 digits not all 6 of them..
        //therefore when i check if it matches with "", it recieves an error??????
        //this successfully deletes all of them except 1 digit (the last digit #6) however the
        // cursor gets put in front of the 6 and not behind it, so it doesnt get deleted.
        // i cannot move on for this end to end test unless this gets fixed!
        //I will comment this out as i need to move on and do not know why this error is happening..
        //after commenting this out the following section of code has the same error :(
        Espresso.onView(withId(R.id.txtboxUsername)).perform(click());
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL));
        Espresso.onView(withId(R.id.txtboxUsername)).perform(click());
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL)).check(matches(withText("")));
        Espresso.closeSoftKeyboard();


        //entering only username
        String username4 = "username4";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(username4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("Please fill out all data entries"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        //deleting username
        Espresso.onView(withId(R.id.txtboxUsername)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //entering an account that doesn't exist

        String nonexistantUsername = "nonexistantusername";
        String nonexistantPassword = "nonexistantpassword";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(nonexistantUsername));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(nonexistantPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());
        Espresso.onView(withText("This account doesn't exist"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Espresso.onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed())).perform(click());

        //deleting username
        Espresso.onView(withId(R.id.txtboxUsername)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //deleting password
        Espresso.onView(withId(R.id.txtboxPassword)).perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));
        Espresso.closeSoftKeyboard();

        //successful login

        String existingUsername = "admin";
        String existingPassword = "123456";

        Espresso.onView(withId(R.id.txtboxUsername)).perform(typeText(existingUsername));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.txtboxPassword)).perform(typeText(existingPassword));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnLogin)).perform(click());

        Activity mainMenuActivity2 = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000);
        assertNotNull(mainMenuActivity2);

    }

    @After
    public void tearDown() throws Exception {
    }
}