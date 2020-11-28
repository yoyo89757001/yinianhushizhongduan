package com.example.yiliaoyinian.ui.fragments1.fuwu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.SellaceBean;
import com.example.yiliaoyinian.Beans.ServiceTypeBean;
import com.example.yiliaoyinian.Beans.ServiceTypeBean2;
import com.example.yiliaoyinian.Beans.XbenaKraBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.PopHeadAdapter;
import com.example.yiliaoyinian.databinding.ActivityAddBianQianBinding;
import com.example.yiliaoyinian.databinding.ActivityAddServerBinding;
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
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

public class AddServerActivity extends BaseActivity implements OnDateSetListener, View.OnClickListener {




    private QMUIPopup popup=null;
    private List<ServiceTypeBean> leixingList=new ArrayList<>();
    private List<ServiceTypeBean> xiangmuList=new ArrayList<>();
    private PopHeadAdapter popHeadAdapterLX=null;
    private PopHeadAdapter popHeadAdapterXM=null;
    private String leixingId=null,leixingName;private String xiangmuId=null,xiangmuName,itemCode="";
    private SellaceBean.ResultBean.DataBean dataBean=null;
    private long timeL1,timeL2;

    private ActivityAddServerBinding binding=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddServerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  @OnClick({R.id.viewBT1, R.id.viewBT2, R.id.viewBT3, R.id.viewBT4, R.id.viewBT5, R.id.fabu,R.id.fanhui})
        findViewById(R.id.viewBT1).setOnClickListener(this);
        findViewById(R.id.viewBT2).setOnClickListener(this);
        findViewById(R.id.viewBT3).setOnClickListener(this);
        findViewById(R.id.viewBT4).setOnClickListener(this);
        findViewById(R.id.viewBT5).setOnClickListener(this);
        binding.fabu.setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);

        EventBus.getDefault().register(this);
        binding.fabu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        binding.fabu.setChangeAlphaWhenPress(true);//点击改变透明度
        QMUIKeyboardHelper.setVisibilityEventListener(this, new QMUIKeyboardHelper.KeyboardVisibilityEventListener() {
            @Override
            public boolean onVisibilityChanged(boolean isOpen, int heightDiff) {
                // Log.d("AddServerActivity", "isOpen:" + isOpen);
                //  Log.d("AddServerActivity", "heightDiff:" + heightDiff);
                if (isOpen) {
                    ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(binding.scrollView, "translationY", 0.0f,
                            -QMUIDisplayHelper.px2dp(AddServerActivity.this, heightDiff));
                    mAnimatorTranslateY.setDuration(300);
                    mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                    mAnimatorTranslateY.start();
                } else {
                    ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(binding.scrollView, "translationY", binding.scrollView.getTranslationY(), 0);
                    mAnimatorTranslateY.setDuration(300);
                    mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                    mAnimatorTranslateY.start();
                }
                return false;
            }
        });

        popHeadAdapterLX=new PopHeadAdapter(leixingList,AddServerActivity.this);//服务类型
        popHeadAdapterXM=new PopHeadAdapter(xiangmuList,AddServerActivity.this);//项目类型


        link_getServiceTypeList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(SellaceBean.ResultBean.DataBean msgWarp){
        dataBean=msgWarp;
        binding.name.setText(msgWarp.getPatientName());
    }




    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        //Log.d("AddServerActivity", "millseconds:" + millseconds);
        // Log.d("AddServerActivity", DateUtils.time(millseconds+""));
        if (timePickerView.getTag() == null)
            return;
        if (timePickerView.getTag().equals("ff1")) {
            binding.time1.setText(DateUtils.time3(millseconds + ""));
            timeL1=millseconds;
        } else {
            binding.time2.setText(DateUtils.time3(millseconds + ""));
            timeL2=millseconds;
        }
        timePickerView.dismiss();

    }


    private void link_getServiceTypeList() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nurseItem/getServiceTypeList");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败",binding.fabu);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "服务类型"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ServiceTypeBean2 logingBe = gson.fromJson(jsonObject, ServiceTypeBean2.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            leixingList.clear();
                                            for (ServiceTypeBean2.ResultBean bean : logingBe.getResult()) {
                                                ServiceTypeBean typeBean=new ServiceTypeBean();
                                                typeBean.setTypeId(bean.getType()+"");
                                                typeBean.setTypeName(bean.getTypeName());
                                                leixingList.add(typeBean);
                                            }
                                            popHeadAdapterLX.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        }else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.fabu);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.fabu);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    private void link_getByType(String id) {

        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nurseItem/getByType?type="+id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败",binding.fabu);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "项目类型"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    XbenaKraBean logingBe = gson.fromJson(jsonObject, XbenaKraBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            xiangmuList.clear();
                                            for (XbenaKraBean.ResultBean resultBean : logingBe.getResult()) {
                                                ServiceTypeBean typeBean=new ServiceTypeBean();
                                                typeBean.setTypeId(resultBean.getId()+"");
                                                typeBean.setTypeName(resultBean.getItemName());
                                                typeBean.setTypeName2(resultBean.getItemCode());
                                                xiangmuList.add(typeBean);
                                            }
                                            popHeadAdapterXM.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        }else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.fabu);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.fabu);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }


    private QMUITipDialog qmuiTipDialog = null;
    private void link_complete() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("serviceName", leixingName+"");
            object.put("serviceContent", binding.neirong.getText().toString().trim());
            object.put("serviceType", leixingId);
            object.put("itemType", xiangmuId);
            object.put("itemName", xiangmuName);
            object.put("patientId", dataBean.getId());
            object.put("patientCode", dataBean.getPatientCode());
            object.put("startTime", timeL1);
            object.put("endTime", timeL2);
            object.put("patientName", dataBean.getPatientName());
            object.put("bedId", dataBean.getBedId());
            object.put("itemCode", itemCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/nurseService/add");
        // step 3：创建 Call 对象
      Call  call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", binding.name);
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
                                            ToastUtils.setSuccess("发布成功", binding.name);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SystemClock.sleep(2100);
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
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.name);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing()){
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), binding.name);
                            }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewBT1:
                startActivity(new Intent(this, SelectPatientsActivity.class));
                break;
            case R.id.viewBT2:
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
                        .setType(Type.ALL)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(Color.parseColor("#FF59B683"))
                        .setWheelItemTextSize(16)
                        .build();
                mDialogAll.show(getSupportFragmentManager(), "ff1");

                break;
            case R.id.viewBT3:
                TimePickerDialog mDialogAll2 = new TimePickerDialog.Builder()
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
                        .setType(Type.ALL)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(Color.parseColor("#FF59B683"))
                        .setWheelItemTextSize(16)
                        .build();
                mDialogAll2.show(getSupportFragmentManager(), "ff2");

                break;
            case R.id.viewBT4:

                popup = QMUIPopups.listPopup(this, 520, 400, popHeadAdapterLX, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.leixing.setText(leixingList.get(position).getTypeName());
                        leixingId=leixingList.get(position).getTypeId();
                        leixingName=leixingList.get(position).getTypeName();
                        binding.xiangmu.setText("");
                        link_getByType(leixingId);
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
            case R.id.viewBT5:
                if (leixingList.size()<=0 || xiangmuList.size()<=0){
                    ToastUtils.setMessage("请先选择服务类型",binding.xiangmu);
                    return;
                }
                popup = QMUIPopups.listPopup(this, 520, 400, popHeadAdapterXM, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.xiangmu.setText(xiangmuList.get(position).getTypeName());
                        xiangmuId=xiangmuList.get(position).getTypeId();
                        xiangmuName=xiangmuList.get(position).getTypeName();
                        itemCode=xiangmuList.get(position).getTypeName2();
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
            case R.id.fabu:

                if (!binding.time1.getText().toString().trim().equals("") && !binding.time2.getText().toString().trim().equals("") &&
                        !binding.neirong.getText().toString().trim().equals("") && dataBean!=null && !binding.leixing.getText().toString().trim().equals("") && !binding.xiangmu.getText().toString().trim().equals("")){
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("发布中...")
                            .create();
                    qmuiTipDialog.show();
                    link_complete();
                }else {
                    ToastUtils.setMessage("请填写完整信息",binding.fabu);
                }

                break;

            case R.id.fanhui:
                finish();
                break;
        }
    }
}
