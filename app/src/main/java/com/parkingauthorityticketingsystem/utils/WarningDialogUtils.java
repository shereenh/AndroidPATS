package com.parkingauthorityticketingsystem.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by yiwu on 10/9/2017.
 */

public class WarningDialogUtils {
    public static void warning(Context context, String string){
        //make sure input is valid
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Warning").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        if(string.equals("PlateNumber_null")){
            alertDialog.setMessage("You should input PlateNumber!").create().show();
        }else if(string.equals("MaxTime_null")){
            alertDialog.setMessage("You should input Max Time!").create().show();
        }


    }

}
