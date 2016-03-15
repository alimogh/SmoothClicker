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
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pylapp.smoothclicker.R;


/**
 * The activity which displays the credits / third-parties licences.
 *
 * @author pylapp
 * @version 1.2.0
 * @since 15/03/2016
 */
public class CreditsActivity extends AppCompatActivity {

    /**
     * Triggered to create the view
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /**
     * Triggered when the view has been created.
     * Initializes the listeners for TextViews containing URLs.
     * @param savedInstanceState -
     */
    @Override
    protected void onPostCreate( Bundle savedInstanceState ){

        // The logo's URL
        TextView tv = (TextView) findViewById(R.id.tvCreditsApplicationLogoUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_logo_url)));
                startActivity(i);
            }
        });

        // The MaterialArcMenu's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsMamUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_mam_url)));
                startActivity(i);
            }
        });

        // The HTextView's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsHtextviewUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_htextview_url)));
                startActivity(i);
            }
        });

        // The app's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsAppUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_app_url)));
                startActivity(i);
            }
        });

        // The app's author personnal page
        tv = (TextView) findViewById(R.id.tvCreditsAppDesc);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_app_personal_page)));
                startActivity(i);
            }
        });

        super.onPostCreate(savedInstanceState);

    }

}
