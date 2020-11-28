package com.example.yiliaoyinian.ui.shuju;

import android.app.Dialog;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanAndConnectCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.SaveBleDaBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.ui.SaoMaActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.views.CustomViewpager;
import com.example.yiliaoyinian.views.LanYaFragmentPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LanYaXueYaJiActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

//    @BindView(R.id.fanhui)
//    ImageView fanhui;
//    @BindView(R.id.jilu)
//    TextView jilu;
//    @BindView(R.id.jjhh)
//    TextView jjhh;
//    @BindView(R.id.v1)
    QMUIButton v1;
    QMUIButton v2;
    QMUIButton v3;
    QMUIButton v4;
    QMUIButton celiang;
    View v11;
    View v22;
    TextView t1;
    TextView t2;
    View lanya;
    TextView da1;
    TextView da2;
    ConstraintLayout ttll;
    LinearLayout ddddddd;
    private ConstraintLayout.LayoutParams params3;
    private ConstraintLayout.LayoutParams params4;
    CustomViewpager viewpage;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    private BleDevice bleDevice = null;
    private boolean isS = true;
    private String deviceCode = "", deviceType = "";
    private QMUITipDialog qmuiTipDialog = null;
    private int type = 0;
    private int da4 = 0;
    private int dd3 = 0, dd4 = 0, dd5 = 0;
    private TextView celjilu;
    private TextView paintName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lan_ya_xue_ya_ji);
        // @OnClick({R.id.celiang, R.id.tl1, R.id.tl2, R.id.jilu, R.id.fanhui})
        findViewById(R.id.celiang).setOnClickListener(this);
        findViewById(R.id.tl1).setOnClickListener(this);
        findViewById(R.id.tl2).setOnClickListener(this);
        findViewById(R.id.jilu).setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);
        viewpage=findViewById(R.id.viewpage);
        ddddddd=findViewById(R.id.ddddddd);
        ttll=findViewById(R.id.ttll);
        da2=findViewById(R.id.da2);
        da1=findViewById(R.id.da1);
        lanya=findViewById(R.id.lanya);
        t2=findViewById(R.id.t2);
        t1=findViewById(R.id.t1);
        v22=findViewById(R.id.v22);
        v11=findViewById(R.id.v11);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        v4=findViewById(R.id.v4);
        celiang=findViewById(R.id.celiang);
        celjilu=findViewById(R.id.celiangjilu);
        celjilu.setOnClickListener(this);
        paintName=findViewById(R.id.paintName);


        v1.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        v2.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        v3.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        v4.setRadius(QMUIDisplayHelper.dp2px(this, 25));

        //  Log.d("LanYaXueYaJiActivity", "QMUIDisplayHelper.dp2px(this,155):" + QMUIDisplayHelper.dp2px(this, 155));
        deviceCode = getIntent().getStringExtra("deviceCode");
        deviceType = getIntent().getStringExtra("deviceType");

        params3 = (ConstraintLayout.LayoutParams) v3.getLayoutParams();
        params4 = (ConstraintLayout.LayoutParams) v4.getLayoutParams();
        celiang.setRadius(QMUIDisplayHelper.dp2px(this, 21));
        celiang.setChangeAlphaWhenPress(true);//点击改变透明度

        LanYaFragmentPagerAdapter myFragmentPagerAdapter = new LanYaFragmentPagerAdapter(getSupportFragmentManager(), deviceType);
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(2);

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {//蓝牙血压计
            BleManager.getInstance()
                    .enableLog(false)
                    .setReConnectCount(2, 5000)
                    .setSplitWriteNum(20)
                    .setConnectOverTime(10000)
                    .setOperateTimeout(10000);
            BleManager.getInstance().enableBluetooth();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initBle();
                        }
                    });
                }
            }).start();
            celiang.setEnabled(false);
        } else {//其他的
            ttll.setVisibility(View.GONE);
            SaveInfoBean saveInfoBean2 = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
            //link_complete(saveInfoBean2,100,90,90);
            if (saveInfoBean2.getPatientCode() != null && !saveInfoBean2.getPatientCode().equals("")) {
                link_complete(saveInfoBean2.getPatientCode(), saveInfoBean2.getPatientId());
            }
        }
    }

    private void initJDT() {
        da4 = 0;
        dd3 = 0;
        dd4 = 0;
        dd5 = 0;
        isS = true;
        da1.setText("");
        da2.setText("");
        params4.height = 1;
        v4.setLayoutParams(params4);
        v4.invalidate();
        params3.height = 1;
        v3.setLayoutParams(params3);
        v3.invalidate();
    }

    private void initBle() {
        bleDevice = null;
        initJDT();
        UUID[] uuids = new UUID[]{UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")};
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(uuids)      // 只扫描指定的服务的设备，可选
                // .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
                // .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                // .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(20000)              // 扫描超时时间，可选，默认10秒；小于等于0表示不限制扫描时间
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
        BleManager.getInstance().scanAndConnect(new BleScanAndConnectCallback() {
            @Override
            public void onScanStarted(boolean success) {
                // 开始扫描（主线程）
                Log.d("LanYaXueYaJiActivity", "开始扫描:" + success);
                if (!isFinishing()) {
                    celiang.setText("正在扫描...");
                }
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.d("LanYaXueYaJiActivity", "onScanning:");
            }

            @Override
            public void onScanFinished(BleDevice scanResult) {
                // 扫描结束，结果即为扫描到的第一个符合扫描规则的BLE设备，如果为空表示未搜索到（主线程）
                Log.d("LanYaXueYaJiActivity", "扫描结束" + scanResult);
                if (!isFinishing()) {
                    celiang.setEnabled(true);
                    if (scanResult == null) {
                        new CommomDialog(LanYaXueYaJiActivity.this, R.style.dialogs, "未扫描到设备,是否重新扫描?", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Log.d("DAFragment3", "confirm:" + confirm);
                                if (confirm) {
                                    //退出动作
                                    initBle();
                                    dialog.dismiss();
                                } else {
                                    celiang.setText("扫描设备");
                                }
                            }
                        }).setTitle("确认").setPositiveButton("确定").show();
                    }
                }
            }

            @Override
            public void onStartConnect() {
                // 开始连接（主线程）
                Log.d("LanYaXueYaJiActivity", "开始连接");
                if (!isFinishing()) {
                    celiang.setText("正在连接...");
                }
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                // 连接失败（主线程）
                Log.d("LanYaXueYaJiActivity", "连接失败");
                if (!isFinishing()) {
                    celiang.setEnabled(true);
                    new CommomDialog(LanYaXueYaJiActivity.this, R.style.dialogs, "连接失败,是否重新连接?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            Log.d("DAFragment3", "confirm:" + confirm);
                            if (confirm) {
                                //退出动作
                                initBle();
                                dialog.dismiss();
                            } else {
                                celiang.setText("扫描设备");
                            }
                        }
                    }).setTitle("确认").setPositiveButton("确定").show();
                }
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接成功，BleDevice即为所连接的BLE设备（主线程）
                Log.d("LanYaXueYaJiActivity", "连接成功");
                if (!isFinishing()) {
                    celiang.setEnabled(true);
                    celiang.setText("开始测量");
                    lanya.setBackgroundResource(R.drawable.lanya1);
                    LanYaXueYaJiActivity.this.bleDevice = bleDevice;
                    BleManager.getInstance().notify(
                            bleDevice,
                            "0000fff0-0000-1000-8000-00805f9b34fb",
                            "0000fff1-0000-1000-8000-00805f9b34fb",
                            new BleNotifyCallback() {
                                @Override
                                public void onNotifySuccess() {
                                    // 打开通知操作成功
                                    Log.d("LanYaXueYaJiActivity", "打开通知操作成功");
                                }

                                @Override
                                public void onNotifyFailure(BleException exception) {
                                    // 打开通知操作失败
                                    Log.d("LanYaXueYaJiActivity", "打开通知操作失败");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LanYaXueYaJiActivity.this, "打开通知操作失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onCharacteristicChanged(byte[] data) {
                                    // 打开通知后，设备发过来的数据将在这里出现
                                    Log.d("LanYaXueYaJiActivity", Arrays.toString(data));
                                    if (data.length == 7) {//测量事件[-3, -3, -5, 0, 55, 13, 10]//55
                                        da4 = data[4];
                                        if (da4 < 0) {
                                            da4 = 256 + data[4];
                                        }
                                        // Log.d("LanYaXueYaJiActivity", "da:" + da4);
                                        // if (da >= 0 && dq) {//收缩压
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                da1.setText(da4 + "");
                                                float pp = QMUIDisplayHelper.dp2px(LanYaXueYaJiActivity.this, (int) ((float) da4 / 2.2f));
                                                if (pp <= 3) {
                                                    pp = 3;
                                                }
                                                params3.height = (int) pp;
                                                v3.setLayoutParams(params3);
                                                v3.invalidate();
                                            }
                                        });

                                    }
                                    if (data.length == 8) {//结束事件[-3, -3, -4, 102, 65, 74, 13, 10]//102, 65, 74
                                        dd3 = data[3];
                                        dd4 = data[4];
                                        dd5 = data[5];
                                        if (dd3 < 0) {
                                            dd3 = 256 + data[3];
                                        }
                                        if (dd4 < 0) {
                                            dd4 = 256 + data[4];
                                        }
                                        if (dd5 < 0) {
                                            dd5 = 256 + data[5];
                                        }
                                        if (isS) {
                                            isS = false;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    da1.setText(dd3 + "");
                                                    float pp = QMUIDisplayHelper.dp2px(LanYaXueYaJiActivity.this, (int) ((float) dd3 / 2.2f));
                                                    params3.height = (int) pp;
                                                    v3.setLayoutParams(params3);
                                                    v3.invalidate();

                                                    da2.setText(dd4 + "");
                                                    float pp2 = QMUIDisplayHelper.dp2px(LanYaXueYaJiActivity.this, (int) ((float) dd4 / 2.2f));
                                                    params4.height = (int) pp2;
                                                    v4.setLayoutParams(params4);
                                                    v4.invalidate();

                                                    celiang.setEnabled(true);
                                                    celiang.setText("开始测量");
                                                    SaveInfoBean saveInfoBean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                                                    if (saveInfoBean.getPatientCode() == null || saveInfoBean.getPatientCode().equals("")) {
                                                        ToastUtils.setMessage("数据无法上传,请先扫码绑定患者", v1);
                                                        return;
                                                    }
                                                    //马上保存数据
                                                    link_complete(saveInfoBean, dd4, dd3, dd5);
                                                }
                                            });
                                        }
                                    }
                                    if (data.length == 6) {//测量遇到错误
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                initJDT();
                                                celiang.setEnabled(true);
                                                celiang.setText("开始测量");
                                            }
                                        });
                                    }
                                }
                            });
                }
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                // 连接断开，isActiveDisConnected是主动断开还是被动断开（主线程）
                Log.d("LanYaXueYaJiActivity", "连接断开" + isActiveDisConnected);
                if (!isFinishing()) {
                    initBle();
                    //断开先自动重连1次失败再弹出框
//                    if (linkCont<=0){
//                        //重连

//                    }else {
//
//                    }
                    celiang.setEnabled(true);
                    //   lanya.setBackgroundResource(R.drawable.lanya0);

//                    new CommomDialog(LanYaXueYaJiActivity.this, R.style.dialogs, "连接断开,是否重新连接?", new CommomDialog.OnCloseListener() {
//                        @Override
//                        public void onClick(Dialog dialog, boolean confirm) {
//                            Log.d("DAFragment3", "confirm:" + confirm);
//                            if (confirm) {
//                                //退出动作
//                                initBle();
//                                dialog.dismiss();
//                            } else {
//                                celiang.setText("扫描设备");
//                            }
//                        }
//                    }).setTitle("确认").setPositiveButton("确定").show();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
         String pnmae=MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getPatientName();
        if (pnmae!=null && !pnmae.equals(""))
            paintName.setText("当前绑定("+pnmae+")");
        else
            paintName.setText("当前未绑定患者");
    }

    //保存蓝牙测量数据
    private void link_complete(SaveInfoBean saveInfoBean, int diastolic, int systolic, int heartrate) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("patientCode", saveInfoBean.getPatientCode());
            object.put("patientId", saveInfoBean.getPatientId());
            object.put("diastolic", diastolic);//舒张是小的
            object.put("systolic", systolic);//收缩压是大的
            object.put("heartrateM", heartrate);
            object.put("deviceCode", deviceCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/bluetoothBP/add");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络连接超时", v1);
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
                boolean isSa = false;
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
                    Log.d("LoginActivity", "保存蓝牙数据" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    SaveBleDaBean logingBe = gson.fromJson(jsonObject, SaveBleDaBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult() != null) {
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //绑定数据给患者
                                        new CommomDialog(LanYaXueYaJiActivity.this, R.style.dialogs, "是否将此次测量数据上传?", new CommomDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm) {
                                                Log.d("DAFragment3", "confirm:" + confirm);
                                                if (confirm) {//确认就上传数据
                                                    //退出动作
                                                    qmuiTipDialog = new QMUITipDialog.Builder(LanYaXueYaJiActivity.this)
                                                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                                            .setTipWord("上传数据中...")
                                                            .create();
                                                    qmuiTipDialog.show();
                                                    link_bindPatientbindPatient(logingBe.getResult().getDataCode());
                                                    dialog.dismiss();
                                                }
                                            }
                                        }).setTitle("确认").setPositiveButton("确定").show();

                                    }
                                });
                        } else {
                            isSa = true;
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v1);
                        }
                    } else {
                        isSa = true;
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v1);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                    isSa = true;
                }
//                finally {
//                    if (isSa) {
//                        SaveInfoBean saveInfoBean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
//                        SphygmomanometerDataBean bean = new SphygmomanometerDataBean();
//                        bean.setBloodPressure1(Integer.parseInt(diastolic));
//                        bean.setBloodPressure2(Integer.parseInt(systolic));
//                        bean.setHeartRate(Integer.parseInt(heartrate));
//                        bean.setDeviceCode(deviceCode);
//                        bean.setTime(System.currentTimeMillis() + "");
//                        bean.setPatientName(saveInfoBean.getPatientName());
//                        bean.setPatientCode(saveInfoBean.getPatientCode());
//                        bean.setPatientId(saveInfoBean.getPatientId());
//                        bean.setDoctorName(saveInfoBean.getNurseName());
//                        bean.setTime(DateUtils.time(System.currentTimeMillis() + ""));
//                        MyApplication.myApplication.getSphygmomanometerDataBox().put(bean);
//                        if (!isFinishing()) {
//                            ToastUtils.setMessage("上传出错,测量数据已保存到本地", v1);
//                        }
//                    }
//                }
            }
        });
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        View view = viewpage.getChildAt(position);
        int height = view.getMeasuredHeight();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewpage.getLayoutParams();
        layoutParams.height = height;
        viewpage.setLayoutParams(layoutParams);

        ddddddd.setFocusable(true);
        ddddddd.setFocusableInTouchMode(true);
        Log.d("LanYaXueYaJiActivity", "lll");
        EventBus.getDefault().post("setFocusableInTouchModely");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        Log.d("Fragment1", "state:" + state);
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    t1.setTextColor(Color.parseColor("#FF4099C5"));
                    v11.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    t2.setTextColor(Color.parseColor("#FF4099C5"));
                    v22.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }




    private void resetSelected() {
        t1.setTextColor(Color.parseColor("#FFA7A7A7"));
        v11.setVisibility(View.GONE);
        t2.setTextColor(Color.parseColor("#FFA7A7A7"));
        v22.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (BleManager.getInstance().getScanSate().getCode() == 1) {
            Log.d("LanYaXueYaJiActivity", "结束扫描");
            BleManager.getInstance().cancelScan();
        }
        BleManager.getInstance().destroy();
        super.onDestroy();
    }

    //  十六进制的字符串转换成byte数组
    public byte[] HexCommandtoByte(byte[] data) {
        if (data == null) {
            return null;
        }
        int nLength = data.length;

        String strTemString = new String(data, 0, nLength);
        String[] strings = strTemString.split(" ");
        nLength = strings.length;
        data = new byte[nLength];
        for (int i = 0; i < nLength; i++) {
            if (strings[i].length() != 2) {
                data[i] = 0;
                continue;
            }
            try {
                data[i] = (byte) Integer.parseInt(strings[i], 16);
            } catch (Exception e) {
                data[i] = 0;
            }
        }

        return data;
    }

    private void link_bindPatientbindPatient(String dataCode) {//普通血压计的初始化调用
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/measureData/bindPatient?dataCode=" + dataCode);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", v2);
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
                                        ToastUtils.setSuccess("上传成功", v1);
                                        EventBus.getDefault().post("saveInfoUpadtaJG");
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v2);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v2);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }

    private void link_complete(String patientCode, long patientId) {//普通血压计的初始化调用
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("patientCode", patientCode);
            object.put("patientId", patientId);
            object.put("deviceCode", deviceCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/measureUser/add");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", v2);
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
                    ErrorBean logingBe = gson.fromJson(jsonObject, ErrorBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
//                            if (!isFinishing())
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                    }
//                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v2);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), v2);
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
            case R.id.celiang:
                celiang.setEnabled(false);
                initJDT();
                Log.d("LanYaXueYaJiActivity", "点击测量");
                if (celiang.getText().toString().equals("扫描设备")) {
                    initBle();
                } else if (bleDevice != null) {
                    String mstrRestartSend = "FD FD FA 05 0D 0A";
                    BleManager.getInstance().write(
                            bleDevice,
                            "0000fff0-0000-1000-8000-00805f9b34fb",
                            "0000fff2-0000-1000-8000-00805f9b34fb",
                            HexCommandtoByte(mstrRestartSend.getBytes()),
                            new BleWriteCallback() {
                                @Override
                                public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                    // 发送数据到设备成功
                                    Log.d("LanYaXueYaJiActivity", "发送数据到设备成功" + Arrays.toString(justWrite));
                                    celiang.setText("正在测量...");
                                }

                                @Override
                                public void onWriteFailure(BleException exception) {
                                    // 发送数据到设备失败
                                    Log.d("LanYaXueYaJiActivity", "发送数据到设备失败");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            celiang.setEnabled(true);
                                            celiang.setText("扫描设备");
                                            Toast.makeText(LanYaXueYaJiActivity.this, "发送启动测量代码到蓝牙失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });

                }

                break;
            case R.id.tl1:
                resetSelected();
                t1.setTextColor(Color.parseColor("#FF4099C5"));
                v11.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.tl2:
                resetSelected();
                t2.setTextColor(Color.parseColor("#FF4099C5"));
                v22.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.jilu:
                startActivity(new Intent(this, SaoMaActivity.class).putExtra("type", 2));
                break;
            case R.id.fanhui:
                finish();
                break;
            case R.id.celiangjilu:
                startActivity(new Intent(this, CeLiangJiLuActivity.class));
                break;
        }
    }
}
