package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.yiliaoyinian.databinding.ActivityChongFuShiJianBinding;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChongFuShiJianActivity extends AppCompatActivity {
    private ActivityChongFuShiJianBinding binding=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChongFuShiJianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.rl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChongFuShiJianActivity.this,ZiDingYiTimeActivity.class));
            }
        });

        binding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("QAQ-1,2,3,4,5,6,0");
                finish();
            }
        });
        binding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("QAQ-1,2,3,4,5");
                finish();
            }
        });
        binding.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("QAQ-6,0");
                finish();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishs(String finish) {
        if (finish.equals("finishfinish")){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}