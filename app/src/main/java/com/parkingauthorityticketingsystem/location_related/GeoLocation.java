package com.parkingauthorityticketingsystem.location_related;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by shereen on 10/14/2017.
 *
 * Converts GPS coordinates to Street address
 * 1. Using Geocoder (not used) or
 * 2. Using Google Api Geocode as Web Request
 */

public class GeoLocation {


    private final String USER_AGENT = "Mozilla/5.0";
    Geocoder geocoder;
    String myAddress = "ABC";
    static int i = 0;

    public GeoLocation(Context context){
        geocoder = new Geocoder(context, Locale.ENGLISH);
    }

    public String getAddress(double LATITUDE, double LONGITUDE, boolean state){

        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuffer str = new StringBuffer();
                str.append(returnedAddress.getThoroughfare());         // Street Address
                if(state) str.append(","+returnedAddress.getAdminArea());          //State
                myAddress = str.toString();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            myAddress = "Can't get Address!";
        }
        return myAddress;

    }

    public String getAddressTwo(double LATITUDE, double LONGITUDE, boolean state) throws
    Exception{

        JSONObject result = getLocationFormGoogle(LATITUDE + "," + LONGITUDE );

        return getCityAddress(result);
    }

    protected JSONObject getLocationFormGoogle(String placesName) throws Exception {

        String apiRequest = "https://maps.googleapis.com/maps/api/geocode/json?key=";
        String key = "AIzaSyCib0KMeWqNm4TcErUHcgY6-RC0a17qZqs";
                //"https://maps.googleapis.com/maps/api/geocode/json?latlng=" + placesName+"&types=route"; //+ "&ka&sensor=false"

        URL obj = new URL(apiRequest
            + key
            + "&latlng=" + placesName);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Progress: "+response.toString());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(response.toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject;
    }

    protected static String getCityAddress( JSONObject result ){
        if( result.has("results") ){
            try {
                JSONArray array = result.getJSONArray("results");
                if( array.length() > 0 ){
                    JSONObject place = array.getJSONObject(0);
                    JSONArray components = place.getJSONArray("address_components");
                    for( int i = 0 ; i < components.length() ; i++ ){
                        JSONObject component = components.getJSONObject(i);
                        JSONArray types = component.getJSONArray("types");
                        for( int j = 0 ; j < types.length() ; j ++ ){
                            if( types.getString(j).equals("route") ){
                                return component.getString("long_name");
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
