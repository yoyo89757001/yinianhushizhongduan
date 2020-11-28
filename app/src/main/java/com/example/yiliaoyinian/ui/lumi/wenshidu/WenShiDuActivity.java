package com.example.yiliaoyinian.ui.lumi.wenshidu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.RTCGQBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityWuXianKaiGuan2Binding;
import com.example.yiliaoyinian.ui.lumi.dongjingtie.DongJinTieSettingActivity;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.FilterContext;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class WenShiDuActivity extends AppCompatActivity {
    private ActivityWuXianKaiGuan2Binding binding;
    private WGInfoSave wgInfoSave=null;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private BianQianAdapter bianQianAdapter=null;
    private List<RTCGQBean> dangBeanList=new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWuXianKaiGuan2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        wgInfoSave= (WGInfoSave) getIntent().getSerializableExtra("saveinfo");
        Log.d("RenTiChuanGanQiActivity", wgInfoSave.toString()+"传过来的子设备值");
        if (wgInfoSave!=null)
            binding.myTitle.setText(wgInfoSave.getName());
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WenShiDuActivity.this, DongJinTieSettingActivity.class).putExtra("wgInfoSave",wgInfoSave));
            }
        });
        bianQianAdapter=new BianQianAdapter(R.layout.kuaidi_item_rtcgq2,dangBeanList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerview.setAdapter(bianQianAdapter);
        View view1= LayoutInflater.from(WenShiDuActivity.this).inflate(R.layout.anull_data,null);
        bianQianAdapter.setEmptyView(view1);

        qmuiTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中...")
                .create();
        qmuiTipDialog.show();
        loginApiunBind(wgInfoSave.getDid());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("ttyyhgggghj")) {
            finish();
        }
    }

    private static class BianQianAdapter extends BaseQuickAdapter<RTCGQBean, BaseViewHolder> implements LoadMoreModule {


        public BianQianAdapter(int layoutResId, @Nullable List<RTCGQBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, RTCGQBean taskBean) {
            try {
                if (baseViewHolder.getLayoutPosition()==0){
                    baseViewHolder.setGone(R.id.kkk,true);
                    View view=baseViewHolder.getView(R.id.tvDot);
                    ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) view.getLayoutParams();
                    params.topMargin = QMUIDisplayHelper.dp2px(getContext(),40);
                    view.setLayoutParams(params);
                    // view.invalidate();
                }else {
                    baseViewHolder.setGone(R.id.kkk,false);
                    View view=baseViewHolder.getView(R.id.tvDot);
                    ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) view.getLayoutParams();
                    params.topMargin = QMUIDisplayHelper.dp2px(getContext(),10);
                    view.setLayoutParams(params);
                }
                baseViewHolder.setText(R.id.title,taskBean.getValues());

                baseViewHolder.setText(R.id.content_tv, DateUtils.time22(taskBean.getTime()));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loginApiunBind(String did){ //解除绑定
        //开启网关添加子设备模式
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/properties/history/query";
        JSONObject json =new  JSONObject();
        JSONArray array=new JSONArray();
        array.put("temperature_value");
        array.put("humidity_value");
        array.put("pressure_value");
        try {
            json.put("did", did);
//            json.put("startTime", "1542615034000");
//            json.put("endTime", System.currentTimeMillis()+"");
            json.put("properties", array);
//            json.put("siz", 100);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("RenTiChuanGanQiActivity", "查询设备属性参数:"+json.toString());
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign= FilterContext.createSign(header, MyApplication.APPKEY, false);
        Request builder = new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign+"")
                .post(RequestBody.create(body+"",JSONTYPE)).url(url).build();
        String[] ssee =body.split("\n");
        Log.d("RenTiChuanGanQiActivity", "查询设备属性"+ssee[0] +ssee[1]);

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (!WenShiDuActivity.this.isFinishing())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("RenTiChuanGanQiActivity", "查询设备属性请求地址:" + call.request().url()+stA);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    Log.d("WenShiDuActivity", jsonss+"dddddddd");
                    com.alibaba.fastjson.JSONArray json = JSON.parseArray(jsonss);
                    int j=0;
                    for (int i = 0; i < json.size(); i++) {
                         j++;
                        if (j==3){
                            j=0;
                            Log.d("WenShiDuActivity", "i:" + i);
                            RTCGQBean healthBean1 = JSON.parseObject(json.get(i).toString(), RTCGQBean.class);
                            RTCGQBean healthBean2 = JSON.parseObject(json.get(i-1).toString(), RTCGQBean.class);
                            RTCGQBean healthBean3 = JSON.parseObject(json.get(i-2).toString(), RTCGQBean.class);
                            healthBean1.setValues("温度:"+(Integer.parseInt(healthBean1.getValue())/100.0)+"\n"+
                                    "湿度:"+(Integer.parseInt(healthBean2.getValue())/1000.0+"%")+"\n"+
                                    "气压:"+healthBean3.getValue()+"Pa");
                            dangBeanList.add(healthBean1);
                        }

                    }
                    // Log.d("WGSettingActivity", "查询设备属性的历史值request:"+stA);
                    // Log.d("WGSettingActivity", "查询设备属性的历史值json解密:"+jsonss);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }finally {
                    if (!WenShiDuActivity.this.isFinishing())
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bianQianAdapter.notifyDataSetChanged();
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }
                        });
                }
            }
        });
    }


}