package com.example.yiliaoyinian.ui.lumi.zidonghua;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityColorSelectBinding;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import org.greenrobot.eventbus.EventBus;
import java.text.DecimalFormat;



public class SeWenSelectActivity extends AppCompatActivity {
    private ActivityColorSelectBinding binding=null;
    private WGInfoSave wgInfoSave;
    private String colors="FFFFFF";
    private int ligt = 50;
    private String setActionDefinitionId;
    private String setModel;
    private String setMiaoshu;
    private String getParamId;
    private int cccvv = 200;


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

//                putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
//                putExtra("setModel",dangBeanList.get(position).getModel()).
//                putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
//                putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()));

                ActionsBean actionsBean=new ActionsBean();
                actionsBean.setSubjectId(wgInfoSave.getDid());
                actionsBean.setActionDefinitionId(setActionDefinitionId);
                actionsBean.setModel(setModel);
                actionsBean.setName(wgInfoSave.getName());
                actionsBean.setMiaoshu(setMiaoshu+"(颜色:"+colors+")");
                JSONArray jsonArray=new JSONArray();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("value",cccvv);
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

        binding.colorPickerView.setPaletteDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.zzzfff)));
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
                    float a = motionEvent.getY();
                    if (a<0){
                        a=0f;
                    }
                    if (a>view.getHeight()){
                        a= (float) view.getHeight();
                    }
                    float bizhi = a / view.getHeight();
                    //  Log.d("DPSettingActivity", "bizhi:" + bizhi);
                    String scolors=2700+((6500.0f-2700.0f)*bizhi)+"";
                    // Log.d("DPSettingActivity", scolors+"值");
                    DecimalFormat format = new DecimalFormat("0.00");
                    String str = doubleToString(Double.parseDouble(scolors));
                    //  Log.d("DPSettingActivity", str+"值");
                    cccvv = (int)(1000000.0f/(Float.parseFloat(str)));

                }
                return false;
            }
        });
        binding.infiss.setText("颜色值: #"+colors);

    }

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public String doubleToString(double num){
        String strNum = String.valueOf(num);
        int n = strNum.indexOf(".");
        if(n>0){
            //截取小数点后的数字
            String dotNum = strNum.substring(n+1);
            if("0".equals(dotNum)){
                return strNum+"0";
            }else{
                if(dotNum.length()==1){
                    return strNum +"0";
                }else{
                    return strNum;
                }
            }
        }else{
            return strNum+".00";
        }
    }


}