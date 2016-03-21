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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pylapp.smoothclicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A BaseAdapter for the view containing the points to click on
 *
 * @author pylapp
 * @version 1.0.0
 * @since  17/03/2016
 * @see BaseAdapter
 */
public class PointsListAdapter extends BaseAdapter {


    /* ********** *
     * ATTRIBUTES *
     * ********** */

    /**
     *
     */
    private Context mContext;

    /**
     * The list of points to click on
     */
    private List<Point> mPoints;


    /* *********** *
     * CONSTRUCTOR *
     * *********** */

    /**
     * @param c - The context
     * @param xyPoints - An array list with each X/Y coords
     */
    public PointsListAdapter( Context c, ArrayList<Integer> xyPoints ){

        super();

        mContext = c;
        mPoints = new ArrayList<Point>();

        if ( xyPoints == null ){
            throw new IllegalArgumentException("The xyPoints is null !");
        }

        // If we have odd number of values, we have an unfilled point
        if ( xyPoints.size() % 2 != 0 ){
            throw new IllegalArgumentException("There is not the good number of values for xyPoints. The array list must be like {x1, y1, x2, y2, ..., xN, yN}");
        }

        mPoints.add( new Point( xyPoints.size() / 2 + " clicks"));
        for ( int i = 0; i < xyPoints.size(); i +=2 ){
            Point p = new Point(xyPoints.get(i), xyPoints.get(i+1));
            mPoints.add(p);
        }

    }


    /* ************************ *
     * METHODS FROM BaseAdapter *
     * ************************ */

    /**
     *
     * @return int -
     */
    @Override
    public int getCount() {
        return mPoints.size();
    }

    /**
     *
     * @param position
     * @return Object
     */
    @Override
    public Object getItem( int position ){
        return mPoints.get(position);
    }

    /**
     *
     * @param position -
     * @return long -
     */
    @Override
    public long getItemId( int position ){
        return position;
    }

    /**
     *
     * @param position -
     * @param convertView -
     * @param parent -
     * @return View -
     */
    @Override
    public View getView( int position, View convertView, ViewGroup parent ){

        LayoutInflater lf = LayoutInflater.from(mContext);
        convertView = lf.inflate(R.layout.list_points_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tvTitleOfPointList);

        tv.setText(mPoints.get(position).toString());

        return convertView;

    }


    /* ************* *
     * OTHER METHODS *
     * ************* */

    /**
     *
     * @param p - The point to add
     */
    public void add( Point p ){
        mPoints.add(p);
    }

    /**
     *
     * @param location - The index of the point to get
     * @return Point - The point at this location
     */
    public Point get( int location ){
        return mPoints.get(location);
    }

    /**
     * Returns the list of points
     * @return List<Point>
     */
    public List<Point> getList(){
        return mPoints;
    }

    /**
     *
     * @param location - The location of the point to remove
     * @return Point - The removed point
     */
    public Point remove( int location ){
        return mPoints.remove(location);
    }

    /**
     * Clears the list of points
     */
    public void clear(){
        mPoints.clear();
    }

    /**
     *
     * @return int - The number of points
     */
    public int size(){
        return mPoints.size();
    }


    /* ************* *
     * INNER CLASSES *
     * ************* */

    /**
     * Models a point with its X and Y coordinates
     */
    public static class Point {

        /**
         * The X coordinate
         */
        public int mX;
        /**
         * The Y coordinate
         */
        public int mY;
        /**
         * A description
         */
        public String mDesc;
        /**
         * Should we click on it ?
         */
        public boolean mIsUsable;

        public Point( int x, int y ){
            super();
            mX = x;
            mY = y;
            mDesc = null;
            mIsUsable = true;
        }

        public Point( String desc ){
            super();
            mDesc = desc;
            mIsUsable = false;
        }

        @Override
        public String toString(){
            if ( mDesc != null && mDesc.length() > 0 ) return mDesc;
            StringBuffer sb = new StringBuffer();
            sb.append("x = ").append(mX).append(" / y = ").append(mY);
            return sb.toString();
        }

    } // End of public static class Point

}
