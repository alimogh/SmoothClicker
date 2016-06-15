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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import pylapp.smoothclicker.android.R;

import java.util.ArrayList;

/**
 * A translucent activity to help the user to click on a point on its screen over another app for example.
 * To use it:
     <pre>
         <activity
             android:name=".views.SelectMultiPointsActivity"
             android:theme="@style/Theme.Transparent">
         </activity>
     </pre>
 *
 * @author pylapp
 * @version 1.3.0
 * @since 17/03/2016
 */
public class SelectMultiPointsActivity extends TranslucentActivity {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The list of coordinates of points to click on.
     * The list contains each X and Y value as follows:
         <pre>
         { x0, y 0, x1, y1, ..., xN, yN}
         </pre>
     */
    // FIXME Dirty, heavy
    private ArrayList<Integer> mXYCoordinates;

    /**
     * The handler which makes the helping toasts appear
     */
    private Handler mHandlerHelpingToasts;
    /**
     * The runnable for the handler which makes the helping toasts appear
     */
    private Runnable mRunnableHelpingToasts;
    /**
     * The time to wait before displaying (in ms)
     */
    private static final int TIME_FOR_HELPING_TOASTS = 30*1000;


    //private static final String LOG_TAG = SelectMultiPointsActivity.class.getSimpleName();


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

        mXYCoordinates = new ArrayList<>();

        // Get the touch coordinates
        View v = findViewById(R.id.translucentMainView);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Get the coordinates
                final int X = (int) event.getX();
                final int Y = (int) event.getY();

                mXYCoordinates.add(X);
                mXYCoordinates.add(Y);

                initHelpingToastsRoutine();

                // Notify the user
                showInSnackbarWithDismissAction("Click X = " + X + " / Y = " + Y);

                return false;

            }
        });

    }

    /**
     * Triggered when the activity is resumed
     */
    @Override
    public void onResume(){
        super.onResume();
        initHelpingToastsRoutine();
        displayHelpingToast();
    }

    /**
     * Triggered when the activity is paused
     */
    @Override
    public void onPause(){
        cleanHelpingToastsRoutine();
        super.onPause();
    }

    /**
     * Triggered when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        cleanHelpingToastsRoutine();
        Intent returnIntent = new Intent();
        returnIntent.putIntegerArrayListExtra(ClickerActivity.SELECT_POINTS_ACTIVITY_RESULT, mXYCoordinates);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /**
     * Displays an helping toast saying to the user he has to click on the screen then back to save its actions
     */
    private void displayHelpingToast(){
        Toast.makeText(SelectMultiPointsActivity.this, getString(R.string.info_message_invite_new_points), Toast.LENGTH_SHORT).show();
    }

    /**
     * Initializes the routine which make helping toasts appear
     */
    private void initHelpingToastsRoutine(){

        cleanHelpingToastsRoutine();

        mHandlerHelpingToasts = new Handler();
        mRunnableHelpingToasts = new Runnable() {
            @Override
            public void run() {
                displayHelpingToast();
                mHandlerHelpingToasts.postDelayed( mRunnableHelpingToasts, TIME_FOR_HELPING_TOASTS );
            }
        };

        mHandlerHelpingToasts.postDelayed(mRunnableHelpingToasts, TIME_FOR_HELPING_TOASTS);

    }

    /**
     * Cleans the routine which make helping toast appear
     */
    private void cleanHelpingToastsRoutine(){

        if ( mHandlerHelpingToasts != null && mRunnableHelpingToasts != null ){
            mHandlerHelpingToasts.removeCallbacks(mRunnableHelpingToasts);
            mHandlerHelpingToasts = null;
        }

        if ( mRunnableHelpingToasts != null ) mRunnableHelpingToasts = null;

    }

    /**
     * Displays in the snack bar a message
     * @param message - The string to display. Will do nothing if null or empty
     */
    private void showInSnackbarWithoutAction( String message ){
        if ( message == null || message.length() <= 0 ) return;
        View v = findViewById(R.id.translucentMainView);
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).setAction("", null).show();
    }

    /**
     * Displays in the snack bar a message with a dismiss action.
     * If the user click on the dismiss action, its selected point will be removed
     * @param message - The string to display. Will do nothing if null or empty
     */
    private void showInSnackbarWithDismissAction( String message ){

        if ( message == null || message.length() <= 0 ) return;

        View v = findViewById(R.id.translucentMainView);

        Snackbar.make(v, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_action_dismiss),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeLastPoint();
                            }
                        }
                ).show();

    }

    /**
     * Removes form the list of coordinates the last point, i.e. the two last items
     */
    private void removeLastPoint(){
        if ( mXYCoordinates == null || mXYCoordinates.size() <= 0 ) return;
        int index = mXYCoordinates.size() - 1; // Start from 0...
        mXYCoordinates.remove(index); // The Y
        mXYCoordinates.remove(index-1); // The X
    }

}
