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

package pylapp.smoothclicker.android.uiautomator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
//import org.junit.Test;

import java.util.Collection;

import pylapp.smoothclicker.android.AbstractTest;
import pylapp.smoothclicker.android.R;
import pylapp.smoothclicker.android.views.ClickerActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class to use to make UI tests with UIAutomator of the IntroScreensActivity.
 *
 * <i> <b>Warning:</b> The intro screens activity is displayed only one time, so we should run these tests before all other tests cases </i>
 *
 * @author pylapp
 * @version 1.1.0
 * @since 22/03/2016
 * @see AbstractTest
 */
public class UIAutomatorTestIntroScreensActivity extends AbstractTest {


    /**
     * The UIDevice object is the primary way to access and manipulate the state of the device
     */
    private UiDevice mDevice;

    /**
     *
     */
    private static final int LAUNCH_TIMEOUT_MS = 5000;

    /**
     *
     */
    private static final String PACKAGE_APP_PATH = "pylapp.smoothclicker.android";


    /**
     * Starts the main activity to test from the home screen
     */
    @Before
    public void startMainActivityFromHomeScreen() {

        Context context = InstrumentationRegistry.getContext();

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT_MS);

        // Launch the app
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_APP_PATH);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_APP_PATH).depth(0)), LAUNCH_TIMEOUT_MS);

    }

    /**
     * Tests the left and right swipes on the intro screens
     *
     * <i>A swipe to the right on the first screen does nothing</i>
     * <i>A swipe to the left on the last screen does nothing</i>
     * <i>A swipe on the left on any screens except the first and the least makes the next screen appear</i>
     * <i>A swipe on the right on any screens except the first and the least makes the previous screen appear</i>
     */
    //@Test
    public void swipes(){

        l(this, "@Test swipes");

        Context context = InstrumentationRegistry.getTargetContext();

        try {

            UiObject viewPager = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/view_pager")
            );

            // Are we in the first screen ?
            testIfSlide1(context);

            // Swipe to the right
            viewPager.swipeRight(100);
            testIfSlide1(context);

            // Swipe to the left
            viewPager.swipeLeft(100);
            testIfSlide2(context);

            // Swipe to the left
            viewPager.swipeLeft(100);
            testIfSlide3(context);

            // Swipe to the left
            viewPager.swipeLeft(100);
            testIfSlide4(context);

            // Swipe to the left
            viewPager.swipeLeft(100);
            testIfSlide5(context);

            // Swipe to the left
            viewPager.swipeLeft(100);
            testIfSlide5(context);

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Tests the click on the skip button
     *
     * <i>A click on the skip button must finish the intro activity and make the clicker activity be displayed</i>
     */
    //@Test
    public void skip(){

        l(this, "@Test skip");

        Context context = InstrumentationRegistry.getTargetContext();

        try {

            UiObject skip = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );

            // Are we in the first screen ?
            testIfSlide1(context);

            // Skip
            skip.click();
            w(1000);

            assertEquals(ClickerActivity.class.getSimpleName(), getActivityInstance().getClass().getSimpleName());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test if the current slide is the slide 1
     */
    private void testIfSlide1( Context c ){
        try {
            UiObject title = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/title")
            );
            UiObject description = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/description")
            );
            UiObject next = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );
            assertEquals(c.getString(R.string.intro_welcome_label), title.getText());
            assertEquals(c.getString(R.string.intro_welcome_desc), description.getText());
            assertTrue(next.exists());
        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail(uonfe.getMessage() );
        }
    }

    /**
     * Test if the current slide is the slide 2
     */
    private void testIfSlide2( Context c ){
        try {
            UiObject title = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/title")
            );
            UiObject description = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/description")
            );
            UiObject next = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );
            assertEquals(c.getString(R.string.intro_root_label), title.getText());
            assertEquals(c.getString(R.string.intro_root_desc), description.getText());
            assertTrue(next.exists());
        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail(uonfe.getMessage() );
        }
    }

    /**
     * Test if the current slide is the slide 3
     */
    private void testIfSlide3( Context c ){
        try {
            UiObject title = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/title")
            );
            UiObject description = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/description")
            );
            UiObject next = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );
            assertEquals(c.getString(R.string.intro_clicks_label), title.getText());
            assertEquals(c.getString(R.string.intro_clicks_desc), description.getText());
            assertTrue(next.exists());
        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail(uonfe.getMessage() );
        }
    }

    /**
     * Test if the current slide is the slide 4
     */
    private void testIfSlide4( Context c ){
        try {
            UiObject title = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/title")
            );
            UiObject description = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/description")
            );
            UiObject next = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );
            assertEquals(c.getString(R.string.intro_notifications_label), title.getText());
            assertEquals(c.getString(R.string.intro_notifications_desc), description.getText());
            assertTrue(next.exists());
        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail(uonfe.getMessage() );
        }
    }

    /**
     * Test if the current slide is the slide 5
     */
    private void testIfSlide5( Context c ){
        try {
            UiObject title = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/title")
            );
            UiObject description = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/description")
            );
            UiObject next = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/skip")
            );
            assertEquals(c.getString(R.string.intro_free_label), title.getText());
            assertEquals(c.getString(R.string.intro_free_desc), description.getText());
            assertTrue(next.exists());
        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail(uonfe.getMessage() );
        }
    }

    private static Activity mResumedActivity;

    /**
     * Retrieves the on going activity
     * @return Activity - The current activity
     */
    private static Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    mResumedActivity = (Activity) resumedActivities.iterator().next();
                }
                if (resumedActivities.size() <= 0 ){
                    mResumedActivity = null;
                }
            }
        });
        return mResumedActivity;
    }

}
