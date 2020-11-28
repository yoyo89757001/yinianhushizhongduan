package com.example.yiliaoyinian.ui.fragments1.fuwu;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.yiliaoyinian.Beans.LouDongBean;
import com.example.yiliaoyinian.Beans.SellaceBean;
import com.example.yiliaoyinian.Beans.ServiceTypeBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.PatientsAdapter;
import com.example.yiliaoyinian.adapter.PopHeadAdapter;
import com.example.yiliaoyinian.databinding.ActivitySelectPatientsBinding;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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

public class SelectPatientsActivity extends BaseActivity implements View.OnClickListener {




    private List<SellaceBean.ResultBean.DataBean> patientsBeanList = new ArrayList<>();
    private PatientsAdapter adapters;
    private List<ServiceTypeBean> buildingList = new ArrayList<>();
    private PopHeadAdapter buildingAdapter = null;
    private QMUIPopup popup = null;
    private String louDongId="";
    private int page=1;
    private QMUITipDialog qmuiTipDialog = null;
    private ActivitySelectPatientsBinding binding=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySelectPatientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // @OnClick({R.id.c3, R.id.replace, R.id.select,R.id.fanhui})
        findViewById(R.id.c3).setOnClickListener(this);
        findViewById(R.id.replace).setOnClickListener(this);
        findViewById(R.id.select).setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);

        binding.replace.setBorderColor(Color.parseColor("#59B683"));
        binding.replace.setRadius(QMUIDisplayHelper.dp2px(this, 18));
        binding.replace.setChangeAlphaWhenPress(true);//点击改变透明度
        binding.select.setRadius(QMUIDisplayHelper.dp2px(this, 18));
        binding.select.setChangeAlphaWhenPress(true);//点击改变透明度

        adapters = new PatientsAdapter(R.layout.patinets_item, patientsBeanList);

        adapters.getLoadMoreModule().setAutoLoadMore(true);//第一次不调用加载更多方法
        adapters.getLoadMoreModule().setEnableLoadMore(true);
        adapters.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerview.setLayoutManager(manager);
        binding.recyclerview.setAdapter(adapters);
        View view1= LayoutInflater.from(this).inflate(R.layout.anull_data,null);
        adapters.setEmptyView(view1);
        // 设置加载更多监听事件
        adapters.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
               // Log.d("Fragment1_1", "加载更多16");
                page++;
                String t1=binding.name.getText().toString().trim();
                String t2=binding.phone.getText().toString().trim();
               // if (!t1.equals("") || !t2.equals("") || louDongId!=null){
                    //有一个不为空就能查
                    link_getInPatient(page,t1,t2,louDongId,10);
               // }else {
              //      ToastUtils.setMessage("请输入查询条件",select);
              //  }
            }
        });
        adapters.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);

                for (int i = 0; i < patientsBeanList.size(); i++) {
                    if (position == i) {
                        patientsBeanList.get(i).setSelectType(true);
                    } else {
                        patientsBeanList.get(i).setSelectType(false);
                    }
                }
                adapter.notifyDataSetChanged();
                finish();
            }
        });

        buildingAdapter = new PopHeadAdapter(buildingList, SelectPatientsActivity.this);

        link_getBedDetail();
        link_getInPatient(1,"","","",10);
    }



    private void link_getInPatient(int pageNum,String patientName,String phone,String bedId,int pageSize) {//查询患者
        if (bedId==null)
            bedId="";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object=new JSONObject();
        JSONObject object2=new JSONObject();

        try {
            object.put("pageNum",pageNum);
            object.put("pageSize",pageSize);

            object2.put("patientName",patientName);
            object2.put("phone",phone);
            object2.put("bedId",bedId);
            object.put("params",object2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL+"/api/nursePatient/getInPatient");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败",binding.select);
                    if (qmuiTipDialog!=null)
                        qmuiTipDialog.dismiss();
                }
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
                    SellaceBean logingBe = gson.fromJson(jsonObject, SellaceBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            patientsBeanList.addAll(logingBe.getResult().getData());
                                            if (logingBe.getResult().getData().size()<10){
                                                adapters.getLoadMoreModule().loadMoreComplete();
                                                adapters.getLoadMoreModule().loadMoreEnd(true);
                                            }else {
                                                adapters.getLoadMoreModule().loadMoreComplete();
                                            }
                                            adapters.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        }else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.select);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.select);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }finally {
                    if (!isFinishing()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qmuiTipDialog!=null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }


    private void link_getBedDetail() {//获取楼栋
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/buildBed/getBedDetail");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败",binding.select);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "楼栋"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LouDongBean logingBe = gson.fromJson(jsonObject, LouDongBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        buildingList.clear();
                                        for (LouDongBean.ResultBean resultBean : logingBe.getResult()) {
                                            ServiceTypeBean typeBean=new ServiceTypeBean();
                                            typeBean.setTypeId(resultBean.getBedId()+"");
                                            typeBean.setTypeName(resultBean.getBuildName());
                                            buildingList.add(typeBean);
                                        }
                                        buildingAdapter.notifyDataSetChanged();
                                    }
                                });
                        }else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.select);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing()){
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.select);
                            }
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }

    private void link_getList() {//已入科患者
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nursePatient/getList");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败",binding.recyclerview);
                }
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
                    SellaceBean logingBe = gson.fromJson(jsonObject, SellaceBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){

                            if (!isFinishing()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            patientsBeanList.addAll(logingBe.getResult().getData());
                                            adapters.getLoadMoreModule().loadMoreComplete();
                                            adapters.getLoadMoreModule().loadMoreEnd(true);
                                            adapters.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.recyclerview);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.recyclerview);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "患者列表异常");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (SellaceBean.ResultBean.DataBean dataBean : patientsBeanList) {
            if (dataBean.isSelectType()){
                EventBus.getDefault().post(dataBean);
                break;
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.c3:
                popup = QMUIPopups.listPopup(this, 520, 400, buildingAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.building.setText(buildingList.get(position).getTypeName());
                        louDongId=buildingList.get(position).getTypeId();
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
            case R.id.replace:
                binding.name.setText("");
                binding.phone.setText("");
                binding.building.setText("");
                patientsBeanList.clear();
                louDongId="";
                adapters.notifyDataSetChanged();
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("加载中...")
                        .create();
                qmuiTipDialog.show();
                link_getInPatient(1,"","","",10);
                break;
            case R.id.select:
                QMUIKeyboardHelper.hideKeyboard(binding.select);
                String t1=binding.name.getText().toString().trim();
                String t2=binding.phone.getText().toString().trim();
                // if (!t1.equals("") || !t2.equals("") || louDongId!=null){
                //有一个不为空就能查
                patientsBeanList.clear();
                adapters.notifyDataSetChanged();
                qmuiTipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("查询中...")
                        .create();
                qmuiTipDialog.show();
                link_getInPatient(1,t1,t2,louDongId,10);
                //   }else {
                //     ToastUtils.setMessage("请输入查询条件",select);
                //  }
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
