package com.example.yiliaoyinian.ui.lumi.wangguan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityAddWG2Binding;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AddWGActivity2 extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddWG2Binding binding;
    private boolean isA=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddWG2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);

        binding.fanhui.setOnClickListener(this);
        binding.xiayibu.setOnClickListener(this);
        binding.bottomLl.setOnClickListener(this);


        binding.xiayibu.setBackgroundColor(Color.parseColor("#40333333"));
        binding.xiayibu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        binding.xiayibu.setChangeAlphaWhenPress(true);//点击改变透明度

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

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
                    startActivity(new Intent(AddWGActivity2.this,AddWGActivity3.class));
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}