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

package com.pylapp.smoothclicker.views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.utils.ConfigVersions;

/**
 * The preferences activity of this SmoothClicker app.
 *
 * @author pylapp
 * @version 1.1.0
 * @since 17/03/2016
 */
public class SettingsActivity extends AppCompatActivity {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The release
     */
    private static String sVersionRelease;


    /* ********* *
     * CONSTANTS *
     * ********* */

    private static final String PREF_KEY_CREDITS = "pref_key_credit";
    private static final String PREF_KEY_APP = "pref_key_app";
    public static final String PREF_KEY_SHAKE_TO_CLEAN = "pref_key_settings_shaketoclean";
    private static final String PREF_KEY_ABOUT_VERSION = "pref_about_version_title";


    /* ****************************** *
     * METHODS FROM AppCompatActivity *
     * ****************************** */

    /**
     * Triggered to create the view
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Builds the release string
        StringBuffer sb = new StringBuffer();
        sb.append(ConfigVersions.VERSION_TAG_CURRENT);
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pi.versionName;
            sb.append(" - v").append(versionName);
            int versionCode = pi.versionCode;
            sb.append(" - ").append(versionCode);
        } catch ( PackageManager.NameNotFoundException e ){
            e.printStackTrace();
        }
        sVersionRelease = sb.toString();

        // Display the fragment as the main content.
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }


    /* *********** *
     * INNER CLASS *
     * *********** */

    /**
     * The Fragment for preferences.
     * See http://developer.android.com/guide/topics/ui/settings.html
     */
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate( Bundle savedInstanceState ){

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            // The credits view with all third party contents
            Preference pref = (Preference) findPreference(PREF_KEY_CREDITS);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), CreditsActivity.class));
                    return true;
                }
            });

            // The page of the author
            pref = (Preference) findPreference(PREF_KEY_APP);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.credits_app_url)));
                    startActivity(i);
                    return true;
                }
            });

            // The version
            pref = (Preference) findPreference(PREF_KEY_ABOUT_VERSION);
            pref.setSummary(sVersionRelease);

        }

    } // End of public static class SettingsFragment extends PreferenceFragment

}
