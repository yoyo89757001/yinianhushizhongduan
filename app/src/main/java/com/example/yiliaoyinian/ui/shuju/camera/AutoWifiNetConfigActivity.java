package com.example.yiliaoyinian.ui.shuju.camera;


import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;

import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.IntentConstants;
import com.ezviz.sdk.configwifi.WiFiUtils;
import com.ezviz.sdk.configwifi.ap.ConnectionDetector;
import com.hikvision.wifi.configuration.BaseUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AutoWifiNetConfigActivity extends BaseActivity {

    public static final String WIFI_PASSWORD = "wifi_password";
    public static final String WIFI_SSID = "wifi_ssid";
    public static final String DEVICE_TYPE = "device_type";

    private Button btnNext;
    private TextView tvSSID;
    private EditText edtPassword;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_wifi_net_config);
        EventBus.getDefault().register(this);
        initTitleBar();
        findViews();
        initUI();
        setListener();

        if (ConnectionDetector.getConnectionType(this) != ConnectionDetector.WIFI) {
            tvSSID.setText("");
            showWifiRequiredDialog();
        } else {
            updateWifiInfo();
        }

    }


    private void initTitleBar() {
        View mTitleBar =  findViewById(R.id.fanhui);
        mTitleBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showWifiRequiredDialog() {

        new CommomDialog(this, R.style.dialogs, "请到“设置”-“Wi-Fi”功能中开启Wi-Fi并连接到网络", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                Log.d("DAFragment3", "confirm:" + confirm);
                if (confirm) {
                    //退出动作
                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    dialog.dismiss();
                }
            }
        }).setTitle("需要将手机连接到Wi-Fi").setPositiveButton("连接Wi-Fi").show();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateWifiInfo(){
        // 优先使用getCurrentWifiSsid方法获取wifi名
        String wifiName = WiFiUtils.getCurrentWifiSsid(this);
        // 如上述方式无效，则使用getWifiSSID方法进行获取
        if (!isValidWifiSSID(wifiName)){
            wifiName = BaseUtil.getWifiSSID(this);
        }
        if (isValidWifiSSID(wifiName)){
            tvSSID.setText(wifiName);
        }
    }

    private boolean isValidWifiSSID(String wifiName){
        return !TextUtils.isEmpty(wifiName) && !"".equalsIgnoreCase(wifiName);
    }

    private void findViews() {
        btnNext = findViewById(R.id.btnNext);
        tvSSID = findViewById(R.id.tvSSID);
        edtPassword = findViewById(R.id.edtPassword);
    }


    private void initUI() {
        String password = "";
        edtPassword.setText(password);
    }


    private void setListener() {
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseConfigWifiWay();
            }
        });
    }

    private void goToChooseConfigWifiWay(){
        Intent toConfigIntent = new Intent(AutoWifiNetConfigActivity.this, ConfigWifiExecutingActivity.class);
        toConfigIntent.putExtras(getIntent());
        toConfigIntent.putExtra(IntentConstants.ROUTER_WIFI_SSID, tvSSID.getText().toString());
        toConfigIntent.putExtra(IntentConstants.ROUTER_WIFI_PASSWORD, TextUtils.isEmpty(edtPassword.getText().toString())
                ? "smile" : edtPassword.getText().toString());
        startActivity(toConfigIntent);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
}
