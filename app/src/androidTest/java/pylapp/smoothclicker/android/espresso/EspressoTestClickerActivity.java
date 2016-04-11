/*
    MIT License

    Copyright (c) 2016  Pierre-Yves Lapersonne (Twitter: @pylapp, Mail: pylapp(dot)pylapp(at)gmail(dot)com)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */
// ✿✿✿✿ ʕ •ᴥ•ʔ/ ︻デ═一

package pylapp.smoothclicker.android.espresso;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;

import pylapp.smoothclicker.android.AbstractTest;
import pylapp.smoothclicker.android.R;
import pylapp.smoothclicker.android.utils.Config;
import pylapp.smoothclicker.android.views.ClickerActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class to use to make instrumented / unit tests with Espresso of the ClickerActivity
 *
 *  @author pylapp
 *  @version 1.1.0
 *  @since 23/03/2016
 *  @see AbstractTest
 */
public class EspressoTestClickerActivity extends AbstractTest {


    /**
     * Defines a rule for our tests : here the activity to test.
     * An Espresso object..
     */
    @Rule
    public ActivityTestRule<ClickerActivity> mActivityRule = new ActivityTestRule<>(ClickerActivity.class);

    /**
     *
     */
    @Before
    public void init(){
        l(this,"@Before init");
    }

    /**
     *
     */
    @After
    public void clean(){
        l(this, "@After clean");
    }

    /**
     * Tests clicks on the select points activity and if the list of points is well populated (in the clicker activity)
     *
     * <i>If a click is donne at coordinates X/Y on the dedicated activity, the main activity has to display the selected point in its spinner</i>
     */
    @Test
    public void selectPoints(){

        l(this, "@Test selectPoints");

        // Open the arc menu
        onView(withId(R.id.fabAction)).perform(click());
        w(1000);

        // Start the select multipoints activity
        onView(withId(R.id.fabSelectPoint)).perform(click());
        w(2000);

        int x = 500;
        int y  = 600;

        // Make two clicks on the screen
        onView(withId(R.id.translucentMainView)).perform(clickXY(x, y));
        w(2000);

        // Go back to the main activity
        pressBack();
        w(2000);

        onView(withId(R.id.clickerActivityMainLayout)).perform(swipeUp());
        w(1000);

        // Check the spinner containing all the selected points
        onView(withId(R.id.sPointsToClick)).perform(click());
      //  onView(withText("1 clicks")).perform(click());
        onView(withText("x = "+x+" / y = "+y)).check(matches(isDisplayed()));

    }

    /**
     * Custom ViewAction to click on dedicated coordinates
     * @param x -
     * @param y -
     * @return ViewAction -
     */
    private ViewAction clickXY( final int x, final int y ){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates( View view ){

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

}
