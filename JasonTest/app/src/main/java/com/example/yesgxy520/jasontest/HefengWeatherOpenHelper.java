package com.example.yesgxy520.jasontest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yesgxy520 on 6/1/2016.
 */
public class HefengWeatherOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_CITY="create table City ( "
            +" id integer primary key autoincrement, "
            +"city_name text,"
            +" city_id text)";

    public HefengWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CITY);
    }

    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){

    }

}
