package com.example.yesgxy520.jasontest;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener{
    AlertDialog.Builder dialog;
    List<City> cityList;
    HefengWeatherDB hefengWeatherDB;
    SQLiteDatabase db;
    final String NO_CITY="nocity";
    String cityId;
    String weather1_txt;
    String weather2_txt;
    String temp1_txt;
    String temp2_txt;
    LinearLayout weatherLayout;
    LinearLayout tempLayout;
    Button queryButton;
    EditText editText;
    TextView weather1_tv;
    TextView weather2_tv;
    TextView temp1_tv;
    TextView temp2_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryButton = (Button) findViewById(R.id.queryButton);
        editText = (EditText) findViewById(R.id.editText);
        weather1_tv=(TextView)findViewById(R.id.weather1_tv);
        weather2_tv=(TextView)findViewById(R.id.weather2_tv);
        temp1_tv=(TextView)findViewById(R.id.temp1_tv);
        temp2_tv=(TextView)findViewById(R.id.temp2_tv);
        weatherLayout=(LinearLayout)findViewById(R.id.weatherLayout);
        weatherLayout.setVisibility(View.INVISIBLE);
        tempLayout=(LinearLayout)findViewById(R.id.tempLayout);
        tempLayout.setVisibility(View.INVISIBLE);
        queryButton.setOnClickListener(this);
        hefengWeatherDB = HefengWeatherDB.getInstance(this);
        cityList=hefengWeatherDB.loadCities();
        db = hefengWeatherDB.db;
        if(cityList.size()==0) {
            HttpUtil.sendHttpRequest("https://api.heweather.com/x3/citylist?search=allchina&key=d06f6064818d4048a2670d839246f4fd",
                    new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("city_info");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    City city = new City();
                                    city.setCityName(jsonObject1.getString("city"));
                                    city.setCityId(jsonObject1.getString("id"));
                                    hefengWeatherDB.saveCity(city);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        public void onError(Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    });
        }

    }
    public void onClick(View view) {
        weatherLayout.setVisibility(View.INVISIBLE);
        tempLayout.setVisibility(View.INVISIBLE);
        Cursor cursor = db.query("City", null, "city_name=?", new String[]{editText.getText().toString()}, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                cityId = cursor.getString(cursor.getColumnIndex("city_id"));
            } while (cursor.moveToNext());
        }
        if (cursor.getCount()==0) {
            dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("警告");
            dialog.setMessage("没有这个城市");
            dialog.setCancelable(false);
            dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
            cursor.close();
        } else {
            cursor.close();
            queryWeather("https://api.heweather.com/x3/weather?cityid=" + cityId + "&key=d06f6064818d4048a2670d839246f4fd");
        }
    }

    public void queryWeather(String address) {
        HttpUtil.sendHttpRequest(address,
                new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("cond");
                            JSONObject jsonObject4=jsonObject2.getJSONObject("tmp");
                            weather1_txt = jsonObject3.getString("txt_d");
                            weather2_txt = jsonObject3.getString("txt_n");
                            temp1_txt=jsonObject4.getString("min");
                            temp2_txt=jsonObject4.getString("max");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weatherLayout.setVisibility(View.VISIBLE);
                                    tempLayout.setVisibility(View.VISIBLE);
                                    weather1_tv.setText(weather1_txt);
                                    weather2_tv.setText(weather2_txt);
                                    temp1_tv.setText(temp1_txt+"度");
                                    temp2_tv.setText(temp2_txt+"度");
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                });
    }


}



  //                      textView = (TextView) findViewById(R.id.textview);
//        HttpUtil.sendHttpRequest("https://api.heweather.com/x3/citylist?search=allchina&key=d06f6064818d4048a2670d839246f4fd",
//                new HttpCallbackListener() {
//                    @Override
//                    public void onFinish(String response) {
//                        try {
//                            JSONArray jsonArray=new JSONArray("city info");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                            }
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                            JSONObject jsonObject2 = jsonObject1.getJSONObject("basic");
//                            JSONObject jsonObject3 = jsonObject2.getJSONObject("update");
//                            abc = jsonObject3.getString("loc");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textView.setText(abc);
//                                }
//                            });
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                });
//    }

