package com.example.yiliaoyinian.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.CheckBBean;
import com.example.yiliaoyinian.Beans.ErWeiMaBean;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.DecodeHintType;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SaoMaActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {


    ZXingView zxingview;
    ImageView blacks;
    QMUIButton guan;
    private boolean isop=false;
    private boolean isA=false;
    private int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sao_ma);
        zxingview=findViewById(R.id.zxingview);
        blacks=findViewById(R.id.blacks);
        blacks.setOnClickListener(this);
        guan=findViewById(R.id.guan);
        guan.setOnClickListener(this);
        zxingview.setDelegate(this);
        Map<DecodeHintType, Object> hintMap = new EnumMap<>(DecodeHintType.class);
        // List<BarcodeFormat> formatList = new ArrayList<>();
        // formatList.add(BarcodeFormat.QR_CODE);
        // formatList.add(BarcodeFormat.UPC_A);
        // formatList.add(BarcodeFormat.EAN_13);
        // formatList.add(BarcodeFormat.CODE_128);
        //hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList); // 可能的编码格式
        //hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
        hintMap.put(DecodeHintType.CHARACTER_SET, "UTF-8"); // 编码字符集
        zxingview.setType(BarcodeType.CUSTOM, hintMap); // 自定义识别的类型


        guan.setRadius(QMUIDisplayHelper.dp2px(this, 16));
        guan.setChangeAlphaWhenPress(true);//点击改变透明度
        type=getIntent().getIntExtra("type",0);


    }

    private QMUITipDialog qmuiTipDialog = null;
    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.d("SaoMaActivity", result+"结果");
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null)
            vibrator.vibrate(200);//震动
        if (type==2){//绑定患者信息;
            try {
                ErWeiMaBean student = JSONObject.parseObject(result, ErWeiMaBean.class);
                if (student!=null){
                    qmuiTipDialog = new QMUITipDialog.Builder(this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("绑定中...")
                            .create();
                    qmuiTipDialog.show();
                    link_checkPatientInfo(student.getData().getDataId()+"",student.getData().getDataCode(),student.getData().getDataType());
                }else {
                    ToastUtils.setMessage("二维码数据不匹配",zxingview);
                    zxingview.startSpot();
                }
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.setMessage("二维码数据不匹配",zxingview);
                zxingview.startSpot();
            }
        }else {//验证身份
            EventBus.getDefault().post(result);
            finish();
        }
    }




    private void link_checkPatientInfo(String idid,String code,int dataType) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nursePatient/getBaseInfo?patientId="+idid+"&patientCode="+code+"&dataType="+dataType);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", zxingview);
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
                    CheckBBean logingBe = gson.fromJson(jsonObject, CheckBBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            SaveInfoBean saveInfoBean=MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                                            saveInfoBean.setPatientAge(logingBe.getResult().getAge());
                                            saveInfoBean.setPatientCode(logingBe.getResult().getPatientCode());
                                            saveInfoBean.setPatientId(logingBe.getResult().getId());
                                            saveInfoBean.setPatientName(logingBe.getResult().getPatientName());
                                            MyApplication.myApplication.getSaveInfoBeanBox().put(saveInfoBean);
                                            ToastUtils.setMessage("绑定成功",zxingview);
                                            EventBus.getDefault().post("TimeOut30res");
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SystemClock.sleep(1900);
                                                    if (!isFinishing())
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }).start();

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing()){
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zxingview);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zxingview);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (!isFinishing())
                    ToastUtils.setMessage("数据异常,返回后重试",zxingview);
                }
            }
        });
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        Log.d("SaoMaActivity", "isDark:" + isDark);
        if (isDark) {
            if (!isop){
                guan.setVisibility(View.VISIBLE);
                isop=true;
                zxingview.openFlashlight(); // 打开闪光灯
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.d("SaoMaActivity", "相机出错");

    }

    @Override
    protected void onStart() {
        super.onStart();

        zxingview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        zxingview.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blacks:
                finish();
                break;
            case R.id.guan:
                guan.setVisibility(View.GONE);
                zxingview.closeFlashlight(); // 关闭闪光灯
                isop=false;
                break;
        }
    }
}
