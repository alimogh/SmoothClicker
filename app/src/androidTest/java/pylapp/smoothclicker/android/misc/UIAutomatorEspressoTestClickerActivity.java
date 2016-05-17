package pylapp.smoothclicker.android.misc;

import android.content.Context;
import android.content.Intent;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import android.view.View;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import pylapp.smoothclicker.android.AbstractTest;
import pylapp.smoothclicker.android.R;
import pylapp.smoothclicker.android.utils.Config;

import static android.support.test.InstrumentationRegistry.getInstrumentation;


/**
 * Class to use to make UI tests with UIAutomator and Espresso of the ClickerActivity.
 *
 * @author pylapp
 * @version 1.1.1
 * @since 11/04/2016
 * @see AbstractTest
 */
public class UIAutomatorEspressoTestClickerActivity extends AbstractTest {


    /**
     * The UIDevice object is the primary way to access and manipulate the state of the device
     */
    private UiDevice mDevice;

    /**
     *
     */
    private static final int MOCK_POINT_X = 500;
    /**
     *
     */
    private static final int MOCK_POINT_Y = 600;

    /**
     *
     */
    private static final int LAUNCH_TIMEOUT_MS = 5000;
    /**
     *
     */
    private static final String PACKAGE_APP_PATH = "pylapp.smoothclicker.android";


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
     * Test the click on the button for the "clean all" feature
     *
     * <i>If the button to clean all is clicked, the configuration / values ion the fields have to be the default values, and the list of points has to be cleaned</i>
     * <i>If the button to clean all is clicked and no values has been changed, the values still remain the default ones</i>
     */
    @Test
    public void clickCleanAll(){

        l(this, "@Test clickCleanAll");

        try {

            // Get the menu item
            UiObject mi = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/action_clean_all")
            );

            // Add some values
            UiObject delayField = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/etDelay")
            );
            delayField.setText("007");

            Espresso.closeSoftKeyboard();

            w(1000);
            UiObject repeatField = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/etRepeat")
            );
            repeatField.setText("42");
            Espresso.closeSoftKeyboard();
            w(1000);
            UiObject timeGapField = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/etTimeBeforeEachClick")
            );
            timeGapField.setText("123");
            Espresso.closeSoftKeyboard();
            w(1000);
            UiObject endless = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/cbEndlessRepeat")
            );
            endless.click();
            w(1000);
            UiObject vibrateOnStart = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/cbVibrateOnStart")
            );
            vibrateOnStart.click();
            w(1000);
            UiObject vibrateOnClick = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/cbVibrateOnClick")
            );
            vibrateOnClick.click();
            w(1000);
            UiObject notifications = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/cbNotifOnClick")
            );
            notifications.click();
            w(1000);

            fillSpinnerAsUser();

            // Click on the menu item
            mi.click();

            // Check if the values have been made empty
            assertEquals(Config.DEFAULT_DELAY, delayField.getText());
            assertEquals(Config.DEFAULT_TIME_GAP, timeGapField.getText());
            assertEquals(Config.DEFAULT_REPEAT, repeatField.getText());
            assertEquals(Config.DEFAULT_REPEAT_ENDLESS, endless.isChecked());
            assertEquals(Config.DEFAULT_VIBRATE_ON_START, vibrateOnStart.isChecked());
            assertEquals(Config.DEFAULT_VIBRATE_ON_CLICK, vibrateOnClick.isChecked());
            assertEquals(Config.DEFAULT_NOTIF_ON_CLICK, notifications.isChecked());
//            onView(withId(R.id.clickerActivityMainLayout)).perform(swipeUp());
//            onView(withId(R.id.sPointsToClick)).perform(click());
//            onView(withId(R.id.sPointsToClick)).check(ViewAssertions.matches(withListSize(1)));

            // Test again to check if the default values remain
            mi.click();
            assertEquals(Config.DEFAULT_DELAY, delayField.getText());
            assertEquals(Config.DEFAULT_TIME_GAP, timeGapField.getText());
            assertEquals(Config.DEFAULT_REPEAT, repeatField.getText());
            assertEquals(Config.DEFAULT_REPEAT_ENDLESS, endless.isChecked());
            assertEquals(Config.DEFAULT_VIBRATE_ON_START, vibrateOnStart.isChecked());
            assertEquals(Config.DEFAULT_VIBRATE_ON_CLICK, vibrateOnClick.isChecked());
            assertEquals(Config.DEFAULT_NOTIF_ON_CLICK, notifications.isChecked());
//            onView(withId(R.id.sPointsToClick)).check(ViewAssertions.matches(withListSize(1)));

        } catch ( Exception e ){
            e.printStackTrace();
            fail( e.getMessage() );
        }

    }

    /**
     * Test the click on the button for the "clean points" feature
     *
     * <i>If the button to clean points is clicked, the list of points has to be cleaned</i>
     * <i>If the button to clean points is clicked and no values has been changed, the values still remain the default ones</i>
     */
    @Test
    public void clickCleanPoints() {

        l(this, "@Test clickCleanPoints");

        try {

            // Get the menu item
            UiObject mi = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/action_clean_all")
            );

            w(5000); // If there is no wait, Espresso fails to get the floating action button

            // Bind the list
            fillSpinnerAsUser();

            // Click on the menu item
            mi.click();

            // Check if the values have been made empty
//            onView(withId(R.id.clickerActivityMainLayout)).perform(swipeUp());
//            onView(withId(R.id.sPointsToClick)).perform(click());
//            onView(withId(R.id.sPointsToClick)).check(ViewAssertions.matches(withListSize(1)));

            // Test again to check if the default values remain
            mi.click();
 //           onView(withId(R.id.sPointsToClick)).check(ViewAssertions.matches(withListSize(1)));

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Selects some points to fill the spinner
     */
    private void fillSpinnerAsUser(){

        // Open the arc menu
        onView(withId(R.id.fabAction)).perform(click());
        w(1000);

        // Start the select multipoints activity
        onView(withId(R.id.fabSelectPoint)).perform(click());
        w(2000);

        // Make two clicks on the screen
        onView(withId(R.id.translucentMainView)).perform(clickXY(MOCK_POINT_X, MOCK_POINT_Y));
        w(2000);

        // Go back to the main activity
        pressBack();
        w(2000);

        onView(withId(R.id.clickerActivityMainLayout)).perform(swipeUp());
        w(1000);

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

                        return new float[]{screenX, screenY};

                    }
                },
                Press.FINGER);
    }

//    /**
//     * Matcher for a list with its size
//     * @param size -
//     * @return Matcher<View>
//     */
//    public static Matcher<View> withListSize( final int size ){
//        return new TypeSafeMatcher<View>() {
//            @Override public boolean matchesSafely( final View view ){
//                return ((ListView) view).getChildCount () == size;
//            }
//            @Override public void describeTo( final Description description ){
//                description.appendText ("ListView should have " + size + " items");
//            }
//        };
//    }

}
