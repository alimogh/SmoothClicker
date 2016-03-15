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

package com.pylapp.smoothclicker.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.pylapp.smoothclicker.R;

/**
 * The splash screen activity
 *
 * @author pylapp
 * @version 1.0.0
 * @since 15/03/2016
 */
public class SplashScreenActivity extends AppCompatActivity {


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     * The duration in ms of the splash
     */
    private static final int SPLASH_TIME_OUT = 3000;


    /* ****************************** *
     * METHODS FROM AppCompatActivity *
     * ****************************** */

    /**
     * Triggered to create the view
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    /**
     * Triggered when the view has been created
     * @param savedInstanceState -
     */
    @Override
    protected void onPostCreate( Bundle savedInstanceState ){

        HTextView htv = (HTextView) findViewById(R.id.htvSplashScreenDesc);
        htv.setAnimateType(HTextViewType.ANVIL);
        htv.animateText(getString(R.string.splashscreen_description)); // FIXME May throw an OutOfMemory error

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, ClickerActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

        super.onPostCreate(savedInstanceState);

    }

}
