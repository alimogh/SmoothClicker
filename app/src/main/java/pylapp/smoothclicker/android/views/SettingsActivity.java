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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pylapp.smoothclicker.android.R;
import pylapp.smoothclicker.android.utils.AppConfigVersions;

/**
 * The preferences activity of this SmoothClicker app.
 *
 * @author pylapp
 * @version 1.8.0
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

    private static final String PREF_KEY_CREDITS                = "pref_key_credit";
    private static final String PREF_KEY_APP                    = "pref_key_app";
    public static final String PREF_KEY_SHAKE_TO_CLEAN          = "pref_key_settings_shaketoclean";
    private static final String PREF_KEY_ABOUT_VERSION          = "pref_about_version_title";
    private static final String PREF_KEY_STORE_PAGE             = "pref_play_store_title";
    public static final String PREF_KEY_WAKELOCK                = "pref_key_settings_wakelock";
    public static final String PREF_KEY_FORCESCREENON_BATTERY   = "pref_key_settings_forcescreenon_battery";
    public static final String PREF_KEY_UNLOCK_SCRIPT           = "pref_key_settings_unlockscript";
    public static final String PREF_KEY_HEIMDALL                = "pref_key_root_heimdall";
    public static final String PREF_KEY_ODIN                    = "pref_key_root_odin";
    public static final String PREF_KEY_CHAINFIRE               = "pref_key_root_chainfire";
    public static final String PREF_KEY_PRTHRESHOLD             = "pref_key_picture_recognition_threshold";
    public static final String PREF_KEY_HELP                    = "pref_key_help";
    public static final String PREF_KEY_START_ON_BOOT           = "pref_key_settings_start_standalone_on_boot";


    //private static final String LOG_TAG = SettingsActivity.class.getSimpleName();


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
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfigVersions.VERSION_TAG_CURRENT);
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
            Preference pref = findPreference(PREF_KEY_CREDITS);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity(), CreditsActivity.class));
                    return true;
                }
            });

            // Set up the listener to make the user go on the Google Play Store's page
            pref = findPreference(PREF_KEY_STORE_PAGE);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    final String appPackageName = getActivity().getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch ( android.content.ActivityNotFoundException anfe ){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    return true;
                }
            });

            // The page of the author
            pref = findPreference(PREF_KEY_APP);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.credits_app_url)));
                    startActivity(i);
                    return true;
                }
            });

            // The version
            pref = findPreference(PREF_KEY_ABOUT_VERSION);
            pref.setSummary(sVersionRelease);

            // To root device under Linux
            pref = findPreference(PREF_KEY_HEIMDALL);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.pref_heimdall_url)));
                    startActivity(i);
                    return true;
                }
            });

            // To root device under Windows
            pref = findPreference(PREF_KEY_ODIN);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.pref_odin_url)));
                    startActivity(i);
                    return true;
                }
            });

            // To get information about root
            pref = findPreference(PREF_KEY_CHAINFIRE);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getString(R.string.pref_chainfire_url)));
                    startActivity(i);
                    return true;
                }
            });

            // The tutorial
            pref = findPreference(PREF_KEY_HELP);
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(SettingsFragment.this.getActivity().getApplicationContext(), IntroScreensActivity.class);
                    startActivity(i);
                    return true;
                }
            });

        }

    } // End of public static class SettingsFragment extends PreferenceFragment

}
