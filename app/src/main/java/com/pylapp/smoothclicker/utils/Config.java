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
 * A version of an app should be found by three things : the version code, the version name and the version tag.
 * The version code is incremented each time a new version is released.
 * The version name is the "user-friendly" version, i.e. with a schema like XX.YY.ZZ.
 * The XX is incremented for very important upgrades, major updates.
 * The YY is incremented or common upgrades, medium.
 * The ZZ is incremented for minor upgrades, i.e. updates for refactoring or cleaning facts.
 * The version tag is a code name, easy to remember. It should be changed each time the XX or YY aprt is modified.
 *
 * @author pylapp
 * @version 1.3.0
 * @since 02/03/2016
 */
public final class Config {

    /*
     * Version tags
     */
    public static final String VERSION_TAG_1_0_0 = "Astonishing Ant";
    public static final String VERSION_TAG_1_3_0 = "Blazing Buffalo";
    public static final String VERSION_TAG_1_4_0 = "Crazy Crane";
    public static final String VERSION_TAG_1_5_0 = "Dumb Dodo";
    public static final String VERSION_TAG_CURRENT = VERSION_TAG_1_5_0;

    /*
     * Default values
     */
    public static final boolean DEFAULT_START_DELAYED    = true;
    public static final String DEFAULT_DELAY             = "1";
    public static final String DEFAULT_TIME_GAP          = "1";
    public static final String DEFAULT_REPEAT            = "10";
    public static final boolean DEFAULT_VIBRATE_ON_START = false;
    public static final boolean DEFAULT_VIBRATE_ON_CLICK = true;
    public static final boolean DEFAULT_NOTIF_ON_CLICK   = true;
    public static final int DEFAULT_X_CLICK              = 502;
    public static final int DEFAULT_Y_CLICK              = 797;

    /*
     * The shared preferences
     */
    public static final String SMOOTHCLICKER_SHARED_PREFERENCES_NAME = "com.pylapp.smoothclicker.SMOOTHCLICKER_SHARED_PREFERENCES_NAME";
    public static final String SP_START_TYPE_DELAYED                 = "0x000001";
    public static final String SP_KEY_DELAY                          = "0x000002";
    public static final String SP_KEY_TIME_GAP                       = "0x000003";
    public static final String SP_KEY_REPEAT                         = "0x000004";
    public static final String SP_KEY_VIBRATE_ON_START               = "0x000005";
    public static final String SP_KEY_VIBRATE_ON_CLICK               = "0x000006";
    public static final String SP_KEY_COORD_X                        = "0x000007";
    public static final String SP_KEY_COORD_Y                        = "0x000008";
    public static final String SP_KEY_NOTIF_ON_CLICK                 = "0x000009";

    public static final int SP_ACCESS_MODE                           = Context.MODE_PRIVATE;

}
