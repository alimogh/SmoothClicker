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

package com.pylapp.smoothclicker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.pylapp.smoothclicker.notifiers.NotificationsManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Class to test the NotificationsManager
 *
 * @author pylapp
 * @version 1.0.0
 * @since 21/03/2016
 */
@RunWith(AndroidJUnit4.class)
public class TestNotificationsManager {


    /**
     * The device
     */
    private UiDevice mDevice;
    /**
     * The object creating notifications
     */
    private NotificationsManager nm;


    /**
     * Initializes the tests by going to home screen
     */
    @Before
    public void goToHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

    }

    /**
     * Tests if the start notifications are well displayed
     */
    @Test
    public void makeStartNotification(){

        Context context = InstrumentationRegistry.getContext();
        NotificationsManager nm = NotificationsManager.getInstance(context);
        nm.makeStartNotification();

        // TODO
    }

    /**
     * Tests if the new click notifications are well displayed
     */
    @Test
    public void makeNewClickNotifications(){

        // TODO

    }

    /**
     * Tests if the on going click notifications are well displayed
     */
    @Test
    public void makeOnGoingNotifications(){

        // TODO

    }

    /**
     * Tests if the "click over" notifications are well displayed
     */
    @Test
    public void makeClickOverkNotifications(){

        // TODO

    }

    /**
     * Tests if the countdown notifications are well displayed
     */
    @Test
    public void makeCoutndownkNotifications(){

        // TODO

    }


}
