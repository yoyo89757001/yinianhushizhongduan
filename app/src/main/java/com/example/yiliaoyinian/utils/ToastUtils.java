package com.example.yiliaoyinian.utils;


import android.app.Dialog;
import android.content.Intent;

import android.util.Log;

import android.view.View;


import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.LoginActivity;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;


public class ToastUtils {

//    public static void show (final Activity context, final String s){
//        context.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast tastyToast = TastyToast.makeText(context, s, TastyToast.LENGTH_LONG, TastyToast.INFO);
//                tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                tastyToast.show();
//            }
//        });
//
//    }

    public static void showToken(){
        new CommomDialog(AppManager.getAppManager().currentActivity(), R.style.dialogs, "登录过期,请重新登录?", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    //确定
                    try {
                        AppManager.getAppManager().currentActivity().startActivity(new Intent(AppManager.getAppManager().currentActivity(), LoginActivity.class));
                        dialog.dismiss();
                        AppManager.getAppManager().finishAllActivity();
                        Log.d("UnFinshFragment", "执行");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).setTitle("确认").setPositiveButton("确定").show();
    }

    public static void setSuccess(String msg, View view){
        AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                            .setTipWord(msg)
                            .create();
                    if (AppManager.getAppManager().currentActivity()!=null)
                        qmuiTipDialog.show();
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qmuiTipDialog.isShowing() &&(AppManager.getAppManager().currentActivity()!=null))
                                    qmuiTipDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, 1500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setFAIL(String msg, View view){
        AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                            .setTipWord(msg)
                            .create();
                    if (AppManager.getAppManager().currentActivity()!=null)
                        qmuiTipDialog.show();
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qmuiTipDialog.isShowing() &&(AppManager.getAppManager().currentActivity()!=null))
                                    qmuiTipDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, 1500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setMessage(String msg, View view){
        AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                            .setTipWord(msg)
                            .create();
                    if (AppManager.getAppManager().currentActivity()!=null)
                        qmuiTipDialog.show();
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qmuiTipDialog.isShowing() &&(AppManager.getAppManager().currentActivity()!=null))
                                    qmuiTipDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, 1500);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

//    public static void show2 (final Activity context, final String s){
//        context.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast tastyToast = TastyToast.makeText(context, s, TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                tastyToast.show();
//            }
//        });
//
//    }

}
