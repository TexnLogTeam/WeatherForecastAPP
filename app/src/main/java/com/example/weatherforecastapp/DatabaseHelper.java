package com.example.weatherforecastapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "Weather.db";
    public static final String TABLE_NAME = "WeatherStats_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "date";
    public static final String COL3 = "temp";
    public static final String COL4 = "tempmin";
    public static final String COL5 = "tempmax";
    public static final String COL6 = "sunrise";
    public static final String COL7 = "sunset";
    public static final String COL8 = "wind";
    public static final String COL9 = "preassure";
    public static final String COL10 = "humidity";










    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table"+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,temp TEXT,tempmin TEXT,tempmax TEXT,sunrise TEXT,sunset TEXT,wind TEXT,preassure TEXT,humidity TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String date,String temp,String tempmin,String tempmax,String wind,String preassure,String humidity)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,date);
        contentValues.put(COL3,temp);
        contentValues.put(COL4,tempmin);
        contentValues.put(COL5,tempmax);
        contentValues.put(COL8,wind);
        contentValues.put(COL9,preassure);
        contentValues.put(COL10,humidity);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result==-1) {
            return false;
        }
        else {
            return true;
        }





    }
}
