package com.parkingauthorityticketingsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parkingauthorityticketingsystem.entity.Shiftlog;

import java.util.ArrayList;

/**
 * Created by lintang on 10/08/2017.
 */

public class ShiftlogDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shiftlog5.db";
    static final String TB_NAME="shiftlog";
    static final String ID="_id";
    static final String PLATE="platenumber";
    static final String STATE="state";
    static final String STREET="street";
    static final String STREETNUM="streetnumber";
    static final String FIRSTOBSERVE="firstObserve";
    static final String MAXTIME = "maxtime";
    static final String METERNUM = "meternum";
    static final String X = "x_coordinate";
    static final String Y = "y_coordinate";

    public ShiftlogDBHelper(Context context) {
        super(context,DB_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("database","sss");
        String sql = "create table if not exists " + TB_NAME + "(" + ID + " integer primary key autoincrement,"
                + PLATE + " varchar(20),"
                + STATE+ " varchar(10),"
                + STREET+ " varchar(30),"
                + STREETNUM+ " varchar(30),"
                + MAXTIME + " INTEGER,"
                + METERNUM + " INTEGER,"
                + X + " REAL,"
                + Y + " REAL,"
                + FIRSTOBSERVE + " varchar(30))";
        db.execSQL(sql);
    }
    public void  insertRecords(SQLiteDatabase db){

    }
    public int  insertRecord(Shiftlog sl){
        ContentValues values = new ContentValues();
        values.put(PLATE,sl.getPlateNumber());
        values.put(STATE,sl.getState());
        values.put(STREET,sl.getStreet());
        values.put(STREETNUM,sl.getStreetNum());
        values.put(MAXTIME,sl.getMaxTime());
        values.put(METERNUM,sl.getMeterNum());
        values.put(FIRSTOBSERVE,sl.getFirstObserve());
        values.put(X,sl.getX_coordinate());
        values.put(Y,sl.getY_coordinate());
        SQLiteDatabase db = getWritableDatabase();
        long count = db.insert(TB_NAME,null,values);
        return (int) count;
    }
    //Delete by id
    public int deleteRecord(int id){
        SQLiteDatabase db = getWritableDatabase();
        int count =  db.delete(TB_NAME,ID+"=?", new String[]{""+id});
        return  count;
    }
    public int deleteAllRecord(){
        SQLiteDatabase db = getWritableDatabase();
        int count =  db.delete(TB_NAME,null, null);
        return  count;
    }
    public Shiftlog queryRecord(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TB_NAME,null,ID+"=?", new String[]{""+id},null,null,null);
        if(c.moveToNext()){

            String plateNumber = c.getString(c.getColumnIndex(PLATE));
            String state = c.getString(c.getColumnIndex(STATE));
            String street = c.getString(c.getColumnIndex(STREET));
            String streetNumber = c.getString(c.getColumnIndex(STREETNUM));
            int timeLeft = c.getInt(c.getColumnIndex(MAXTIME));
            String firstObserve = c.getString(c.getColumnIndex(FIRSTOBSERVE));
            String meterNumber = c.getString(c.getColumnIndex(METERNUM));
            double x_coordinate = c.getDouble(c.getColumnIndex(X));
            double y_coordinate = c.getDouble(c.getColumnIndex(Y));
            Shiftlog sl = new Shiftlog(id,plateNumber,state,street,streetNumber,meterNumber,timeLeft,firstObserve,x_coordinate,y_coordinate);
            return sl;
        }
        return null;
    }
    public Shiftlog queryNew(){
        Shiftlog sf = new Shiftlog();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+TB_NAME+" order by "+ID+" desc limit 0,1; ",null );
        Shiftlog shiftlogs = new Shiftlog();
        while(c.moveToNext()){
            int id = c.getInt(0);
            String plateNumber = c.getString(c.getColumnIndex(PLATE));
            String state = c.getString(c.getColumnIndex(STATE));
            String street = c.getString(c.getColumnIndex(STREET));
            String streetNumber = c.getString(c.getColumnIndex(STREETNUM));
            int timeLeft = c.getInt(c.getColumnIndex(MAXTIME));
            String firstObserve = c.getString(c.getColumnIndex(FIRSTOBSERVE));
            String meterNumber = c.getString(c.getColumnIndex(METERNUM));
            double x_coordinate = c.getDouble(c.getColumnIndex(X));
            double y_coordinate = c.getDouble(c.getColumnIndex(Y));
            sf = new Shiftlog(id,plateNumber,state,street,streetNumber,meterNumber,timeLeft,firstObserve,x_coordinate,y_coordinate);

            break;

        }
        c.close();
        return sf;
    }
    public int updateRecord(Shiftlog sl){
        ContentValues values = new ContentValues();
        values.put(PLATE,sl.getPlateNumber());
        values.put(STATE,sl.getState());
        values.put(STREET,sl.getStreet());
        values.put(STREETNUM,sl.getStreetNum());
        values.put(MAXTIME,sl.getMaxTime());
        values.put(METERNUM,sl.getMeterNum());
        values.put(FIRSTOBSERVE,sl.getFirstObserve());
        values.put(X,sl.getX_coordinate());
        values.put(Y,sl.getY_coordinate());
        SQLiteDatabase db = getWritableDatabase();
        int count = db.update(TB_NAME,values, ID+"=?", new String[]{sl.getId()+""});
        return count;
    }
    public ArrayList<Shiftlog> queryAll(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+TB_NAME+" order by "+ID+" desc; ",null );// read data from down to up
        ArrayList<Shiftlog> shiftlogs = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(0);
            String plateNumber = c.getString(c.getColumnIndex(PLATE));
            String state = c.getString(c.getColumnIndex(STATE));
            String street = c.getString(c.getColumnIndex(STREET));
            String streetNumber = c.getString(c.getColumnIndex(STREETNUM));
            int timeLeft = c.getInt(c.getColumnIndex(MAXTIME));
            String firstObserve = c.getString(c.getColumnIndex(FIRSTOBSERVE));
            String meterNumber = c.getString(c.getColumnIndex(METERNUM));
            double x_coordinate = c.getDouble(c.getColumnIndex(X));
            double y_coordinate = c.getDouble(c.getColumnIndex(Y));
            Shiftlog sf = new Shiftlog(id,plateNumber,state,street,streetNumber,meterNumber,timeLeft,firstObserve,x_coordinate,y_coordinate);
            shiftlogs.add(sf);

        }
        c.close();
        return shiftlogs;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> queryStreets(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select distinct street from "+TB_NAME+";",null );// read data from down to up
        ArrayList<String> streets = new ArrayList<>();
        while(c.moveToNext()){
            String street = c.getString(c.getColumnIndex(STREET));
            streets.add(street);

        }
        c.close();
        return streets;
    }

    public ArrayList<Shiftlog> queryOne(String oneStreet){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+TB_NAME+" where street=\""+oneStreet+"\" order by "+ID+" desc; ",null );// read data from down to up
        ArrayList<Shiftlog> shiftlogs = new ArrayList<>();
        while(c.moveToNext()){
            int id = c.getInt(0);
            String plateNumber = c.getString(c.getColumnIndex(PLATE));
            String state = c.getString(c.getColumnIndex(STATE));
            String street = c.getString(c.getColumnIndex(STREET));
            String streetNumber = c.getString(c.getColumnIndex(STREETNUM));
            int timeLeft = c.getInt(c.getColumnIndex(MAXTIME));
            String firstObserve = c.getString(c.getColumnIndex(FIRSTOBSERVE));
            String meterNumber = c.getString(c.getColumnIndex(METERNUM));
            double x_coordinate = c.getDouble(c.getColumnIndex(X));
            double y_coordinate = c.getDouble(c.getColumnIndex(Y));
            Shiftlog sf = new Shiftlog(id,plateNumber,state,street,streetNumber,meterNumber,timeLeft,firstObserve,x_coordinate,y_coordinate);
            shiftlogs.add(sf);

        }
        c.close();
        return shiftlogs;
    }

}
