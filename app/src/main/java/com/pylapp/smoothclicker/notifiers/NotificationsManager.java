/*
    Copyright 2016 Pierre-Yves Lapersonne (aka. "pylapp",  pylapp(dot)pylapp(at)gmail(dot)com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
// ✿✿✿✿ ʕ •ᴥ•ʔ/ ︻デ═一

package com.pylapp.smoothclicker.notifiers;

import android.content.Context;
import android.content.SharedPreferences;

import com.pylapp.smoothclicker.utils.Config;

/**
 * Utility class which manages notifications.
 * It is base on a facade design pattern for notification features.
 *
 * @author pylapp
 * @version 1.2.0
 * @since 16/03/2016
 */
public final class NotificationsManager {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The context to use to get the shared preferences, to get vibrations, etc.
     */
    private Context mContext;

    /**
     * If we have to vibrate on start
     */
    private boolean mVibrateOnStart;
    /**
     * If we have to vibrate on each click
     */
    private boolean mVibrateOnClick;
    /**
     * If we have to display a notification on each new click
     */
    private boolean mNotifOnClick;

    /**
     * The singleton
     */
    private static NotificationsManager sInstance;


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Default constructor
     * @param c - The context to use to get the SharedPreferences, must not be null
     */
    private NotificationsManager( Context c ){
        super();
        if ( c == null ) throw  new IllegalArgumentException("The context parameter mut not be null");
        mContext = c;
        init();
    }


    /* ******* *
     * METHODS *
     * ******* */

    /**
     * Returns the singleton
     * @param c - The context to use, must not be null
     * @return NotificationsManager - The singleton
     */
    public static NotificationsManager getInstance( Context c ){
        if ( sInstance == null ){
            sInstance = new NotificationsManager(c);
        }
        return sInstance;
    }

    /**
     * Makes a notification about the fact the clicking process starts
     */
    public void makeStartNotification(){
        if ( mVibrateOnStart ){
            VibrationNotifier vn = new VibrationNotifier(mContext);
            vn.vibrate(VibrationNotifier.VIBRATE_ON_START_DURATION);
        }
    }

    /**
     * Manages the notifications about the new click.
     * @param x - The x coordinate of the click
     * @param y - The y coordinate of the click
     */
    public void makeNewClickNotifications( int x, int y){

        // Vibrations ?
        if ( mVibrateOnClick ){
            new VibrationNotifier(mContext).vibrate(VibrationNotifier.VIBRATE_ON_CLICK_DURATION);
        }

        // Notification in status bar ?
        if ( mNotifOnClick ) {
            new StatusBarNotifier(mContext).makeNotification(StatusBarNotifier.NotificationTypes.CLICK_MADE, x, y);
        }

    }

    /**
     * Manages the notifications about the on going clicking process
     */
    public void makeClicksOnGoingNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.makeNotification(StatusBarNotifier.NotificationTypes.CLICKS_ON_GOING_BY_APP);
    }

    /**
     * Manages the notifications about the stopped clicking process
     */
    public void makeClicksStoppedNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.makeNotification(StatusBarNotifier.NotificationTypes.CLICKS_STOPPED);
    }

    /**
     * Manages the notifications about the clicking process which is over
     */
    public void makeClicksOverNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.makeNotification(StatusBarNotifier.NotificationTypes.CLICKS_OVER);
    }

    /**
     * Manages the notifications about the count down for delayed starts
     * @param countDown - The leaving amount of seconds before start
     */
    public void makeCountDownNotification( int countDown ){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.makeNotification(StatusBarNotifier.NotificationTypes.COUNT_DOWN, countDown);
    }

    /**
     * Manages the notifications about the granted SU permission
     */
    public void makeSuGrantedNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.makeNotification(StatusBarNotifier.NotificationTypes.SU_GRANTED);
    }

    /**
     * Stops the notifications about the on going clicking process
     */
    public void stopClicksOnGoingNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.CLICKS_ON_GOING_BY_APP);
    }

    /**
     * Stops the notifications about the stopped clicking process
     */
    public void stopClicksStoppedNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.CLICKS_STOPPED);
    }

    /**
     * Stops the notifications about the over clicking process
     */
    public void stopClickOverNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.CLICKS_OVER);
    }

    /**
     * Stops the notifications about the granted SU permission
     */
    public void stopSuGrantedNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.SU_GRANTED);
    }

    /**
     * Stops the notifications about the new made click
     */
    public void stopClickMadeNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.CLICK_MADE);
    }

    /**
     * Stops the notifications about the count-down
     */
    public void stopCountdownNotification(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeNotification(StatusBarNotifier.NotificationTypes.COUNT_DOWN);
    }

    /**
     * Stops all the notifications
     */
    public void stopAllNotifications(){
        StatusBarNotifier sbn = new StatusBarNotifier(mContext);
        sbn.removeAllNotifications();
    }

    /**
     * Initializes the singleton
     */
    private void init(){

        SharedPreferences sp = mContext.getSharedPreferences(Config.SMOOTHCLICKER_SHARED_PREFERENCES_NAME, Config.SP_ACCESS_MODE);

        // Vibrations
        mVibrateOnStart = sp.getBoolean(Config.SP_KEY_VIBRATE_ON_START, Config.DEFAULT_VIBRATE_ON_START);
        mVibrateOnClick = sp.getBoolean(Config.SP_KEY_VIBRATE_ON_CLICK, Config.DEFAULT_VIBRATE_ON_CLICK);

        // Notifications
        mNotifOnClick = sp.getBoolean(Config.SP_KEY_NOTIF_ON_CLICK, Config.DEFAULT_NOTIF_ON_CLICK);

    }

    /**
     * Defines the context to use for this object, the context ahs to be defined to get accesses to vibrator, shared preferences, etc.
     * @param c -
     */
    public void refresh( Context c ){
        mContext = c;
        init();
    }

}
