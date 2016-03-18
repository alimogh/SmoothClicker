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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.views.ClickerActivity;

/**
 * Utility class which manages notifications in the status bar.
 * It is based on a wrapper design pattern.
 *
 * @author pylapp
 * @version 1.3.0
 * @since 16/03/2016
 */
public class StatusBarNotifier {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The context to use to get access to the notifications
     */
    private Context mContext;


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     * The identifier of the notification about tSU permission which has been granted
     */
    public static final int NOTIF_SU_GRANTED             = 0x000101;
    /**
     * The identifier of the notification about the clicking process which is on going
     */
    public static final int NOTIF_CLICK_PROCESS_ON_GOING = 0x000201;
    /**
     * The identifier of the notification about the clicking process which has been stopped
     */
    public static final int NOTIF_CLICK_PROCESS_STOPPED  = 0x000202;
    /**
     * The identifier of the notification about the clicking process which has made all its click
     */
    public static final int NOTIF_CLICK_PROCESS_OVER     = 0x000203;
    /**
     * The identifier of the notification about the clicking process which has made a new click
     */
    public static final int NOTIF_CLICK_MADE             = 0x000204;
    /**
     * The identifier of the notification about the countdown
     */
    public static final int NOTIF_COUNT_DOWN             = 0x000205;


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Default constructor
     *
     * @param c - The context to use to get access to the notifications, must not be null
     */
    public StatusBarNotifier( Context c ){
        super();
        if ( c == null ) throw new IllegalArgumentException("The context param must not be null");
        mContext = c;
    }


    /* ******* *
     * METHODS *
     * ******* */

    /**
     * Makes a unmovable notification with a dedicated LED color.
     * This notification is an "on going" one, and should be displayed will the app is clicking.
     *
     * @param type - The notification type
     * @param params -
     *               <ul>
     *                  <li>For CLICK_MADE :params[0] for the X coordinate, params[1] for the Y coordinate</li>
     *                  <li>For COUNT_DOWN :params[0] for the leaving time to display</li>
     *                  <li>Nothing otherwise</li>
     *               </ul>
     */
    public void makeNotification( NotificationTypes type, int... params ){

        NotificationCompat.Builder b = new NotificationCompat.Builder(mContext);
        b.setSmallIcon(R.drawable.logo_32);
        b.setContentTitle(mContext.getString(R.string.notif_content_title));
        b.setVisibility(Notification.VISIBILITY_PUBLIC);

        if ( type != NotificationTypes.CLICK_MADE ) {
            Intent activityToStartOnClick = new Intent(mContext, ClickerActivity.class);
            activityToStartOnClick.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(mContext, 0, activityToStartOnClick, 0);
            b.setContentIntent(pi);
        }

        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = null;

        switch ( type ){
            case CLICKS_ON_GOING:
                b.setContentText(mContext.getString(R.string.notif_content_text_clicks_on_going));
                b.setProgress(0, 0, true);
                b.setLights(0xff9c27b0, 1000, 500);
                n = b.build();
                n.flags |= Notification.FLAG_NO_CLEAR;
                n.flags |= Notification.FLAG_SHOW_LIGHTS;
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_CLICK_PROCESS_ON_GOING, n);
                break;
            case CLICKS_STOPPED:
                b.setContentText(mContext.getString(R.string.notif_content_text_clicks_stop));
                n = b.build();
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_CLICK_PROCESS_STOPPED, n);
                break;
            case CLICKS_OVER:
                b.setContentText(mContext.getString(R.string.notif_content_text_clicks_over));
                n = b.build();
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_CLICK_PROCESS_OVER, n);
                break;
            case CLICK_MADE:
                StringBuffer sb = new StringBuffer();
                sb.append(mContext.getString(R.string.notif_content_text_click_made));
                if ( params != null && params.length == 2 ){
                    sb.append(" : ").append(params[0]).append(" / ").append(params[1]);
                }
                b.setContentText(sb.toString());
                n = b.build();
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_CLICK_MADE, n);
                break;
            case SU_GRANTED:
                b.setContentText(mContext.getString(R.string.notif_content_text_su_granted));
                n = b.build();
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_SU_GRANTED, n);
            case COUNT_DOWN:
                if ( params != null && params.length == 1 ){
                    b.setContentText(mContext.getString(R.string.notif_content_countdown)+" "+params[0]);
                } else {
                    b.setContentText(mContext.getString(R.string.notif_content_countdown));
                }
                n = b.build();
                n.flags |= Notification.FLAG_NO_CLEAR;
                n.flags |= Notification.FLAG_LOCAL_ONLY;
                nm.notify(NOTIF_COUNT_DOWN, n);
                break;
        }

    }

    /**
     * Removes all notifications
     */
    public void removeAllNotifications(){
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    /**
     * Removes a notification
     */
    public void removeNotification( NotificationTypes type ){
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        switch (type){
            case CLICKS_ON_GOING:
                nm.cancel(NOTIF_CLICK_PROCESS_ON_GOING);
                break;
            case CLICKS_STOPPED:
                nm.cancel(NOTIF_CLICK_PROCESS_STOPPED);
                break;
            case CLICK_MADE:
                nm.cancel(NOTIF_CLICK_MADE);
                break;
            case SU_GRANTED:
                nm.cancel(NOTIF_SU_GRANTED);
                break;
        }
    }


    /* *********** *
     * INNER ENUMS *
     * *********** */

    /**
     * Inner enum which possesses all status-bar notification types
     */
    public enum NotificationTypes {
        /**
         * The clicking process is running
         */
        CLICKS_ON_GOING,
        /**
         * A click has been made
         */
        CLICK_MADE,
        /**
         * The clicking process has been stopped
         */
        CLICKS_STOPPED,
        /**
         * The clicking process is over
         */
        CLICKS_OVER,
        /**
         * The SU permission has been granted
         */
        SU_GRANTED,
        /**
         * The amount of time before start, i.e. a count down
         */
        COUNT_DOWN
    } // End of public enum NotificationTypes

}
