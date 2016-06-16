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

package pylapp.smoothclicker.android.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import pylapp.smoothclicker.android.AbstractTest;
import pylapp.smoothclicker.android.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Class to use to make UI tests with UIAutomator on the StandaloneModeDialog.
 *
 * @author pylapp
 * @version 1.0.0
 * @since 27/05/2016
 * @see AbstractTest
 */
public class ItStandaloneModeDialog extends AbstractTest {


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
     *
     */
    private static final int WAIT_FOR_EXISTS_TIMEOUT = 5000;


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
        Context context = InstrumentationRegistry.getTargetContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_APP_PATH);

        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_APP_PATH).depth(0)), LAUNCH_TIMEOUT_MS);

        // Prepare for tests
        openStandaloneModeDialog();

    }

    /**
     * Test the swipe from the right to the left
     *
     * <i>If the user swipes from the right to the left, the next item in the swipe cursor must be displayed</i>
     */
    @Test
    public void swipeFromRightToLeft(){

        try {

            // Swipe to the last item
            final int STEPS = 1;
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject swipeSelector = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_swipePager")
                );
                Rect bounds = swipeSelector.getBounds();
                mDevice.swipe(bounds.centerX(), bounds.centerY(), 0, bounds.centerY(), 10);
            }

            // Check the last item
            String[] titles = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_titles);
            String[] descs = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_description);
            String expectedLastItemTitle = titles[STEPS];
            String expectedLastItemDesc = descs[STEPS];

            UiObject lastItemTitle = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_title")
            );
            assertEquals(expectedLastItemTitle, lastItemTitle.getText());

            UiObject lastItemDesc = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_description")
            );
            assertEquals(expectedLastItemDesc, lastItemDesc.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the swipe from the left to the right
     *
     * <i>If the user swipes from the left to the right, the previous item in the swipe cursor must be displayed</i>
     */
    @Test
    public void swipeFromLeftToRight(){

        try {

            // Swipe to the last item (to be at the end of the selector)
            final int STEPS = 1;
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject swipeSelector = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_swipePager")
                );
                Rect bounds = swipeSelector.getBounds();
                mDevice.swipe(bounds.centerX(), bounds.centerY(), 0, bounds.centerY(), 10);
            }

            // Go backward
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject swipeSelector = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_swipePager")
                );
                Rect bounds = swipeSelector.getBounds();
                mDevice.swipe(bounds.centerX(), bounds.centerY(), bounds.width(), bounds.centerY(), 10);
            }

            // Check the first item
            String[] titles = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_titles);
            String[] descs = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_description);
            String expectedFirstItemTitle = titles[0];
            String expectedFirstItemDesc = descs[0];

            UiObject lastItemTitle = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_title")
            );
            assertEquals(expectedFirstItemTitle, lastItemTitle.getText());

            UiObject lastItemDesc = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_description")
            );
            assertEquals(expectedFirstItemDesc, lastItemDesc.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the click on the cursor's right arrow
     *
     * <i>If the user clicks on then cursor's right arrow, the next item in the swipe cursor must be displayed</i>
     */
    @Test
    public void clickRightArrow(){

        try {

            // Click on the (not) green arrow
            final int STEPS = 1;
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject rightArrow = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_rightButton")
                );
                rightArrow.click();
            }

            // Check the last item
            String[] titles = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_titles);
            String[] descs = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_description);
            String expectedLastItemTitle = titles[STEPS];
            String expectedLastItemDesc = descs[STEPS];

            UiObject lastItemTitle = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_title")
            );
            assertEquals(expectedLastItemTitle, lastItemTitle.getText());

            UiObject lastItemDesc = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_description")
            );
            assertEquals(expectedLastItemDesc, lastItemDesc.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Test the click on the cursor's left arrow
     *
     * <i>If the user clicks on then cursor's left arrow, the previous item in the swipe cursor must be displayed</i>
     */
    @Test
    public void clickLeftArrow(){

        try {

            // Click on the arrow to be at the end of the cursor
            final int STEPS = 1;
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject rightArrow = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_rightButton")
                );
                rightArrow.click();
            }
            // Go backward
            for ( int i = 1; i <= STEPS; i++ ){
                // Get the selector
                UiObject leftArrow = mDevice.findObject(
                        new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_layout_leftButton")
                );
                leftArrow.click();
            }

            // Check the first item
            String[] titles = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_titles);
            String[] descs = InstrumentationRegistry.getTargetContext().getResources().getStringArray(R.array.standalone_mode_description);
            String expectedFirstItemTitle = titles[0];
            String expectedFirstItemDesc = descs[0];

            UiObject lastItemTitle = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_title")
            );
            assertEquals(expectedFirstItemTitle, lastItemTitle.getText());

            UiObject lastItemDesc = mDevice.findObject(
                    new UiSelector().resourceId(PACKAGE_APP_PATH + ":id/swipeselector_content_description")
            );
            assertEquals(expectedFirstItemDesc, lastItemDesc.getText());

        } catch ( UiObjectNotFoundException uonfe ){
            uonfe.printStackTrace();
            fail( uonfe.getMessage() );
        }

    }

    /**
     * Opens the standalone mode dialog
     */
    private void openStandaloneModeDialog(){
        try {
            // Display the pop-up
            UiObject menu = mDevice.findObject(
                    new UiSelector().className("android.widget.ImageView")
                            //.description("Plus d'options") // FIXME Raw french string
                            .description("Autres options") // WARNING FIXME French string used, use instead system R values
            );
            menu.click();
            UiObject menuItem = mDevice.findObject(
                    new UiSelector().className("android.widget.TextView").text(InstrumentationRegistry.getTargetContext().getString(R.string.action_standalone))
            );
            menuItem.click();
        } catch ( UiObjectNotFoundException uinfe ){
            uinfe.printStackTrace();
            fail(uinfe.getMessage());
        }
    }

}
