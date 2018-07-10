package com.parkingauthorityticketingsystem.location_related;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parkingauthorityticketingsystem.LocationFragment;

import java.io.InputStream;

/**
 * Created by shereen on 11/1/2017.
 */

public class LocationService extends IntentService{

    private static final String TAG = LocationService.class.getSimpleName();

    public static final String PENDING_RESULT_EXTRA = "pending_result";
    public static final String URL_EXTRA = "url";
    public static final String LOC_RESULT_EXTRA = "city";

    public static final int RESULT_CODE = 0;
    public static final int INVALID_URL_CODE = 1;
    public static final int ERROR_CODE = 2;

    public static final int INITIAL_CODE = 9;
    public static final int STREETS_CODE = 8;

    double latitiude,longitude;
    LocationInformation f;
    String LABEL;
    String answer;


    public LocationService() {
        super(TAG);

        System.out.println("New Service created");
    }

        @Override
        protected void onHandleIntent(Intent intent) {
            PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT_EXTRA);
          //  InputStream in = null;
            try {
                Bundle b = intent.getExtras();
                latitiude = b.getDouble("LATITUDE");
                longitude = b.getDouble("LONGITUDE");
                LABEL = b.getString("LABEL");

                System.out.println("From service: "+latitiude+"  "+longitude+" "+LABEL);

                f = new LocationInformation();
                Intent result = new Intent();

                if(LABEL.equals("initial")){
                    answer = f.getInitial(latitiude,longitude, LocationFragment.conTEXT);
                    result.putExtra(LOC_RESULT_EXTRA, answer);
                    reply.send(this, INITIAL_CODE, result);
                }
                else if(LABEL.equals("streets")){
                    answer = f.getFive(latitiude,longitude, LocationFragment.conTEXT);
                    result.putExtra(LOC_RESULT_EXTRA, answer);
                    reply.send(this, STREETS_CODE, result);
                }

                System.out.println("Service result: "+answer);

               // result.putExtra("returnLABEL",LABEL);


            } catch (PendingIntent.CanceledException exc) {
                Log.i(TAG, "reply cancelled", exc);
            }
        }

        @Override
        protected void finalize() throws Throwable{
            f = null;
            super.finalize();
        }

}
