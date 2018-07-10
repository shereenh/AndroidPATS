package com.parkingauthorityticketingsystem.location_related;

import android.content.Context;

import com.parkingauthorityticketingsystem.location_related.GeoLocation;

import java.util.Random;

/**
 * Created by shereen on 10/14/2017.
 *
 * Class used to find the 5 nearest street names
 *
 */

public class LocationInformation {

    String result="";
    double newLat;
    double newLon;
    double factor = 1.0;
    int numOfAddresses = 0,randomNumber;
    GeoLocation geoLocation;
    String newStreetName = "";
    double[] displacement = {0.0005, -0.0005};

    public String getFive(double lat,double lon,Context context){

       // lat = 40.7465185;
        //lon = -73.97309230000002;
        int i,j;
        Random rand = new Random();
        geoLocation = new GeoLocation(context);

        try {
            double k = 0.0, k1 = 0.0;
            if (!(newStreetName = geoLocation.getAddressTwo(lat, lon, false)).equals("null"))
                result = newStreetName + ",";

            outerloop:
            for (j = 0; j < 10; j++) {

                for (i = 0; i < 4; i++) {

                    //  System.out.println("Again");

                    k = displacement[rand.nextInt(2)];
                    k1 = displacement[rand.nextInt(2)];

                    newLat = lat + (k * factor);    //get random coordinates
                    newLon = lon + (k1 * factor);

                    // System.out.println("Iteration " + i + "\nk= " + k + " k1= " + k1 + "\nnewLat= " + newLat + " newLon= " + newLon);
                    newStreetName = geoLocation.getAddressTwo(newLat, newLon, false);

//                System.out.println("Iteration " + i + "\nnewLat= " + newLat + " newLon= " + newLon
//                        + "\nnewStreetName= " + newStreetName + "results= " + result + "numOfAdd= " + numOfAddresses);

                    //check if same street name
                    if (!result.contains(newStreetName)
                            && !newStreetName.equals("null")) {    //not same street name
                        numOfAddresses++;
                        result += newStreetName + ",";
                        //    System.out.println(numOfAddresses+" "+i+" "+j);
                        if (numOfAddresses == 4) {
                            System.out.println("1j= " + j);
                            break outerloop;
                        }
                    }
                }
                factor = factor + 1.0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    //    System.out.println("2j= "+j);
        return result;
    }

    public String getInitial(double lon, double lat,Context context){
        geoLocation = new GeoLocation(context);
        String address;
        try {
            for (int i = 0; i < 5; i++) {
                address = geoLocation.getAddressTwo(lon, lat, true);
                System.out.println("Initial: " + address);
                if (!address.contains("null")) {
                    return address;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";
    }
}
