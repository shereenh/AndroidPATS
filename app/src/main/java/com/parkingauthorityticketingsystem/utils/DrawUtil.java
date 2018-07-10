package com.parkingauthorityticketingsystem.utils;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parkingauthorityticketingsystem.R;
import com.parkingauthorityticketingsystem.entity.Shiftlog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yiwu on 2017/10/20.
 */

public class DrawUtil {
    public static void drawMarkers(List<Shiftlog> shiftlogs, GoogleMap googleMap) {
        /*helper = new ShiftlogDBHelper(getContext());
        shiftlogs = helper.queryAll(); // Read data from database*/
        Log.i("aaaaa",shiftlogs.size()+"");
        for(Shiftlog sl : shiftlogs) {
            LatLng position = new LatLng(sl.getX_coordinate(),sl.getY_coordinate());
            Marker position_marker;

            double timeLeftMilli = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            try {
                curDate = formatter.parse(sl.getFirstObserve());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timeLeftMilli = sl.getMaxTime()*60*1000 - (System.currentTimeMillis()- curDate.getTime());
            timeLeftMilli = timeLeftMilli / 60 / 1000;
            if(timeLeftMilli > 5){
                position_marker = googleMap.addMarker(new MarkerOptions().position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_green_48dp)));
                position_marker.setTag(sl);
            }else if(timeLeftMilli < 5 && timeLeftMilli > 0){
                position_marker = googleMap.addMarker(new MarkerOptions().position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_yellow_48dp)));
                position_marker.setTag(sl);
            }
            else{
                position_marker = googleMap.addMarker(new MarkerOptions().position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_red_48dp)));
                position_marker.setTag(sl);

            }

        }
    }
}
