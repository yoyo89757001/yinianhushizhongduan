package com.example.yiliaoyinian.ui.lumi.zidonghua;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGPlay1Bean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityWGPlay1Binding;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.FilterContext;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WGPlay1Activity extends AppCompatActivity {
    private ActivityWGPlay1Binding binding=null;
    private WGInfoSave wgInfoSave=null;
    private BianQianAdapter bianQianAdapter=null;
    private List<WGPlay1Bean> dangBeanList=new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private String setActionDefinitionId;
    private String setModel;
    private String setMiaoshu;
    private String getParamId;
    private String getParamId2;
    private String getParamEnum;
    private final Timer timer = new Timer();
    private TimerTask task;
    private int shengying=50;
    private int ididdd = 0;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWGPlay1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wgInfoSave= (WGInfoSave) getIntent().getSerializableExtra("WGInfoSave");
        setActionDefinitionId=getIntent().getStringExtra("setActionDefinitionId");
        setModel=getIntent().getStringExtra("setModel");
        setMiaoshu=getIntent().getStringExtra("setMiaoshu");
        getParamId=getIntent().getStringExtra("getParamId");
        getParamId2=getIntent().getStringExtra("getParamId2");
        getParamEnum=getIntent().getStringExtra("getParamEnum");
        binding.myTitle.setText(setMiaoshu+"");
       // Log.d("WGPlay1Activity", getParamEnum);
        JSONObject array=JSON.parseObject(getParamEnum);
       // Log.d("WGPlay1Activity", array.toJSONString());
       // Log.d("WGPlay1Activity", array.entrySet() +"gg");
        for (Map.Entry<String, Object> stringObjectEntry : array.entrySet()) {
            dangBeanList.add(new WGPlay1Bean(stringObjectEntry.getKey(), (Integer) stringObjectEntry.getValue(),false));
        }
        if (dangBeanList.size()>0){
            dangBeanList.get(0).setA(true);
            ididdd=dangBeanList.get(0).getId();
        }

        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                shengying=i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                loginupdataInfo(wgInfoSave.getDid(),ididdd,shengying);
            }
        });
        bianQianAdapter=new BianQianAdapter(R.layout.add_dang_item_zidonglis2t,dangBeanList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerview.setAdapter(bianQianAdapter);
        bianQianAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                for (WGPlay1Bean wgPlay1Bean : dangBeanList) {
                    wgPlay1Bean.setA(false);
                }
                dangBeanList.get(position).setA(true);
                bianQianAdapter.notifyDataSetChanged();
                ididdd=dangBeanList.get(position).getId();
                loginupdataInfo(wgInfoSave.getDid(),ididdd,shengying);
            }
        });
        binding.shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss ="";
                for (WGPlay1Bean wgPlay1Bean : dangBeanList) {
                    if (wgPlay1Bean.getId()==ididdd){
                        ss=wgPlay1Bean.getName();
                        break;
                    }
                }
                ActionsBean actionsBean=new ActionsBean();
                actionsBean.setSubjectId(wgInfoSave.getDid());
                actionsBean.setActionDefinitionId(setActionDefinitionId);
                actionsBean.setModel(setModel);
                actionsBean.setName(wgInfoSave.getName());
                actionsBean.setMiaoshu(setMiaoshu+"(播放:"+ss+")");
                JSONArray jsonArray=new JSONArray();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("value",ididdd);
                jsonObject.put("paramId",getParamId);
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("value",shengying);
                jsonObject2.put("paramId",getParamId2);
                jsonArray.add(jsonObject);
                jsonArray.add(jsonObject2);
                actionsBean.setParams(jsonArray);
                Log.d("DingShiActivity", actionsBean.toString()+"是是是");
                EventBus.getDefault().post("finishfinish");
                EventBus.getDefault().post(actionsBean);
                finish();
            }
        });

    }


    private static class BianQianAdapter extends BaseQuickAdapter<WGPlay1Bean, BaseViewHolder> implements LoadMoreModule {


        public BianQianAdapter(int layoutResId, @Nullable List<WGPlay1Bean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, WGPlay1Bean taskBean) {
            baseViewHolder.setText(R.id.name,taskBean.getName());
            if (taskBean.isA()){
                baseViewHolder.setGone(R.id.duigou,false);
            }else {
                baseViewHolder.setGone(R.id.duigou,true);
            }

            //   baseViewHolder.setText(R.id.content_tv,taskBean.getNoteContent());
//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
        }
    }

    private void loginupdataInfo(String did,int light,int shengying){//修改网关信息
        //修改网关信息
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/scene/try";

        JSONObject jsonAll =new  JSONObject();//外面最大的对象
        JSONArray jsonArray2=new JSONArray();//大数组

        JSONArray jsonArray3=new JSONArray();//参数数组
        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("value",light);
        jsonObject3.put("paramId",getParamId);
        JSONObject jsonObject4=new JSONObject();
        jsonObject4.put("value",shengying);
        jsonObject4.put("paramId",getParamId2);
        jsonArray3.add(jsonObject4);
        jsonArray3.add(jsonObject3);


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("actionDefinitionId",setActionDefinitionId);
        jsonObject.put("subjectId",did);
        jsonObject.put("model",setModel);
        jsonObject.put("params",jsonArray3);
        jsonArray2.add(jsonObject);

        jsonAll.put("actions", jsonArray2);

        Log.d("WGPlay1Activity", jsonAll.toJSONString()+"取得参数");
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(jsonAll.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
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

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    if (task != null) {
                        task.cancel();
                        //timer.cancel();
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                loginupstop(did);
                            }
                        };
                        timer.schedule(task, 5000);
                    } else {
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                loginupstop(did);
                            }
                        };
                        timer.schedule(task, 5000);
                    }
                    Log.d("WGSettingActivity", "修改网关信息:"+stA);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void loginupstop(String did){//修改网关信息
        //修改网关信息
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/scene/try";

        JSONObject jsonAll =new  JSONObject();//外面最大的对象
        JSONArray jsonArray2=new JSONArray();//大数组

        JSONArray jsonArray3=new JSONArray();//参数数组

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("actionDefinitionId","AD.lumi.gateway.stop_ringtone_light");
        jsonObject.put("subjectId",did);
        jsonObject.put("model",setModel);
        jsonObject.put("params",jsonArray3);
        jsonArray2.add(jsonObject);

        jsonAll.put("actions", jsonArray2);

        Log.d("WGPlay1Activity", jsonAll.toJSONString()+"取得参数");
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(jsonAll.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
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

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                StringBuilder bu = new  StringBuilder();
                try {
                    String stA = response.body().string();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(WGPlay1Activity.this,"修改成功",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    Log.d("WGSettingActivity", "修改网关信息:"+stA);
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }






}
