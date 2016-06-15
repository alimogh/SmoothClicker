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

package pylapp.smoothclicker.android.json;

import android.content.Context;
import android.content.SharedPreferences;
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

import pylapp.smoothclicker.android.utils.Config;
import pylapp.smoothclicker.android.views.PointsListAdapter;

/**
 * Class which parses the JSON file which must contain the points to click on.
 * based on the "singleton" design pattern.
 *
     <pre>
         adb push myFile.json /storage/emulated/legacy/Download/
     </pre>
 *
 * @author pylapp
 * @version 1.0.0
 * @since 04/05/2016
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

    // For the JSON points file

    /**
     * The key of the array containing the JSON points
     */
    public static final String JSON_ARRAY_POINTS = "points";
    /**
     * The key of the value containing the X point
     */
    public static final String JSON_OBJECT_X = "x";
    /**
     * The key of the value containing the Y point
     */
    public static final String JSON_OBJECT_Y = "y";
    /**
     * The description of the point
     */
    public static final String JSON_OBJECT_DESC = "desc";
    /**
     * The  comment in the JSON point's file
     */
    public static final String JSON_OBJECT_COMMENT = "note";

    // For the JSON config file

    /**
     * The key to get the boolean value for a delayed start mode
     */
    public static final String JSON_OBJECT_DELAYED_START = "delayedStart";
    /**
     * The key to get the int value for a delay
     */
    public static final String JSON_OBJECT_DELAY = "delay";
    /**
     * The key to get the int value for the time gap
     */
    public static final String JSON_OBJECT_TIME_GAP = "timeGap";
    /**
     * The key to get the int value for the repeat
     */
    public static final String JSON_OBJECT_REPEAT = "repeat";
    /**
     * The key to get the boolean value for the endless repeat mode
     */
    public static final String JSON_OBJECT_ENDLESS_REPEAT = "endlessRepeat";
    /**
     * The key to get the boolean value for the vibrate on start mode
     */
    public static final String JSON_OBJECT_VIBRATE_ON_START = "vibrateOnStart";
    /**
     * The key to get the boolean value for the vibrate on click mode
     */
    public static final String JSON_OBJECT_VIBRATE_ON_CLICK = "vibrateOnClick";
    /**
     * The key to get the boolean value for the notifications mode
     */
    public static final String JSON_OBJECT_NOTIFICATIONS = "notifications";


    // For the Watch

    private static final String LOG_TAG = JsonFileParser.class.getSimpleName();



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
     * Parses the JSON file which contains the points to click on
     *
     * @param c - The context, cannot be null
     * @return List<Point> - The points picked form the JSON file
     * @throws NotSuitableJsonPointsFileException - If something wrong occurs during the parse
     */
    private List<PointsListAdapter.Point> parseJsonPointsFile(Context c) throws NotSuitableJsonPointsFileException {

        if ( c == null ) throw new IllegalArgumentException("The context cannot be null");

        List<PointsListAdapter.Point> pointsToClick = new ArrayList<>();

        JSONObject jsonData = null;

        // Get the file, its content and parse it
        File appDir = Config.getAppFolder();
        File file = new File(appDir.getAbsolutePath()+"/"+ Config.FILE_JSON_POINTS_NAME);
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
     * @param what - The point(s) to get
     * @return int[] -
     * @throws NotSuitableJsonPointsFileException - If something wrong occurs with the JSON file
     */
    public int[] getPointFromJsonFile( Context c, TypeOfPoints what ) throws NotSuitableJsonPointsFileException {

        if ( c == null ) throw new IllegalArgumentException("The context cannot be null");

        int [] points = null;
        List<PointsListAdapter.Point> pointsFromJson = parseJsonPointsFile(c);

        if ( pointsFromJson == null || pointsFromJson.size() <= 0 ){
            throw new NotSuitableJsonPointsFileException("The JSON file does not contain points");
        }

        switch ( what ){
            case FIRST_POINT:
                points = new int[2];
                points[0] = pointsFromJson.get(0).x;
                points[1] = pointsFromJson.get(0).y;
                break;
            case SECOND_POINT:
                if ( pointsFromJson.size() < 2 ){
                    throw new NotSuitableJsonPointsFileException("The JSON file does not contain enough points");
                }
                points = new int[2];
                points[0] = pointsFromJson.get(1).x;
                points[1] = pointsFromJson.get(1).y;
                break;
            case ALL_POINTS:
                points = new int[pointsFromJson.size()*2];
                int i = 0;
                for ( PointsListAdapter.Point p : pointsFromJson ){
                    points[i++] = p.x;
                    points[i++] = p.y;
                }
                break;
        }

        return points;

    }

    /**
     * Parses the JSON file which contains the config to apply on th click process
     * @param c - The context which enables to update the shard preferences, must not be null
     * @throws NotSuitableJsonConfigFileException - If a problem occurs with the JSON config file
     */
    public void parseConfigFile( Context c ) throws NotSuitableJsonConfigFileException {

        if ( c == null ) throw new IllegalArgumentException("The context must not be null");

        JSONObject jsonData = null;

        // Get the file, its content and parse it
        File appDir = Config.getAppFolder();
        File file = new File(appDir.getAbsolutePath()+"/"+ Config.FILE_JSON_CONFIG_NAME);
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
            throw new NotSuitableJsonConfigFileException("A problem occurs with the JSON file : "+e.getMessage());
        }

        // Get the values in JSON
        boolean isDelayed = false;
        int delayInS = 0;
        int timeGapInS = 0;
        int repeatEach = 0;
        boolean isEndlessRepeat = false;
        boolean isVibrateOnStart = false;
        boolean isVibrateOnClick = false;
        boolean isDisplayNotifs = false;
        try {
            isDelayed = jsonData.getBoolean(JSON_OBJECT_DELAYED_START);
            delayInS = jsonData.getInt(JSON_OBJECT_DELAY);
            timeGapInS = jsonData.getInt(JSON_OBJECT_TIME_GAP);
            repeatEach = jsonData.getInt(JSON_OBJECT_REPEAT);
            isEndlessRepeat = jsonData.getBoolean(JSON_OBJECT_ENDLESS_REPEAT);
            isVibrateOnStart = jsonData.getBoolean(JSON_OBJECT_VIBRATE_ON_START);
            isVibrateOnClick = jsonData.getBoolean(JSON_OBJECT_VIBRATE_ON_CLICK);
            isDisplayNotifs = jsonData.getBoolean(JSON_OBJECT_NOTIFICATIONS);
        } catch ( JSONException jsone ){
            jsone.printStackTrace();
            throw new NotSuitableJsonConfigFileException("A problem occurs with the JSON file : "+jsone.getMessage());
        }

        // Update the config
        SharedPreferences sp = c.getSharedPreferences(Config.SMOOTHCLICKER_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Config.SP_KEY_START_TYPE_DELAYED, isDelayed);
        editor.putInt(Config.SP_KEY_DELAY, delayInS);
        editor.putInt(Config.SP_KEY_TIME_GAP, timeGapInS);
        editor.putInt(Config.SP_KEY_REPEAT, repeatEach);
        editor.putBoolean(Config.SP_KEY_REPEAT_ENDLESS, isEndlessRepeat);
        editor.putBoolean(Config.SP_KEY_VIBRATE_ON_START, isVibrateOnStart);
        editor.putBoolean(Config.SP_KEY_VIBRATE_ON_CLICK, isVibrateOnClick);
        editor.putBoolean(Config.SP_KEY_NOTIF_ON_CLICK, isDisplayNotifs);

        editor.apply();

    }

    /**
     *
     * @return String - The path to the JSON config file
     */
    public static String getFullPathToConfigFile(){
        File appDir = Config.getAppFolder();
        return appDir.getAbsolutePath()+"/"+ Config.FILE_JSON_CONFIG_NAME;
    }

    /**
     *
     * @return String - The path to the JSON points file
     */
    public static String getFullPathToPointsFile(){
        File appDir = Config.getAppFolder();
        return appDir.getAbsolutePath()+"/"+ Config.FILE_JSON_POINTS_NAME;
    }

    /**
     *
     * @return String - The JSON points file
     */
    public static File getPointsFile(){
        File appDir = Config.getAppFolder();
        return new File(appDir, Config.FILE_JSON_POINTS_NAME);
    }


    /* *********** *
     * INNER ENUMS *
     * *********** */

    /**
     * The types of points to deal with
     */
    public enum TypeOfPoints {
        FIRST_POINT,
        SECOND_POINT,
        ALL_POINTS
    }


}
