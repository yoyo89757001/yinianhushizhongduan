package com.example.yiliaoyinian.ui.shuju.bed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import com.example.yiliaoyinian.Beans.MachineBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceWifiActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class DeviceConfigBedActivity extends BaseActivity implements View.OnClickListener {
//    @BindView(R.id.fanhui)
//    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.image)
//    ImageView image;
    TextView name;
    TextView name2;
    TextView beizhu;
    TextView mac;
    TextView shebeima;
    TextView shebeileixing;

    private MachineBean.ResultBean resultBean = null;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sswxMSGsddddfff(String msgWarp){
        if (msgWarp.equals("DeviceWifiActivity")){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_config_bed);
        // @OnClick({R.id.fanhui,R.id.peiwang_rl})
        findViewById(R.id.fanhui).setOnClickListener(this);
        findViewById(R.id.peiwang_rl).setOnClickListener(this);
        name=findViewById(R.id.name);
        name2=findViewById(R.id.name2);
        beizhu=findViewById(R.id.beizhu);
        mac=findViewById(R.id.mac);
        shebeima=findViewById(R.id.shebeima);
        shebeileixing=findViewById(R.id.shebeileixing);

        EventBus.getDefault().register(this);

        resultBean = (MachineBean.ResultBean) getIntent().getSerializableExtra("person1");
        if (resultBean != null) {
            try {
                name.setText(resultBean.getDeviceName());
                beizhu.setText(resultBean.getDeviceName());
                name2.setText(resultBean.getDeviceTypeName());
                shebeileixing.setText(resultBean.getDeviceType());
                shebeima.setText(resultBean.getDeviceCode());
                mac.setText(resultBean.getDeviceMac());
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.peiwang_rl:
                startActivity(new Intent(this, DeviceWifiActivity.class).putExtra("type",1));
                break;
        }
    }
}
