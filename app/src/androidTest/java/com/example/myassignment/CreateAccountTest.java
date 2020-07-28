package com.example.myassignment;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import java.util.Random;

public class CreateAccountTest {

    @Rule
    public ActivityTestRule<CreateAccount> activityTestRule = new ActivityTestRule<>(CreateAccount.class);
    private CreateAccount createAccount = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createAccountNoUsername(){
        String password = "password";

        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Please make sure all data has been entered."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createAccountNoPassword(){
        String accountName = "newaccountname";

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(accountName));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Please make sure all data has been entered."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createAccountShortPassword(){

        Random rand = new Random();
        String password = "123";
        int userName = rand.nextInt(100);

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(String.valueOf(userName)));
        Espresso.closeSoftKeyboard();
        userName += 1;

        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("Your password must contain at least 6 digits."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createAccountExistingUsername(){
        String accountName = "admin";
        String password = "password";

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(accountName));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());
        Espresso.onView(withText("This username already exists" + "\n" + "Please enter a new one."))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void createAccount(){

        Random rand = new Random();
        String password = "password";
        int userName = rand.nextInt(100);

        Espresso.onView(withId(R.id.editTextUsername)).perform(typeText(String.valueOf(userName)));
        Espresso.closeSoftKeyboard();
        userName += 1;

        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnCreateUserAccount)).perform(click());

        Espresso.onView(withText("Your account has been successfully created.")).inRoot(isDialog())
        .check(matches(isDisplayed()));

        Espresso.onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed())).perform(click());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        assertNotNull(mainActivity);
        mainActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
    }
}