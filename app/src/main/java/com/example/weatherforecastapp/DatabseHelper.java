package com.example.weatherforecastapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME= "Weather.db";
   // public static final String TABLE_NAME= "WeatherStats";
    public static final String COL1="id";
    //public static final String COL2="city";
    public static final String COL2="date";
    public static final String COL3="temp";
    public static final String COL4="tempmin";
    public static final String COL5="tempmax";
    public static final String COL6="wind";
    public static final String COL7="preassure";
    public static final String COL8="humidity";




    public DatabseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    MainActivity ma = new MainActivity();


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

    public boolean insertData( String date,String temp,String tempmin,String tempmax,String wind,String preassure,String humidity){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL2,date);
        cv.put(COL3,temp);
        cv.put(COL4,tempmin);
        cv.put(COL5,tempmax);
        cv.put(COL6,wind);
        cv.put(COL7,preassure);
        cv.put(COL8,humidity);

        long res= db.insert("WeatherStats",null,cv);

        if (res==-1)
            return false;
        else
            return true;
    }


    public Cursor getAllHistory() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from WeatherStats",null);

        return res;



    }
}
