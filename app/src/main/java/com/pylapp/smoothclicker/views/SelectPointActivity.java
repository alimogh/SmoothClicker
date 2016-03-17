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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
