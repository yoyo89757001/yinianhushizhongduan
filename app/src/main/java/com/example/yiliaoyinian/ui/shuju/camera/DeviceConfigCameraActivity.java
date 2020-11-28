package com.example.yiliaoyinian.ui.shuju.camera;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import com.example.yiliaoyinian.Beans.MachineBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.IntentConstants;
import com.example.yiliaoyinian.utils.ToastUtils;




public class DeviceConfigCameraActivity extends BaseActivity implements View.OnClickListener {
   // @BindView(R.id.fanhui)
    View fanhui;
  //  @BindView(R.id.myTitle)
  //  TextView myTitle;
 //   @BindView(R.id.image)
  //  ImageView image;
  //  @BindView(R.id.name)
    TextView name;
    TextView name2;
    TextView beizhu;
    TextView mac;
    TextView shebeima;
    TextView shebeileixing;
   // ImageView peiwang;
   // RelativeLayout peiwangRl;
    private MachineBean.ResultBean resultBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_config);
        //   @OnClick({R.id.fanhui, R.id.peiwang_rl})
        findViewById(R.id.fanhui).setOnClickListener(this);
        findViewById(R.id.peiwang_rl).setOnClickListener(this);
        name=findViewById(R.id.name);
        name2=findViewById(R.id.name2);
        beizhu=findViewById(R.id.beizhu);
        mac=findViewById(R.id.mac);
        shebeima=findViewById(R.id.shebeima);
        shebeileixing=findViewById(R.id.shebeileixing);




        resultBean = (MachineBean.ResultBean) getIntent().getSerializableExtra("person1");
        if (resultBean != null) {
            try {
                Log.d("DeviceConfigCameraActiv", resultBean.getValidateCode()+"验证码");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.peiwang_rl:
                if (resultBean.getValidateCode()!=null && resultBean.getDeviceMac()!=null){
                    startActivity(new Intent(this,AutoWifiPrepareStepOneActivity.class).putExtra("type",1)
                            .putExtra(IntentConstants.DEVICE_SERIAL,resultBean.getDeviceMac())
                            .putExtra(IntentConstants.DEVICE_VERIFY_CODE,resultBean.getValidateCode()));
                }else {
                    ToastUtils.setMessage("该设备没有验证码",fanhui);
                }
                break;
        }
    }
}
