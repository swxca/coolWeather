package com.xyz.coolweather.activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyz.coolweather.R;
import com.xyz.coolweather.model.City;
import com.xyz.coolweather.model.CoolWeatherDB;
import com.xyz.coolweather.model.County;
import com.xyz.coolweather.model.Province;
import com.xyz.coolweather.util.HttpCallbackListener;
import com.xyz.coolweather.util.HttpUtil;
import com.xyz.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yesgxy520 on 6/1/2016.
 */

//这个Activity还有一种写法,就是直接继承ListActivity,对于这种纯列表的Activity,
//ListActivity是很方便的,具体用法google一下.
public class ChooseAreaActivityTest extends Activity {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    public static final int MESSEAGE_PROVINCE=0;
    public static final int MESSEAGE_CITY=1;
    public static final int MESSEAGE_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList= new ArrayList<String>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;
    //handler的使用要注意避免内存泄露.
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSEAGE_PROVINCE:
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    titleText.setText("中国—晓艺制造");
                    currentLevel=LEVEL_PROVINCE;
                    break;
                case MESSEAGE_CITY:
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    titleText.setText(selectedProvince.getProvinceName()+"-晓艺制造");
                    currentLevel=LEVEL_CITY;
                    break;
                case MESSEAGE_COUNTY:
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    titleText.setText(selectedCity.getCityName()+"-晓艺制造");
                    currentLevel=LEVEL_COUNTY;
                    break;
                default:
                    break;
            }

        }

    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView=(ListView) findViewById(R.id.list_view);
        titleText=(TextView) findViewById(R.id.title_text);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        coolWeatherDB=CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(index);
                    queryCities();
                }else if(currentLevel==LEVEL_CITY) {
                    selectedCity = cityList.get(index);
                    queryCounties();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                provinceList = coolWeatherDB.loadProvince();
                if (provinceList.size() > 0) {
                    dataList.clear();
                    for (Province province : provinceList) {
                        dataList.add(province.getProvinceName());
                    }
                    Message message=new Message();
                    message.what=MESSEAGE_PROVINCE;
                    handler.sendMessage(message);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryFromServer(null, "province");
                        }
                    });
                }
            }
        }).start();


    }

    private void queryCities(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                cityList=coolWeatherDB.loadCities(selectedProvince.getId());
                if(cityList.size()>0) {
                    dataList.clear();
                    for (City city : cityList) {
                        dataList.add(city.getCityName());
                    }
                    Message message=new Message();
                    message.what=MESSEAGE_CITY;
                    handler.sendMessage(message);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryFromServer(selectedProvince.getProvinceCode(),"city");
                        }
                    });

                }
            }
        }).start();
    }

    private void queryCounties() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                countyList = coolWeatherDB.loadCounties(selectedCity.getId());
                if (countyList.size() > 0) {
                    dataList.clear();
                    for (County county : countyList) {
                        dataList.add(county.getCountyName());
                    }
                    Message message = new Message();
                    message.what = MESSEAGE_COUNTY;
                    handler.sendMessage(message);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryFromServer(selectedCity.getCityCode(),"county");
                        }
                    });

                }
            }
        }).start();
    }

    private void  queryFromServer(final String code,final  String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvincesResponse(coolWeatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(coolWeatherDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(coolWeatherDB, response, selectedCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvince();
                                ;
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivityTest.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    public void onBackPressed(){
        if(currentLevel==LEVEL_COUNTY){
            queryCities();
        }else if(currentLevel==LEVEL_CITY){
            queryProvince();
        }else{
            finish();
        }
    }




}
