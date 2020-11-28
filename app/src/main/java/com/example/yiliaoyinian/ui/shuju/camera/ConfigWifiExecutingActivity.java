package com.example.yiliaoyinian.ui.shuju.camera;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;

import com.example.yiliaoyinian.utils.IntentConstants;

import com.videogo.openapi.EZOpenSDK;
import com.videogo.wificonfig.APWifiConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



public class ConfigWifiExecutingActivity extends BaseActivity {

    private final static String TAG = "配网界面";


    private View mConfigResultView;
    private View mConfigSuccessView;
    private View mConfigFailView;
    private TextView mConfigErrorInfoTv;
    private String mAllErrorInfo;
    private LinearLayout peiwang=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wifi_executing);
        EventBus.getDefault().register(this);


        initUI();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                showExecutingUi();
            }
        });
        startWifi(getIntent());
    }


    public void startWifi(Intent intent){
        String routerWifiName = intent.getStringExtra(IntentConstants.ROUTER_WIFI_SSID);
        String routerWifiPwd = intent.getStringExtra(IntentConstants.ROUTER_WIFI_PASSWORD);
        String deviceSerial = intent.getStringExtra(IntentConstants.DEVICE_SERIAL);
        String deviceVerifyCode = intent.getStringExtra(IntentConstants.DEVICE_VERIFY_CODE);
        String deviceHotspotSSID /*设备热点名称，可以为空*/= intent.getStringExtra(IntentConstants.DEVICE_HOTSPOT_SSID);
        String deviceHotspotPwd /*设备热点密码，可以为空*/= intent.getStringExtra(IntentConstants.DEVICE_HOTSPOT_PWD);
//        boolean autoConnect /*是否自动连接到设备热点*/= !intent.getBooleanExtra(IntentConstants.USE_MANUAL_AP_CONFIG,
//                false);
        // 开始配网
        Log.d(TAG, routerWifiName+"routerWifiName");
        Log.d(TAG, routerWifiPwd+"routerWifiPwd");
        Log.d(TAG, deviceSerial+"deviceSerial");
        Log.d(TAG, deviceVerifyCode+"deviceVerifyCode");
        Log.d(TAG, deviceHotspotSSID+"deviceHotspotSSID");
        Log.d(TAG, deviceHotspotPwd+"DEVICE_HOTSPOT_PWD");
        deviceHotspotSSID="EZVIZ_"+deviceSerial;
        deviceHotspotPwd="EZVIZ_"+deviceVerifyCode;
        Log.d(TAG, deviceHotspotSSID+"deviceHotspotSSID");
        Log.d(TAG, deviceHotspotPwd+"DEVICE_HOTSPOT_PWD");
        EZOpenSDK.getInstance().startAPConfigWifiWithSsid(routerWifiName, routerWifiPwd,
                deviceSerial, deviceVerifyCode,
                deviceHotspotSSID, deviceHotspotPwd,
                true, new APWifiConfig.APConfigCallback() {
                    @Override
                    public void onSuccess() {
                      //  link_complete();
                        showConfigSuccessUi();
                    }

                    @Override
                    public void onInfo(int code, String message) {
                        Log.d("ConfigWifiExecutingActi", "code:" + code);
                        Log.d("ConfigWifiExecutingActi", message);
                        super.onInfo(code, message);
                    }

                    @Override
                    public void OnError(int i) {
                        Log.d("ConfigWifiExecutingActi", "i:" + i);
                        showConfigFailUi();
                        EZOpenSDK.getInstance().stopAPConfigWifiWithSsid();
                        switch (i) {
                            case 15:
                                // TODO: 2018/7/24 超时
                                erroyTest("配置超时");
                                break;
                            case 1:
                                // TODO: 2018/7/24 参数错误
                                erroyTest("参数错误");
                                break;
                            case 2:
                                // TODO: 2018/7/24 设备ap热点密码错误
                                erroyTest("设备ap热点密码错误");
                                break;
                            case 3:
                                // TODO: 2018/7/24  连接ap热点异常
                                erroyTest("连接ap热点异常");
                                break;
                            case 4:
                                // TODO: 2018/7/24 搜索WiFi热点错误
                                erroyTest("搜索WiFi热点错误");
                                break;
                            default:
                                // TODO: 2018/7/24 未知错误
                                erroyTest("未知错误");
                                break;
                        }
                    }
                });

    }

    private void erroyTest(String mes){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConfigErrorInfoTv.setText(mes);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 重试
     */
    public void onClickRetryConfigWifi(View view) {
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                showExecutingUi();
            }
        });
        startWifi(getIntent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sswxMSGsddddfff(String msgWarp){
        try {
            if (msgWarp.equals("gdfgfdgfhrtrete")){
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        EZOpenSDK.getInstance().stopAPConfigWifiWithSsid();
        super.onDestroy();
    }

    private void initUI() {
        peiwang=findViewById(R.id.peiwangll);
        mConfigResultView = findViewById(R.id.app_common_vg_config_result);
        mConfigSuccessView = findViewById(R.id.app_common_config_result_success);
        mConfigFailView = findViewById(R.id.app_common_config_result_fail);
        mConfigErrorInfoTv = findViewById(R.id.app_common_all_config_error_info);
        View view=findViewById(R.id.fanhui);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showExecutingUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peiwang.setVisibility(View.VISIBLE);
                mConfigResultView.setVisibility(View.GONE);
                mConfigSuccessView.setVisibility(View.GONE);
                mConfigFailView.setVisibility(View.GONE);
                mAllErrorInfo = null;
            }
        });
    }

    private void showConfigSuccessUi(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peiwang.setVisibility(View.GONE);
                mConfigResultView.setVisibility(View.VISIBLE);
                mConfigSuccessView.setVisibility(View.VISIBLE);
                mConfigFailView.setVisibility(View.GONE);
                mAllErrorInfo = null;
            }
        });
    }

    private void showConfigFailUi(){
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               peiwang.setVisibility(View.GONE);
               mConfigErrorInfoTv.setText(mAllErrorInfo);
               mConfigResultView.setVisibility(View.VISIBLE);
               mConfigSuccessView.setVisibility(View.GONE);
               mConfigFailView.setVisibility(View.VISIBLE);
               mAllErrorInfo = null;
           }
       });
    }

    public void blackHome(View view) {
        EventBus.getDefault().post("gdfgfdgfhrtrete");
    }



}
