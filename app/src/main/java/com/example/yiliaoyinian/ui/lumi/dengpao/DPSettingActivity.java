package com.example.yiliaoyinian.ui.lumi.dengpao;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;


import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;



import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.FilterContext;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;


import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


import java.text.DecimalFormat;
import java.util.HashMap;

import java.util.Vector;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class DPSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ColorPickerView  colorPickerView=null;
    private TextView infiss;
    private String colors="2700";
    private int ligt = 50;
    private SwitchCompat switchCompat;
    private AppCompatSeekBar seekBar;
   //private SmartRefreshLayout refreshLayout;

    private QMUIButton bt1,bt2,bt3,bt4;
    private ImageView shezhi;
    private LinearLayout bottombar;
    private RelativeLayout relativeLayout;
    private  LinearLayoutManager manager;
    private boolean sitop =false;
    private int mun = 0;
    //private LinearLayout scrollView;

    private Vector<WGInfoSave> divescerBeanVector=new Vector<>();
    private static boolean isDH = false;
    //private ImageView ima;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private String APPID = "4113230e4535848642a52f0b";
    private String APPKEY = "2HoQ1X1420pGAC6DYMzvVKqypV7t9W9p";
    private Box<WGInfoSave> wgInfoSaveBox=null;
    private boolean isOpen=true;
    private  WGInfoSave save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_setting);
        wgInfoSaveBox=MyApplication.myApplication.getWgInfoSaveBox();
        WGInfoSave savee= (WGInfoSave) getIntent().getSerializableExtra("saveinfo");
        EventBus.getDefault().register(this);

        //ima=findViewById(R.id.ima);
        //scrollView=findViewById(R.id.scrollView);
        relativeLayout=findViewById(R.id.topbar);
        findViewById(R.id.fanhui).setOnClickListener(this);
        infiss=findViewById(R.id.infiss);
        shezhi=findViewById(R.id.shezhi);
        shezhi.setOnClickListener(this);
        switchCompat=findViewById(R.id.switcher);
        seekBar=findViewById(R.id.seekbar);
        seekBar.setMax(100);
        colorPickerView=findViewById(R.id.colorPickerView);
        bt1=findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2=findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3=findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        bt4=findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
        bt1.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt1.setChangeAlphaWhenPress(true);//点击改变透明度
        bt2.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt2.setChangeAlphaWhenPress(true);//点击改变透明度
        bt3.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt3.setChangeAlphaWhenPress(true);//点击改变透明度
        bt4.setRadius(QMUIDisplayHelper.dp2px(this, 22));
        bt4.setChangeAlphaWhenPress(true);//点击改变透明度
        try {
            save =  wgInfoSaveBox.query().equal(WGInfoSave_.did,savee.getDid()).build().findFirst();
            Log.d("WGSettingActivity", "save:" + save);
            if (save!=null){
                colors = save.getArgb();
                ligt = save.getLight();
                isOpen=save.isOPen();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        switchCompat.setChecked(isOpen);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setEnabled(false);
                isOpen = b;
                if (save!=null){
                    save.setOPen(b);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,save.getDid(),ligt,isOpen,3);
            }
        });


        seekBar.setProgress(ligt);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i<=0){
                    i=1;
                }
                ligt = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setLight(ligt);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(colors,save.getDid(),ligt,isOpen,0);
            }
        });

        colorPickerView.setPaletteDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.zzzfff)));
        colorPickerView.setActionMode(ActionMode.LAST);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colors=envelope.getHexCode().substring(2,8);
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                    if (save!=null){
                        save.setArgb(colors);
                        wgInfoSaveBox.put(save);
                    }
//
            }
        });

        colorPickerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    if (motionEvent.getAction()==MotionEvent.ACTION_UP){
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
                        loginupdataInfo(((int)(1000000.0f/(Float.parseFloat(str))))+"",save.getDid(),ligt,isOpen,4);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }
        });
        infiss.setText("颜色值: #"+colors+"   "+"亮度: "+ligt);
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) colorPickerView.getLayoutParams();
      params.width = (int) (QMUIDisplayHelper.getScreenHeight(getApplicationContext())*0.25f);
      params.height = (int) (QMUIDisplayHelper.getScreenHeight(getApplicationContext())*0.25f);
      colorPickerView.setLayoutParams(params);
      colorPickerView.invalidate();


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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("fsdggdgfdgeeefdg")){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                colors = "E38F45";
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                loginupdataInfo(370+"",save.getDid(),ligt,isOpen,4);
                break;
            case R.id.bt2:
                colors = "E2b38d";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(343+"",save.getDid(),ligt,isOpen,4);
                break;
            case R.id.bt3:
                colors = "E2c8b8";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(266+"",save.getDid(),ligt,isOpen,4);
                break;
            case R.id.bt4:
                colors = "E2d7d6";
                infiss.setText("颜色值: #"+colors+" "+"亮度: "+ligt);
                if (save!=null){
                    save.setArgb(colors);
                    wgInfoSaveBox.put(save);
                }
                loginupdataInfo(158+"",save.getDid(),ligt,isOpen,4);
                break;
            case R.id.shezhi:
                startActivity(new Intent(this, DPInfosListActivity.class).putExtra("did",save.getDid()).putExtra("zunm",divescerBeanVector.size()));

                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }




    private void loginupdataInfo(String argb,String did,int light,boolean isOpen,int type){//修改网关信息
        Log.d("DPSettingActivity", argb+"argb");
        //修改网关信息
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/properties/write";
        JSONObject jsonObject =new  JSONObject();
       // JSONObject json =new  JSONObject();
        JSONObject data =new  JSONObject();
        try {
//            json.put("brightness_level", 10); //夜灯亮度，0~100
//            json.put("alarm_status", 1);//报警状态，0:没报警 ，1：报警
//            json.put("corridor_light_status", 1);//夜灯 1：打开 0：关闭
//            json.put("system_volume", 80);//系统音量0～100
//            json.put("argb_value", Color.parseColor("#"+argb));//夜灯ARGB

            jsonObject.put("did",did+"");
            switch (type){
                case 0://亮度
                    jsonObject.put("data",new JSONObject().put("light_level", light));
                    break;
                case 1://报警
                    jsonObject.put("data",new JSONObject().put("alarm_status", 1));
                    break;
                case 2://音量
                    jsonObject.put("data",new JSONObject().put("system_volume", 80));
                    break;
                case 3://开关
                    jsonObject.put("data",new JSONObject().put("power_status", isOpen?1:0));
                    break;
                case 4://颜色
                    jsonObject.put("data",new JSONObject().put("colour_temperature",argb));
                    break;
            }
            Log.d("WGSettingActivity", jsonObject.toString()+"参数"+argb);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json.put("installCode", "")
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(jsonObject.toString(), AESUtil.getAESKey(APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign=FilterContext.createSign(header, APPKEY, false);
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switchCompat.setEnabled(true);
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               // StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "修改网关信息:"+stA);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switchCompat.setEnabled(true);
                        }
                    });
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });

    }


}