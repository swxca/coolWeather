package com.example.yesgxy520.jasontest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yesgxy520 on 6/1/2016.
 */
public class HefengWeatherDB {
    public static final String DB_NAME="hefeng_weather";
    public static final int VERSION=1;
    public static HefengWeatherDB hefengWeatherDB;
    public static SQLiteDatabase db;

    private HefengWeatherDB(Context context){
        HefengWeatherOpenHelper dbHelper=new HefengWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }

    public synchronized static HefengWeatherDB getInstance(Context context){
        if(hefengWeatherDB== null){
            hefengWeatherDB=new HefengWeatherDB(context);
        }
        return hefengWeatherDB;
    }



    public void saveCity(City city) {
        if (city!= null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_id", city.getCityId());
            db.insert("City", null, values);
        }

    }

    public List<City> loadCities(){
        List<City> list=new ArrayList<City>();
        Cursor cursor=db.query("City",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city=new City();
                city.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                list.add(city);
            }while(cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;

    }


}
