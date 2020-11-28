package com.example.yiliaoyinian.ui.shuju.camera;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiliaoyinian.Beans.MachineBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;



public class DeviceConfigBle2Activity extends BaseActivity {
//    @BindView(R.id.fanhui)
//    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.image)
//    ImageView image;
//    @BindView(R.id.name)
    TextView name;
    TextView name2;
    TextView beizhu;
    TextView mac;
    TextView shebeima;
    TextView shebeileixing;


    private MachineBean.ResultBean resultBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_config_ble2);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name=findViewById(R.id.name);
        name2=findViewById(R.id.name2);
        beizhu=findViewById(R.id.beizhu);
        mac=findViewById(R.id.mac);
        shebeima=findViewById(R.id.shebeima);
        shebeileixing=findViewById(R.id.shebeileixing);

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


}
