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
