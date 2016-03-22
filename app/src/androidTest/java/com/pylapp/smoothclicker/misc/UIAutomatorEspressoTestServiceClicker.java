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
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import com.pylapp.smoothclicker.AbstractTest;
import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.clicker.ServiceClicker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.rule.ServiceTestRule.withTimeout;
import static org.junit.Assert.assertTrue;

/**
 * Class to use to make instrumented tests with Espresso and UIAUtomator of the ServiceClicker.
 *
 *  @author pylapp
 *  @version 1.0.0
 *  @since 22/03/2016
 *  @see AbstractTest
 */
public class UIAutomatorEspressoTestServiceClicker extends AbstractTest {


    /**
     * The service rule
     */
    @Rule
    public final ServiceTestRule mServiceRule = withTimeout(10, TimeUnit.SECONDS);
    /**
     * A UIAutomator object to handles the notifications
     */
    private UiDevice mDevice;
    /**
     *
     */
    private Context mContext;


    /**
     * Initializes the NotificationManager
     */
    @Before
    public void init(){
        l(this,"@Before init");
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
        mContext = InstrumentationRegistry.getTargetContext();
    }

    /**
     * Tests the service start method without data in bundle
     */
    @Test
    public void startServiceWithoutValue(){
        l(this, "@Test startServiceWithoutValue");
        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        try {
            mServiceRule.startService( startIntent );
        } catch ( TimeoutException te ){
            // Do nothing, it can rise even if the service is working properly
        }
    }

    /**
     * Tests the service start method without well formed intent
     */
    @Test
    public void startService(){

        l(this, "@Test startService");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        // Create the Intent with the good action
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");

        // Defines the configuration to use
        startIntent.putExtra("0x000011", true); // Start delayed ?
        startIntent.putExtra("0x000012", 20);   // How much delay for the start ?
        startIntent.putExtra("0x000013", 2);    // The amount of time to wait between clicks
        startIntent.putExtra("0x000021", 10);    // The number of repeat to do
        startIntent.putExtra("0x000022", false);// Endless repeat ?
        startIntent.putExtra("0x000031", false);// Vibrate on start ?
        startIntent.putExtra("0x000032", false);// Vibrate on each click ?
        startIntent.putExtra("0x000041", true);// Make notifications ?
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(695); // x0
        points.add(799); // y0
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points

        try {
            mServiceRule.startService( startIntent );
        } catch ( TimeoutException te ){
            // Do nothing, it can rise even if the service is working properly
        }

        // Test the countdown notification
        testNotification(mContext.getString(R.string.notif_content_text_countdown));

        w(10000);

        // Test the new click notification
        testNotification(mContext.getString(R.string.notif_content_text_click_made));

        l(this, "pouet 1");

        // Test the on going process notification
        testNotification(mContext.getString(R.string.notif_content_text_clicks_on_going_service));

        w(10000);

        l(this, "pouet 2");

        // Test the terminated notification
        testNotification(mContext.getString(R.string.notif_content_text_clicks_over));

    }

    /**
     * Tests the service start method with dummy values
     */
    @Test
    public void startServiceWithBadAction() {

        l(this, "@Test startServiceWithBadAction");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.STOP");
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Tests the service start method with dummy values
     */
    //@Test
    public void startServiceWithNegativeValues1() {

        l(this, "@Test startServiceWithNegativeValues1");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");
        startIntent.putExtra("0x000012", -20);   // How much delay for the start ?
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(0); // x0
        points.add(0); // y0
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Tests the service start method with dummy values
     */
    //@Test
    public void startServiceWithNegativeValues2() {

        l(this, "@Test startServiceWithNegativeValues2");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");
        startIntent.putExtra("0x000013", -2);    // The amount of time to wait between clicks
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(0); // x0
        points.add(0); // y0
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Tests the service start method with dummy values
     */
    //@Test
    public void startServiceWithNegativeValues3() {

        l(this, "@Test startServiceWithNegativeValues3");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");
        startIntent.putExtra("0x000021", -10);    // The number of repeat to do
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(0); // x0
        points.add(0); // y0
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Tests the service start method with dummy values
     */
    //@Test
    public void startServiceWithNegativeValues4() {

        l(this, "@Test startServiceWithNegativeValues4");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");
        ArrayList<Integer> points = new ArrayList<Integer>();
        points.add(0); // x0
        points.add(42); // y0
        points.add(1337); // x1
        points.add(-50); // y1
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Tests the service start method without null values
     */
    //@Test
    public void startServiceWithNullValues(){

        l(this, "@Test startServiceWithNullValues");

        Intent startIntent = new Intent(InstrumentationRegistry.getTargetContext(), ServiceClicker.class);
        // Create the Intent with the good action
        startIntent.setAction("com.pylapp.smoothclicker.clicker.ServiceClicker.START");

        // Defines the configuration to use
        startIntent.putExtra("0x000011", true); // Start delayed ?
        startIntent.putExtra("0x000012", 20);   // How much delay for the start ?
        startIntent.putExtra("0x000013", 2);    // The amount of time to wait between clicks
        startIntent.putExtra("0x000021", 10);    // The number of repeat to do
        startIntent.putExtra("0x000022", false);// Endless repeat ?
        startIntent.putExtra("0x000031", false);// Vibrate on start ?
        startIntent.putExtra("0x000032", false);// Vibrate on each click ?
        startIntent.putExtra("0x000041", true);// Make notifications ?
        ArrayList<Integer> points = new ArrayList<Integer>();
        startIntent.putIntegerArrayListExtra("0x000051", points); // The list of points
        try { mServiceRule.startService( startIntent ); } catch ( TimeoutException te ){ }

    }

    /**
     * Inner method to get a dedicated notification and test it
     * @param textContent - The text to use to get the notification
     */
    private void testNotification( String textContent ){

        UiObject n = mDevice.findObject(
                new UiSelector()
                        .resourceId("android:id/text")
                        .className("android.widget.TextView")
                        .packageName("com.pylapp.smoothclicker")
                        .textContains(textContent));

        mDevice.openNotification();
        n.waitForExists(30000);
        assertTrue(n.exists());

    }


}
