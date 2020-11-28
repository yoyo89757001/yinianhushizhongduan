package com.example.yiliaoyinian.ui.wode;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yiliaoyinian.Beans.HuGongBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.ui.LoginActivity;
import com.example.yiliaoyinian.utils.AppManager;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WoDeZiLiaoActivity extends BaseActivity {

    QMUIRadiusImageView touxiang;
    TextView name;
    TextView sex;
    TextView danwei;
    TextView dizhi;
    QMUIButton tuichu;
    TextView banben;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zi_liao);
        touxiang=findViewById(R.id.touxiang);
        sex=findViewById(R.id.sex);
        danwei=findViewById(R.id.danwei);
        dizhi=findViewById(R.id.dizhi);
        tuichu=findViewById(R.id.tuichu);
        banben=findViewById(R.id.banben);
        name=findViewById(R.id.name);


        View c = findViewById(R.id.fanhui);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tuichu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        tuichu.setChangeAlphaWhenPress(true);//点击改变透明度
//        Glide.with(this)
//                .load(MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getHeadImg())
//                //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(touxiang);

        banben.setText("当前版本号:"+getLocalVersionName(this));
        link_getNurseInfo();

    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    private void link_getNurseInfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseWorker/getNurseInfo");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", tuichu);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "护工" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    HuGongBean logingBe = gson.fromJson(jsonObject, HuGongBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                name.setText(logingBe.getResult().getNurseName());
                                                if (logingBe.getResult().getGender() == 1) {
                                                    sex.setText("男");
                                                } else {
                                                    sex.setText("女");
                                                }
                                                // age.setText(logingBe.getResult().get);
                                                Glide.with(WoDeZiLiaoActivity.this)
                                                        .load(logingBe.getResult().getHeadImg())
                                                        .error(R.drawable.defo_bg)
                                                        //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                                        .into(touxiang);
                                                danwei.setText(logingBe.getResult().getNurseCode());
                                                dizhi.setText(DateUtils.time1(logingBe.getResult().getEntryTime() + ""));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            if (!isFinishing()) {
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), tuichu);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), tuichu);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }


    public void onViewClickeds(View view) {
        new CommomDialog(this, R.style.dialogs, "请确认是否退出?", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                Log.d("DAFragment3", "confirm:" + confirm);
                if (confirm) {
                    //退出动作
                    SaveInfoBean bean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                    bean.setToken(null);
                    MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                    startActivity(new Intent(WoDeZiLiaoActivity.this, LoginActivity.class));
                    dialog.dismiss();
                    AppManager.getAppManager().finishAllActivity();
                }
            }
        }).setTitle("确认").setPositiveButton("确定").show();

    }
}
