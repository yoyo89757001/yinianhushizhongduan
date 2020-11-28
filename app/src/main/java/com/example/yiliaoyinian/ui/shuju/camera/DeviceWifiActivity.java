package com.example.yiliaoyinian.ui.shuju.camera;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.TextView;
import com.espressif.iot.esptouch.yyppqq.EsptouchTask;
import com.espressif.iot.esptouch.yyppqq.IEsptouchResult;

import com.example.yiliaoyinian.BuildConfig;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;

import com.example.yiliaoyinian.utils.IntentConstants;

import com.example.yiliaoyinian.utils.ToastUtils;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizEventType;
import com.gizwits.gizwifisdk.enumration.GizWifiConfigureMode;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.enumration.GizWifiGAgentType;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;



import org.greenrobot.eventbus.EventBus;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;



public class DeviceWifiActivity extends BaseActivity implements View.OnClickListener {

//    @BindView(R.id.fanhui)
//    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.statusBar)
//    RelativeLayout statusBar;
   // @BindView(R.id.tv_net)
    TextView tvNet;
    //@BindView(R.id.ly_net)
  //  LinearLayout lyNet;
  //  @BindView(R.id.et_pass)
    EditText etPass;
   // @BindView(R.id.tv_tip)
   // TextView tvTip;
 //   @BindView(R.id.btn_next)
    Button btnNext;
    private QMUITipDialog qmuiTipDialog = null;
    private EsptouchTask esptouchTask =null ;
    WifiInfo wifiInfo=null;
    private String ssid=null;
    private int type=0;
    //private String xuliehao,yanzhengma;
    private boolean isJZY=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_wifi);
        //@OnClick({R.id.fanhui, R.id.btn_next})
        findViewById(R.id.fanhui).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        tvNet=findViewById(R.id.tv_net);
        etPass=findViewById(R.id.et_pass);
        btnNext=findViewById(R.id.btn_next);

        type=getIntent().getIntExtra("type",0);
        WifiManager wifi_service = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifi_service!=null){
            wifiInfo = wifi_service.getConnectionInfo();
            ssid=wifiInfo.getSSID();
            if (ssid!=null){
              ssid=ssid.substring(1,ssid.length()-1);
            }else {
                ssid="";
            }
            tvNet.setText(ssid);

            Log.d("DeviceWifiActivity", wifiInfo.getSSID()+"hhhhh");
        }else {
            ToastUtils.setMessage("获取WI-FI信息失败",btnNext);
        }

       // xuliehao=getIntent().getStringExtra(IntentConstants.DEVICE_SERIAL);
      //  yanzhengma=getIntent().getStringExtra(IntentConstants.DEVICE_VERIFY_CODE);

        if (type==1){
            initSdk();//机智云
        }

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
        GizWifiSDK.sharedInstance().startWithAppInfo(DeviceWifiActivity.this,
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
                           // Log.d("ChuangJiActivity", "sdk初始化成功");
                            isJZY=true;

                            break;
                    }
                    break;
            }
        }

        @Override
        public void didSetDeviceOnboarding(GizWifiErrorCode result, GizWifiDevice device) {
            Log.d("ChuangJiActivity", "配网状态返回: " + result);
            switch (result) {
                case GIZ_SDK_SUCCESS: // 配网成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog!=null){
                                qmuiTipDialog.dismiss();
                            }
                            ToastUtils.setSuccess("配网成功",btnNext);
                            btnNext.setText("返回首页");
                        }
                    });
                    break;
                case GIZ_SDK_DEVICE_CONFIG_IS_RUNNING: // 配网进行中

                    break;
                default: // 配网失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog!=null){
                                qmuiTipDialog.dismiss();
                            }
                        }
                    });
                    ToastUtils.setFAIL("配网失败",btnNext);
                    break;
            }
        }

    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.btn_next:
                if (btnNext.getText().toString().equals("开始配网")){
                    qmuiTipDialog = new QMUITipDialog.Builder(DeviceWifiActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("配网中,请耐心等待...")
                            .create();
                    qmuiTipDialog.show();
                    if (type==1){
                        if (wifiInfo!=null){
                            if (isJZY){
                                configWifi(ssid,etPass.getText().toString().trim());
                            }else {
                                ToastUtils.setSuccess("配网初始化未完成,或初始化失败",btnNext);
                            }
                        }else {
                            if (qmuiTipDialog!=null){
                                qmuiTipDialog.dismiss();
                            }
                            ToastUtils.setMessage("获取WI-FI信息失败",btnNext);
                        }
                    }else {
                        if (wifiInfo!=null){
                            esptouchTask = new EsptouchTask(ssid, wifiInfo.getBSSID(), etPass.getText().toString().trim(), DeviceWifiActivity.this);
                            new Thread() {
                                @Override
                                public void run() {
                                    EsptouchTask task = esptouchTask;
                                    if (null != task) {
                                        try {
                                            task.setPackageBroadcast(false);
                                            IEsptouchResult result = task.executeForResult();
                                            if (result.isSuc()) {
                                                // 配网成功
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (qmuiTipDialog!=null){
                                                            qmuiTipDialog.dismiss();
                                                        }
                                                        ToastUtils.setSuccess("配网成功",btnNext);
                                                        btnNext.setText("返回首页");
                                                    }
                                                });
                                            } else {
                                                if (!result.isCancelled()) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (qmuiTipDialog!=null){
                                                                qmuiTipDialog.dismiss();
                                                            }
                                                        }
                                                    });
                                                    ToastUtils.setFAIL("配网失败",btnNext);
                                                }
                                            }
                                        } catch (RuntimeException e) {
                                            e.printStackTrace();
                                            ToastUtils.setFAIL("配网失败"+e.getMessage(),btnNext);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (qmuiTipDialog!=null){
                                                        qmuiTipDialog.dismiss();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }.start();
                        }else {
                            if (qmuiTipDialog!=null){
                                qmuiTipDialog.dismiss();
                            }
                            ToastUtils.setMessage("获取WI-FI信息失败",btnNext);
                        }
                    }


                }else if (btnNext.getText().toString().equals("返回首页")){
                    EventBus.getDefault().post("DeviceWifiActivity");
                    finish();
                }

                break;
        }
    }
}
