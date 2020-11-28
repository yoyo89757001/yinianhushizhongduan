package com.example.yiliaoyinian.ui.shuju;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.yiliaoyinian.Beans.LuMIDevBean;
import com.example.yiliaoyinian.Beans.MachineBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.Beans.ZhanShiDataBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.ZhanShiDataAdapter;
import com.example.yiliaoyinian.ui.lumi.AddDeviceLumiActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.SdkInitTool;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.views.MachineFragmentPagerAdapter;
import com.example.yiliaoyinian.views.ReboundHScrollView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.widget.QMUIFontFitTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
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
import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;





/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ImageView saoma;
    QMUIFontFitTextView topTv;
    TextView fuwu;
    LinearLayout l1;
    TextView zhangzhe;
    LinearLayout l2;
    TextView zhiban;
    LinearLayout l3;
    TextView tizheng;
    LinearLayout l4;
    TextView lumi;
    LinearLayout l5;
    ViewPager viewpage;
    RelativeLayout top;
    QMUIFontFitTextView paintName;
    private RecyclerView recyclerView;
    private ZhanShiDataAdapter adapter;
    private List<ZhanShiDataBean.ResultBean.AbnormalListBean> taskBeanListzs = new ArrayList<>();
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOR = 3;
    public static final int PAGE_FILE = 4;
    private SmartRefreshLayout refreshLayout;
   // private Vector<MachineBean.ResultBean> taskBeanList = new Vector<>();
    private ReboundHScrollView scrollView;


    public Fragment3() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("TimeOut30")){
            paintName.setText("当前未绑定患者");
        }
        if (msgWarp.equals("ref_device_list")){
            link_getDeviceList();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        EventBus.getDefault().register(this);
        //  @OnClick({R.id.l1, R.id.l2, R.id.l3, R.id.l4, R.id.saoma})
        view.findViewById(R.id.l1).setOnClickListener(this);
        view.findViewById(R.id.l2).setOnClickListener(this);
        view.findViewById(R.id.l3).setOnClickListener(this);
        view.findViewById(R.id.l4).setOnClickListener(this);
        view.findViewById(R.id.l5).setOnClickListener(this);
        view.findViewById(R.id.saoma).setOnClickListener(this);

        zhangzhe=view.findViewById(R.id.zhangzhe);
        recyclerView=view.findViewById(R.id.recyclerview);
        saoma=view.findViewById(R.id.saoma);
        topTv=view.findViewById(R.id.top_tv);
        fuwu=view.findViewById(R.id.fuwu);
        l1=view.findViewById(R.id.l1);
        l2=view.findViewById(R.id.l2);
        zhiban=view.findViewById(R.id.zhiban);
        l3=view.findViewById(R.id.l3);
        tizheng=view.findViewById(R.id.tizheng);
        l4=view.findViewById(R.id.l4);
        lumi=view.findViewById(R.id.lumi);
        l5=view.findViewById(R.id.l5);
        viewpage=view.findViewById(R.id.viewpage);
        top=view.findViewById(R.id.top);
        scrollView=view.findViewById(R.id.gbg);
        paintName=view.findViewById(R.id.paintName);

        refreshLayout=view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                link_getAbnormalData();
                link_getDeviceList();
                link_complete();
            }
        });
        if (getActivity() != null) {
            MachineFragmentPagerAdapter myFragmentPagerAdapter = new MachineFragmentPagerAdapter(getChildFragmentManager());
            viewpage.setAdapter(myFragmentPagerAdapter);
            //设置当前页的ID
            viewpage.setCurrentItem(0);
            //添加翻页监听事件
            viewpage.addOnPageChangeListener(this);
            viewpage.setOffscreenPageLimit(4);
        }
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new ZhanShiDataAdapter(R.layout.zhanshi_item_data, taskBeanListzs);
        // 获取模块
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.anull_data, null);
        adapter.setEmptyView(view1);
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多12");
//                // 当前这次数据加载完毕，调用此方法
//                //  mAdapter.getLoadMoreModule().loadMoreComplete();
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//            }
//        });

        //  link_getAbnormalData();

        TextPaint tp3 = fuwu.getPaint();
        tp3.setFakeBoldText(true);
        link_getAbnormalData();
        link_getDeviceList();
        return view;
    }


    private void link_getDeviceList() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/deviceProduct/getDeviceList?deviceType=all");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new MachineBean().getResult());
                link_getlumiList();
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败",recyclerView);
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                String ss=null;
                MachineBean logingBe = null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("LoginActivity", "获取设备数据"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    logingBe = gson.fromJson(jsonObject, MachineBean.class);
                    if (logingBe!=null && logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                                if (logingBe.getResult().size()>0){
                                    logingBe.getResult().get(0).setType(1001);
                                }
                        }else {
                            if (getActivity()!=null){
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),recyclerView);
                            }
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (getActivity()!=null){
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),recyclerView);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }finally {
                    if (logingBe!=null)
                    EventBus.getDefault().post(logingBe.getResult());
                    link_getlumiList();
                }
            }
        });
    }

    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private void link_getlumiList() {
        JSONObject json =new  JSONObject();
        try {
            json.put("pinlessUser", MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getPhone());
            json.put("modelType", 1);
        } catch (JSONException e) {
           Log.d("Fragment3", e.getMessage()+"AllConnects");
        }
       // Log.d("Fragment3", "AllConnects1111");
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(RequestBody.create(json.toString(),JSONTYPE))
                .url(Consts.URL2+"/app/lvmi/list");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
      //  Log.d("Fragment3", "AllConnects1111");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败",recyclerView);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefresh();
                        }
                    });
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                String ss=null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("AllConnects", "获取绿米设备数据"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LuMIDevBean logingBe = gson.fromJson(jsonObject, LuMIDevBean.class);
                     if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getData()!=null){
                            if (logingBe.getData().size()>0){
                                    for (LuMIDevBean.DataDTO datum : logingBe.getData()) {
                                        WGInfoSave save=MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.did,datum.getDid()).build().findFirst();
                                        if (save==null){
                                            WGInfoSave wgInfoSave=new WGInfoSave();
                                            wgInfoSave.setModle(datum.getModel());
                                            wgInfoSave.setWeizhi(datum.getPlace());
                                            wgInfoSave.setName(datum.getName());
                                            wgInfoSave.setState(datum.getStatus());
                                            wgInfoSave.setFirmwareVersion(datum.getFirmwareVersion());
                                            wgInfoSave.setDid(datum.getDid());
                                            wgInfoSave.setParentDid(datum.getParentDid());
                                            wgInfoSave.setModelType(datum.getModelType());
                                            wgInfoSave.setPhoto(datum.getStr1());
                                            wgInfoSave.setOPen(true);
                                            wgInfoSave.setArgb("ffffff");
                                            wgInfoSave.setLight(50);
                                            wgInfoSave.setSoundValue(60);
                                            wgInfoSave.setWeizhi("房间");
                                            MyApplication.myApplication.getWgInfoSaveBox().put(wgInfoSave);
                                        }
                                    }
                                   EventBus.getDefault().post(logingBe);
                                }
                        }
                     }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }finally {
                    if (getActivity()!=null){
                        ToastUtils.setMessage("网络请求失败",recyclerView);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefresh();
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Fragment3", "断电");
       // link_getAbnormalData();
        String pnmae=MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getPatientName();
        if (pnmae!=null && !pnmae.equals(""))
        paintName.setText("当前绑定("+pnmae+")");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }
    private void link_complete() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("appKey", "61166123935f421db22d5e54400d521a");
            object.put("appSecret", "5bb23e738156ae8f04f09f53c1893c50");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .url("https://open.ys7.com/api/lapp/token/get?appKey=61166123935f421db22d5e54400d521a&appSecret=5bb23e738156ae8f04f09f53c1893c50");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败", recyclerView);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "萤石"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsString().equals("200")){
                        JsonObject j=jsonObject.get("data").getAsJsonObject();
                        String toke=j.get("accessToken").getAsString();
                        Log.d("MainActivity", toke+"token");
                        SaveInfoBean bean=MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                        bean.setYsToken(toke);
                        bean.setAppKey("61166123935f421db22d5e54400d521a");
                        bean.setAppSecret("5bb23e738156ae8f04f09f53c1893c50");
                        MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                        //初始化萤石sdk
                        SdkInitTool.initSdk(getApplication(),bean);


                    }else {
                        if (getActivity()!=null){
                            ToastUtils.setMessage("获取摄像头token失败", recyclerView);
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");

                }
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        Log.d("Fragment1", "state:" + state);
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    TextPaint tp1 = fuwu.getPaint();
                    tp1.setFakeBoldText(true);
                    fuwu.setTextColor(Color.parseColor("#FF59B683"));
                    fuwu.setTextSize(18);
                    scrollView.fullScroll(ScrollView.FOCUS_LEFT);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    TextPaint tp1 = zhangzhe.getPaint();
                    tp1.setFakeBoldText(true);
                    zhangzhe.setTextColor(Color.parseColor("#FF59B683"));
                    zhangzhe.setTextSize(18);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    TextPaint tp1 = zhiban.getPaint();
                    tp1.setFakeBoldText(true);
                    zhiban.setTextColor(Color.parseColor("#FF59B683"));
                    zhiban.setTextSize(18);
                    break;
                }
                case PAGE_FOR: {
                    resetSelected();
                    TextPaint tp1 = tizheng.getPaint();
                    tp1.setFakeBoldText(true);
                    tizheng.setTextColor(Color.parseColor("#FF59B683"));
                    tizheng.setTextSize(18);
                    break;
                }
                case PAGE_FILE: {
                    resetSelected();
                    TextPaint tp1 = lumi.getPaint();
                    tp1.setFakeBoldText(true);
                    lumi.setTextColor(Color.parseColor("#FF59B683"));
                    lumi.setTextSize(18);
                    scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
                    break;
                }
            }
        }
    }

    private void resetSelected() {
        fuwu.setTextColor(Color.parseColor("#FFB6B6B6"));
        zhangzhe.setTextColor(Color.parseColor("#FFB6B6B6"));
        zhiban.setTextColor(Color.parseColor("#FFB6B6B6"));
        tizheng.setTextColor(Color.parseColor("#FFB6B6B6"));
        lumi.setTextColor(Color.parseColor("#FFB6B6B6"));
        TextPaint tp1 = fuwu.getPaint();
        tp1.setFakeBoldText(false);
        TextPaint tp2 = zhangzhe.getPaint();
        tp2.setFakeBoldText(false);
        TextPaint tp3 = zhiban.getPaint();
        tp3.setFakeBoldText(false);
        TextPaint tp4 = tizheng.getPaint();
        tp4.setFakeBoldText(false);
        TextPaint tp5 = lumi.getPaint();
        tp5.setFakeBoldText(false);
        fuwu.setTextSize(15);
        zhangzhe.setTextSize(15);
        zhiban.setTextSize(15);
        lumi.setTextSize(15);
        tizheng.setTextSize(15);
    }



    private void link_getAbnormalData() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/deviceProduct/getAbnormalData");
        // step 3：创建 Call 对象
       Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.setMessage("网络请求失败", recyclerView);
                            refreshLayout.finishRefresh();
                        }
                    });
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                String ss = null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("LoginActivity", "获取异常数据" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ZhanShiDataBean logingBe = gson.fromJson(jsonObject, ZhanShiDataBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult() != null) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (logingBe.getResult().getAbnormalList().size() == 0) {
                                                topTv.setVisibility(View.GONE);
                                                recyclerView.setVisibility(View.GONE);
                                            } else {
                                                topTv.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                topTv.setText("当前共" + logingBe.getResult().getAllNum() + "条数据，正常" + logingBe.getResult().getMeasureNum() + "条，异常" + logingBe.getResult().getAbnormalNum() + "条");
                                                taskBeanListzs.clear();
                                                taskBeanListzs.addAll(logingBe.getResult().getAbnormalList());
                                                adapter.notifyDataSetChanged();
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }finally {
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefresh();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.l1:
                resetSelected();
                TextPaint tp1 = fuwu.getPaint();
                tp1.setFakeBoldText(true);
                fuwu.setTextColor(Color.parseColor("#FF59B683"));
                fuwu.setTextSize(18);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.l2:
                resetSelected();
                TextPaint tp2 = zhangzhe.getPaint();
                tp2.setFakeBoldText(true);
                zhangzhe.setTextColor(Color.parseColor("#FF59B683"));
                zhangzhe.setTextSize(18);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.l3:
                resetSelected();
                TextPaint tp3 = zhiban.getPaint();
                tp3.setFakeBoldText(true);
                zhiban.setTextColor(Color.parseColor("#FF59B683"));
                zhiban.setTextSize(18);
                viewpage.setCurrentItem(PAGE_THREE);
                break;
            case R.id.l4:
                resetSelected();
                TextPaint tp4 = tizheng.getPaint();
                tp4.setFakeBoldText(true);
                tizheng.setTextColor(Color.parseColor("#FF59B683"));
                tizheng.setTextSize(18);
                viewpage.setCurrentItem(PAGE_FOR);
                break;
            case R.id.l5:
                resetSelected();
                TextPaint tp5 = lumi.getPaint();
                tp5.setFakeBoldText(true);
                lumi.setTextColor(Color.parseColor("#FF59B683"));
                lumi.setTextSize(18);
                viewpage.setCurrentItem(PAGE_FILE);
                break;
            case R.id.saoma:
                startActivity(new Intent(getActivity(), AddDeviceLumiActivity.class));
                break;
        }
    }
}
