package com.example.yiliaoyinian.ui.zhiban;


import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;

import com.example.yiliaoyinian.ui.BaseActivity;

import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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


public class AddBianQianActivity extends BaseActivity implements View.OnClickListener {


    TextView baocun;
    AppCompatEditText title;
    AppCompatEditText miaoshu;
    private QMUITipDialog qmuiTipDialog = null;
    private long id;
    private String titleT=null,conetn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bian_qian);
        //@OnClick({R.id.fanhui, R.id.baocun})
        findViewById(R.id.fanhui).setOnClickListener(this);
        findViewById(R.id.baocun).setOnClickListener(this);
        baocun=findViewById(R.id.baocun);
        title=findViewById(R.id.title);
        miaoshu=findViewById(R.id.miaoshu);

        id=getIntent().getLongExtra("id",0);
        conetn=getIntent().getStringExtra("conetn");
        titleT=getIntent().getStringExtra("title");

        if (titleT!=null){
            title.setText(titleT);
        }
        if (conetn!=null){
            miaoshu.setText(conetn);
        }
    }



    private void link_complete(String patientCode, String patientId) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("noteTitle", patientCode);
            object.put("noteContent", patientId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nurseNote/add");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", baocun);
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
                                        try {
                                            ToastUtils.setMessage("保存成功", baocun);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SystemClock.sleep(1900);
                                                    if (!isFinishing())
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            finish();
                                                        }
                                                    });

                                                }
                                            }).start();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), baocun);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), baocun);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }


    private void link_complete2(String patientCode, String patientId) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("id", id);
            object.put("noteTitle", patientCode);
            object.put("noteContent", patientId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nurseNote/update");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", baocun);
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
                                        try {
                                            ToastUtils.setMessage("修改成功", baocun);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SystemClock.sleep(1900);
                                                    if (!isFinishing()){
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                }
                                            }).start();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), baocun);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), baocun);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                if (!title.getText().toString().trim().equals("") && !miaoshu.getText().toString().trim().equals("")){
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("保存中..")
                            .create();
                    qmuiTipDialog.show();
                    if (id!=0){//修改
                        link_complete2(title.getText().toString().trim(),miaoshu.getText().toString().trim());
                    }else {
                        link_complete(title.getText().toString().trim(),miaoshu.getText().toString().trim());
                    }
                }else {
                    ToastUtils.setMessage("信息不完整",baocun);
                }
                break;
            case R.id.baocun:
                if (!title.getText().toString().trim().equals("") && !miaoshu.getText().toString().trim().equals("")){
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("保存中...")
                            .create();
                    qmuiTipDialog.show();
                    if (id!=0){//修改
                        link_complete2(title.getText().toString().trim(),miaoshu.getText().toString().trim());
                    }else {
                        link_complete(title.getText().toString().trim(),miaoshu.getText().toString().trim());
                    }
                }else {
                    ToastUtils.setMessage("信息不完整",baocun);
                }
                break;
        }
    }
}
