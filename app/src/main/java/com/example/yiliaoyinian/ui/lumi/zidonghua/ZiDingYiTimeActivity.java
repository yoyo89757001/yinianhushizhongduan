package com.example.yiliaoyinian.ui.lumi.zidonghua;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.yiliaoyinian.Beans.ZdyTimeBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityZiDingYiTimeBinding;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;


public class ZiDingYiTimeActivity extends AppCompatActivity {
    private ActivityZiDingYiTimeBinding binding=null;
    private List<ZdyTimeBean> zdyTimeBeanList=new ArrayList<>();
    private StringBuilder nnn = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityZiDingYiTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nnn.append("QAQ-");

        zdyTimeBeanList.add(new ZdyTimeBean(1,"周一",true));
        zdyTimeBeanList.add(new ZdyTimeBean(2,"周二",true));
        zdyTimeBeanList.add(new ZdyTimeBean(3,"周三",true));
        zdyTimeBeanList.add(new ZdyTimeBean(4,"周四",true));
        zdyTimeBeanList.add(new ZdyTimeBean(5,"周五",true));
        zdyTimeBeanList.add(new ZdyTimeBean(6,"周六",true));
        zdyTimeBeanList.add(new ZdyTimeBean(0,"周日",true));

        binding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(0);
            }
        });
        binding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(1);
            }
        });
        binding.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(2);
            }
        });
        binding.rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(3);
            }
        });
        binding.rl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(4);
            }
        });
        binding.rl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(5);
            }
        });
        binding.rl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(6);
            }
        });
        binding.baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ZdyTimeBean zdyTimeBean : zdyTimeBeanList) {
                     Log.d("ZiDingYiTimeActivity", zdyTimeBean.toString());
                     if (zdyTimeBean.isA()){
                         nnn.append(zdyTimeBean.getId());
                         nnn.append(",");
                     }
                }
                String dlp = nnn.toString().substring(0,nnn.length()-1);
                Log.d("ZiDingYiTimeActivity", dlp+"dddddddddd");
                EventBus.getDefault().post("finishfinish");
                EventBus.getDefault().post(dlp);
                finish();
            }
        });
    }



    private void change(int type){
       boolean a = zdyTimeBeanList.get(type).isA();
       zdyTimeBeanList.get(type).setA(!a);
        for(int i=0;i<7;i++){
            switch (i){
                case 0:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im1.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im1.setBackground(null);
                    }
                    break;
                case 1:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im2.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im2.setBackground(null);
                    }
                    break;
                case 2:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im3.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im3.setBackground(null);
                    }
                    break;
                case 3:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im4.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im4.setBackground(null);
                    }
                    break;
                case 4:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im5.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im5.setBackground(null);
                    }
                    break;
                case 5:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im6.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im6.setBackground(null);
                    }
                    break;
                case 6:
                    if (zdyTimeBeanList.get(i).isA()){
                        binding.im7.setBackgroundResource(R.drawable.duigou);
                    }else {
                        binding.im7.setBackground(null);
                    }
                    break;
            }
        }

    }


}