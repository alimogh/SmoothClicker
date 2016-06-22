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

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import pylapp.smoothclicker.android.R;


/**
 * The activity which displays the credits / third-parties licences.
 *
 * @author pylapp
 * @version 2.0.0
 * @since 15/03/2016
 */
public class CreditsActivity extends AppCompatActivity {


    //private static final String LOG_TAG = CreditsActivity.class.getSimpleName();


    /* ****************************** *
     * METHODS FROM AppCompatActivity *
     * ****************************** */

    /**
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        ListView lv = (ListView)findViewById(R.id.list);
        String[] labels = getApplicationContext().getResources().getStringArray(R.array.credits_labels);
        String[] descriptions = getApplicationContext().getResources().getStringArray(R.array.credits_descriptions);
        String[] urls = getApplicationContext().getResources().getStringArray(R.array.credits_urls);
        String[] licenses = getApplicationContext().getResources().getStringArray(R.array.credits_licenses);
        lv.setAdapter(new CreditsBaseAdapter( labels, descriptions, urls, licenses, this));
    }

    /* *********** *
     * INNER CLASS *
     * *********** */

    /**
     * An adapter for the list of credits
     */
    public static class CreditsBaseAdapter extends BaseAdapter {

        String [] labels;
        String [] descriptions;
        String [] urls;
        String [] licenses;
        Context context;
        LayoutInflater layoutInflater;

        public CreditsBaseAdapter( String [] labels, String[] descriptions,
                                   String [] urls, String[] licenses, Context context ){
            super();
            if ( labels == null ) throw new IllegalArgumentException("Labels cannot be null!");
            if ( descriptions == null ) throw new IllegalArgumentException("Descriptions cannot be null!");
            if ( urls == null ) throw new IllegalArgumentException("URLs cannot be null!");
            if ( licenses == null ) throw new IllegalArgumentException("Licenses cannot be null!");
            if ( context == null ) throw new IllegalArgumentException("Context cannot be null!");
            this.labels = labels;
            this.descriptions = descriptions;
            this.urls = urls;
            this.licenses = licenses;
            this.context = context;
            layoutInflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount(){
            return labels.length;
        }

        @Override
        public Object getItem( int position ){
            return null;
        }

        @Override
        public long getItemId( int position ){
            return position;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ){

            // Get the data
            String label = labels[position];
            String description = descriptions[position];
            String url = urls[position];
            String license = licenses[position];

            // Build a view
            convertView = layoutInflater.inflate(R.layout.content_credit_row, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_credits_label);
            tv.setText(label);
            tv = (TextView) convertView.findViewById(R.id.tv_credits_description);
            tv.setText(description);
            tv = (TextView) convertView.findViewById(R.id.tv_credits_url);
            tv.setText(url);
            tv = (TextView) convertView.findViewById(R.id.tv_credits_license);
            tv.setText(license);

            return convertView;

        }

    } // End of public static class CreditsBaseAdapter extends BaseAdapter

}
