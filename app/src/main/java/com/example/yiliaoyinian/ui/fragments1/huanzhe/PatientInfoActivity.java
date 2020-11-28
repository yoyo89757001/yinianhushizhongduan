package com.example.yiliaoyinian.ui.fragments1.huanzhe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.viewpager.widget.ViewPager;
import com.example.yiliaoyinian.Beans.HuLiZuBaen;
import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.PopHeadBlackAdapter2;
import com.example.yiliaoyinian.databinding.ActivityPatientInfoBinding;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;

import com.example.yiliaoyinian.views.DAFragmentPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PatientInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {



    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FORE = 3;



    private List<HuLiZuBaen.ResultBean> xiangmuList = new ArrayList<>();
    private PopHeadBlackAdapter2 popHeadAdapterXM = null;

    private ActivityPatientInfoBinding binding=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPatientInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  @OnClick({R.id.tl1, R.id.tl2, R.id.xianshi, R.id.tl3, R.id.tl4, R.id.fanhui})
        findViewById(R.id.tl1).setOnClickListener(this);
        findViewById(R.id.tl2).setOnClickListener(this);
        findViewById(R.id.tl3).setOnClickListener(this);
        findViewById(R.id.tl4).setOnClickListener(this);
        findViewById(R.id.xianshi).setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);



        binding.xianshi.setBorderColor(Color.parseColor("#59B683"));
        binding.xianshi.setRadius(QMUIDisplayHelper.dp2px(this, 10));
        binding.xianshi.setChangeAlphaWhenPress(true);//点击改变透明度

        DAFragmentPagerAdapter myFragmentPagerAdapter = new DAFragmentPagerAdapter(getSupportFragmentManager());
        binding.viewpageDa.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        binding.viewpageDa.setCurrentItem(0);
        //添加翻页监听事件
        binding.viewpageDa.addOnPageChangeListener(this);
        binding.viewpageDa.setOffscreenPageLimit(4);


        popHeadAdapterXM = new PopHeadBlackAdapter2(xiangmuList, PatientInfoActivity.this);

        link_getPatientInfo(getIntent().getLongExtra("id",0));

    }

    private void link_getPatientInfo(long id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nursePatient/getPatientInfo?id="+id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败",binding.xianshi);
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "患者信息"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    HuanZheBean logingBe = gson.fromJson(jsonObject, HuanZheBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing()){
                                 runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            binding.chFh.setText(logingBe.getResult().getPatientInfo().getBedName());
                                            String timer=DateUtils.time(logingBe.getResult().getPatientInfo().getCheckInTime()+"");
                                            binding.ruyuanshijian2.setText("入院时间: "+timer);
                                            @SuppressLint("SimpleDateFormat")
                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                            Date startTime = df.parse(DateUtils.time(System.currentTimeMillis()+""));
                                            Date endTime = df.parse(timer);
                                           // Log.d("PatientInfoActivity", timer+"  "+DateUtils.time(System.currentTimeMillis()+""));
                                            long diff = startTime.getTime() - endTime.getTime();
                                            long days = diff / (1000 * 60 * 60 * 24);
                                            binding.ruyuanshijian.setText("入院第: "+days+"天");
                                            if (logingBe.getResult().getNurseLevel()!=null){
                                                binding.huli.setText("护理等级: "+logingBe.getResult().getNurseLevel().getNurseLevelName());
                                            }
                                            binding.logo.setText(logingBe.getResult().getPatientInfo().getBuildName());
                                            binding.zhuzhi.setText("主治医生: "+logingBe.getResult().getPatientInfo().getDoctorName());
                                            binding.idid.setText("ID: "+logingBe.getResult().getPatientInfo().getPatientCode());
                                            binding.bingqing.setText(logingBe.getResult().getPatientInfo().getIllness());
                                            binding.huanzhe.setText(logingBe.getResult().getPatientInfo().getPatientName());
                                            if (logingBe.getResult().getPatientInfo().getGender()==1){
                                                binding.sex.setText("男  "+logingBe.getResult().getPatientInfo().getAge()+"岁");
                                            }else {
                                                binding.sex.setText("女  "+logingBe.getResult().getPatientInfo().getAge()+"岁");
                                            }
                                            if (logingBe.getResult().getGroupList()!=null && logingBe.getResult().getGroupList().size()>0){
                                                binding.hulizu.setText("护理组: "+logingBe.getResult().getGroupList().get(0).getGroupName());
                                              //  StringBuilder hulizuss= new StringBuilder();
                                                for (HuanZheBean.ResultBean.GroupListBean groupListBean : logingBe.getResult().getGroupList()) {
                                                   // hulizuss.append(groupListBean.getGroupName());
                                                    link_getByGroupId(groupListBean.getId());
                                                }
                                            }
                                            if (logingBe.getResult().getMeasureDataList()!=null){
                                                //全部数据
                                               // Log.d("PatientInfoActivity", "logingBe.getResult().getAbnormalList().size():" + logingBe.getResult().getAbnormalList().size());
                                               // Log.d("PatientInfoActivity", "logingBe.getResult().getMeasureDataList().size():" + logingBe.getResult().getMeasureDataList().size());
                                            //    Log.d("PatientInfoActivity", "logingBe.getResult().getMeasureDataList().size():" + logingBe.getResult().getLineOption().size());
                                                EventBus.getDefault().post(logingBe.getResult());
                                            }
//                                            if (logingBe.getResult().getAbnormalList()!=null &&logingBe.getResult().getAbnormalList().size()>0){
//                                                //异常数据
//                                                EventBus.getDefault().post(logingBe.getResult().getAbnormalList());
//                                            }
//                                            if (logingBe.getResult().getServiceList()!=null &&logingBe.getResult().getServiceList().size()>0){
//                                                //护嘱项目
//                                                EventBus.getDefault().post(logingBe.getResult().getServiceList());
//                                            }
//                                            if (logingBe.getResult().getLogList()!=null &&logingBe.getResult().getLogList().size()>0){
//                                                //执行记录
//                                                EventBus.getDefault().post(logingBe.getResult().getLogList());
//                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.xianshi);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.xianshi);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }

    private void link_getByGroupId(long id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nurseWorker/getByGroupId?groupId="+id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败",binding.xianshi);
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
                    HuLiZuBaen logingBe = gson.fromJson(jsonObject, HuLiZuBaen.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (!isFinishing()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                           // xiangmuList.clear();
                                            xiangmuList.addAll(logingBe.getResult());
                                            popHeadAdapterXM.notifyDataSetChanged();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.xianshi);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),binding.xianshi);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // Log.d("PatientInfoActivity", "position:" + position);
        View view = binding.viewpageDa.getChildAt(position);
        int height = view.getMeasuredHeight();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) binding.viewpageDa.getLayoutParams();
        layoutParams.height = height;
        binding.viewpageDa.setLayoutParams(layoutParams);
       // viewpage.invalidate();
      //  scrollView.fullScroll(NestedScrollView.FOCUS_UP);
        Log.d("PatientInfoActivity", "重新更新布局");

    }



    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        Log.d("Fragment1", "state:" + state);
        if (state==0){
          //  scrollView.fullScroll(NestedScrollView.FOCUS_UP);
        }
        if (state == 2) {
            switch (binding.viewpageDa.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    binding.t1.setTextColor(Color.parseColor("#FF59B683"));
                    binding.v1.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    binding.t2.setTextColor(Color.parseColor("#FF59B683"));
                    binding.v2.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    binding.t3.setTextColor(Color.parseColor("#FF59B683"));
                    binding.v3.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_FORE: {
                    resetSelected();
                    binding.t4.setTextColor(Color.parseColor("#FF59B683"));
                    binding.v4.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }


    private void resetSelected() {
        binding.t1.setTextColor(Color.parseColor("#FFC1C1C2"));
        binding.v1.setVisibility(View.GONE);
        binding.t2.setTextColor(Color.parseColor("#FFC1C1C2"));
        binding.v2.setVisibility(View.GONE);
        binding.t3.setTextColor(Color.parseColor("#FFC1C1C2"));
        binding.v3.setVisibility(View.GONE);
        binding.t4.setTextColor(Color.parseColor("#FFC1C1C2"));
        binding.v4.setVisibility(View.GONE);
      //  Log.d("PatientInfoActivity", "重新更新布局2");

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tl1:
                resetSelected();
                binding.t1.setTextColor(Color.parseColor("#FF59B683"));
                binding.v1.setVisibility(View.VISIBLE);
                binding.viewpageDa.setCurrentItem(PAGE_ONE);

                break;
            case R.id.tl2:
                resetSelected();
                binding.t2.setTextColor(Color.parseColor("#FF59B683"));
                binding.v2.setVisibility(View.VISIBLE);
                binding.viewpageDa.setCurrentItem(PAGE_TWO);

                break;
            case R.id.tl3:
                resetSelected();
                binding.t3.setTextColor(Color.parseColor("#FF59B683"));
                binding.v3.setVisibility(View.VISIBLE);
                binding.viewpageDa.setCurrentItem(PAGE_THREE);
                break;
            case R.id.tl4:
                resetSelected();
                binding.t4.setTextColor(Color.parseColor("#FF59B683"));
                binding.v4.setVisibility(View.VISIBLE);
                binding.viewpageDa.setCurrentItem(PAGE_FORE);
                break;
            case R.id.xianshi:
                if (xiangmuList.size()==0){
                    ToastUtils.setMessage("暂无信息",binding.xianshi);
                    return;
                }
                //  popup.dismiss();
                // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                QMUIPopups.listPopup(this, 480, 400, popHeadAdapterXM, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        //  popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 4))
                        .shadow(true)
                        .arrow(true)
                        .bgColor(Color.parseColor("#333333"))
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.xianshi);
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
