package com.example.yiliaoyinian.ui.lumi.rentichuanganqi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.databinding.ActivityRenTiChuanGanQiBinding;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class RenTiChuanGanQiActivity extends AppCompatActivity {
         private ActivityRenTiChuanGanQiBinding binding=null;
         private WGInfoSave wgInfoSave=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRenTiChuanGanQiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wgInfoSave= (WGInfoSave) getIntent().getSerializableExtra("saveinfo");
        Log.d("RenTiChuanGanQiActivity", wgInfoSave.toString()+"传过来的子设备值");
        if (wgInfoSave!=null)
        binding.myTitle.setText(wgInfoSave.getName());
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.ri.setBackgroundColor(Color.parseColor("#ff59B683"));
        binding.ri.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        binding.ri.setChangeAlphaWhenPress(true);//点击改变透明度
        binding.zhou.setBackgroundColor(Color.parseColor("#0059B683"));
        binding.zhou.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        binding.zhou.setChangeAlphaWhenPress(true);//点击改变透明度
        binding.ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ri.setBackgroundColor(Color.parseColor("#ff59B683"));
                binding.ri.setRadius(QMUIDisplayHelper.dp2px(RenTiChuanGanQiActivity.this, 15));
                binding.ri.setChangeAlphaWhenPress(true);//点击改变透明度
                binding.ri.setTextColor(Color.parseColor("#ffffffff"));
                binding.zhou.setBackgroundColor(Color.parseColor("#0059B683"));
                binding.zhou.setRadius(QMUIDisplayHelper.dp2px(RenTiChuanGanQiActivity.this, 15));
                binding.zhou.setChangeAlphaWhenPress(true);//点击改变透明度
                binding.zhou.setTextColor(Color.parseColor("#333333"));

            }
        });
        binding.zhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.zhou.setBackgroundColor(Color.parseColor("#ff59B683"));
                binding.zhou.setRadius(QMUIDisplayHelper.dp2px(RenTiChuanGanQiActivity.this, 15));
                binding.zhou.setChangeAlphaWhenPress(true);//点击改变透明度
                binding.zhou.setTextColor(Color.parseColor("#ffffffff"));
                binding.ri.setBackgroundColor(Color.parseColor("#0059B683"));
                binding.ri.setRadius(QMUIDisplayHelper.dp2px(RenTiChuanGanQiActivity.this, 15));
                binding.ri.setChangeAlphaWhenPress(true);//点击改变透明度
                binding.ri.setTextColor(Color.parseColor("#333333"));

            }
        });

    }


}