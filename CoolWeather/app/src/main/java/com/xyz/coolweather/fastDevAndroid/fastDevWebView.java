package com.xyz.coolweather.fastDevAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by zhangtao on 16/4/26.
 */
public class fastDevWebView extends WebView {
    String actionBarTitle;
    //三个构造方法,互相调用.(还可以在第三个里增加XML的属性然后调用)
    public fastDevWebView(Context context) {
        this(context, null);
    }
    public fastDevWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public fastDevWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //初始化一些webview的设置
    public void initSettings() {
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
    }
    //载入有进度条的链接
    public void setUrlWithProgress(final Activity activity, String url, final TextView textView) {
        initSettings();
        this.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                activity.setProgress(newProgress * 1000);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                textView.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(activity)
                        .setTitle("提示")
                        .setMessage(message)
                        .setPositiveButton("我知道了", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result.confirm();
                            }
                        });
                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
            }
        });
        this.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                //TODO 这里放无网络图片
                view.loadData("file:///android_asset/MobileNetUnconnected.html", "text/html", "UTF-8");
            }
        });
        this.loadUrl(url);
    }




    //交互的接口
    public interface JStoJavaInterface {
        @JavascriptInterface
        void JStoJava(String str);

    }
    private JStoJavaInterface jStoJavaInterface;
    //JS和java的交互,调用的时候在JS的代码里用interfaceName.JStoJava("")调用,
    //JStoJava里实现android的逻辑代码
    //android的target大于17重写后要在方法上加注解JavascriptInterface
    public void setJStoJava(JStoJavaInterface jStoJavaInterface, String interfaceName) {
        this.jStoJavaInterface = jStoJavaInterface;
        initSettings();
        this.addJavascriptInterface(jStoJavaInterface, interfaceName);
    }
    //java调用JS
    public void javaToJS(String functionName) {
        initSettings();
        this.loadUrl("javascript:" + functionName);
    }
    //载入一个html文件,这个文件放在系统默认的assets文件夹里.
    public void loadHtmlFile(String fileName) {
        initSettings();
        this.loadUrl("file:///android_asset/" + fileName);
    }
}



