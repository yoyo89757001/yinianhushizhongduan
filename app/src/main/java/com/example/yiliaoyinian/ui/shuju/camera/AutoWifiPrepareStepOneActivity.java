package com.example.yiliaoyinian.ui.shuju.camera;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class AutoWifiPrepareStepOneActivity extends BaseActivity implements OnClickListener {


    private Button btnNext;
    private Button btnIntroduce;
    private AnimationDrawable aminBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_wifi_prepare_step_on);
        EventBus.getDefault().register(this);
        initTitleBar();
        initUI();
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

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initUI() {
        btnNext = (Button) findViewById(R.id.btnNext);
        btnIntroduce = (Button) findViewById(R.id.btnIntroduce);
        btnNext.setOnClickListener(this);
        btnIntroduce.setOnClickListener(this);

//        topTip.setText(getString(R.string.tip_heard_voice));
//        imageBg.setImageResource(R.drawable.video_camera1_3);
//        btnNext.setText(R.string.autowifi_heard_voice);
//        btnIntroduce.setText(R.string.autowifi_not_heard_voice);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        int id = v.getId();
        if (id == R.id.btnNext){
            intent = new Intent(this, AutoWifiNetConfigActivity.class);
            intent.putExtras(getIntent());
            startActivity(intent);
        }else if(id == R.id.btnIntroduce){
            intent = new Intent(this, AutoWifiResetActivity.class);
            intent.putExtras(getIntent());
            startActivity(intent);
        }
//        switch (v.getId()) {
//            case R.id.btnNext:
//                intent = new Intent(this, AutoWifiNetConfigActivity.class);
//                intent.putExtras(getIntent());
//                startActivity(intent);
//                break;
//            case R.id.btnIntroduce:
//                intent = new Intent(this, AutoWifiResetActivity.class);
//                intent.putExtras(getIntent());
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (aminBg != null) {
            aminBg.stop();
            aminBg = null;
        }
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
