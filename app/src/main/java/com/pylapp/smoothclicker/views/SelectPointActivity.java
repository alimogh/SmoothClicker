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

package com.pylapp.smoothclicker.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.pylapp.smoothclicker.R;

/**
 * A translucent activity to help the user to click on a point on its screen over another app for example.
 * To use it:
     <pre>
         <activity
             android:name=".views.SelectPointActivity"
             android:theme="@style/Theme.Transparent">
         </activity>
     </pre>
 *
 * @author pylapp
 * @version 1.0.0
 * @since 17/03/2016
 */
@Deprecated
public class SelectPointActivity extends TranslucentActivity {


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

        Toast.makeText(SelectPointActivity.this, getString(R.string.info_message_invite_new_point), Toast.LENGTH_SHORT).show();

        // Get the touch coordinates
        View v = findViewById(R.id.translucentMainView);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Get the coordinates
                final int X = (int) event.getX();
                final int Y = (int) event.getY();

                int [] result = new int[2];
                result[0] = X;
                result[1] = Y;

                // Notify the user
                Toast.makeText(SelectPointActivity.this, "Click X="+X+" / Y=" + Y, Toast.LENGTH_SHORT).show();

                // Finish after sending data to the parent activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra(ClickerActivity.SELECT_POINT_ACTIVITY_RESULT,result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

                return false;

            }
        });

    }

    /**
     * Triggered when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

}
