package com.example.yiliaoyinian.ui.xunfang;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import com.example.yiliaoyinian.Beans.CheckBBean;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.AppManager;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFontFitTextView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class XunFangActivity extends BaseActivity implements View.OnClickListener {

    QMUIFontFitTextView chuanghao;
    QMUIFontFitTextView name;
    QMUIFontFitTextView sex;
    QMUIFontFitTextView hulidengji;
    QMUIButton bt11;
    QMUIButton bt12;
    QMUIButton bt13;
    QMUIButton bt14;
    QMUIButton bt21;
    QMUIButton bt22;
    QMUIButton bt23;
    QMUIButton bt24;
    QMUIButton bt31;
    QMUIButton bt32;
    QMUIButton bt33;
    QMUIButton bt34;
    QMUIButton bt41;
    QMUIButton bt42;
    QMUIButton bt43;
    QMUIButton bt44;
    QMUIButton bt51;
    QMUIButton bt52;
    QMUIButton bt53;
    QMUIButton bt54;
    QMUIButton bt61;
    QMUIButton bt62;
    QMUIButton bt63;
    QMUIButton bt64;
    QMUIButton queding;

    private long idid=0;
    private String code=null,nameP=null;
    private String content=null;
    private String sp1="清楚",sp2="正常",sp3="正常",sp4="正常",sp5="普食",sp6="正常";
    private int dataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xun_fang);

        findViewById(R.id.bt1_1).setOnClickListener(this);
        findViewById(R.id.bt1_2).setOnClickListener(this);
        findViewById(R.id.bt1_3).setOnClickListener(this);
        findViewById(R.id.bt1_4).setOnClickListener(this);
        findViewById(R.id.bt2_1).setOnClickListener(this);
        findViewById(R.id.bt2_2).setOnClickListener(this);
        findViewById(R.id.bt2_3).setOnClickListener(this);
        findViewById(R.id.bt2_4).setOnClickListener(this);
        findViewById(R.id.bt3_1).setOnClickListener(this);
        findViewById(R.id.bt3_2).setOnClickListener(this);
        findViewById(R.id.bt3_3).setOnClickListener(this);
        findViewById(R.id.bt3_4).setOnClickListener(this);
        findViewById(R.id.bt4_1).setOnClickListener(this);
        findViewById(R.id.bt4_2).setOnClickListener(this);
        findViewById(R.id.bt4_3).setOnClickListener(this);
        findViewById(R.id.bt4_4).setOnClickListener(this);
        findViewById(R.id.bt5_1).setOnClickListener(this);
        findViewById(R.id.bt5_2).setOnClickListener(this);
        findViewById(R.id.bt5_3).setOnClickListener(this);
        findViewById(R.id.bt5_4).setOnClickListener(this);
        findViewById(R.id.bt6_1).setOnClickListener(this);
        findViewById(R.id.bt6_2).setOnClickListener(this);
        findViewById(R.id.bt6_3).setOnClickListener(this);
        findViewById(R.id.bt6_4).setOnClickListener(this);
        findViewById(R.id.queding).setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);

        chuanghao=findViewById(R.id.chuanghao);
        name=findViewById(R.id.name);
        sex=findViewById(R.id.sex);
        hulidengji=findViewById(R.id.hulidengji);
        queding=findViewById(R.id.queding);

        bt11=findViewById(R.id.bt1_1);
        bt12=findViewById(R.id.bt1_2);
        bt13=findViewById(R.id.bt1_3);
        bt14=findViewById(R.id.bt1_4);
        bt21=findViewById(R.id.bt2_1);
        bt22=findViewById(R.id.bt2_2);
        bt23=findViewById(R.id.bt2_3);
        bt24=findViewById(R.id.bt2_4);
        bt31=findViewById(R.id.bt3_1);
        bt32=findViewById(R.id.bt3_2);
        bt33=findViewById(R.id.bt3_3);
        bt34=findViewById(R.id.bt3_4);
        bt41=findViewById(R.id.bt4_1);
        bt42=findViewById(R.id.bt4_2);
        bt43=findViewById(R.id.bt4_3);
        bt44=findViewById(R.id.bt4_4);
        bt51=findViewById(R.id.bt5_1);
        bt52=findViewById(R.id.bt5_2);
        bt53=findViewById(R.id.bt5_3);
        bt54=findViewById(R.id.bt5_4);
        bt61=findViewById(R.id.bt6_1);
        bt62=findViewById(R.id.bt6_2);
        bt63=findViewById(R.id.bt6_3);
        bt64=findViewById(R.id.bt6_4);



        rest1();rest2();rest3();rest4();rest5();rest6();

        queding.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        queding.setChangeAlphaWhenPress(true);//点击改变透明度
        bt11.setTextColor(Color.parseColor("#FFFFFF"));
        bt11.setBackgroundColor(Color.parseColor("#59B683"));
        bt11.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt11.setChangeAlphaWhenPress(true);//点击改变透明度
        bt21.setTextColor(Color.parseColor("#FFFFFF"));
        bt21.setBackgroundColor(Color.parseColor("#59B683"));
        bt21.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt21.setChangeAlphaWhenPress(true);//点击改变透明度
        bt31.setTextColor(Color.parseColor("#FFFFFF"));
        bt31.setBackgroundColor(Color.parseColor("#59B683"));
        bt31.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt31.setChangeAlphaWhenPress(true);//点击改变透明度
        bt41.setTextColor(Color.parseColor("#FFFFFF"));
        bt41.setBackgroundColor(Color.parseColor("#59B683"));
        bt41.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt41.setChangeAlphaWhenPress(true);//点击改变透明度
        bt51.setTextColor(Color.parseColor("#FFFFFF"));
        bt51.setBackgroundColor(Color.parseColor("#59B683"));
        bt51.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt51.setChangeAlphaWhenPress(true);//点击改变透明度
        bt61.setTextColor(Color.parseColor("#FFFFFF"));
        bt61.setBackgroundColor(Color.parseColor("#59B683"));
        bt61.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt61.setChangeAlphaWhenPress(true);//点击改变透明度

       idid=getIntent().getLongExtra("id",0);
       code=getIntent().getStringExtra("code");
       nameP=getIntent().getStringExtra("name");
       dataType=getIntent().getIntExtra("dataType",-1);
       qmuiTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载数据中...")
                .create();
        qmuiTipDialog.show();
       link_checkPatientInfo();

    }


    private QMUITipDialog qmuiTipDialog = null;


    private void rest1(){

        bt11.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt11.setChangeAlphaWhenPress(true);//点击改变透明度
        bt11.setBorderColor(Color.parseColor("#B6B6B6"));
        bt11.setBackground(null);
        bt11.setTextColor(Color.parseColor("#B6B6B6"));

        bt12.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt12.setChangeAlphaWhenPress(true);//点击改变透明度
        bt12.setBorderColor(Color.parseColor("#B6B6B6"));
        bt12.setBackground(null);
        bt12.setTextColor(Color.parseColor("#B6B6B6"));

        bt13.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt13.setChangeAlphaWhenPress(true);//点击改变透明度
        bt13.setBorderColor(Color.parseColor("#B6B6B6"));
        bt13.setBackground(null);
        bt13.setTextColor(Color.parseColor("#B6B6B6"));

        bt14.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt14.setChangeAlphaWhenPress(true);//点击改变透明度
        bt14.setBorderColor(Color.parseColor("#B6B6B6"));
        bt14.setBackground(null);
        bt14.setTextColor(Color.parseColor("#B6B6B6"));
    }

    private void rest2(){
        bt21.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt21.setChangeAlphaWhenPress(true);//点击改变透明度
        bt21.setBorderColor(Color.parseColor("#B6B6B6"));
        bt21.setBackground(null);
        bt21.setTextColor(Color.parseColor("#B6B6B6"));

        bt22.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt22.setChangeAlphaWhenPress(true);//点击改变透明度
        bt22.setBorderColor(Color.parseColor("#B6B6B6"));
        bt22.setBackground(null);
        bt22.setTextColor(Color.parseColor("#B6B6B6"));

        bt23.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt23.setChangeAlphaWhenPress(true);//点击改变透明度
        bt23.setBorderColor(Color.parseColor("#B6B6B6"));
        bt23.setBackground(null);
        bt23.setTextColor(Color.parseColor("#B6B6B6"));

        bt24.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt24.setChangeAlphaWhenPress(true);//点击改变透明度
        bt24.setBorderColor(Color.parseColor("#B6B6B6"));
        bt24.setBackground(null);
        bt24.setTextColor(Color.parseColor("#B6B6B6"));
    }

    private void rest3(){
        bt31.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt31.setChangeAlphaWhenPress(true);//点击改变透明度
        bt31.setBorderColor(Color.parseColor("#B6B6B6"));
        bt31.setBackground(null);
        bt31.setTextColor(Color.parseColor("#B6B6B6"));

        bt32.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt32.setChangeAlphaWhenPress(true);//点击改变透明度
        bt32.setBorderColor(Color.parseColor("#B6B6B6"));
        bt32.setBackground(null);
        bt32.setTextColor(Color.parseColor("#B6B6B6"));

        bt33.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt33.setChangeAlphaWhenPress(true);//点击改变透明度
        bt33.setBorderColor(Color.parseColor("#B6B6B6"));
        bt33.setBackground(null);
        bt33.setTextColor(Color.parseColor("#B6B6B6"));

        bt34.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt34.setChangeAlphaWhenPress(true);//点击改变透明度
        bt34.setBorderColor(Color.parseColor("#B6B6B6"));
        bt34.setBackground(null);
        bt34.setTextColor(Color.parseColor("#B6B6B6"));
    }
    private void rest4(){
        bt41.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt41.setChangeAlphaWhenPress(true);//点击改变透明度
        bt41.setBorderColor(Color.parseColor("#B6B6B6"));
        bt41.setBackground(null);
        bt41.setTextColor(Color.parseColor("#B6B6B6"));

        bt42.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt42.setChangeAlphaWhenPress(true);//点击改变透明度
        bt42.setBorderColor(Color.parseColor("#B6B6B6"));
        bt42.setBackground(null);
        bt42.setTextColor(Color.parseColor("#B6B6B6"));

        bt43.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt43.setChangeAlphaWhenPress(true);//点击改变透明度
        bt43.setBorderColor(Color.parseColor("#B6B6B6"));
        bt43.setBackground(null);
        bt43.setTextColor(Color.parseColor("#B6B6B6"));

        bt44.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt44.setChangeAlphaWhenPress(true);//点击改变透明度
        bt44.setBorderColor(Color.parseColor("#B6B6B6"));
        bt44.setBackground(null);
        bt44.setTextColor(Color.parseColor("#B6B6B6"));
    }

    private void rest5(){
        bt51.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt51.setChangeAlphaWhenPress(true);//点击改变透明度
        bt51.setBorderColor(Color.parseColor("#B6B6B6"));
        bt51.setBackground(null);
        bt51.setTextColor(Color.parseColor("#B6B6B6"));

        bt52.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt52.setChangeAlphaWhenPress(true);//点击改变透明度
        bt52.setBorderColor(Color.parseColor("#B6B6B6"));
        bt52.setBackground(null);
        bt52.setTextColor(Color.parseColor("#B6B6B6"));

        bt53.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt53.setChangeAlphaWhenPress(true);//点击改变透明度
        bt53.setBorderColor(Color.parseColor("#B6B6B6"));
        bt53.setBackground(null);
        bt53.setTextColor(Color.parseColor("#B6B6B6"));

        bt54.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt54.setChangeAlphaWhenPress(true);//点击改变透明度
        bt54.setBorderColor(Color.parseColor("#B6B6B6"));
        bt54.setBackground(null);
        bt54.setTextColor(Color.parseColor("#B6B6B6"));
    }

    private void rest6(){
        bt61.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt61.setChangeAlphaWhenPress(true);//点击改变透明度
        bt61.setBorderColor(Color.parseColor("#B6B6B6"));
        bt61.setBackground(null);
        bt61.setTextColor(Color.parseColor("#B6B6B6"));

        bt62.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt62.setChangeAlphaWhenPress(true);//点击改变透明度
        bt62.setBorderColor(Color.parseColor("#B6B6B6"));
        bt62.setBackground(null);
        bt62.setTextColor(Color.parseColor("#B6B6B6"));

        bt63.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt63.setChangeAlphaWhenPress(true);//点击改变透明度
        bt63.setBorderColor(Color.parseColor("#B6B6B6"));
        bt63.setBackground(null);
        bt63.setTextColor(Color.parseColor("#B6B6B6"));

        bt64.setRadius(QMUIDisplayHelper.dp2px(this, 15));
        bt64.setChangeAlphaWhenPress(true);//点击改变透明度
        bt64.setBorderColor(Color.parseColor("#B6B6B6"));
        bt64.setBackground(null);
        bt64.setTextColor(Color.parseColor("#B6B6B6"));
    }


    private void link_checkPatientInfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nursePatient/getBaseInfo?patientId="+idid+"&patientCode="+code);
        // step 3：创建 Call 对象
       Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", sex);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    if (!isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    CheckBBean logingBe = gson.fromJson(jsonObject, CheckBBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            chuanghao.setText("床号: " + logingBe.getResult().getBedName());
                                            name.setText("姓名: " + logingBe.getResult().getPatientName());
                                            if (logingBe.getResult().getGender() == 1) {
                                                sex.setText("性别: 男");
                                            } else {
                                                sex.setText("性别: 女");
                                            }
                                            hulidengji.setText("护理等级: " + logingBe.getResult().getNurseLevelName());
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        } else {
                            if (!isFinishing()){
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), sex);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), sex);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    private void link_patroladd() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("type", "1");
            object.put("typeName", "巡房评估");
            object.put("content", content);
            object.put("patientName", nameP);
            object.put("patientId", idid);
            object.put("patientCode", code);
            object.put("dataType", dataType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/patrol/add");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", sex);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (qmuiTipDialog != null)
                            qmuiTipDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ErrorBean logingBe = gson.fromJson(jsonObject, ErrorBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        QMUITipDialog qmuiTipDialog = new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                                .setTipWord("巡房成功")
                                                .create();
                                        qmuiTipDialog.show();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                SystemClock.sleep(1000);
                                                if (!isFinishing())
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            if (qmuiTipDialog.isShowing()){
                                                                qmuiTipDialog.dismiss();
                                                            }
                                                            finish();
                                                        }catch (Exception e){
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }
                                        }).start();

                                    }
                                });
                        } else {
                            if (!isFinishing()){
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), sex);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), sex);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1_1:
                rest1();
                bt11.setTextColor(Color.parseColor("#FFFFFF"));
                bt11.setBackgroundColor(Color.parseColor("#59B683"));
                bt11.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt11.setChangeAlphaWhenPress(true);//点击改变透明度
                sp1="清楚";
                break;
            case R.id.bt1_2:
                rest1();
                bt12.setTextColor(Color.parseColor("#FFFFFF"));
                bt12.setBackgroundColor(Color.parseColor("#59B683"));
                bt12.setBackgroundColor(Color.parseColor("#59B683"));
                bt12.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt12.setChangeAlphaWhenPress(true);//点击改变透明度
                sp1="嗜睡";
                break;
            case R.id.bt1_3:
                rest1();
                bt13.setTextColor(Color.parseColor("#FFFFFF"));
                bt13.setBackgroundColor(Color.parseColor("#59B683"));
                bt13.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt13.setChangeAlphaWhenPress(true);//点击改变透明度
                sp1="模糊";
                break;
            case R.id.bt1_4:
                rest1();
                bt14.setTextColor(Color.parseColor("#FFFFFF"));
                bt14.setBackgroundColor(Color.parseColor("#59B683"));
                bt14.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt14.setChangeAlphaWhenPress(true);//点击改变透明度
                sp1="昏睡";
                break;
            case R.id.bt2_1:
                rest2();
                bt21.setTextColor(Color.parseColor("#FFFFFF"));
                bt21.setBackgroundColor(Color.parseColor("#59B683"));
                bt21.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt21.setChangeAlphaWhenPress(true);//点击改变透明度
                sp2="正常";
                break;
            case R.id.bt2_2:
                rest2();
                bt22.setTextColor(Color.parseColor("#FFFFFF"));
                bt22.setBackgroundColor(Color.parseColor("#59B683"));
                bt22.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt22.setChangeAlphaWhenPress(true);//点击改变透明度
                sp2="压疮";
                break;
            case R.id.bt2_3:
                rest2();
                bt23.setTextColor(Color.parseColor("#FFFFFF"));
                bt23.setBackgroundColor(Color.parseColor("#59B683"));
                bt23.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt23.setChangeAlphaWhenPress(true);//点击改变透明度
                sp2="烫伤";
                break;
            case R.id.bt2_4:
                rest2();
                bt24.setTextColor(Color.parseColor("#FFFFFF"));
                bt24.setBackgroundColor(Color.parseColor("#59B683"));
                bt24.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt24.setChangeAlphaWhenPress(true);//点击改变透明度
                sp2="外伤";
                break;
            case R.id.bt3_1:
                rest3();
                bt31.setTextColor(Color.parseColor("#FFFFFF"));
                bt31.setBackgroundColor(Color.parseColor("#59B683"));
                bt31.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt31.setChangeAlphaWhenPress(true);//点击改变透明度
                sp3="正常";
                break;
            case R.id.bt3_2:
                rest3();
                bt32.setTextColor(Color.parseColor("#FFFFFF"));
                bt32.setBackgroundColor(Color.parseColor("#59B683"));
                bt32.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt32.setChangeAlphaWhenPress(true);//点击改变透明度
                sp3="全瘫";
                break;
            case R.id.bt3_3:
                rest3();
                bt33.setTextColor(Color.parseColor("#FFFFFF"));
                bt33.setBackgroundColor(Color.parseColor("#59B683"));
                bt33.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt33.setChangeAlphaWhenPress(true);//点击改变透明度
                sp3="截瘫";
                break;
            case R.id.bt3_4:
                rest3();
                bt34.setTextColor(Color.parseColor("#FFFFFF"));
                bt34.setBackgroundColor(Color.parseColor("#59B683"));
                bt34.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt34.setChangeAlphaWhenPress(true);//点击改变透明度
                sp3="其它";
                break;
            case R.id.bt4_1:
                rest4();
                bt41.setTextColor(Color.parseColor("#FFFFFF"));
                bt41.setBackgroundColor(Color.parseColor("#59B683"));
                bt41.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt41.setChangeAlphaWhenPress(true);//点击改变透明度
                sp4="正常";
                break;
            case R.id.bt4_2:
                rest4();
                bt42.setTextColor(Color.parseColor("#FFFFFF"));
                bt42.setBackgroundColor(Color.parseColor("#59B683"));
                bt42.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt42.setChangeAlphaWhenPress(true);//点击改变透明度
                sp4="近视";
                break;
            case R.id.bt4_3:
                rest4();
                bt43.setTextColor(Color.parseColor("#FFFFFF"));
                bt43.setBackgroundColor(Color.parseColor("#59B683"));
                bt43.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt43.setChangeAlphaWhenPress(true);//点击改变透明度
                sp4="远视";
                break;
            case R.id.bt4_4:
                rest4();
                bt44.setTextColor(Color.parseColor("#FFFFFF"));
                bt44.setBackgroundColor(Color.parseColor("#59B683"));
                bt44.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt44.setChangeAlphaWhenPress(true);//点击改变透明度
                sp4="失明";
                break;
            case R.id.bt5_1:
                rest5();
                bt51.setTextColor(Color.parseColor("#FFFFFF"));
                bt51.setBackgroundColor(Color.parseColor("#59B683"));
                bt51.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt51.setChangeAlphaWhenPress(true);//点击改变透明度
                sp5="普食";
                break;
            case R.id.bt5_2:
                rest5();
                bt52.setTextColor(Color.parseColor("#FFFFFF"));
                bt52.setBackgroundColor(Color.parseColor("#59B683"));
                bt52.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt52.setChangeAlphaWhenPress(true);//点击改变透明度
                sp5="半流食";
                break;
            case R.id.bt5_3:
                rest5();
                bt53.setTextColor(Color.parseColor("#FFFFFF"));
                bt53.setBackgroundColor(Color.parseColor("#59B683"));
                bt53.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt53.setChangeAlphaWhenPress(true);//点击改变透明度
                sp5="流食";
                break;
            case R.id.bt5_4:
                rest5();
                bt54.setTextColor(Color.parseColor("#FFFFFF"));
                bt54.setBackgroundColor(Color.parseColor("#59B683"));
                bt54.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt54.setChangeAlphaWhenPress(true);//点击改变透明度
                sp5="禁食";
                break;
            case R.id.bt6_1:
                rest6();
                bt61.setTextColor(Color.parseColor("#FFFFFF"));
                bt61.setBackgroundColor(Color.parseColor("#59B683"));
                bt61.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt61.setChangeAlphaWhenPress(true);//点击改变透明度
                sp6="正常";
                break;
            case R.id.bt6_2:
                rest6();
                bt62.setTextColor(Color.parseColor("#FFFFFF"));
                bt62.setBackgroundColor(Color.parseColor("#59B683"));
                bt62.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt62.setChangeAlphaWhenPress(true);//点击改变透明度
                sp6="便秘";
                break;
            case R.id.bt6_3:
                rest6();
                bt63.setTextColor(Color.parseColor("#FFFFFF"));
                bt63.setBackgroundColor(Color.parseColor("#59B683"));
                bt63.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt63.setChangeAlphaWhenPress(true);//点击改变透明度
                sp6="腹泻";
                break;
            case R.id.bt6_4:
                rest6();
                bt64.setTextColor(Color.parseColor("#FFFFFF"));
                bt64.setBackgroundColor(Color.parseColor("#59B683"));
                bt64.setRadius(QMUIDisplayHelper.dp2px(this, 15));
                bt64.setChangeAlphaWhenPress(true);//点击改变透明度
                sp6="其它";
                break;
            case R.id.queding:
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("保存中...")
                        .create();
                qmuiTipDialog.show();
                content="【意识状态】 "+sp1+" 【皮肤粘膜】 "+sp2+" 【肢体活动】 "+sp3+" 【视力】 "+sp4+" 【饮食】 "+sp5+" 【排便】 "+sp6;
                link_patroladd();
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
