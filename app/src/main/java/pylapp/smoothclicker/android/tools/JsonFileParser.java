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

package pylapp.smoothclicker.android.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pylapp.smoothclicker.android.views.PointsListAdapter;

/**
 * Class which parses a JSON file which must contain the points to click on.
 * Based on the "singleton" design pattern.
 *
 <pre>
        adb push smoothclicker_points.json /storage/emulated/legacy/Download/
 </pre>
 *
 *
 * @version 1.0.0
 * @since 04/05/2016
 * @see Exception
 */
public class JsonFileParser {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     * The singleton
     */
    public static final JsonFileParser instance = new JsonFileParser();


    /* ********* *
     * CONSTANTS *
     * ********* */

    /**
     * The file to the JSON file, in /storage/emulated/legacy/Download/
     */
    private static final String JSON_FILE_PATH = "smoothclicker_points.json";

    /**
     * The key of the array containing the JSON points
     */
    private static final String JSON_ARRAY_POINTS = "points";
    /**
     * The key of the value containing the X point
     */
    private static final String JSON_OBJECT_X = "x";
    /**
     * The key of the value containing the Y point
     */
    private static final String JSON_OBJECT_Y = "y";
    /**
     * The description of the point
     */
    private static final String JSON_OBJECT_DESC = "desc";

    private static final String LOG_TAG = "JsonFileParser";



    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * Default constructor
     */
    private JsonFileParser(){
        super();
    }


    /* ******* *
     * METHODS *
     * ******* */

    /**
     * Parses the JSON file
     *
     * @param c - The context, cannot be null
     * @return List<Point> - The points picked form the JSON file
     * @throws NotSuitableJsonPointsFileException - If something wrong occurs during the parse
     */
    private List<PointsListAdapter.Point> parseJsonFile( Context c ) throws NotSuitableJsonPointsFileException {

        if ( c == null ) throw new IllegalArgumentException("The context cannot be null");

        List<PointsListAdapter.Point> pointsToClick = new ArrayList<>();

        JSONObject jsonData = null;

        // Get the file, its content and parse it
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir.getAbsolutePath()+"/"+JSON_FILE_PATH);
        try {
            InputStream is = new FileInputStream( file );
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");
            jsonData = new JSONObject(jsonString);
        } catch ( IOException | JSONException e  ){
            e.printStackTrace();
            throw new NotSuitableJsonPointsFileException("A problem occurs with the JSON file : "+e.getMessage());
        }

        // Store the parsed data in the list of points
        try {
            JSONArray points = jsonData.getJSONArray(JSON_ARRAY_POINTS);
            for ( int i = 0; i < points.length(); i++ ){
                JSONObject point = points.getJSONObject(i);
                int x = Integer.parseInt(point.getString(JSON_OBJECT_X));
                int y = Integer.parseInt(point.getString(JSON_OBJECT_Y));
                String desc = point.getString(JSON_OBJECT_DESC);
                PointsListAdapter.Point p = new PointsListAdapter.Point(x, y, desc);
                pointsToClick.add(p);
                Log.d(LOG_TAG, "Adds new point to click on: "+p.toString());
            }
        } catch ( Exception e ){
            e.printStackTrace();
            throw new NotSuitableJsonPointsFileException("A problem occurs with the JSON file : "+e.getMessage());
        }

        return pointsToClick;

    }

    /**
     * Returns the point in the JSON file.
     * Will load in a first time the JSON file.
     *
     * @param c  - The context, must not be null
     * @return int[] -
     * @throws NotSuitableJsonPointsFileException - If something wrong occurs with the JSON file
     */
    public int[] getPointFromJsonFile( Context c ) throws NotSuitableJsonPointsFileException {

        if ( c == null ) throw new IllegalArgumentException("The context cannot be null");

        int [] points = null;
        List<PointsListAdapter.Point> pointsFromJson = parseJsonFile(c);

        if ( pointsFromJson == null || pointsFromJson.size() <= 0 ){
            throw new NotSuitableJsonPointsFileException("The JSON file does not contain points");
        }

        points = new int[pointsFromJson.size()*2];
        int i = 0;
        for ( PointsListAdapter.Point p : pointsFromJson ){
            points[i++] = p.x;
            points[i++] = p.y;
        }

        return points;

    }

}
