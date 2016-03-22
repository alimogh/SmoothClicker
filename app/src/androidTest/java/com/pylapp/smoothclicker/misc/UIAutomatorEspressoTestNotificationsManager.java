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

package com.pylapp.smoothclicker.misc;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import android.test.suitebuilder.annotation.SmallTest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.AbstractTest;
import com.pylapp.smoothclicker.notifiers.NotificationsManager;
import com.pylapp.smoothclicker.views.ClickerActivity;


/**
 * Class to use to make UI tests with Espresso and UIAutomator of the NotificationsManager.
 *
 *  @author pylapp
 *  @version 1.0.0
 *  @since 21/03/2016
 *  @see AbstractTest
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class UIAutomatorEspressoTestNotificationsManager extends AbstractTest {


    /**
     * Defines a rule for our tests : here the activity to test.
     * An Espresso object..
     */
    @Rule
    public ActivityTestRule<ClickerActivity> mActivityRule = new ActivityTestRule<>(ClickerActivity.class);

    /**
     * The object to test its behaviour
     */
    private NotificationsManager mNm;

    /**
     * A UIAutomator object to handles the notifications
     */
    private UiDevice mDevice;

    /**
     * The application context.
     */
    private Context mContext;


    /**
     * Initializes the NotificationManager
     */
    @Before
    public void init(){
        l(this,"@Before init");
        mContext = mActivityRule.getActivity().getApplicationContext();
        mNm = NotificationsManager.getInstance(mContext);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
    }

    /**
     * Cleans
     */
    @After
    public void clean(){
        l(this,"@After clean");
        mDevice.pressHome();
    }

    /**
     * Test the countdown notifications
     */
    @Test
    public void makeCountDownNotification(){

        l(this,"@Test makeCountDownNotification");

        final int countDown = 15;
        String base = mContext.getString(R.string.notif_content_text_countdown);
        String m = base + " " + countDown;
        mNm.makeCountDownNotification(countDown);

        testNotification(m);

    }

    /**
     * Test the click over notifications
     */
    @Test
    public void makeClickOverNotification(){

        l(this,"@Test makeClickOverNotification");

        mNm.makeClicksOverNotification();

        String m = mContext.getString(R.string.notif_content_text_clicks_over);
        testNotification(m);

    }

    /**
     * Test the click stopped notifications
     */
    @Test
    public void makeClickStoppedNotification(){

        l(this,"@Test makeClickStoppedNotification");

        mNm.makeClicksStoppedNotification();

        String m = mContext.getString(R.string.notif_content_text_clicks_stop);
        testNotification(m);

    }

   /**
    * Test the click on going notifications
    */
    @Test
    public void makeClickOnGoingNotification(){

        l(this,"@Test makeClickOnGoingNotification");

        mNm.makeClicksOnGoingNotification();

        String m = mContext.getString(R.string.notif_content_text_clicks_on_going_app);
        testNotification(m);

    }

    /**
     * Test the new click notifications
     */
    @Test
    public void makeNewClickNotification(){

        l(this,"@Test makeNewClickNotification");

        // Make the notification appear
        final int X = 42;
        final int Y = 1337;
        String m = mContext.getString(R.string.notif_content_text_click_made);
        mNm.makeNewClickNotifications(X, Y);

        // Check it
        testNotification(m);

    }

    /**
     * Inner method to get a dedicated notification and test it
     * @param textContent - The etxt to use to get the notification
     */
    private void testNotification( String textContent ){

        UiObject n = mDevice.findObject(
                new UiSelector()
                        .resourceId("android:id/text")
                        .className("android.widget.TextView")
                        .packageName("com.pylapp.smoothclicker")
                        .textContains(textContent));

        mDevice.openNotification();
        n.waitForExists(2000);
        assertTrue(n.exists());

    }

}
