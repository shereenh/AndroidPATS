package com.parkingauthorityticketingsystem.utils;

/**
 * Created by yiwu on 2017/11/12.
 */

public class DistanceUtil {
    public static double getDistance(double a, double b, double lat, double lng) {
       return Math.sqrt( Math.pow(a - lat,2) + Math.pow(b - lng,2));
    }
}
