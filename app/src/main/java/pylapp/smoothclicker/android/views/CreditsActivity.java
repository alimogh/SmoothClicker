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

package pylapp.smoothclicker.android.views;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pylapp.smoothclicker.android.R;


/**
 * The activity which displays the credits / third-parties licences.
 *
 * @author pylapp
 * @version 1.8.0
 * @since 15/03/2016
 */
public class CreditsActivity extends AppCompatActivity {


    //private static final String LOG_TAG = CreditsActivity.class.getSimpleName();


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

        // The SwitchButton's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsSwitchButtonUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_switchbutton_url)));
                startActivity(i);
            }
        });

        // The AppIntro's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsAppintroUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_appintro_url)));
                startActivity(i);
            }
        });

        // The Swipe Selector's Github URL
        tv = (TextView) findViewById(R.id.tvCreditsSsUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_ss_url)));
                startActivity(i);
            }
        });

        // The Material SeekBar Preference
        tv = (TextView) findViewById(R.id.tvCreditsMsbpUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_msbp_url)));
                startActivity(i);
            }
        });

        // The Dexter library
        tv = (TextView) findViewById(R.id.tvCreditsDexterUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_dexter_url)));
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

        // The app's author personal page
        tv = (TextView) findViewById(R.id.tvCreditsAppDesc);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_app_personal_page)));
                startActivity(i);
            }
        });

        // The icon made by FreepiK form Flaticon
        tv = (TextView) findViewById(R.id.tvCreditsFlaticonsFreepikUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_icon_flaticon_freepik_url)));
                startActivity(i);
            }
        });

        // The icon made by Yannick form Flaticon
        tv = (TextView) findViewById(R.id.tvCreditsFlaticonsYannickUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_icon_flaticon_yannick_url)));
                startActivity(i);
            }
        });

        // The icon made by Elegant Themes form Flaticon
        tv = (TextView) findViewById(R.id.tvCreditsFlaticonsElegantthemesUrl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.credits_icon_flaticon_elegantthemes_url)));
                startActivity(i);
            }
        });

        super.onPostCreate(savedInstanceState);

    }

}
