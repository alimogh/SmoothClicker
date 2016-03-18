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


package com.pylapp.smoothclicker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pylapp.smoothclicker.clicker.ServiceClicker;
import com.pylapp.smoothclicker.tools.Logger;

/**
 * Broadcast receiver which receives broadcasts about the battery state and the boot state so as to start or stop the service making clicks..
 *
 * @author pylapp
 * @version 1.0.0
 * @since 18/03/2016
 * @see android.content.BroadcastReceiver
 */
public class BRClicker extends BroadcastReceiver {


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     * The action of the broadcast for a boot completed event
     */
    private static final String BR_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
//    /**
//     * The action of the broadcast for a battery okay event
//     */
//    private static final String BR_BATTERY_OKAY = "android.intent.action.ACTION_BATTERY_OKAY";
    /**
     * The action of the broadcast for a battery low event
     */
    private static final String BR_BATTERY_LOW = "android.intent.action.ACTION_BATTERY_LOW";


    private static final String LOG_TAG = "BRCLicker";


    /* ****************************** *
     * METHODS FROM BroadcastReceiver *
     * ****************************** */

    @Override
    public void onReceive( Context context, Intent intent ){

        if ( intent == null || intent.getAction() == null ){
            Logger.fe(LOG_TAG, "The BroadcastReceiver BRClicker has received a broadcast without intent or action O_ô");
            return;
        }

        final String action = intent.getAction();
        Logger.d(LOG_TAG, "Receives new broadcast: "+action);

        switch ( action ){
            case BR_BATTERY_LOW:
                stopClickerService( context );
                break;
//            case BR_BATTERY_OKAY:
//                restartClickerService( context );
//                break;
            case BR_BOOT_COMPLETED:
                startService( context );
                break;
            default:
                Logger.w(LOG_TAG, "The BroadcastReceiver BRClicker has received a strange broadcast: "+action);
                break;
        }

    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     * Starts the clicker service
     * @param c - The context to sue to start the service
     */
    private void startService( Context c ){
        Intent i = new Intent(c, ServiceClicker.class);
        i.setAction(ServiceClicker.SERVICE_CLICKER_INTENT_FILTER_NAME_WAKEUP);
        c.startService(i);
    }

    /**
     * Stops the clicker service
     * @param c - The context to use to start the service
     */
    private void stopClickerService( Context c ){
        Intent i = new Intent(c, ServiceClicker.class);
        i.setAction(ServiceClicker.SERVICE_CLICKER_INTENT_FILTER_NAME_STOP);
        c.stopService(i);
    }

//    /**
//     * Restarts the clicker service
//     * @param c - The context to use to start the service
//     */
//    private void restartClickerService( Context c ){
//        stopClickerService(c);
//        startService(c);
//    }

}
