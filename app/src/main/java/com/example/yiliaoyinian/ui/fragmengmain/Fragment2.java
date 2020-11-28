package com.example.yiliaoyinian.ui.fragmengmain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.ErWeiMaBean;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;

import com.example.yiliaoyinian.ui.xunfang.XunFangActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.DecodeHintType;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements QRCodeView.Delegate, View.OnClickListener {


    QMUITopBar topbar;
    TextView t1;
    View v1;
    TextView t2;
    View v2;
    QMUIButton guan;
    private boolean isop = false;
    ZXingView zxingview;
    private int tyy=0;
    private QMUITipDialog qmuiTipDialog = null;


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        EventBus.getDefault().register(this);
        topbar=view.findViewById(R.id.topbar);
        topbar.setTitle("扫码");
        t1=view.findViewById(R.id.t1);
        v1=view.findViewById(R.id.v1);
        t2=view.findViewById(R.id.t2);
        v2=view.findViewById(R.id.v2);
        guan=view.findViewById(R.id.guan);
        zxingview=view.findViewById(R.id.zxingview);
       // @OnClick({R.id.tl1, R.id.tl2, R.id.guan})
        view.findViewById(R.id.tl1).setOnClickListener(this);
        view.findViewById(R.id.tl2).setOnClickListener(this);
        guan.setOnClickListener(this);


        if (getActivity() != null)
            guan.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 16));
        guan.setChangeAlphaWhenPress(true);//点击改变透明度
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

        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("mycameraOpen")) {
            isop = false;
            tyy=0;
            resetSelected();
            t1.setTextColor(Color.parseColor("#ffffff"));
            v1.setVisibility(View.VISIBLE);
            zxingview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
            zxingview.startSpotAndShowRect(); // 显示扫描框，并开始识别
            Log.d("Fragment2", "打开camera");
        }
        if (msgWarp.equals("mycameraClose")) {
            Log.d("Fragment2", "关闭camera");
            zxingview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        }

    }




    private void resetSelected() {
        t1.setTextColor(Color.parseColor("#ffffff"));
        v1.setVisibility(View.GONE);
        t2.setTextColor(Color.parseColor("#ffffff"));
        v2.setVisibility(View.GONE);
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.d("SaoMaActivity", result + "结果");
        if (getActivity() != null) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null)
                vibrator.vibrate(200);//震动
        }
        ErWeiMaBean student = JSONObject.parseObject(result, ErWeiMaBean.class);//type:1-患者，2-楼层，3-房间，4-床位
        if (tyy==0){
            //签到//发送请求给后台签到
            qmuiTipDialog = new QMUITipDialog.Builder(getActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("签到中...")
                    .create();
            qmuiTipDialog.show();
            String p=null;
            if (student.getType()==1){
                p=student.getData().getDataName();
            }else if (student.getType()==2){
                p="楼层签到";
            }else if (student.getType()==3){
                p="房间签到";
            }else if (student.getType()==4){
                p="床位签到";
            }
            link_complete(student.getType()+"",p,JSONObject.toJSONString(student.getData()),student.getData().getDataType());

        }else {
            //巡房
           //
            if (student.getType()!=1){
                if (getActivity()!=null)
                    ToastUtils.setMessage("巡房失败,二维码数据不正确",zxingview);
            }else {
                startActivity(new Intent(getActivity(),XunFangActivity.class)
                        .putExtra("id",student.getData().getDataId())
                        .putExtra("dataType",student.getData().getDataType())
                        .putExtra("code",student.getData().getDataCode())
                        .putExtra("name",student.getData().getDataName()));
            }

        }


    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        Log.d("SaoMaActivity", "isDark:" + isDark + isop);
        if (isDark) {
            if (!isop) {
                guan.setVisibility(View.VISIBLE);
                isop = true;
                zxingview.openFlashlight(); // 打开闪光灯
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.d("SaoMaActivity", "相机出错");

    }

    private void link_complete(String type,String typeName,String signQRData,int dataType) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("type", type);
            object.put("typeName", "患者签到");
            object.put("signQRData", signQRData);
            object.put("dataType", dataType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/signIn/add");
        // step 3：创建 Call 对象
       Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败", zxingview);
                    getActivity().runOnUiThread(new Runnable() {
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
                    if (getActivity()!=null)
                        getActivity().runOnUiThread(new Runnable() {
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
                            if (getActivity()!=null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        qmuiTipDialog = new QMUITipDialog.Builder(getActivity())
                                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                                .setTipWord("签到成功")
                                                .create();
                                        qmuiTipDialog.show();
                                        zxingview.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (qmuiTipDialog != null)
                                                    qmuiTipDialog.dismiss();
                                            }
                                        }, 3000);

                                    }
                                });
                        } else {
                            ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zxingview);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zxingview);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");

                }
            }
        });
    }


    @Override
    public void onDestroyView() {

        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tl1:
                tyy=0;
                resetSelected();
                t1.setTextColor(Color.parseColor("#ffffff"));
                v1.setVisibility(View.VISIBLE);
                if (zxingview != null)
                    zxingview.startSpot();

                break;
            case R.id.tl2:
                tyy=1;
                resetSelected();
                t2.setTextColor(Color.parseColor("#ffffff"));
                v2.setVisibility(View.VISIBLE);
                if (zxingview != null)
                    zxingview.startSpot();

                //  startActivity(new Intent(getActivity(), XunFangActivity.class));
                break;
            case R.id.guan:
                guan.setVisibility(View.GONE);
                zxingview.closeFlashlight(); // 关闭闪光灯
                isop = false;
                break;
        }
    }
}
