package com.example.yiliaoyinian.ui.shuju.camera;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yiliaoyinian.BuildConfig;
import com.example.yiliaoyinian.R;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizEventType;
import com.gizwits.gizwifisdk.enumration.GizWifiConfigureMode;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.enumration.GizWifiGAgentType;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class ChuangJiActivity extends AppCompatActivity implements View.OnClickListener {

//    @BindView(R.id.klkl)
//    Button klkl;
//    @BindView(R.id.souo)
//    Button souo;
    private Map<String, GizWifiDevice> deviceMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuang_ji);
       //   @OnClick({R.id.klkl, R.id.souo})
        findViewById(R.id.klkl).setOnClickListener(this);
        findViewById(R.id.souo).setOnClickListener(this);


        initSdk();
    }


    private void configWifi(String ssid, String password) {
        List<GizWifiGAgentType> types = new ArrayList<GizWifiGAgentType>();
        types.add(GizWifiGAgentType.GizGAgentESP);
        GizWifiSDK.sharedInstance().setDeviceOnboardingDeploy(ssid, password,
                GizWifiConfigureMode.GizWifiAirLink, null, 60, types, false);

    }

    private void initSdk() {
        ConcurrentHashMap<String, String> appInfo = new ConcurrentHashMap<>();
        appInfo.put("appId", BuildConfig.GIZWITS_APP_ID);
        appInfo.put("appSecret", BuildConfig.GIZWITS_APP_SECRET);
        List<ConcurrentHashMap<String, String>> productInfoList = new ArrayList<>();
        ConcurrentHashMap<String, String> productInfo = new ConcurrentHashMap<String, String>();
        productInfo.put("productKey", BuildConfig.GIZWITS_PRODUCT_KEY_BED);
        productInfo.put("productSecret", BuildConfig.GIZWITS_PRODUCT_SECRET_BED);
        productInfoList.add(productInfo);
        ConcurrentHashMap<String, String> cloudServiceInfo = new ConcurrentHashMap<>();
        cloudServiceInfo.put("api", BuildConfig.GIZWITS_API);
        cloudServiceInfo.put("site", BuildConfig.GIZWITS_SITE);
        GizWifiSDK.sharedInstance().setListener(listener);
//        GizWifiSDK.sharedInstance().startWithAppID(context, Constant.GIZWITS_APP_ID);
        GizWifiSDK.sharedInstance().startWithAppInfo(ChuangJiActivity.this,
                appInfo, productInfoList, cloudServiceInfo, false);
    }

    private GizWifiSDKListener listener = new GizWifiSDKListener() {

        @Override
        public void didNotifyEvent(GizEventType eventType,
                                   Object eventSource,
                                   GizWifiErrorCode eventID,
                                   String eventMessage) {
            Log.d("ChuangJiActivity", "床机事件通知:" + eventMessage);
            switch (eventType) {
                case GizEventSDK:
                    switch (eventID) {
                        case GIZ_SDK_START_SUCCESS: // SDK初始化成功
                            Log.d("ChuangJiActivity", "sdk初始化成功");
                            //启动搜索设备
                            // 使用缓存的设备列表刷新UI
                            List<GizWifiDevice> devices = GizWifiSDK.sharedInstance().getDeviceList();
                            for (GizWifiDevice device : devices) {
                                Log.d("ChuangJiActivity", device.getProductName()+"ddddd");
                            }

                            break;
                    }
                    break;
            }
        }

        @Override
        public void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
            // 搜索设备
            switch (result) {
                case GIZ_SDK_SUCCESS:
                    deviceMap.clear();
                    for (GizWifiDevice device : deviceList) {
                        if (!TextUtils.isEmpty(device.getMacAddress())) {
                            String mac = device.getMacAddress().toLowerCase();
                            Log.d("ChuangJiActivity", "Mac:"+mac);
                            deviceMap.put(mac, device);
                        }
                    }
                    break;
                default:

                    break;
            }
        }

//        @Override
//        public void didUserLogin(GizWifiErrorCode result, String uid, String token) {
//            Log.d("ChuangJiActivity", "用户登录结果.getResult():" + result.getResult());
//            // 用户登录结果
////            switch (result) {
////                case GIZ_SDK_SUCCESS: // 登录成功
////                    if (null != loginTask) {
////                        GizwitsContext.this.uid = uid;
////                        GizwitsContext.this.token = token;
////                        loginTask = null;
////                    }
////                    break;
////                default: // 登录失败
////                    if (null != loginTask && !loginTask.login()) {
////                        // 登录结束
////                        loginTask = null;
////                    }
////                    break;
////            }
//        }


    };



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.klkl:
                String ssid=null;
                WifiManager wifi_service = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                if (wifi_service!=null){
                    WifiInfo wifiInfo = wifi_service.getConnectionInfo();
                    ssid=wifiInfo.getSSID();
                    if (ssid!=null){
                        ssid=ssid.substring(1,ssid.length()-1);
                    }else {
                        ssid="";
                    }
                }
                configWifi(ssid,"kyj@2020");
                break;
            case R.id.souo:

                GizWifiSDK.sharedInstance().getBoundDevices("6703778fb5a64331ba6372a7cccdd4f3", "1e9ef222f84d477b959e50cc0740ab85");


                break;
        }
    }
}
