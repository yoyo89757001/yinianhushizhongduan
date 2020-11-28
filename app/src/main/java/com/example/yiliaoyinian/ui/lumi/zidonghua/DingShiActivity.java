package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityDingShiBinding;
import com.example.yiliaoyinian.utils.DateUtils;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class DingShiActivity extends AppCompatActivity implements OnDateSetListener {
    private ActivityDingShiBinding binding=null;
    private String times = "";
    private String nums = "1,2,3,4,5,6,0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDingShiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DingShiActivity.this,ChongFuShiJianActivity.class));
            }
        });
        binding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                        .setCallBack(DingShiActivity.this)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择时间")
//                        .setYearText("年")
//                        .setMonthText("月")
//                        .setDayText("日")
                        .setHourText("时")
                        .setMinuteText("分")
                        .setCyclic(true)
                        //.setMinMillseconds(System.currentTimeMillis())
                        //.setMaxMillseconds(DateUtils.dataOne("2100-1-1"))
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(Color.parseColor("#FF59B683"))
                        .setType(Type.HOURS_MINS)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(Color.parseColor("#FF59B683"))
                        .setWheelItemTextSize(16)
                        .build();
                mDialogAll.show(getSupportFragmentManager(), "ff1");
            }
        });

        binding.baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (times.equals("")){
                    QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(DingShiActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                            .setTipWord("你还没选择时间")
                            .create();
                    qmuiTipDialog.show();
                    binding.baocun.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    }, 1500);
                    return;
                }
                ActionsBean actionsBean=new ActionsBean();
                actionsBean.setSubjectId("");
                actionsBean.setTriggerDefinitionId("TD.app.timer.timer");
                actionsBean.setModel("app.timer.v1");
                actionsBean.setName("定时");
                actionsBean.setMiaoshu("每天");
                JSONArray jsonArray=new JSONArray();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("value",times+" * * "+nums);
                jsonObject.put("paramId","PD.timer");
                jsonArray.add(jsonObject);
                actionsBean.setParams(jsonArray);
                Log.d("DingShiActivity", actionsBean.toString()+"是是是");
                EventBus.getDefault().post("finishfinish");
                EventBus.getDefault().post(actionsBean);
                finish();

            }
        });

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        //Log.d("AddServerActivity", "millseconds:" + millseconds);
        // Log.d("AddServerActivity", DateUtils.time(millseconds+""));
        if (timePickerView.getTag() == null)
            return;
        if (timePickerView.getTag().equals("ff1")) {
            binding.kaishishijian.setText(DateUtils.ti(millseconds + ""));
            times=DateUtils.tiee(millseconds + "");
        }
        timePickerView.dismiss();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishs(String finish) {
        String[] sss = finish.split("QAQ-");
        if (sss.length==2){
            nums=sss[1];
            Log.d("DingShiActivity", nums);
            if (nums.equals("1,2,3,4,5")){//星期一到星期五
                binding.shijian.setText("周一到周五");
            }else if (nums.equals("6,0")){
                binding.shijian.setText("周末");
            }else if (nums.equals("1,2,3,4,5,6,0")){
                binding.shijian.setText("每天");
            }else {
                binding.shijian.setText("自定义");
            }
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}