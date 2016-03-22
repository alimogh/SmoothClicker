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

package com.pylapp.smoothclicker.clicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.pylapp.smoothclicker.R;
import com.pylapp.smoothclicker.notifiers.NotificationsManager;
import com.pylapp.smoothclicker.utils.Config;
import com.pylapp.smoothclicker.tools.Logger;
import com.pylapp.smoothclicker.views.PointsListAdapter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Async Task which consists on executing the click task
 *
 * @author pylapp
 * @version 2.4.0
 * @since 02/03/2016
 * @see android.os.AsyncTask
 */
// FIXME Use the Observer/Observable design pattern with NotificationsManager as Observer and ATClicker as observable
public class ATClicker extends AsyncTask<List<PointsListAdapter.Point>, Void, Void >{


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * Represents an external process. Enables writing to, reading from, destroying,
     * and waiting for it, as well as querying its exit value.
     * It is sued to get a Super User access.
     */
    private Process mProcess;

    /**
     * The output stream of the {@link Process} object
     */
    private DataOutputStream mOutputStream;

    /**
     * The application context
     */
    private Context mContext;

    /**
     * The list of points to click on
     */
    private List<PointsListAdapter.Point> mPoints;
    /**
     * The type of start
     */
    private boolean mIsStartDelayed;
    /**
     * The delay of the start
     */
    private int mDelay;
    /**
     * The time to wait between each click
     */
    private int mTimeGap;
    /**
     * The amount of repeat to do
     */
    private int mRepeat;
    /**
     * If the repeat is endless
     */
    private boolean mIsRepeatEndless;
    /**
     * The singleton of this class
     */
    private static ATClicker sInstance; // FIXME Dirty, heavy...


    /* ********* *
     * CONSTANTS *
     * ********* */

    private static final String LOG_TAG = "ATClicker";


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Constructor
     * @param c - The context to use to get the SharedPreferences to get the configuration
     */
    private ATClicker( Context c ){
        super();
        if ( c == null ) throw new IllegalArgumentException("The Context _c_ variable is null !");
        mContext = c;
    }


    /* ********************** *
     * METHODS FROM AsyncTask *
     * ********************** */

    /**
     *
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        SharedPreferences sp = mContext.getSharedPreferences(Config.SMOOTHCLICKER_SHARED_PREFERENCES_NAME, Config.SP_ACCESS_MODE);
        mIsStartDelayed = sp.getBoolean(Config.SP_KEY_START_TYPE_DELAYED, Config.DEFAULT_START_DELAYED);
        mDelay = sp.getInt(Config.SP_KEY_DELAY, Integer.parseInt(Config.DEFAULT_DELAY));
        if ( mDelay < 0 ){
            throw new IllegalArgumentException("The delay cannot be < 0!");
        }
        mTimeGap = sp.getInt(Config.SP_KEY_TIME_GAP, Integer.parseInt(Config.DEFAULT_TIME_GAP));
        if ( mTimeGap < 0 ){
            throw new IllegalArgumentException("The time gap between clicks cannot be < 0!");
        }
        mRepeat = sp.getInt(Config.SP_KEY_REPEAT, Integer.parseInt(Config.DEFAULT_REPEAT));
        if ( mRepeat < 0 ) {
            throw new IllegalArgumentException("The repeat amount cannot be < 0!");
        }
        mIsRepeatEndless = sp.getBoolean(Config.SP_KEY_REPEAT_ENDLESS, Config.DEFAULT_REPEAT_ENDLESS);
        NotificationsManager.getInstance(mContext).stopAllNotifications();
        NotificationsManager.getInstance(mContext).makeStartNotification();

    }

    /**
     *
     * @param params - Nothing
     * @return Void - Nothing
     */
    @Override
    protected Void doInBackground( List<PointsListAdapter.Point>... params ){ // FIXME Too heavy

        if ( checkIfCancelled() ) return null;
        if ( params == null || params.length <= 0 ){
            throw new IllegalArgumentException("No points to click on!");
        }

        mPoints = params[0];

        /*
         * Step 1 : Get the process as "su"
         */
        try {
            Logger.d(LOG_TAG, "Get 'su' process...");
            mProcess = Runtime.getRuntime().exec("su");
        } catch ( IOException e ){
            Logger.e(LOG_TAG, "Exception thrown during 'su' : " + e.getMessage());
            e.printStackTrace();
            displayToast("An error occurs during super-user process retrieve: "+e.getMessage());
            displayToast(mContext.getString(R.string.error_su_missing));
            return null;
        }

        if ( checkIfCancelled() ) return null;

        /*
         * Step 2 : Get the process output stream
         */
        Logger.d(LOG_TAG, "Get 'su' process data output stream...");
        mOutputStream = new DataOutputStream(mProcess.getOutputStream());

        if ( checkIfCancelled() ) return null;

        /*
         * Step 3 : Execute the command, the same we can execute from ADB within a terminal and deal with the configuration
             $ adb devices
             > ...
             $ adb shell
                $ input tap XXX YYY
         */

        // Should we delay the execution ?
        if ( mIsStartDelayed ){
            Logger.d(LOG_TAG, "The start is delayed, will sleep : "+mDelay);
            final int count = mDelay;
            // Loop for each second
            for ( int i = 1; i <= count; i++ ){
                try {
                    if ( checkIfCancelled() ) return null;
                    NotificationsManager.getInstance(mContext).makeCountDownNotification(mDelay-i);
                    Thread.sleep(1000); // Sleep of 1 second
                } catch ( InterruptedException ie ){}
            }
            NotificationsManager.getInstance(mContext).stopAllNotifications();
        }

        NotificationsManager.getInstance(mContext).makeClicksOnGoingNotification();

        /*
         * Is the execution endless ?
         */
        if (mIsRepeatEndless) {

            while (true) {
                if (checkIfCancelled()) return null;
                Logger.d(LOG_TAG, "Should repeat the process ENDLESSLY");
                executeTap();
                // Should we wait before the next action ?
                if ( mTimeGap > 0 ){
                    try {
                        Logger.d(LOG_TAG, "Should wait before each process occurrences : "+mTimeGap);
                        Thread.sleep(mTimeGap*1000);
                    } catch ( InterruptedException ie ){}
                } else {
                    Logger.d(LOG_TAG, "Should NOT wait before each process occurrences : "+mTimeGap);
                }
            }

        /*
         * Should we repeat the execution ?
         */
        } else if ( mRepeat > 1 ){

            Logger.d(LOG_TAG, "Should repeat the process : " + mRepeat);
            for ( int i = 0; i < mRepeat; i++ ){
                if ( checkIfCancelled() ) return null;
                executeTap();
                // Should we wait before the next action ?
                if ( mTimeGap > 0 ){
                    try {
                        Logger.d(LOG_TAG, "Should wait before each process occurrences : "+mTimeGap);
                        Thread.sleep(mTimeGap*1000);
                    } catch ( InterruptedException ie ){}
                } else {
                    Logger.d(LOG_TAG, "Should NOT wait before each process occurrences : "+mTimeGap);
                }
            }

        /*
         * Just one execution
         */
        } else {
            if ( checkIfCancelled() ) return null;
            Logger.d(LOG_TAG, "Should NOT repeat the process : "+mRepeat);
            executeTap();
        }

        NotificationsManager.getInstance(mContext).stopAllNotifications();
        NotificationsManager.getInstance(mContext).makeClicksOverNotification();
        Logger.d(LOG_TAG, "The input event seems to be triggered");

        return null;

    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     *
     * @return boolean - True if the AsyncTask has been cancelled, false otherwise
     */
    private boolean checkIfCancelled(){
        if ( isCancelled() || getStatus() == Status.FINISHED ){
            NotificationsManager.getInstance(mContext).stopAllNotifications();
            NotificationsManager.getInstance(mContext).makeClicksStoppedNotification();
            return true;
        }
        return false;
    }

    /**
     * @param c - The context to sue to retrieve data from Shared Preferences
     * @return ATClicker - The singleton
     */
    public static final ATClicker getInstance( Context c ){
        if ( sInstance == null ) sInstance = new ATClicker(c);
        return sInstance;
    }

    /**
     * Stops the AsyncTask
     */
    public static final void stop(){
        Logger.d(LOG_TAG, "Stops the clicking process");
        if ( sInstance == null ){
            Logger.w(LOG_TAG, "The ATClicker is null");
            return;
        }
        if ( ! sInstance.isCancelled() ) sInstance.cancel(true);
        else Logger.w(LOG_TAG, "The ATClicker has been canceled previously");
        sInstance = null;
    }

    /**
     * Executes the tap action
     */
    private void executeTap(){

        for ( PointsListAdapter.Point p : mPoints ){

            if ( ! p.mIsUsable ) continue;

            int x = p.mX;
            int y = p.mY;

            String shellCmd = "/system/bin/input tap " + x + " " + y + "\n";
            Logger.d(LOG_TAG, "The system command will be executed : " + shellCmd);
            try {
                if ( mProcess == null || mOutputStream == null ) throw new IllegalStateException("The process or its stream is not defined !");
                mOutputStream.writeBytes(shellCmd);
                NotificationsManager.getInstance(mContext).makeNewClickNotifications(x, y);
            } catch ( IOException ioe ){
                Logger.e(LOG_TAG, "Exception thrown during tap execution : " + ioe.getMessage());
                ioe.printStackTrace();
                displayToast("An error occurs during tap execution: " + ioe.getMessage());
            }

            // Should we wait before the next action ?
            if ( mTimeGap > 0 ){
                try {
                    Logger.d(LOG_TAG, "Should wait before each process occurrences : "+mTimeGap);
                    Thread.sleep(mTimeGap*1000);
                } catch ( InterruptedException ie ){}
            } else {
                Logger.d(LOG_TAG, "Should NOT wait before each process occurrences : "+mTimeGap);
            }

        } // End of for ( PointsListAdapter.Point p : mPoints )

    }

    /**
     * Displays a toast with a dedicated message
     * @param m - The message to display in a toast
     */
    private void displayToast( final String m ){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, m, Toast.LENGTH_LONG).show();
            }
        });
    }

}
