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

package com.pylapp.smoothclicker.clicker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pylapp.smoothclicker.R;

/**
 * Service to call our to start from the outside (e.g. a third party app) so as to trigger the cliking process
 * without using the GUI and its dedicated Activity instances.
 *
 * @author pylapp
 * @version 1.0.0
 * @since 18/03/2016
 * @see IntentService
 */
public class ServiceClicker extends IntentService {


    /* ********** *
     * ATTRIBUTES *
     * ********** */


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     *
     */
    public static final String SERVICE_CLICKER_INTENT_FILTER_NAME_START = "com.pylapp.smoothclicker.clicker.ServiceClicker.START";

    // Should be equal as R.string.service_label_serviceclicker
    private static final String SERVICE_LABEL_SERVICECLICKER = "Service Clicker of Smooth Clicker";

    /**
     * The key to use to store the delayed start (boolean value) in a bundle
     */
    public static final String BUNDLE_KEY_DELAYED_START = "0x000011";
    /**
     * The key to use to store the delay (integer value) in a bundle
     */
    public static final String BUNDLE_KEY_DELAY = "0x000012";
    /**
     * The key to use to store the time gap to wait between clicks (integer value) in a bundle
     */
    public static final String BUNDLE_KEY_TIME_GAP = "0x000013";
    /**
     * The key to use to store the amount of repetition (integer value) in a bundle
     */
    public static final String BUNDLE_KEY_REPEAT = "0x000021";
    /**
     * The key to use to store the repeat endless (boolean value) in a bundle
     */
    public static final String BUNDLE_KEY_REPEAT_ENDLESS = "0x000022";
    /**
     * The key to use to store the vibrate on start (boolean value) in a bundle
     */
    public static final String BUNDLE_KEY_VIBRATE_ON_START = "0x000031";
    /**
     * The key to use to store the vibrate on each click (boolean value) in a bundle
     */
    public static final String BUNDLE_KEY_VIBRATE_ON_CLICK = "0x000032";
    /**
     * The key to use to store the notification (boolean value) in a bundle
     */
    public static final String BUNDLE_KEY_NOTIFICATIONS = "0x000041";
    /**
     * The key to use to store the points to click on (ArrayList of Integers value) in a bundle
     */
    public static final String BUNDLE_KEY_POINTS = "0x000051";

    /**
     * The key for the status of the service sent within a broadcast
     */
    public static final String BROADCAST_KEY_STATUS = "com.pylapp.smoothclicker.clicker.ServiceClicker.STATUS";

    private static final String BROADCAST_ACTION = "com.pylapp.smoothclicker.clicker.ServiceClicker.BROADCAST";


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Default constructor
     */
    public ServiceClicker(){
        super(SERVICE_LABEL_SERVICECLICKER);
    }


    /* ******************** *
     * METHODS FROM Service *
     * ******************** */

    /**
     * Triggered when a new intent has been received
     * @param intent -
     */
    @Override
    protected void onHandleIntent( Intent intent ){

        if ( intent == null ){
            broadcastStatus(StatusTypes.BAD_CONFIG);
            return;
        }

        /*
         * Step 1 : Get the intent : should we start or stop ?
         */
        // TODO


        /*
         * Step 2 : Broadcast the status
         */
        // TODO

        /*
         * Step 3a : Saves the config
         */
        // TODO

        /*
         * Step 3b : Starts the clicking process
         */
        // TODO

        /*
         * Step 4 : Broadcast the status
         */
        // TODO

        /*
         * Step 5 : Broadcast the status when the process is done
         */
        // TODO

        /*
         * Step 6 : Finish !
         */
        // TODO

    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     * Sends a broadcast with the status of the service
     * @param status - The status to broadcast
     */
    private void broadcastStatus( StatusTypes status ){
        Intent i = new Intent(BROADCAST_ACTION);
        i.putExtra(BROADCAST_KEY_STATUS, status.mCode);
        sendBroadcast(i);
    }


    /* *********** *
     * INNER ENUMS *
     * *********** */

    /**
     * The list of status
     */
    public enum StatusTypes {

        /**
         * The status the service can be: it has been triggered
         */
        AWAKE("0x001001"),
        /**
         * The status the service can be: its is working on the click process
         */
        STARTED("0x001002"),
        /**
         * The status the service can be: the click process is over
         */
        TERMINATED("0x001003"),
        /**
         * The status the service can be: it has received a bad configuration
         */
        BAD_CONFIG("0x001004");

        String mCode;

        StatusTypes( String s ){
            mCode = s;
        }

    }

}
