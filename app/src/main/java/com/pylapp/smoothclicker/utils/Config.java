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

package com.pylapp.smoothclicker.utils;

import android.content.Context;

/**
 * Just a configuration class with useful values
 *
 * @author pylapp
 * @version 1.5.0
 * @since 02/03/2016
 */
public final class Config {

    /*
     * Default values
     */
    public static final boolean DEFAULT_START_DELAYED    = true;
    public static final String DEFAULT_DELAY             = "5";
    public static final String DEFAULT_TIME_GAP          = "3";
    public static final String DEFAULT_REPEAT            = "0";
    public static final boolean DEFAULT_REPEAT_ENDLESS   = false;
    public static final boolean DEFAULT_VIBRATE_ON_START = false;
    public static final boolean DEFAULT_VIBRATE_ON_CLICK = true;
    public static final boolean DEFAULT_NOTIF_ON_CLICK   = true;
    public static final boolean DEFAULT_SHAKE_TO_CLEAN   = true;

    /*
     * The shared preferences
     */
    public static final String SMOOTHCLICKER_SHARED_PREFERENCES_NAME = "com.pylapp.smoothclicker.SMOOTHCLICKER_SHARED_PREFERENCES_NAME";
    public static final String SP_KEY_START_TYPE_DELAYED             = "0x000011";
    public static final String SP_KEY_DELAY                          = "0x000012";
    public static final String SP_KEY_TIME_GAP                       = "0x000021";
    public static final String SP_KEY_REPEAT                         = "0x000031";
    public static final String SP_KEY_REPEAT_ENDLESS                 = "0x000032";
    public static final String SP_KEY_VIBRATE_ON_START               = "0x000041";
    public static final String SP_KEY_VIBRATE_ON_CLICK               = "0x000042";
    public static final String SP_KEY_NOTIF_ON_CLICK                 = "0x000051";

    public static final int SP_ACCESS_MODE                           = Context.MODE_PRIVATE;

}
