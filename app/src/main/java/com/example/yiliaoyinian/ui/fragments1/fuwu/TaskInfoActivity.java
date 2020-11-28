package com.example.yiliaoyinian.ui.fragments1.fuwu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;


import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.CheakPBean;
import com.example.yiliaoyinian.Beans.ErWeiMaBean;

import com.example.yiliaoyinian.Beans.RenWuBean;
import com.example.yiliaoyinian.Beans.TaskInFoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityTaskInfoBinding;
import com.example.yiliaoyinian.dialog.CommomDialog2;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.ui.SaoMaActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.IOException;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class TaskInfoActivity extends BaseActivity {
   private ActivityTaskInfoBinding binding=null;




    private QMUITipDialog qmuiTipDialog = null;
    private long serviceId = 0;
    String patientCode = null, patientId = null, myPatientId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EventBus.getDefault().register(this);
        serviceId = getIntent().getLongExtra("id", 0);

        View f = findViewById(R.id.fanhui);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.button.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        binding.button.setChangeAlphaWhenPress(true);//点击改变透明度
        binding.myTitle.setText(getIntent().getStringExtra("name") + "");
        binding.kkk.setText(getIntent().getStringExtra("title")+"");
        if (getIntent().getIntExtra("type", 2) == 1) {//未完成
            link_getByServiceId(serviceId);
        } else {
            //已完成
            binding.button.setVisibility(View.GONE);
            link_getByServiceId2(serviceId);
        }
    }


    public void onViewClicked(View view) {
        if (binding.button.getText().toString().equals("验证身份")) {
            //点击验证身份
            startActivity(new Intent(this, SaoMaActivity.class));

        } else if (binding.button.getText().toString().equals("完成")) {
            if (patientId != null && patientCode != null) {
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("提交中...")
                        .create();
                qmuiTipDialog.show();
                link_complete(patientCode, patientId);
            } else {
                ToastUtils.setMessage("请扫码后重试", binding.button);
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("UnFinshFragment"))
            return;
        Log.d("TaskInfoActivity", "msgWarp" + msgWarp);
        //第三种方式,使用Gson的思想
        try {
            ErWeiMaBean student = JSONObject.parseObject(msgWarp, ErWeiMaBean.class);
            Log.d("TaskInfoActivity", "student:" + student);
            if (student != null && student.getType() == 1 && myPatientId != null) {//type:1-患者，2-楼层，3-房间，4-床位
                if (myPatientId.equals(student.getData().getDataId() + "")) {
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("验证中...")
                            .create();
                    qmuiTipDialog.show();
                    patientCode = student.getData().getDataCode();
                    patientId = student.getData().getDataId() + "";
                    link_checkPatientInfo(student.getData().getDataCode(), student.getData().getDataId() + "",student.getData().getDataType());
                } else {
                    patientCode = null;
                    patientId = null;
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                            .setTipWord("身份不匹配")
                            .create();
                    qmuiTipDialog.show();
                    binding.button.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    }, 1800);
                }
            } else {
                patientCode = null;
                patientId = null;
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                        .setTipWord("身份不匹配")
                        .create();
                qmuiTipDialog.show();
                binding.button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (qmuiTipDialog != null)
                            qmuiTipDialog.dismiss();
                    }
                }, 1800);
            }
        } catch (Exception e) {
            qmuiTipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("身份不匹配")
                    .create();
            qmuiTipDialog.show();
            binding.button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (qmuiTipDialog != null)
                        qmuiTipDialog.dismiss();
                }
            }, 1800);
            Log.d("TaskInfoActivity", e.getMessage() + "json异常");
        }


    }


    private void link_getByServiceId(long id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseService/getByServiceId?serviceId=" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.button);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    TaskInFoBean logingBe = gson.fromJson(jsonObject, TaskInFoBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                myPatientId = logingBe.getResult().getPatientId() + "";
                                                binding.contentTv.setText(logingBe.getResult().getServiceContent());
                                                binding.tasktime.setText("任务时间: " + DateUtils.time(logingBe.getResult().getStartTime() + ""));
                                                binding.zhuzhi.setText("主治医生: " + logingBe.getResult().getDoctorName());
                                                binding.button.setVisibility(View.VISIBLE);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing()) {
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                            }
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }


    private void link_getByServiceId2(long id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseServiceLog/getLogById?id=" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.button);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "已完成服务信息:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    TaskInFoBean logingBe = gson.fromJson(jsonObject, TaskInFoBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                binding.contentTv.setText(logingBe.getResult().getServiceContent());
                                                binding.tasktime.setText(DateUtils.time(logingBe.getResult().getStartTime() + ""));
                                                binding.zhuzhi.setText("主治医生: " + logingBe.getResult().getDoctorName());
                                                binding.c1.setVisibility(View.VISIBLE);
                                                binding.endTime.setVisibility(View.VISIBLE);
                                                binding.hushi.setVisibility(View.VISIBLE);
                                                binding.endTime.setText("处理时间: " + DateUtils.time(logingBe.getResult().getFinishTime() + ""));
                                                binding.hushi.setText("护士: " + logingBe.getResult().getFinishUser());
                                                binding.chuanghao.setText("床号: " + logingBe.getResult().getFloorName() + "-" + logingBe.getResult().getRoomName() + "-" + logingBe.getResult().getBedName());
                                                binding.name.setText("姓名: " + logingBe.getResult().getPatientName());
                                                if (logingBe.getResult().getGender() == 1) {
                                                    binding.sex.setText("性别: 男");
                                                } else {
                                                    binding.sex.setText("性别: 女");
                                                }
                                                binding.huli.setText("护理等级: " + logingBe.getResult().getNurseLevelName());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    private void link_checkPatientInfo(String patientCode, String patientId,int dataType) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("patientCode", patientCode);
            object.put("patientId", patientId);
            object.put("serviceId", serviceId);
            object.put("dataType", dataType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nurseService/checkPatientInfo");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.button);
                if (!isFinishing())
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
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    CheakPBean logingBe = gson.fromJson(jsonObject, CheakPBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult() != null)
                                if (!isFinishing())
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                binding.chuanghao.setText("床号: " + logingBe.getResult().getFloorName() + "-" + logingBe.getResult().getRoomName() + "-" + logingBe.getResult().getBedName());
                                                binding.name.setText("姓名: " + logingBe.getResult().getPatientName());
                                                if (logingBe.getResult().getGender() == 1) {
                                                    binding.sex.setText("性别: 男");
                                                } else {
                                                    binding.sex.setText("性别: 女");
                                                }
                                                binding.huli.setText("护理等级: " + logingBe.getResult().getNurseLevelName());
                                                binding.c1.setVisibility(View.VISIBLE);
                                                link_complete(patientCode, patientId);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        qmuiTipDialog = new QMUITipDialog.Builder(TaskInfoActivity.this)
                                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                                .setTipWord("验证失败")
                                                .create();
                                        qmuiTipDialog.show();
                                        binding.c1.setVisibility(View.VISIBLE);
                                        //改变位置
                                        binding.button.setText("完成");
                                        binding.button.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (qmuiTipDialog != null)
                                                    qmuiTipDialog.dismiss();
                                            }
                                        }, 1800);
                                    }
                                });

                        }
                    } else {
                        if (!isFinishing())
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (qmuiTipDialog != null)
                                        qmuiTipDialog.dismiss();
                                }
                            });
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (!isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                }
            }
        });
    }

    private void link_complete(String patientCode, String patientId) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("patientCode", patientCode);
            object.put("patientId", patientId);
            object.put("serviceId", serviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nurseService/complete");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", binding.button);
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
                    Log.d("LoginActivity", "任务处理:"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    RenWuBean logingBe = gson.fromJson(jsonObject, RenWuBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult()!=null) {
                            if (!isFinishing()) {
                                //ToastUtils.setMessage("操作成功", button);
                                EventBus.getDefault().post("UnFinshFragment");
                            }
                            SystemClock.sleep(300);
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            //验证成功
                                            String showtitles = "";
                                            if (logingBe.getResult().getType()==2) {
                                                showtitles = "本次处理完成！\n下次处理时间:" + DateUtils.time(logingBe.getResult().getLastTime()+"");
                                            } else {
                                                showtitles = "本次处理完成！";
                                            }
                                            new CommomDialog2(TaskInfoActivity.this, R.style.dialogs2, showtitles, new CommomDialog2.OnCloseListener() {
                                                @Override
                                                public void onClick(Dialog dialog, boolean confirm) {
                                                   // Log.d("DAFragment3", "confirm:" + confirm);
                                                    if (confirm) {
                                                        //退出动作
                                                        dialog.dismiss();
                                                        finish();
                                                    }
                                                }
                                            }).setTitle("温馨提示").setPositiveButton("确定").show();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.button);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");

                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
