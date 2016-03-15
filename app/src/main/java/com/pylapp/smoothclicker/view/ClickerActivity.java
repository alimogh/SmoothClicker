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
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.pylapp.smoothclicker.clicker.ATClicker;
import com.pylapp.smoothclicker.utils.Config;
import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.utils.ConfigStatus;
import com.pylapp.smoothclicker.utils.Logger;


/**
 * The main activity of this SmoothClicker app.
 * It shows the configuration widgets to set up the click actions
 *
 * @author pylapp
 * @version 1.1.0
 * @since 02/03/2016
 */
public class ClickerActivity extends AppCompatActivity {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    private static final String LOG_TAG = "ClickerActivity";


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

        // Check whether we're recreating a previously destroyed instance
        if ( savedInstanceState != null ){
            Switch typeOfStart = (Switch) findViewById(R.id.sTypeOfStartDelayed);
            typeOfStart.setChecked(savedInstanceState.getBoolean(Config.SP_START_TYPE_DELAYED));
            EditText et = (EditText) findViewById(R.id.etDelay);
            et.setText(savedInstanceState.getString(Config.SP_KEY_DELAY));
            et = (EditText) findViewById(R.id.etTimeBeforeEachClick);
            et.setText(savedInstanceState.getString(Config.SP_KEY_TIME_GAP));
            et = (EditText) findViewById(R.id.etRepeat);
            et.setText(savedInstanceState.getString(Config.SP_KEY_REPEAT));
            CheckBox cb = (CheckBox) findViewById(R.id.cbVibrateOnStart);
            cb.setChecked(savedInstanceState.getBoolean(Config.SP_KEY_VIBRATE_ON_START));
            cb = (CheckBox) findViewById(R.id.cbVibrateOnClick);
            cb.setChecked(savedInstanceState.getBoolean(Config.SP_KEY_VIBRATE_ON_CLICK));
        }

        setContentView(R.layout.activity_clicker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initInnerListeners();

    }

    /**
     * Triggered when the view has been created
     * @param savedInstanceState -
     */
    @Override
    protected void onPostCreate( Bundle savedInstanceState ){
        initDefaultValues();
        super.onPostCreate(savedInstanceState);
    }

    /**
     * Triggered to save the state
     * @param savedInstanceState -
     */
    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ){

        // Get values to save

        Switch sTypeOfStart = (Switch) findViewById(R.id.sTypeOfStartDelayed);
        boolean isDelayed = sTypeOfStart.isChecked();

        EditText et = (EditText) findViewById(R.id.etDelay);
        int delayInS = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etTimeBeforeEachClick);
        int timeGapInS = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etRepeat);
        int repeatEach =  Integer.parseInt(et.getText().toString());

        CheckBox cb = (CheckBox) findViewById(R.id.cbVibrateOnStart);
        boolean vibrateOnStart = cb.isChecked();

        cb = (CheckBox) findViewById(R.id.cbVibrateOnClick);
        boolean vibrateOnClick = cb.isChecked();

        et = (EditText) findViewById(R.id.etXcoord);
        int xCoord = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etYcoord);
        int yCoord = Integer.parseInt(et.getText().toString());

        // Save the values

        savedInstanceState.putBoolean(Config.SP_START_TYPE_DELAYED, isDelayed);
        savedInstanceState.putInt(Config.SP_KEY_DELAY, delayInS);
        savedInstanceState.putInt(Config.SP_KEY_TIME_GAP, timeGapInS);
        savedInstanceState.putInt(Config.SP_KEY_REPEAT, repeatEach);
        savedInstanceState.putBoolean(Config.SP_KEY_VIBRATE_ON_START, vibrateOnStart);
        savedInstanceState.putBoolean(Config.SP_KEY_VIBRATE_ON_CLICK , vibrateOnClick);
        savedInstanceState.putInt(Config.SP_KEY_COORD_X, xCoord);
        savedInstanceState.putInt(Config.SP_KEY_COORD_Y, yCoord);

        super.onSaveInstanceState(savedInstanceState);

    }

    /**
     * Triggered to create the options menu
     * @param menu -
     * @return boolean - Always true
     */
    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        getMenuInflater().inflate(R.menu.menu_clicker, menu);
        return true;
    }

    /**
     * Triggered when an itemahs been selected on the options menu
     * @param item -
     * @return boolean -
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item ){
        int id = item.getItemId();
        switch ( id ){
            case R.id.action_about:
                displayAboutInfo();
                break;
            case R.id.action_credit:
                startCreditsActivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     * Gets the configuration from the widget and back it up
     * @return ConfigStatus - The state of the config
     */
    private ConfigStatus updateConfig(){

        Logger.d(LOG_TAG, "Updates configuration");

        // Get the defined values
        Switch sTypeOfStart = (Switch) findViewById(R.id.sTypeOfStartDelayed);
        boolean isDelayed = sTypeOfStart.isChecked();

        EditText et = (EditText) findViewById(R.id.etDelay);
        if ( et.getText() == null ) return ConfigStatus.DELAY_NOT_DEFINED;
        int delayInS = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etTimeBeforeEachClick);
        if ( et.getText() == null ) return ConfigStatus.TIME_GAP_NOT_DEFINED;
        int timeGapInS = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etRepeat);
        if ( et.getText() == null ) return ConfigStatus.REPEAT_NOT_DEFINED;
        int repeatEach =  Integer.parseInt(et.getText().toString());

        CheckBox cb = (CheckBox) findViewById(R.id.cbVibrateOnStart);
        boolean vibrateOnStart = cb.isChecked();

        cb = (CheckBox) findViewById(R.id.cbVibrateOnClick);
        boolean vibrateOnClick = cb.isChecked();

        et = (EditText) findViewById(R.id.etXcoord);
        int xCoord = Integer.parseInt(et.getText().toString());

        et = (EditText) findViewById(R.id.etYcoord);
        int yCoord = Integer.parseInt(et.getText().toString());

        // Update the shared preferences
        SharedPreferences sp = getSharedPreferences(Config.SMOOTHCLICKER_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Config.SP_START_TYPE_DELAYED, isDelayed);
        editor.putInt(Config.SP_KEY_DELAY, delayInS);
        editor.putInt(Config.SP_KEY_TIME_GAP, timeGapInS);
        editor.putInt(Config.SP_KEY_REPEAT, repeatEach);
        editor.putBoolean(Config.SP_KEY_VIBRATE_ON_START, vibrateOnStart);
        editor.putBoolean(Config.SP_KEY_VIBRATE_ON_CLICK, vibrateOnClick);
        editor.putInt(Config.SP_KEY_COORD_X, xCoord);
        editor.putInt(Config.SP_KEY_COORD_Y, yCoord);

        editor.commit();

        return ConfigStatus.TIME_GAP_NOT_DEFINED.READY;

    }

    /**
     * Runs the clicking process
     */
    private void startClickingProcess(){
        ATClicker.stop();
        ATClicker.getInstance(this).execute();
    }

    /**
     * Stops the running process
     */
    private void stopClickingProcess(){
        Logger.d(LOG_TAG, "Stops the clicking process");
        ATClicker.stop();
    }

    /**
     * Initializes the default values
     */
    private void initDefaultValues(){

        Logger.d(LOG_TAG, "Initializes the default values");

        Switch typeOfStart = (Switch) findViewById(R.id.sTypeOfStartDelayed);
        typeOfStart.setChecked(Config.DEFAULT_START_TYPE);
        EditText et = (EditText) findViewById(R.id.etDelay);
        et.setText(Config.DEFAULT_DELAY);
        et = (EditText) findViewById(R.id.etTimeBeforeEachClick);
        et.setText(Config.DEFAULT_TIME_GAP);
        et = (EditText) findViewById(R.id.etRepeat);
        et.setText(Config.DEFAULT_REPEAT);
        CheckBox cb = (CheckBox) findViewById(R.id.cbVibrateOnStart);
        cb.setChecked(Config.DEFAULT_VIBRATE_ON_START);
        cb = (CheckBox) findViewById(R.id.cbVibrateOnClick);
        cb.setChecked(Config.DEFAULT_VIBRATE_ON_CLICK);
        et = (EditText) findViewById(R.id.etXcoord);
        et.setText(Config.DEFAULT_X_CLICK+"");
        et = (EditText) findViewById(R.id.etYcoord);
        et.setText(Config.DEFAULT_Y_CLICK+"");

    }

    /**
     * Displays the "about" information, i.e. some information about the app.
     * The display is made in the snackbar.
     */
    private void displayAboutInfo(){

        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch ( PackageManager.NameNotFoundException | NullPointerException e ){
            e.printStackTrace();
        }

        String tag = Config.VERSION_TAG_1;
        String code = pi.versionCode+"";
        String name = pi.versionName;

        StringBuffer sb = new StringBuffer();
        sb.append(tag).append(" - Version code : ").append(code).append(" - Version : ").append(name);
        showInSnackbarWithoutAction(sb.toString());

    }

    /**
     * Starts the activity which displays the credits / third-parties licences
     */
    private void startCreditsActivity(){
        startActivity( new Intent(ClickerActivity.this, CreditsActivity.class) );
    }

    /**
     * Displays in the snack bar a message
     * @param message - The string to display. Will do nothing if null or empty
     */
    private void showInSnackbarWithoutAction( String message ){
        if ( message == null || message.length() <= 0 ) return;
        View v = findViewById(R.id.mainView);
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).setAction("", null).show();
    }

    /**
     * Initializes the listeners on the widgets
     */
    private void initInnerListeners(){

        // The button to trigger the click(s) process
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStart);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayMessage(MessageTypes.START_PROCESS);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ){
                updateConfig();
                startClickingProcess();
            }
        });

        // The stop button
        fab = (FloatingActionButton) findViewById(R.id.fabStop);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayMessage(MessageTypes.STOP_PROCESS);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ){
                stopClickingProcess();
            }
        });

        // The button to add a new point to click
        fab = (FloatingActionButton) findViewById(R.id.fabAddPointToClick);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayMessage(MessageTypes.NEW_CLICK);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage(MessageTypes.NOT_IMPLEMENTED);
            }
        });

        // The switch button about the type of start
        // If checked, enabled the filed for the delay
        Switch sTypeOfStart = (Switch) findViewById(R.id.sTypeOfStartDelayed);
        sTypeOfStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ){
                EditText etDelay = (EditText) findViewById(R.id.etDelay);
                etDelay.setEnabled(isChecked);
                if ( "666".equals(etDelay.getText().toString()) ) Toast.makeText(ClickerActivity.this, "✿✿✿✿ ʕ •ᴥ•ʔ/ ︻デ═一 Hotter Than Hell !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Displays a message in the snackbar and the logger
     * @param mt - The type of the message to display
     */
    private void displayMessage( MessageTypes mt ){
        String m = null;
        switch ( mt ){
            case NEW_CLICK:
                m = ClickerActivity.this.getString(R.string.info_message_new_point);
                Logger.d("SmoothClicker", m);
                break;
            case START_PROCESS:
                m = ClickerActivity.this.getString(R.string.info_message_start);
                Logger.d("SmoothClicker", m);
                break;
            case STOP_PROCESS:
                m = ClickerActivity.this.getString(R.string.info_message_stop);
                Logger.d("SmoothClicker", m);
                break;
            case SU_GRANTED:
                m = ClickerActivity.this.getString(R.string.info_message_su_granted);
                Logger.i("SmoothClicker", m);
                break;
            case SU_NOT_GRANTED:
                m = ClickerActivity.this.getString(R.string.error_message_su_not_granted);
                Logger.e("SmoothClicker", m);
                break;
            case NOT_IMPLEMENTED:
                m = ClickerActivity.this.getString(R.string.error_not_implemented);
                Logger.e("SmoothClicker", m);
                break;
            default:
                m = null;
                break;
        }
        if ( m == null || m.length() <= 0 ) return;
        showInSnackbarWithoutAction(m);
    }


    /* *********** *
     * INNER ENUMS *
     * *********** */

    /**
     * The type of messages to display
     */
    private enum MessageTypes {
        SU_GRANTED,
        SU_NOT_GRANTED,
        START_PROCESS,
        STOP_PROCESS,
        NEW_CLICK,
        NOT_IMPLEMENTED
    } // private enum MESSAGE_TYPE

}
