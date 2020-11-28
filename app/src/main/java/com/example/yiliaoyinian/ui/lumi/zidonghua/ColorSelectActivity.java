package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.databinding.ActivityColorSelectBinding;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import org.greenrobot.eventbus.EventBus;


public class ColorSelectActivity extends AppCompatActivity {
    private ActivityColorSelectBinding binding=null;
    private WGInfoSave wgInfoSave;
    private String colors="FFFFFF";
    private int ligt = 50;
    private String setActionDefinitionId;
    private String setModel;
    private String setMiaoshu;
    private String getParamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityColorSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wgInfoSave= (WGInfoSave) getIntent().getSerializableExtra("WGInfoSave");
        setActionDefinitionId=getIntent().getStringExtra("setActionDefinitionId");
        setModel=getIntent().getStringExtra("setModel");
        setMiaoshu=getIntent().getStringExtra("setMiaoshu");
        getParamId=getIntent().getStringExtra("getParamId");
        binding.seekbar.setMax(100);
        binding.myTitle.setText(setMiaoshu+"");
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//保存

//                        putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
//                        putExtra("setModel",dangBeanList.get(position).getModel()).
//                        putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
//                        putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()));

                ActionsBean actionsBean=new ActionsBean();
                actionsBean.setSubjectId(wgInfoSave.getDid());
                actionsBean.setActionDefinitionId(setActionDefinitionId);
                actionsBean.setModel(setModel);
                actionsBean.setName(wgInfoSave.getName());
                actionsBean.setMiaoshu(setMiaoshu+"(颜色:"+colors+")");
                JSONArray jsonArray=new JSONArray();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("value",Integer.valueOf(colors,16));
                jsonObject.put("paramId",getParamId);
                jsonArray.add(jsonObject);
                actionsBean.setParams(jsonArray);
                Log.d("DingShiActivity", actionsBean.toString()+"是是是");
                EventBus.getDefault().post("finishfinish");
                EventBus.getDefault().post(actionsBean);
                finish();

            }
        });
        binding.seekbar.setProgress(ligt);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ligt = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                binding.infiss.setText("颜色值: #"+colors);
            }
        });

        binding.colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colors=envelope.getHexCode().substring(2,8);
            }
        });
        binding.colorPickerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.infiss.setText("颜色值: #"+colors);

                }
                return false;
            }
        });
        binding.infiss.setText("颜色值: #"+colors);

    }


}