package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.yiliaoyinian.Beans.Auth0111;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.SeleteLMBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityZDH1Binding;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.AppManager;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.FilterContext;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.utils.UpState;
import com.qmuiteam.qmui.recyclerView.QMUIRVItemSwipeAction;
import com.qmuiteam.qmui.recyclerView.QMUISwipeAction;
import com.qmuiteam.qmui.recyclerView.QMUISwipeViewHolder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.jetbrains.annotations.NotNull;
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




public class ZDHActivity1 extends AppCompatActivity implements UpState {
    private ActivityZDH1Binding binding=null;
    private List<Auth0111> stringList =new ArrayList<>();
    private String did;
    private Adapter mAdapter;
    private QMUIRVItemSwipeAction swipeAction=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityZDH1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        did=getIntent().getStringExtra("did");
        AppManager.getAppManager().addActivity(this);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.jiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.jiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ZDHActivity1.this,ZDHActivityAddMain.class));
            }
        });
        if (stringList.size()<=0){
            binding.shuju.setVisibility(View.VISIBLE);
        }

        intitdata();
    }


    private void intitdata(){
         swipeAction = new QMUIRVItemSwipeAction(true, new QMUIRVItemSwipeAction.Callback() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.remove(viewHolder.getAdapterPosition());
            }

            @Override
            public int getSwipeDirection(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return QMUIRVItemSwipeAction.SWIPE_LEFT;
            }

            @Override
            public void onClickAction(QMUIRVItemSwipeAction swipeAction, RecyclerView.ViewHolder selected, QMUISwipeAction action) {
                super.onClickAction(swipeAction, selected, action);
                // Toast.makeText(ZDHActivityAddMain.this,
                //      "你点击了第 " + selected.getAdapterPosition() + " 个 item 的" + action.getText(),
                //   Toast.LENGTH_SHORT).show();
                if(mAdapter.mAction1 == action){
                    qmuiTipDialog = new QMUITipDialog.Builder(ZDHActivity1.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("删除中...")
                            .create();
                    if (!ZDHActivity1.this.isFinishing())
                        qmuiTipDialog.show();
                    logindelete(stringList.get(selected.getAdapterPosition()).getLinkageId(),selected.getAdapterPosition());
                    logindeleteSJ(stringList.get(selected.getAdapterPosition()).getId());
                }else{
                    swipeAction.clear();
                }
            }
        });
        swipeAction.attachToRecyclerView(binding.recyclerview);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ZDHActivity1.this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAdapter = new Adapter(ZDHActivity1.this,this);
        binding.recyclerview.setAdapter(mAdapter);

    }


    @Override
    public void up(int p, boolean b,SwitchCompat switchCompat) {
        qmuiTipDialog = new QMUITipDialog.Builder(ZDHActivity1.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("更改中...")
                .create();
        if (!ZDHActivity1.this.isFinishing())
            qmuiTipDialog.show();
        loginupdatestate(stringList.get(p).getLinkageId(),b,switchCompat);
    }


    static class Adapter extends RecyclerView.Adapter<QMUISwipeViewHolder>{

        private List<Auth0111> mData = new ArrayList<>();
        private UpState upState;

        final QMUISwipeAction mAction1;
        //   final QMUISwipeAction mAction2;

        public Adapter(Context context,UpState upState){
            this.upState=upState;
            QMUISwipeAction.ActionBuilder builder = new QMUISwipeAction.ActionBuilder()
                    .textSize(QMUIDisplayHelper.sp2px(context, 14))
                    .textColor(Color.WHITE)
                    .paddingStartEnd(QMUIDisplayHelper.dp2px(MyApplication.myApplication, 14));
            mAction1 = builder.text("删除").backgroundColor(Color.RED).build();

        }

        public List<Auth0111> getmData(){
            return mData;
        }
        public void setData(@Nullable List<Auth0111> list) {
             mData.clear();
            if(list != null){
                mData.addAll(list);
            }
            notifyDataSetChanged();
        }

        public void remove(int pos){
            Log.d("Adapter", "删除了");
            mData.remove(pos);
            notifyItemRemoved(pos);
        }

        public void removeAll(){
            Log.d("Adapter", "删除");
            mData.clear();
            notifyDataSetChanged();
        }

        public void add(int pos, Auth0111 item) {
            mData.add(pos, item);
            notifyItemInserted(pos);
        }

        public void prepend(@NonNull List<Auth0111> items){
            mData.addAll(0, items);
            notifyDataSetChanged();
        }

        public void append(@NonNull Auth0111 items){
            mData.add(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QMUISwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_13, parent, false);
            final QMUISwipeViewHolder vh = new QMUISwipeViewHolder(view);
            vh.addSwipeAction(mAction1);
            // vh.addSwipeAction(mAction2);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Adapter", "vh.getAdapterPosition():" + vh.getAdapterPosition());
                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull QMUISwipeViewHolder holder, int position) {

            TextView textView = holder.itemView.findViewById(R.id.title);
            textView.setText(mData.get(position).getName());
            SwitchCompat switchCompat=holder.itemView.findViewById(R.id.switchsss);
            if (mData.get(position).getState()==1){
                switchCompat.setChecked(true);
            }else {
                switchCompat.setChecked(false);
            }
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d("Adapter", "b:" + b);
                    upState.up(position,b,switchCompat);
                }
            });

        }
        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        qmuiTipDialog = new QMUITipDialog.Builder(ZDHActivity1.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中...")
                .create();
        if (!ZDHActivity1.this.isFinishing())
            qmuiTipDialog.show();
        loginApi(did);

    }

    private QMUITipDialog qmuiTipDialog = null;
    private void loginApi(String did){
        String  url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/subject/query?subjectId="+did;
//        val jsons = JsonArray()
//        val json =  JSONObject()
//        jsons.add(did);
//        json.put("dids", jsons)
        // val requestBody: RequestBody = json.toString().toRequestBody(JSONTYPE)
        //   println("ssssddfe ${json.toString()}")
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);
        header.put(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG, "zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        // val body:String = AESUtil.encryptCbc(json.toString(), AESUtil.getAESKey(APPKEY)).trim()
        // header[CommonRequest.REQUEST_HEADER_PAYLOAD] = body.trim()
        header.put(CommonRequest.REQUEST_HEADER_TIME, time);
        //    println("body加密前 ${json.toString()}  key长度 ${AESUtil.getAESKey(APPKEY).size} ")
        //   println("解密body:${AESUtil.decryptCbc(body, AESUtil.getAESKey(APPKEY))}")

        String sign = FilterContext.createSign(header, MyApplication.APPKEY, false);
        // println("body：$body");  println("sign: $sign")
        Request builder =new  Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader(CommonRequest.REQUEST_HEADER_APPID, MyApplication.APPID)
                .addHeader(CommonRequest.REQUEST_HEADER_LANG, "zh")
                .addHeader(CommonRequest.REQUEST_HEADER_TIME, time)
                .addHeader(CommonRequest.REQUEST_HEADER_SIGN, sign)
                //  .post(body.toRequestBody()).url(url).build()
                .get().url(url).build();
        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
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
                    Log.d("ZDHActivity1", stA+"根据对象查询自动化列表"+call.request().url()+" did:"+did);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    JSONArray json = JSON.parseArray(jsonss);
                    int i=1;
                    stringList.clear();
                    for (Object o : json) {
                      //  Log.d("ZDHActivity1", o.toString()+"自动化");
                        Auth0111 auth0111 = JSON.parseObject(o.toString(), Auth0111.class);
                        auth0111.setName("自动化"+i);
                        i++;
                        auth0111.setDid(did);
                        stringList.add(auth0111);
                    }
                    loginseleteSJ(did);

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private void logindelete(String linkageId,int p){
        String  url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/delete";

        JSONObject data =new  JSONObject();
        try {
            data.put("linkageId",linkageId+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(data.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign=FilterContext.createSign(header, MyApplication.APPKEY, false);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                            swipeAction.clear();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("ZDHActivity1", stA+"删除自动化列表"+call.request().url()+" linkageId:"+linkageId);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    if (healthBean.getCode()==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    mAdapter.remove(p);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    swipeAction.clear();
                                    ToastUtils.setFAIL("删除失败",binding.fanhui);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    private void loginupdatestate(String linkageId,boolean state,SwitchCompat switchCompat){//更新状态
        String  url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/state/update";

        JSONObject data =new  JSONObject();
        try {
            data.put("linkageId",linkageId);
            data.put("state",state?1:0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);
        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
        header.put(CommonRequest.REQUEST_HEADER_LANG,"zh");
        //若请求为post，需对body参数进行AES128加密，然后把加密的密文拼接到签名报文一起做签名处理
        String body = null;
        try {
            body = AESUtil.encryptCbc(data.toString(), AESUtil.getAESKey(MyApplication.APPKEY)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        header.put(CommonRequest.REQUEST_HEADER_PAYLOAD,body);
        header.put(CommonRequest.REQUEST_HEADER_TIME,time);
        String sign=FilterContext.createSign(header, MyApplication.APPKEY, false);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("ZDHActivity1", stA+"更新状态自动化列表"+call.request().url()+" linkageId:"+linkageId);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    if (healthBean.getCode()==0){

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.d("ZDHActivity1", "更新失败");
                                    ToastUtils.setFAIL("更改失败",binding.fanhui);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (qmuiTipDialog != null)
                                    qmuiTipDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    private void logindeleteSJ(String id){
        String  url = Consts.URL2+"/app/lvmi/linkageDelete";

        JSONObject data =new  JSONObject();
        try {
            data.put("id",id+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request builder = new  Request.Builder()
                .addHeader("token", MyApplication.myApplication.getToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(data.toString()+"",JSONTYPE)).url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("ZDHActivity1", stA+"删除自动化"+call.request().url()+" did:"+did);
//                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
//                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {

//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginseleteSJ(String did){//查询接口
        String  url = Consts.URL2+"/app/lvmi/findLinkage";
        JSONObject data =new  JSONObject();
        try {
            data.put("did",did);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request builder = new  Request.Builder()
                .addHeader("token", MyApplication.myApplication.getToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(data.toString()+"",JSONTYPE)).url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("ZDHActivity1", stA+"自动化查询"+call.request().url()+" did:"+did);
                    SeleteLMBean healthBean = JSON.parseObject(stA, SeleteLMBean.class);
                    try {
                        for (Auth0111 auth0111 : stringList) {
                            for (SeleteLMBean.DataDTO datum : healthBean.getData()) {
                                if (auth0111.getLinkageId().equals(datum.getLinkageId())){
                                    auth0111.setName(datum.getName());
                                    auth0111.setId(datum.getId());
                                    break;
                                }
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.shuju.setVisibility(View.GONE);
                                mAdapter.setData(stringList);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog != null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

}