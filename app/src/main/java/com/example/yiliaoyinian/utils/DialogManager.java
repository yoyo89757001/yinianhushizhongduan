package com.example.yiliaoyinian.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.LoginActivity;

import java.util.Stack;

/**
 * Activity管理类
 * @author Donkor
 */
public class DialogManager {

    private static DialogManager instance=null;
    private CommomDialog commomDialog=null;
    private DialogManager(){}
    /**
     * 单一实例
     */
    public static synchronized  DialogManager getAppManager(){
        if(instance==null){
            instance=new DialogManager();
        }
        return instance;
    }

    public synchronized void showToken(){
            AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("DialogManager", "commomDialog:" + commomDialog);
                    if (commomDialog == null) {
                        commomDialog = new CommomDialog(AppManager.getAppManager().currentActivity(), R.style.dialogs, "登录已过期,请重新登录?", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    //确定
                                    SaveInfoBean bean= MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                                    bean.setToken(null);
                                    MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                                    AppManager.getAppManager().currentActivity().startActivity(new Intent(AppManager.getAppManager().currentActivity(), LoginActivity.class));
                                    dialog.dismiss();
                                    AppManager.getAppManager().finishAllActivity();
                                }
                            }
                        }).setTitle("确认").setPositiveButton("确定");
                        commomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                commomDialog = null;
                                Log.d("DialogManager", "Dialog置空");
                            }
                        });
                        commomDialog.show();
                    }
                }
            });
    }


}

