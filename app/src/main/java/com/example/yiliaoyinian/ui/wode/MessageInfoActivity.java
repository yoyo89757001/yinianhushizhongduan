package com.example.yiliaoyinian.ui.wode;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.MssagesInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MessageInfoActivity extends BaseActivity {

    View fanhui;
//    @BindView(R.id.myTitle)
//    TextView myTitle;
//    @BindView(R.id.topbar)
//    RelativeLayout topbar;
//    @BindView(R.id.title)
    TextView title;
    TextView contentTv;
    TextView fabuzhe;
    TextView shijian;
    private long idid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fanhui=findViewById(R.id.fanhui);
        title=findViewById(R.id.title);
        contentTv=findViewById(R.id.content_tv);
        fabuzhe=findViewById(R.id.fabuzhe);
        shijian=findViewById(R.id.shijian);


        idid = getIntent().getLongExtra("idid", 0);
        link_complete(idid);


    }

    private void link_complete(long pageNum) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/sysMessage/getById?id=" + pageNum);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", fanhui);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "系统通知" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    MssagesInfoBean logingBe = gson.fromJson(jsonObject, MssagesInfoBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            title.setText(logingBe.getResult().getTitle());
                                            contentTv.setText(logingBe.getResult().getContent());
                                            fabuzhe.setText(logingBe.getResult().getPublishUserName());
                                            shijian.setText(DateUtils.time(logingBe.getResult().getPublishTime()+""));
                                            link_complete2(idid);
                                        }
                                    });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }

    private void link_complete2(long pageNum) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/sysMessage/changeRead?id=" + pageNum);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", fanhui);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "改为已读" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ErrorBean logingBe = gson.fromJson(jsonObject, ErrorBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post("MessageInfoActivity");
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), fanhui);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }



}
