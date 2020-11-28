package com.example.yiliaoyinian.ui.lumi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;


import com.example.yiliaoyinian.Beans.TSWGBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityAddSonDeviceBinding;
import com.example.yiliaoyinian.dialog.CommomDialog;


import com.example.yiliaoyinian.dialog.CommomDialog2;
import com.example.yiliaoyinian.utils.AESUtil;

import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.FilterContext;

import com.qmuiteam.qmui.widget.QMUIProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddSonDeviceActivity extends AppCompatActivity {
    private ActivityAddSonDeviceBinding binding;
    private String type;
    protected static final int STOP = 0x10000;
    protected static final int NEXT = 0x10001;
    private ProgressHandler myHandler = new ProgressHandler();
    private MyThread myThread=null;
    private boolean isA = true;
    private int count=60;
    private String did="";
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private String APPID = MyApplication.APPID;
    private String APPKEY = MyApplication.APPKEY;
    private CommomDialog commomDialog=null;
    private CommomDialog2 dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddSonDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        type=getIntent().getStringExtra("type");
        did=getIntent().getStringExtra("did");
        switch (type){
            case "2":
                binding.im11.setImageResource(R.drawable.d_motion3x);
                break;
            case "3":
                binding.im11.setImageResource(R.drawable.d_magnet);
                break;
            case "4":
                binding.im11.setImageResource(R.drawable.d_weather);
                break;
            case "5":
                binding.im11.setImageResource(R.drawable.d_vibration);
                break;
            case "6":
                binding.im11.setImageResource(R.drawable.d_led);
                binding.fggg.setText("请重复开关灯泡5次,然后等待网关连接.");
                break;
            case "7":
                binding.im11.setImageResource(R.drawable.d_switch);
                break;

        }

        myHandler.setProgressBar(binding.circleProgressBar);
        binding.circleProgressBar.setMaxValue(60);
        binding.circleProgressBar.setProgress(60,false);
        binding.circleProgressBar.setQMUIProgressBarTextGenerator(new QMUIProgressBar.QMUIProgressBarTextGenerator() {
            @Override
            public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
                if (value<=0){
                    if (commomDialog==null){
                        commomDialog =  new CommomDialog(AddSonDeviceActivity.this, R.style.dialogs, "未添加成功,是否重试?", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Log.d("DAFragment3", "confirm:" + confirm);
                                if (confirm) {
                                    //退出动作
                                    loginApi();
                                    binding.circleProgressBar.setProgress(60,false);
                                    isA=true;
                                    myThread=new MyThread();
                                    myThread.start();
                                    dialog.dismiss();
                                    commomDialog=null;
                                }
                            }
                        }).setTitle("确认").setPositiveButton("重试");
                        commomDialog.show();
                    }

                }
                return  value  + "秒";
            }
        });

        myThread=new MyThread();
        myThread.start();

        loginApi();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG5(TSWGBean msgWarp) {
        Log.d("MainActivity", "收到的JSONObject：" + msgWarp.toString());
        try {
            if (msgWarp.getCode()==0){
                isA=false;
                if (myThread!=null)
                myThread.interrupt();
                if (dialog2!=null && dialog2.isShowing())
                    dialog2.dismiss();
                 dialog2=  new CommomDialog2(AddSonDeviceActivity.this, R.style.dialogs2, "子设备添加成功!", new CommomDialog2.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        // Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            dialog.dismiss();
                            finish();
                        }
                    }
                });
                 dialog2.setTitle("温馨提示");
                 dialog2.setPositiveButton("确定");
                 dialog2.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class MyThread extends Thread{
        @Override
        public void run() {
            while (isA){
                if (count<=0){
                    count=60;
                    isA=false;
                    return;
                }
                SystemClock.sleep(1000);
                count-=1;
                Message msg = new Message();
                msg.what = NEXT;
                msg.arg1 = count;
                myHandler.sendMessage(msg);


            }
        }
    }

    private static class ProgressHandler extends Handler {
        private WeakReference<QMUIProgressBar> weakCircleProgressBar;

        void setProgressBar(QMUIProgressBar circleProgressBar) {
            weakCircleProgressBar = new WeakReference<>(circleProgressBar);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STOP:
                    break;
                case NEXT:
                    if (!Thread.currentThread().isInterrupted()) {
                        if ( weakCircleProgressBar.get() != null) {
                             weakCircleProgressBar.get().setProgress(msg.arg1);
                        }
                    }
            }

        }
    }


    private void loginApi(){
//        val params:MutableMap<String,Any> = mutableMapOf<String,Any>()
//        params.set("client_id","4113230e4535848642a52f0b")
//        params.set("response_type","code")
//        params.set("state","yinian")
//        params.set("account","13642730363")
//        params.set("password","ABC123456")
//        params.set("redirect_uri","http://006ecchedddice.jumpbc.chuairan.com")

        //开启网关添加子设备模式
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/connect/subdevice/start";
        JSONObject json = new JSONObject();
        try {
            json.put("did", did);
           // json.put("installCode", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  RequestBody requestBody  = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String,String> header = new HashMap<>(4);
        header.put(CommonRequest.REQUEST_HEADER_APPID,APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body.trim());
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign = FilterContext.createSign(header, APPKEY, false);

        Request builder = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();
        //.get().url(url).build()
        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "请求添加子设备返回结果:"+stA);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        isA=false;
        EventBus.getDefault().unregister(this);
        myThread.interrupt();
        super.onDestroy();
    }
}