package com.example.yiliaoyinian.ui.lumi.wangguan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.databinding.ActivityWangLuosBinding;

public class WangLuosActivity extends AppCompatActivity {
    private ActivityWangLuosBinding binding=null;
    private String did="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWangLuosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        did=getIntent().getStringExtra("did");
        try {
            WGInfoSave save= MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.did,did).build().findFirst();
            if (save!=null){
                binding.dds.setText(save.getSsid()+"");
                binding.dds33.setText(save.getRssi()+"");
                binding.sjk3.setText(save.getIp()+"");
                binding.sjk4.setText(save.getMac()+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}