package com.xyz.coolweather.fastDevAndroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.bigkoo.svprogresshud.SVProgressHUD;

public class fastDevAlert {
    // 有一个ok的阻塞alert,请不要在主线程调用
    // 在用户点击ok后返回
    public static void AlertOkSync(final String message,final String ButtonMsg,final Context context){
        AlertOkCancelSync(message,ButtonMsg,null,context);
    }

    // 返回1 表示用户点了Ok(OkButtonText),返回2表示用户点了Cancel(CancelButtonText)
    // OkButtonText/CancelButtonText 传入null 表示不显示这个按钮
    // 请不要在主线程调用
    public static int AlertOkCancelSync(final String message,final String OkButtonText,final String CancelButtonText,final Context context){
        final fastDevSignalChan chan = fastDevSignalChan.New();
        final fastDevPointer<Integer> pClickIndex = new fastDevPointer<Integer>(-1);
        fastDevThread.RunOnMainThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(message);
                if (OkButtonText != null) {
                    builder.setPositiveButton(OkButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pClickIndex.val = 1;
                            chan.send();
                        }
                    });
                }
                if (CancelButtonText != null) {
                    builder.setNegativeButton(CancelButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pClickIndex.val = 2;
                            chan.send();
                        }
                    });
                }
                builder.show();
            }
        });
        chan.recv();
        return pClickIndex.val;
    }

    public static void ShowAlertView(String title, String message, Context context, final Runnable clickCommand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickCommand.run();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }
//    public static void ShowAlertViewI18n(String title, String message, Context context, final Runnable clickCommand) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.确定), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                clickCommand.run();
//            }
//        });
//        builder.setNegativeButton(context.getResources().getString(R.string.取消), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//        builder.show();
//    }

    public static void ShowCustomAlertView(String title, String message, String PosBtn,String NegBtn, Context context, final Runnable clickCommand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(PosBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickCommand.run();
            }
        });
        builder.setNegativeButton(NegBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }

    // 显示等待中弹窗.
    public static void ShowWaitingDialog(final Context context,final String msg){
        fastDevThread.RunOnMainThread(new Runnable() {
            @Override
            public void run() {
                //SVProgressHUD.showInfoWithStatus(context, msg);
            }
        });
    }
    // 隐藏等待中弹窗.
    public static void HiddenWaitingDialog(final Context context){
        fastDevThread.RunOnMainThread(new Runnable() {
            @Override
            public void run() {
                //SVProgressHUD.dismiss(context);
            }
        });
    }
}
