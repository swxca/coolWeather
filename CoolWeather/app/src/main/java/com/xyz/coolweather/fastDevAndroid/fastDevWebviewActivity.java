package com.xyz.coolweather.fastDevAndroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zhangtao on 16/4/27.
 */
public class fastDevWebviewActivity extends Activity {
    static fastDevWebView fastDevWebView;
    static String str;
    TextView appBar;
    static int appBarColor;
    RelativeLayout barLayout;
    static int iconBack;

    private static WebViewClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        appBar = new TextView(this);
        appBar.setTextColor(Color.WHITE);
        //使用getApplicationContext,保证activity销毁时webview被顺利回收
        fastDevWebView = new fastDevWebView(getApplicationContext());

        barLayout=new RelativeLayout(this);
        RelativeLayout.LayoutParams barLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, fastDevScreen.dpChangepx(this, 55));
        ImageView backBtn=new ImageView(this);
        backBtn.setBackground(getResources().getDrawable(iconBack));
        RelativeLayout.LayoutParams backBtnLayoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        backBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        backBtnLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setActionBarColor();
        //appBar.setLayoutParams(new LinearLayout.LayoutParams((int) fastScreen.getScreenWidthPixels(this), fastScreen.dpChangepx(this, 55)));
        //Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
        appBar.setGravity(Gravity.CENTER);
        appBar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        RelativeLayout.LayoutParams appBarLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        appBarLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);


        fastDevWebView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (str.startsWith("http")) {
            fastDevWebView.setUrlWithProgress(this, str,appBar);
        } else {
            fastDevWebView.loadHtmlFile(str);
        }
        if (fastDevWebView != null) {
            fastDevWebView.setWebViewClient(mClient);
        }

        barLayout.addView(backBtn, backBtnLayoutParams);
        barLayout.addView(appBar,appBarLayoutParams);

        linearLayout.addView(barLayout,barLayoutParams);
        linearLayout.addView(fastDevWebView);
        setContentView(linearLayout);
    }
    //跳转一个webview,actionbar的颜色自己指定
    public static void startWebviewActivity(Activity activity, String url, int color,int inputIconBack) {
        appBarColor = color;
        iconBack=inputIconBack;
        str = url;
        Intent intent = new Intent(activity, fastDevWebviewActivity.class);
        activity.startActivity(intent);
    }
    //跳转一个webview,actionbar的颜色自己指定
    public static void startWebviewActivity(Activity activity, String url, int color,int inputIconBack ,WebViewClient client) {
        appBarColor = color;
        iconBack=inputIconBack;
        str = url;
        mClient=client;
        Intent intent = new Intent(activity, fastDevWebviewActivity.class);
        activity.startActivity(intent);
    }
    //跳转一个webview,actionbar的默认颜色是蓝色.
    public static void startWebviewActivity(Activity activity, String url,int inputIconBack) {
        appBarColor = -1;
        iconBack=inputIconBack;
        str = url;
        Intent intent = new Intent(activity, fastDevWebviewActivity.class);
        activity.startActivity(intent);
    }
    //设置Actionbar的颜色
    public void setActionBarColor() {
        if (appBarColor == -1) {
            barLayout.setBackgroundColor(Color.BLUE);
        } else {
            barLayout.setBackgroundColor(appBarColor);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fastDevWebView.removeAllViews();
        fastDevWebView.destroy();
    }
}
