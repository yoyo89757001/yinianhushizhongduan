package com.example.yiliaoyinian.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.LiChuangBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.Beans.ZiDivescerBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.LiChuangAdapter;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.FilterContext;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.SdkInitTool;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.views.ControlScrollViewPager;
import com.example.yiliaoyinian.views.MyFragmentPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZDeviceInfo;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    ImageView im1;
    TextView tv1;
    ImageView im2;
    TextView tv2;
    ImageView im3;
    TextView tv3;
    ImageView im4;
    TextView tv4;
    RecyclerView recyclerview;
    private int TIMEOUT = 0;


    private ControlScrollViewPager viewpage;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FORE = 3;
    private List<LiChuangBean> liChuangBeans=new ArrayList<>();
    private LiChuangAdapter adapter=null;
   // private WifiManager mWifiManager = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
       // @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4})

        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        ll3=findViewById(R.id.ll3);
        ll4=findViewById(R.id.ll4);
        im1=findViewById(R.id.im1);
        im2=findViewById(R.id.im2);
        im3=findViewById(R.id.im3);
        im4=findViewById(R.id.im4);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);


        findViewById(R.id.ll1).setOnClickListener(this);
        findViewById(R.id.ll2).setOnClickListener(this);
        findViewById(R.id.ll3).setOnClickListener(this);
        findViewById(R.id.ll4).setOnClickListener(this);

        EventBus.getDefault().register(this);
        viewpage = findViewById(R.id.viewpage);
        recyclerview=findViewById(R.id.recyclerview);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(4);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        TimeChangeReceiver timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);

        adapter=new LiChuangAdapter(R.layout.lichuang_iem,liChuangBeans);
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        // 先注册需要点击的子控件id（注意，请不要写在convert方法里）
        adapter.addChildClickViewIds(R.id.quxiao,R.id.shanchu);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.shanchu) {
                    qmuiTipDialog = new QMUITipDialog.Builder(MainActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("提交中...")
                            .create();
                    qmuiTipDialog.show();
                    link_checkPatientInfo(liChuangBeans.get(position).getServiceId(),position);
                }
            }
        });

        link_complete();//获取萤石token



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //获取所有网关下的子设备
//
////                List<WGInfoSave> save2=MyApplication.myApplication.getWgInfoSaveBox().getAll();
////                Log.d("MainActivity", "子设备个数2:" + save2.size());
////
////                List<WGInfoSave> save=MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,2).build().find();
////                Log.d("MainActivity", "子设备个数:" + save.size());
////                for (WGInfoSave wgInfoSave : save) {
////                    Log.d("MainActivity", "删掉子设备:" + MyApplication.myApplication.getWgInfoSaveBox().remove(wgInfoSave));
////                }
//
//                List<WGInfoSave> wgInfoSaves=MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,1).build().find();
//                Log.d("MainActivity", "网关个数:" + wgInfoSaves.size());
//                for (WGInfoSave wgInfoSave : wgInfoSaves) {
//                     loginApiquer(wgInfoSave.getDid());
//                    SystemClock.sleep(1000);
//                }
//               // SystemClock.sleep(10000);
//              //  List<WGInfoSave> save3=MyApplication.myApplication.getWgInfoSaveBox().getAll();
//              //  Log.d("MainActivity", "子设备个数3:" + save3.size());
//            }
//        }).start();

        //lumi.gateway.acn01 网关

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                resetSelected();
                tv1.setTextColor(Color.parseColor("#FF222222"));
                im1.setBackgroundResource(R.drawable.work);
                viewpage.setCurrentItem(PAGE_ONE, false);
                break;
            case R.id.ll2:
                resetSelected();
                tv2.setTextColor(Color.parseColor("#FF222222"));
                im2.setBackgroundResource(R.drawable.xuan2);
                viewpage.setCurrentItem(PAGE_TWO, false);

                break;
            case R.id.ll3:
                resetSelected();
                tv3.setTextColor(Color.parseColor("#FF222222"));
                im3.setBackgroundResource(R.drawable.xuan3);
                viewpage.setCurrentItem(PAGE_THREE, false);
                break;
            case R.id.ll4:
                resetSelected();
                tv4.setTextColor(Color.parseColor("#FF222222"));
                im4.setBackgroundResource(R.drawable.xuan4);
                viewpage.setCurrentItem(PAGE_FORE, false);
                break;

        }
    }


    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {//
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_TIME_TICK:
                    Log.d("TimeChangeReceiver", "一分钟过去了" + TIMEOUT);
                    TIMEOUT++;
                    if (TIMEOUT >= 30) {
                        TIMEOUT = -20000;
                        //清空本人人员
                        try {
                            SaveInfoBean bean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                            if (bean.getPatientCode() != null) {
                                bean.setPatientName(null);
                                bean.setPatientId(0);
                                bean.setPatientCode(null);
                                MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                                EventBus.getDefault().post("TimeOut30");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Intent.ACTION_TIME_CHANGED:
                    //设置了系统时间
                    // Toast.makeText(context, "system time changed", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    //设置了系统时区的action
                    //  Toast.makeText(context, "system time zone changed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


//    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
//        String SYSTEM_REASON = "reason";
//        String SYSTEM_HOME_KEY = "homekey";
//        String SYSTEM_HOME_KEY_LONG = "recentapps";
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) { // 监听home键
//
//
////                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
////                intent1.addCategory(Intent.CATEGORY_LAUNCHER);
////                intent1.setAction(Intent.ACTION_MAIN);
////                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
////                startActivity(intent1);
//
//                ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//                if (am!=null){
//                    am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
//                }else {
//                    Intent intentu = new Intent(Intent.ACTION_MAIN);
//                    intentu.addCategory(Intent.CATEGORY_HOME);
//                    startActivity(intentu);
//                }
////                String reason = intent.getStringExtra(SYSTEM_REASON);
//
//
//              //  Log.d("MainActivity", action+"   "+reason);
//                // 表示按了home键,程序到了后台
//
//            }
//        }
//    };




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("TimeOut30res")) {
            TIMEOUT = 0;
        }
//        if (msgWarp.equals("link_did_zi")){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //获取所有网关下的子设备
//                    List<WGInfoSave> wgInfoSaves=MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.modelType,1).build().find();
//                    for (WGInfoSave wgInfoSave : wgInfoSaves) {
//                        SystemClock.sleep(1000);
//                        loginApiquer(wgInfoSave.getDid());
//                    }
//                }
//            }).start();
//        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG5(JSONObject msgWarp) {
        Log.d("MainActivity", "收到的JSONObject：" + msgWarp.toJSONString());
        try {
            LiChuangBean liChuangBean= JSONObject.parseObject(msgWarp.toJSONString(),LiChuangBean.class);
            liChuangBeans.add(liChuangBean);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            EventBus.getDefault().post("mycameraOpen");
            recyclerview.setVisibility(View.GONE);
        } else {
            EventBus.getDefault().post("mycameraClose");
            recyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    tv1.setTextColor(Color.parseColor("#FF222222"));
                    im1.setBackgroundResource(R.drawable.work);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    tv2.setTextColor(Color.parseColor("#FF222222"));
                    im2.setBackgroundResource(R.drawable.xuan2);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    tv3.setTextColor(Color.parseColor("#FF222222"));
                    im3.setBackgroundResource(R.drawable.xuan3);
                    break;
                }
                case PAGE_FORE: {
                    resetSelected();
                    tv4.setTextColor(Color.parseColor("#FF222222"));
                    im4.setBackgroundResource(R.drawable.xuan4);
                    break;
                }
            }
        }
    }

    private void resetSelected() {
        tv1.setTextColor(Color.parseColor("#FFC1C1C2"));
        tv2.setTextColor(Color.parseColor("#FFC1C1C2"));
        tv3.setTextColor(Color.parseColor("#FFC1C1C2"));
        tv4.setTextColor(Color.parseColor("#FFC1C1C2"));
        im1.setBackgroundResource(R.drawable.weixuan1);
        im2.setBackgroundResource(R.drawable.saoma);
        im3.setBackgroundResource(R.drawable.data);
        im4.setBackgroundResource(R.drawable.manage);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private QMUITipDialog qmuiTipDialog = null;

    private void link_checkPatientInfo(String idid,int p) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/devicePush/updateStatus?id="+idid);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", recyclerview);
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
                    ErrorBean logingBe = gson.fromJson(jsonObject, ErrorBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            liChuangBeans.remove(p);
                                            adapter.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing()){
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerview);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerview);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (!isFinishing())
                    ToastUtils.setMessage("数据异常,返回后重试",recyclerview);
                }
            }
        });
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
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", viewpage);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "萤石" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsString().equals("200")) {
                        JsonObject j = jsonObject.get("data").getAsJsonObject();
                        String toke = j.get("accessToken").getAsString();
                        Log.d("MainActivity", toke + "token");
                        SaveInfoBean bean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                        bean.setYsToken(toke);
                        bean.setAppKey("61166123935f421db22d5e54400d521a");
                        bean.setAppSecret("5bb23e738156ae8f04f09f53c1893c50");
                        MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                        //初始化萤石sdk
                        SdkInitTool.initSdk(getApplication(), bean);
                        List<EZDeviceInfo> list = EZOpenSDK.getInstance().getDeviceList(0, 10);
                        Log.d("MainActivity", "list.size():" + list.size());
                        for (EZDeviceInfo ezDeviceInfo : list) {
                            Log.d("MainActivity", ezDeviceInfo.getDeviceSerial() + "顶顶顶");
                            Log.d("MainActivity", ezDeviceInfo.getDeviceName());
                        }

                        //  Log.d("MainActivity", "EzvizAPI.getInstance().isLogin():" + EzvizAPI.getInstance().isLogin());
//                        if (!EzvizAPI.getInstance().isLogin()){
//                            MyApplication.getOpenSDK().openLoginPage();
//                        }

                    } else {
                        if (!isFinishing()) {
                            ToastUtils.setMessage("获取摄像头token失败", viewpage);
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");

                }
            }
        });
    }

    private void loginApiquer(String did){
        //开启网关添加子设备模式
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/dev/child/query?did="+did;
        // val json = JSONObject()
        //  json.put("did", did)
        //  val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String,String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);

        String sign= FilterContext.createSign(header, MyApplication.APPKEY, false);
        if (sign==null)
            return;

        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                // .post(body.toRequestBody()).url(url).build()
                .get().url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "查询子设备返回结果:"+stA);
                    Auto7 healthBean = JSON.parseObject(stA,Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    JSONArray json  = JSON.parseArray(jsonss);
                   Box<WGInfoSave> saveBox=MyApplication.myApplication.getWgInfoSaveBox();
                    for (Object o : json) {
                        Log.d("WGSettingActivity", "子设备:"+o.toString());
                        ZiDivescerBean ziDivescerBean=JSON.parseObject(o.toString(), ZiDivescerBean.class);
                        WGInfoSave save=saveBox.query().equal(WGInfoSave_.did,ziDivescerBean.getDid()).build().findFirst();
                       if (save!=null){
                           save.setState(ziDivescerBean.getState());
                           saveBox.put(save);
                           Log.d("MainActivity", "更新子设备保存");
                       }else {
                           switch (ziDivescerBean.getModel()){
                               case "lumi.sensor_motion.aq2":
                                   saveBox.put(new WGInfoSave(ziDivescerBean.getModelType(),ziDivescerBean.getParentDid(),ziDivescerBean.getState(),ziDivescerBean.getDid(),ziDivescerBean.getModel(),
                                           ziDivescerBean.getFirmwareVersion(),"人体传感器",true,"ffffff",50,60,"","","","","房间"));
                                   break;
                               case "lumi.vibration.aq1":
                                   saveBox.put(new WGInfoSave(ziDivescerBean.getModelType(),ziDivescerBean.getParentDid(),ziDivescerBean.getState(),ziDivescerBean.getDid(),ziDivescerBean.getModel(),
                                           ziDivescerBean.getFirmwareVersion(),"动静贴",true,"ffffff",50,60,"","","","","房间"));
                                   break;
                               case "lumi.sensor_magnet.aq2":
                                   saveBox.put(new WGInfoSave(ziDivescerBean.getModelType(),ziDivescerBean.getParentDid(),ziDivescerBean.getState(),ziDivescerBean.getDid(),ziDivescerBean.getModel(),
                                           ziDivescerBean.getFirmwareVersion(),"门窗传感器",true,"ffffff",50,60,"","","","","房间"));
                                   break;
                               case "lumi.sensor_switch.aq3":
                                   saveBox.put(new WGInfoSave(ziDivescerBean.getModelType(),ziDivescerBean.getParentDid(),ziDivescerBean.getState(),ziDivescerBean.getDid(),ziDivescerBean.getModel(),
                                           ziDivescerBean.getFirmwareVersion(),"无线开关",true,"ffffff",50,60,"","","","","房间"));
                                   break;
                               case "lumi.light.aqcn02"://智能灯
                                   saveBox.put(new WGInfoSave(ziDivescerBean.getModelType(),ziDivescerBean.getParentDid(),ziDivescerBean.getState(),ziDivescerBean.getDid(),ziDivescerBean.getModel(),
                                           ziDivescerBean.getFirmwareVersion(),"智能灯泡",true,"ffffff",50,60,"","","","","房间"));

                                   break;

                           }
                           Log.d("MainActivity", "新建子设备保存");
                       }
                    }
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });

    }

}
