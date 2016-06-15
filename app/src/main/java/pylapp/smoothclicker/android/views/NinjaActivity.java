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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pylapp.smoothclicker.android.R;
import pylapp.smoothclicker.android.clickers.ATClicker;
import pylapp.smoothclicker.android.json.JsonFileParser;
import pylapp.smoothclicker.android.json.NotSuitableJsonConfigFileException;
import pylapp.smoothclicker.android.json.NotSuitableJsonPointsFileException;
import pylapp.smoothclicker.android.notifiers.NotificationsManager;
import pylapp.smoothclicker.android.views.PointsListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A kind of main activity of this Smooth Clicker app.
 * It parses the JSON file containing the points to click on, it parses also the JSON file containing the configuration to use,
 * and it starts the clicking process with these values.
 * This activity can be used in a standalone context: the user does not want to choose the points himself, but just start an already-defined-process.
 *
 * To click on all the points defined in the JSON file:
     <pre>
        am start -n pylapp.smoothclicker.android/pylapp.smoothclicker.android.views.NinjaActivity
     </pre>
 *
 *
 * @version 1.2.0
 * @since 10/05/2016
 * @see AppCompatActivity
 */
public class NinjaActivity extends AppCompatActivity {


    /* ********** *
     * ATTRIBUTES *
     * ********** */


    /**
     * The list of points to click on
     */
    private List<PointsListAdapter.Point> mPointsToClickOn;

    //private static final String LOG_TAG = NinjaActivity.class.getSimpleName();


    /* ****************************** *
     * METHODS FROM AppCompatActivity *
     * ****************************** */

    /**
     * Triggered to create the view
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninja);
        messageToUser(getString(R.string.app_name_ninja));
    }


    /**
     * Triggered when the view is started
     */
    @Override
    public void onResume(){
        super.onResume();
        if ( ! initConfigValues() ) finish();
        NotificationsManager.getInstance(NinjaActivity.this).refresh(NinjaActivity.this);
        if ( ! initPointsToClickOn() ) finish();
        try {
            startClickingProcess();
        } catch ( Exception e ){
            errorToUser(e.getMessage());
        }
        finish();
    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     * Initializes the config values from a dedicated JSON file
     *
     * @return boolean - True if no problem occurs with the config file, false if something wrong happens with it
     */
    public boolean initConfigValues(){

        messageToUser(getString(R.string.ninja_init_config));

        try {
            JsonFileParser.instance.parseConfigFile(NinjaActivity.this);
        } catch ( NotSuitableJsonConfigFileException nsjcfe ){
            nsjcfe.printStackTrace();
            errorToUser(nsjcfe.getMessage());
            return false;
        }

        return true;

    }

    /**
     * Initializes the points to click on defined in a JSON file
     *
     * @return boolean - True if no problem occurs with the points file, false if something wrong happens with it
     */
    public boolean initPointsToClickOn(){

        messageToUser(getString(R.string.ninja_init_points));

        try {
            int [] pointsAsArray = JsonFileParser.instance.getPointFromJsonFile(NinjaActivity.this, JsonFileParser.TypeOfPoints.ALL_POINTS);
            mPointsToClickOn = new ArrayList<>();
            for ( int i = 0; i < pointsAsArray.length; i+=2 ){
                mPointsToClickOn.add( new PointsListAdapter.Point(pointsAsArray[i], pointsAsArray[i+1]) );
            }
            return true;
        } catch ( NotSuitableJsonPointsFileException e ){
            e.printStackTrace();
            errorToUser(e.getMessage());
            return false;
        }

    }

    /**
     *  Starts the clicking process
     */
    public void startClickingProcess(){

        messageToUser(getString(R.string.ninja_start_click_process));

        // Get the points
        List<PointsListAdapter.Point> filteredPointsToClickON = new ArrayList<>();
        if ( mPointsToClickOn == null
                || mPointsToClickOn.size() <= 0 ){
            throw new IllegalStateException("Not enough point !");
        }
        filteredPointsToClickON.addAll(mPointsToClickOn);

        // Go !
        ATClicker.stop();
        ATClicker.getInstance(this).execute(filteredPointsToClickON);

    }

    /**
     * Notifies to the user what the app is doing
     * @param message - The message to send
     */
    private void messageToUser( String message ){
        if ( message == null || message.length() <= 0 ) message = "Working...'";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifies to the user what the app is doing
     * @param message - The message to send
     */
    private void errorToUser( String message ){
        if ( message == null || message.length() <= 0 ) message = "Error...'";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
