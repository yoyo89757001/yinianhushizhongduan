package com.example.yiliaoyinian.ui.lumi.wangguan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityAddWG1Binding;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class AddWGActivity1 extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddWG1Binding binding;
    private boolean isA=false;
    private boolean isB =true;
    private AnimationDrawable animationDrawable=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddWG1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);

        binding.ddff.setOnClickListener(this);
        binding.fanhui.setOnClickListener(this);
        binding.xiayibu.setOnClickListener(this);
        binding.bottomLl.setOnClickListener(this);


        binding.xiayibu.setBackgroundColor(Color.parseColor("#40333333"));
        binding.xiayibu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        binding.xiayibu.setChangeAlphaWhenPress(true);//点击改变透明度

      /*  WifiManager m = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                EsptouchTask e  = new EsptouchTask(m.getConnectionInfo().getSSID(),m.getConnectionInfo().getBSSID(),"kyj@2020",AddWGActivity1.this);
                e.setPackageBroadcast(true);//false多播
                e.setEsptouchListener(new IEsptouchListener() {
                    @Override
                    public void onEsptouchResultAdded(IEsptouchResult result) {
                        Log.d("AddWGActivity1", "result.isCancelled():" + result.isCancelled());
                        Log.d("AddWGActivity1", "result.isSuc():" + result.isSuc());
                        Log.d("AddWGActivity1", result.getBssid());
                    }
                });
               e.executeForResult();

            }
        }).start();*/


        animationDrawable = (AnimationDrawable) binding.im11.getBackground();
        animationDrawable.start();
    }

    @Override
    protected void onStop() {
        isB=false;

        super.onStop();

    }






    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ddff:
                startActivity(new Intent(AddWGActivity1.this,AddWGActivity2.class));
                break;
            case R.id.fanhui:
                finish();
                break;
            case R.id.bottom_ll:
                if (isA){
                    isA=false;
                    binding.imbt.setBackgroundResource(R.drawable.wxuanzhong);
                    binding.xiayibu.setBackgroundColor(Color.parseColor("#40333333"));
                    binding.xiayibu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
                    binding.xiayibu.setChangeAlphaWhenPress(true);//点击改变透明度

                }else {
                    isA=true;
                    binding.imbt.setBackgroundResource(R.drawable.yxuanzhong);
                    binding.xiayibu.setBackgroundColor(Color.parseColor("#FF59B683"));
                    binding.xiayibu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
                    binding.xiayibu.setChangeAlphaWhenPress(true);//点击改变透明度
                }

                break;
            case R.id.xiayibu:
                if (isA){
                    Log.d("AddWGActivity1", "下一步");
                    startActivity(new Intent(AddWGActivity1.this,AddWGActivity3.class));
                }
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("dghadsajhdbasj")){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        animationDrawable.stop();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}