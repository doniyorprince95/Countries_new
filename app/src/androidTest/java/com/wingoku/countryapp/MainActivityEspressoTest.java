package com.wingoku.countryapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();

        // register IdlingResource used by MainActivity.java to enable syncing with Espresso for hand-made threads. For more info regarding
        // idling resource: https://developer.android.com/reference/android/support/test/espresso/IdlingResource.html
        Espresso.registerIdlingResources(mainActivityIdlingResource);

        String stringToFind = "Switzerland";
        countryDataFetchingTest(stringToFind);

        // perform back press event
        Espresso.pressBack();
    }

    // this method will test the data fetching capability of the app for specific country. Internet is required for running this test
    private void countryDataFetchingTest(String stringToFind) {
        //accessing recyclerView with id = R.id.list_countries
        onView(withId(R.id.list_countries))
                // asking espresso to find a recyclerview child View that has string = stringToFind which in this case is Sweden
                // and then perform click event on that child view
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(stringToFind)), click()));

        // asking espresso to find TextView in Toolbar and match it with the stringToFind
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(stringToFind)));
    }
}
