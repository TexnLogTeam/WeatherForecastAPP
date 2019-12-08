package com.example.weatherforecastapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME= "Weather.db";
   // public static final String TABLE_NAME= "WeatherStats";
    public static final String COL1="id";
    public static final String COL2="date";
    public static final String COL3="temp";
    public static final String COL4="tempmin";
    public static final String COL5="tempmax";
    public static final String COL8="wind";
    public static final String COL9="preassure";
    public static final String COL10="humidity";



   /* public DatabseHelper(@Nullable context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    } */

    public DatabseHelper(MainActivity mainActivity) {
        super(mainActivity, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry="create table WeatherStats (id integer primary key autoincrement not null, [date] text, [temp] text, [tempmin] text,[tempmax] text,[wind] text, [preassure] text,[humidity] text)";
        db.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(("DROP TABLE IF EXISTS WeatherStats" ));
        onCreate(db);

    }

    public String addRecord(String date,String temp,String tempmin,String tempmax,String wind,String preassure,String humidity){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("DATE",date);
        cv.put("TEMP",temp);
        cv.put("TEMPMIN",tempmin);
        cv.put("TEMPMAX",tempmax);
        cv.put("WIND",wind);
        cv.put("PREASSURE",preassure);
        cv.put("HUMIDITY",humidity);

        long res= db.insert("WeatherStats",null,cv);

        if (res==1)
            return "Failed to insert current weather data on history. Please try again later. ";
        else
            return "Data succesfully inserted on history !";
    }
}
