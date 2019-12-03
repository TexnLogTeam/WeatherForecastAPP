package com.example.weatherforecastapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME= "Weather.db";
    public static final String TABLE_NAME= "WeatherStats";
    public static final String COL1="id";
    public static final String COL2="date";
    public static final String COL3="temp";
    public static final String COL4="tempmin";
    public static final String COL5="tempmax";
    public static final String COL6="sunrise";
    public static final String COL7="sunset";
    public static final String COL8="wind";
    public static final String COL9="preassure";
    public static final String COL10="humidity";



    public DatabseHelper( Context context, ) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+ TABLE_NAME+"(ID INTEGER AUTO INCREMENT,DATE TEXT,TEMP TEXT,TEMPMIN TEXT,TEMPMAX TEXT,SUNRISE TEXT,SUNSET TEXT,WIND TEXT PREASSRE TEXT,HUMIDITY TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(("DROP TABLE IF EXISTS" + TABLE_NAME));
        onCreate(db);

    }
}
