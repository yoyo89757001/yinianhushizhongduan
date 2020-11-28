package com.example.yiliaoyinian.ui.fragments1.dairuke;


import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.JuZhuBean;
import com.example.yiliaoyinian.Beans.Kllibean;
import com.example.yiliaoyinian.Beans.RuKeBean;
import com.example.yiliaoyinian.Beans.RuKeBean3;
import com.example.yiliaoyinian.Beans.ServiceTypeBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.PopHeadAdapter;
import com.example.yiliaoyinian.databinding.ActivityRuKeNextBinding;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RuKeNextActivity extends BaseActivity implements OnDateSetListener, View.OnClickListener {





    private QMUIPopup popup=null;
    private List<ServiceTypeBean> c2List=new ArrayList<>();
    private List<ServiceTypeBean> c3List=new ArrayList<>();
    private List<ServiceTypeBean> c4List=new ArrayList<>();
    private PopHeadAdapter c2AdapterLX=null;
    private PopHeadAdapter c3AdapterLX=null;
    private PopHeadAdapter c4AdapterLX=null;
    private RuKeBean.ResultBean resultBean=null;
    private JuZhuBean juZhuBean=null;
    private String id1=null,id2=null,id3=null;

    private ActivityRuKeNextBinding binding=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRuKeNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // @OnClick({R.id.viewBt1a, R.id.viewBt2a, R.id.viewBt3a, R.id.viewBt4a, R.id.wancheng,R.id.fanhui})
        findViewById(R.id.viewBt1a).setOnClickListener(this);
        findViewById(R.id.viewBt2a).setOnClickListener(this);
        findViewById(R.id.viewBt3a).setOnClickListener(this);
        findViewById(R.id.viewBt4a).setOnClickListener(this);
        binding.wancheng.setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);


        binding.wancheng.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        binding.wancheng.setChangeAlphaWhenPress(true);//点击改变透明度

        resultBean = (RuKeBean.ResultBean) getIntent().getSerializableExtra("person");
        juZhuBean = (JuZhuBean) getIntent().getSerializableExtra("JuZhu");

        if (resultBean!=null){
            binding.name.setText("姓名: "+resultBean.getPatientName());
            binding.idid.setText("ID: "+resultBean.getPatientCode());
            binding.shenfenzheng.setText("身份证: "+resultBean.getIdCard());
            binding.age.setText("年龄: "+resultBean.getAge()+"岁");
        }
        if (juZhuBean!=null){
            binding.chuangwei.setText("床位: "+juZhuBean.getL4Name());
            link_getGroupByBedId(juZhuBean.getL4());
        }


        c2AdapterLX=new PopHeadAdapter(c2List, RuKeNextActivity.this);
        c3AdapterLX=new PopHeadAdapter(c3List,RuKeNextActivity.this);
        c4AdapterLX=new PopHeadAdapter(c4List,RuKeNextActivity.this);

        link_getOpenIn();

    }




    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (timePickerView.getTag() == null)
            return;
        if (timePickerView.getTag().equals("ff1")) {
            binding.timeRuzhu.setText(DateUtils.time1(millseconds + ""));
        }
        timePickerView.dismiss();
    }


    private void link_getGroupByBedId(long id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseGroup/getGroupByBedId?bedId=" + id);
        // step 3：创建 Call 对象
       Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.wancheng);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "护理组:"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    Kllibean logingBe = gson.fromJson(jsonObject, Kllibean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            StringBuilder sp=new StringBuilder();
                                            for (Kllibean.ResultBean resultBean : logingBe.getResult()) {
                                                sp.append(resultBean.getGroupName());
                                                sp.append(",");
                                            }
                                            String so=sp.toString();
                                            so=so.substring(0,so.length()-1);
                                            binding.hulizu.setText("护理组: "+so);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }

    private void link_getOpenIn() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nursePatient/openIn");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.wancheng);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "入科需要信息:"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    RuKeBean3 logingBe = gson.fromJson(jsonObject, RuKeBean3.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            for (RuKeBean3.ResultBean.LevelListBean levelListBean : logingBe.getResult().getLevelList()) {
                                                ServiceTypeBean serviceTypeBean=new ServiceTypeBean();
                                                serviceTypeBean.setTypeId(levelListBean.getId()+"");
                                                serviceTypeBean.setTypeName(levelListBean.getNurseLevelName());
                                                c2List.add(serviceTypeBean);
                                            }
                                            c2AdapterLX.notifyDataSetChanged();
                                            for (RuKeBean3.ResultBean.DepartListBean levelListBean : logingBe.getResult().getDepartList()) {
                                                ServiceTypeBean serviceTypeBean=new ServiceTypeBean();
                                                serviceTypeBean.setTypeId(levelListBean.getId()+"");
                                                serviceTypeBean.setTypeName(levelListBean.getDepartName());
                                                c3List.add(serviceTypeBean);
                                            }
                                            c3AdapterLX.notifyDataSetChanged();
                                            for (RuKeBean3.ResultBean.WorkerListBean levelListBean : logingBe.getResult().getWorkerList()) {
                                                ServiceTypeBean serviceTypeBean=new ServiceTypeBean();
                                                serviceTypeBean.setTypeId(levelListBean.getId()+"");
                                                serviceTypeBean.setTypeName(levelListBean.getNurseName());
                                                c4List.add(serviceTypeBean);
                                            }
                                            c4AdapterLX.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }

    private QMUITipDialog qmuiTipDialog = null;
    private void link_SaveInBed() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("id", resultBean.getId());
            object.put("patientCode", resultBean.getPatientCode());
            object.put("buildId", juZhuBean.getL1());
            object.put("buildName", juZhuBean.getL1Name());
            object.put("floorId", juZhuBean.getL2());
            object.put("floorName", juZhuBean.getL2Name());
            object.put("roomId", juZhuBean.getL3());
            object.put("roomName", juZhuBean.getL3Name());
            object.put("bedId", juZhuBean.getL4());
            object.put("bedName", juZhuBean.getL4Name());
            object.put("checkInTime", binding.timeRuzhu.getText().toString().trim());
            object.put("nurseLevelId", id1);
            object.put("nurseLevelName", binding.hulidengji.getText().toString().trim());
            object.put("doctorId", id3);
            object.put("doctorName", binding.zhuzhiyishi.getText().toString().trim());
            object.put("nurseDeptId", id2);
            object.put("nurseDeptName", binding.keshi.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nursePatient/inBed");
        // step 3：创建 Call 对象
       Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.wancheng);
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
                                        try {
                                            ToastUtils.setSuccess("入科成功",binding.wancheng);
                                            EventBus.getDefault().post("Fragment1_3");
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        SystemClock.sleep(1900);
                                                        if (!isFinishing())
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                               EventBus.getDefault().post("rukechenggong");
                                                               finish();
                                                            }
                                                        });
                                                    }catch (Exception e){
                                                        e.printStackTrace();
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
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.wancheng);
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
            case R.id.viewBt1a:
                TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                        .setCallBack(this)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setHourText("时")
                        .setMinuteText("分")
                        .setCyclic(false)
                        .setMinMillseconds(System.currentTimeMillis())
                        .setMaxMillseconds(DateUtils.dataOne("2100-1-1"))
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(Color.parseColor("#FF59B683"))
                        .setType(Type.YEAR_MONTH_DAY)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(Color.parseColor("#FF59B683"))
                        .setWheelItemTextSize(18)
                        .build();
                mDialogAll.show(getSupportFragmentManager(), "ff1");
                break;
            case R.id.viewBt2a:
                popup = QMUIPopups.listPopup(this, 520, 400, c2AdapterLX, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.hulidengji.setText(c2List.get(position).getTypeName());
                        id1=c2List.get(position).getTypeId();
                        popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 4))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.c2);
                break;
            case R.id.viewBt3a:
                popup = QMUIPopups.listPopup(this, 520, 400, c3AdapterLX, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.keshi.setText(c3List.get(position).getTypeName());
                        id2=c3List.get(position).getTypeId();
                        popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 4))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.c3);
                break;
            case R.id.viewBt4a:
                popup = QMUIPopups.listPopup(this, 520, 400, c4AdapterLX, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.zhuzhiyishi.setText(c4List.get(position).getTypeName());
                        id3=c4List.get(position).getTypeId();
                        popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 4))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.c4);
                break;
            case R.id.wancheng:
                if (juZhuBean!=null && resultBean!=null && !binding.timeRuzhu.getText().toString().trim().equals("") && !binding.hulidengji.getText().toString().trim().equals("") && !binding.keshi.getText().toString().trim().equals("")){
                    qmuiTipDialog = new QMUITipDialog.Builder(RuKeNextActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("提交中...")
                            .create();
                    qmuiTipDialog.show();
                    link_SaveInBed();
                }else {
                    ToastUtils.setMessage("请填写完整信息",binding.wancheng);
                }
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
