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

package com.pylapp.smoothclicker.uiautomator;

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

import com.pylapp.smoothclicker.AbstractTest;
import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.views.ClickerActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class to use to make UI tests with UIAutomator of the ClickerActivity.
 *
 * @author pylapp
 * @version 1.0.0
 * @since 22/03/2016
 * @see AbstractTest
 */
public class UIAutomatorTestClickerActivity extends AbstractTest {


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
    private static final int WAIT_FOR_EXISTS_TIMEOUT = 5000;
    /**
     *
     */
    private static final String PACKAGE_APP_PATH = "com.pylapp.smoothclicker";


    /**
     * Starts the main activity to test from the home screen
     */
    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT_MS);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_APP_PATH);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_APP_PATH).depth(0)), LAUNCH_TIMEOUT_MS);

    }

    /**
     * Tests the long clicks on the floating action button for start in the arc menu
     *
     * <i>A long click on the button to use to start the process should display a snackbar with an explain message</i>
     */
    @Test
    public void longClickOnArcMenuStartItem(){

        l(this, "@Test longClickOnArcMenuStartItem");

        String expectedText = InstrumentationRegistry.getTargetContext().getString(R.string.info_message_start);

        try {

            /*
             * Display the floating action buttons in the arc menu
             */
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();
            arcMenu.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);

            /*
             * The floating action button
             */
            UiObject fab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH+":id/fabStart")
            );
            fab.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);
            assertTrue(fab.isLongClickable());
            fab.swipeLeft(100); //fab.longClick() makes clicks sometimes, so swipeLeft() is a trick to make always a longclick
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            assertEquals(expectedText, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Tests the long clicks on the floating action button for stop in the arc menu
     *
     * <i>A long click on the button to use to stop the process should display a snackbar with an explain message</i>
     */
    @Test
    public void longClickOnArcMenuStopItem(){

        l(this, "@Test longClickOnArcMenuStopItem");

        String expectedString = InstrumentationRegistry.getTargetContext().getString(R.string.info_message_stop);

        try {

            /*
             * Display the floating action buttons in the arc menu
             */
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();
            arcMenu.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);

            /*
             * The floating action button
             */
            UiObject fab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH+":id/fabStop")
            );
            fab.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);
            assertTrue(fab.isLongClickable());
            fab.swipeLeft(100); //fab.longClick() makes clicks sometimes, so swipeLeft() is a trick to make always a longclick
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            assertEquals(expectedString, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Tests the long clicks on the floating action button for SU grant in the arc menu
     *
     * <i>A long click on the button to use to get the SU grant should display a snackbar with an explain message</i>
     */
    @Test
    public void longClickOnArcMenuSuGrantItem(){

        l(this, "@Test longClickOnArcMenuSuGrantItem");

        String expectedString = InstrumentationRegistry.getTargetContext().getString(R.string.info_message_request_su);

        try {

            /*
             * Display the floating action buttons in the arc menu
             */
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();
            arcMenu.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);

            /*
             * The floating action button
             */
            UiObject fab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH+":id/fabRequestSuGrant")
            );
            fab.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);
            assertTrue(fab.isLongClickable());
            fab.swipeLeft(100); //fab.longClick() makes clicks sometimes, so swipeLeft() is a trick to make always a longclick
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            assertEquals(expectedString, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Tests the long clicks on the floating action button for new point in the arc menu
     *
     * <i>A long click on the button to use to add points to click on should display a snackbar with an explain message</i>
     */
    @Test
    public void longClickOnArcMenuNewPointItem(){

        l(this, "@Test longClickOnArcMenuNewPointItem");

        String expectedString = InstrumentationRegistry.getTargetContext().getString(R.string.info_message_new_point);
        try {

            /*
             * Display the floating action buttons in the arc menu
             */
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();
            arcMenu.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);

            /*
             * The floating action button
             */
            UiObject fab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH+":id/fabSelectPoint")
            );
            fab.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);
            assertTrue(fab.isLongClickable());
            fab.swipeUp(100); //fab.longClick() makes clicks sometimes, so swipeUp() is a trick to make always a longclick
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            assertEquals(expectedString, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test if the switch changes (for the start type) modifies well the delay field
     *
     * <i>If the switch for the start type is ON, the delay field is enabled.</i>
     * <i>If the switch for the start type is OFF, the delay field is disabled.</i>
     */
    @Test
    public void changeDelayOnSwitchChanges(){

        l(this, "@Test changeDelayOnSwitchChanges");

        try {

            UiObject startSwitch = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/sTypeOfStartDelayed")
            );

            UiObject delayField = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/etDelay")
            );

            // Check and uncheck the switch
            for ( int i = 1; i <= 2; i++ ) {
                startSwitch.click();
                if (startSwitch.isChecked()) {
                    assertTrue(delayField.isEnabled());
                } else {
                    assertFalse(delayField.isEnabled());
                }
            }

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test if the endless repeat checkbox changes modifies well the repeat field
     *
     * <i>If the check box for the endless repeat is checked, the repeat field is disabled</i>
     * <i>If the check box for the endless repeat is unchecked, the repeat field is enabled</i>
     */
    @Test
    public void changeRepeatOnCheckboxChanges(){

        l(this, "@Test changeRepeatOnCheckboxChanges");

        try {

            UiObject endlessCheckbox = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/cbEndlessRepeat")
            );

            UiObject repeatField = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/etRepeat")
            );

            // Check and uncheck the switch
            for ( int i = 1; i <= 2; i++ ) {
                endlessCheckbox.click();
                if (endlessCheckbox.isChecked()) {
                    assertFalse(repeatField.isEnabled());
                } else {
                    assertTrue(repeatField.isEnabled());
                }
            }

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the click on the SU grant button
     *
     * <i>If the button to get the SU grant is clicked, a notification about the good access to SU grant is displayed</i>
     */
    @Test
    public void clickOnSuGrantButton(){

        l(this, "@Test clickOnSuGrantButton");

        try {

            // Open the arc menu
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();

            // Request the SU grant
            UiObject suGrantFab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabRequestSuGrant")
            );
            suGrantFab.click();

            // Check the notifications panel
            UiObject notification = mDevice.findObject(
                    new UiSelector()
                            .resourceId("android:id/text")
                            .className("android.widget.TextView")
                            .packageName("com.android.settings")
                            .textContains("Droits Super-utilisateur accordés à Smooth Clicker")); // WARNING FIXME French language, get the string in system R

            mDevice.openNotification();
            notification.waitForExists(2000);
            assertTrue(notification.exists());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the start button without having defined clicks
     *
     * <i>If the button to start the click process is clicked, and no point ahs been defined, a snackbar with an error message  have to be displayed</i>
     */
    @Test
    public void startButtonWithoutDefinedPoints(){

        l(this, "@Test startButtonWithoutDefinedPoints");

        try {

            // Open the arc menu
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();

            // Click on the button
            UiObject startFab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabStart")
            );
            startFab.click();

            // Check the snackbar
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            String expectedString = InstrumentationRegistry.getTargetContext().getString(R.string.error_message_no_click_defined);
            assertEquals(expectedString, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the stop button without having started the process
     *
     * <i>If the button to stop the click process is clicked, and no process was working, a snackbar with an error message ahs to be displayed/i>
     */
    @Test
    public void stopButtonWithoutStartedProcess(){

        l(this, "@Test stopButtonWithoutStartedProcess");

        try {

            // Open the arc menu
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();

            // Click on the button
            UiObject stopFab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabStop")
            );
            stopFab.click();

            // Check the snackbar
            UiObject snack = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/snackbar_text")
            );

            String expectedString = InstrumentationRegistry.getTargetContext().getString(R.string.error_message_was_not_working);
            assertEquals(expectedString, snack.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the button for selecting points
     *
     * <i>If the button to select points is clicked, the activity to select points have to be launched</i>
     * <i>If in this activity we click on back, the main activity  must be displayed and the previous activity finished</i>
     */
    @Test
    public void selectPointsButton(){

        l(this, "@Test selectPointsButton");

        try {

            // Open the arc menu
            UiObject arcMenu = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabAction")
            );
            arcMenu.click();

            // Click on the button
            UiObject selectPointsFab = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/fabSelectPoint")
            );
            selectPointsFab.click();

            // UIAutomator seems to be useless about getting the current activity (deprecated methods)
            // so check if the enw activity's layout is displayed
            UiObject newActivityMainLayout = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/translucentMainView")
            );
            w(2000);
            assertTrue(newActivityMainLayout.exists());

            // Press back and check if we are in the previous activity (i.e. arc menu displayed)
            mDevice.pressBack();
            w(2000);
            assertTrue(arcMenu.exists());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Tests if the back button displays the exit-pop-up
     * <i>If the back button is pressed on the main activity, an exit dialog is displayed</i>
     * <i>If we click on the exit dialog's cancel button, we stay at this activity</i>
     * <i>If we click on the exit dialog's ok button, we exit the app</i>
     */
    @Test
    public void exitDialog(){

        l(this, "@Test exitDialog");

        try {

            // Wait for the main activity
            UiObject mainActivity = mDevice.findObject(
                    new UiSelector()
                            .resourceId("com.pylapp.smoothclicker:id/clickerActivityMainLayout")
                            .packageName(PACKAGE_APP_PATH)
                            .className("android.widget.ScrollView")
            );
            mainActivity.waitForExists(WAIT_FOR_EXISTS_TIMEOUT*2);

            // Back
            mDevice.pressBack();

            // Get the dialog
            String s = InstrumentationRegistry.getTargetContext().getString(R.string.message_confirm_exit_label);
            UiObject exitDialog = mDevice.findObject(
                    new UiSelector()
                            .resourceId("android:id/alertTitle")
                            .packageName(PACKAGE_APP_PATH)
                            .className("android.widget.TextView")
                            .text(s)
            );
            exitDialog.waitForExists(WAIT_FOR_EXISTS_TIMEOUT);
            w(2000);

            // Click on the cancel button
            UiObject cancelButton = mDevice.findObject(
                    new UiSelector()
                            .resourceId("android:id/button2")
                            .packageName(PACKAGE_APP_PATH)
                            .className("android.widget.Button")
            );
            cancelButton.click();
            w(2000);

            // We have to stay in the main activity
            assertFalse(exitDialog.exists());
            assertEquals(ClickerActivity.class.getSimpleName(), getActivityInstance().getClass().getSimpleName());

            // Back
            mDevice.pressBack();

            // Click on the OK button
            UiObject okButton = mDevice.findObject(
                    new UiSelector()
                            .resourceId("android:id/button1")
                            .packageName(PACKAGE_APP_PATH)
                            .className("android.widget.Button")
            );
            okButton.click();
            w(2000);

            // We have to quit the app
            assertFalse(exitDialog.exists());
            assertTrue(getActivityInstance() == null );
//            assertNotEquals(ClickerActivity.class.getSimpleName(), getActivityInstance().getClass().getSimpleName());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
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
