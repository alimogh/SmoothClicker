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
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import pylapp.smoothclicker.android.R;

/**
 * The intro screen
 *
 * @author pylapp
 * @version 1.1.0
 * @since 13/04/2016
 * @see AppIntro
 */
public class IntroScreensActivity extends AppIntro {


    /* ********************* *
     * METHODS FROM AppIntro *
     * ********************* */

    /**
     * Initializes the activity containing intro screens
     * @param savedInstanceState -
     */
    @Override
    public void init( Bundle savedInstanceState ){

        addSlides();

        setBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        setSeparatorColor(ContextCompat.getColor(this, R.color.colorBlack));

        showSkipButton(true);
        setProgressButtonEnabled(true);

        //setZoomAnimation();

    }

    /**
     * Triggered when the "sip" button is pressed.
     * Finishes the activity and starts the "main" activity.
     */
    @Override
    public void onSkipPressed(){
        startMainActivity();
    }

    /**
     * Triggered when the "next" button is pressed.
     * Does nothing more than the basic behavior
     */
    @Override
    public void onNextPressed() {
        // Does nothing
    }

    /**
     * Triggered when the "done" button has been pressed.
     * Starts the clicker activity.
     */
    @Override
    public void onDonePressed() {
        startMainActivity();
    }

    /**
     * Triggered when a slide hass been changed.
     * Does nothing
     */
    @Override
    public void onSlideChanged() {
        // Does nothing
    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     * Starts the "main" activity, i;e. the clicker activity
     */
    private void startMainActivity(){
        Intent i = new Intent(IntroScreensActivity.this, ClickerActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Adds the slides to the carousel
     */
    private void addSlides() {

        // Slide 1: Welcome
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_welcome_label),
                getString(R.string.intro_welcome_desc),
                R.drawable.smooth_clicker,
                ContextCompat.getColor(this, R.color.colorBlack))
        );

        // Slide 2: Clicks
        addSlide(AppIntroFragment.newInstance(
                        getString(R.string.intro_clicks_label),
                        getString(R.string.intro_clicks_desc),
                        R.drawable.clicks,
                        ContextCompat.getColor(this, R.color.colorBlack))
        );

        // Slide 3: Notifications
        addSlide(AppIntroFragment.newInstance(
                        getString(R.string.intro_notifications_label),
                        getString(R.string.intro_notifications_desc),
                        R.drawable.notifications,
                        ContextCompat.getColor(this, R.color.colorBlack))
        );

        // Slide 4: Root ? Nope !
        addSlide(AppIntroFragment.newInstance(
                        getString(R.string.intro_root_label),
                        getString(R.string.intro_root_desc),
                        R.drawable.root,
                        ContextCompat.getColor(this, R.color.colorBlack))
        );

        // Slide 5: Free and open-source
        addSlide(AppIntroFragment.newInstance(
                        getString(R.string.intro_free_label),
                        getString(R.string.intro_free_desc),
                        R.drawable.open_sources,
                        ContextCompat.getColor(this, R.color.colorBlack))
        );

    }

}
