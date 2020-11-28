package com.example.yiliaoyinian.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;

public class SdkInitTool {

    public static void initSdk(@NonNull Application application, SaveInfoBean saveInfoBean){

//        if (sdkInitParams.usingGlobalSDK){//海外
//            // sdk日志开关，正式发布需要去掉
//            EZGlobalSDK.showSDKLog(true);
//            // 设置是否支持P2P取流,详见api
//            EZGlobalSDK.enableP2P(true);
//            // APP_KEY请替换成自己申请的
//            EZGlobalSDK.initLib(application, sdkInitParams.appKey);
//        }else{  //国内
            // sdk日志开关，正式发布需要去掉
            EZOpenSDK.showSDKLog(true);
            // 设置是否支持P2P取流,详见api
            EZOpenSDK.enableP2P(true);
            // APP_KEY请替换成自己申请的
        try {
            EZOpenSDK.initLib(application, saveInfoBean.getAppKey());
        }catch (Exception e){
            e.printStackTrace();
        }

      //  }
        EZOpenSDK ezvizSDK = EZOpenSDK.getInstance();
        if (saveInfoBean.getYsToken() != null){
            ezvizSDK.setAccessToken(saveInfoBean.getYsToken());
        }
        ezvizSDK.setServerUrl("https://open.ys7.com", "https://openauth.ys7.com");
        EzvizAPI.getInstance().setServerUrl("https://open.ys7.com", "https://openauth.ys7.com");
    }

}