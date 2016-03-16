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
import android.os.Vibrator;

/**
 * Utility class which manages vibrations.
 * It is based on a wrapper design pattern.
 * Be sure you use the dedicated permission
 *
     <pre>
        <uses-permission android:name="android.permission.VIBRATE" />
     </pre>
 *
 * @author pylapp
 * @version 1.1.0
 * @since 16/03/2016
 */
public class VibrationNotifier {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The context to use to get the vibrator
     */
    private Context mContext;


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     * The duration of a vibration in ms if the device must vibrate on start
     */
    public static final int VIBRATE_ON_START_DURATION = 500;
    /**
     * The duration of a vibration in ms if the device must vibrate on each click
     */
    public static final int VIBRATE_ON_CLICK_DURATION = 300;


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Default constructor
     *
     * @param c - The context to use to get the vibrator, must not be null
     */
    public VibrationNotifier( Context c ){
        super();
        if ( c == null ) throw new IllegalArgumentException("The context param must not be null");
        mContext = c;
    }


    /* ******* *
     * METHODS *
     * ******* */

    /**
     * Makes the device vibrate during an amount of ms
     * @param duration - The time in ms to vibrate
     */
    public void vibrate( int duration ){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

}
