package com.example.yiliaoyinian.ui.lumi.zidonghua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.ServiceTypeBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.PopHeadAdapter;
import com.example.yiliaoyinian.databinding.ActivityZDHAddMainBinding;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.AppManager;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.FilterContext;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.qmuiteam.qmui.recyclerView.QMUIRVItemSwipeAction;
import com.qmuiteam.qmui.recyclerView.QMUISwipeAction;
import com.qmuiteam.qmui.recyclerView.QMUISwipeViewHolder;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

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


public class ZDHActivityAddMain extends AppCompatActivity {
    private ActivityZDHAddMainBinding binding=null;
    private PopHeadAdapter buildingAdapter = null;
    private QMUIPopup popup = null;
    private List<ServiceTypeBean> buildingList = new ArrayList<>();
    private String louDongId="0";
    private Adapter mAdapter;
    private Adapter2 mAdapter2;
    private MediaType JSONTYPE  = MediaType.parse("application/json");
    private QMUITipDialog qmuiTipDialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityZDHAddMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        buildingList.add(new ServiceTypeBean("0","满足所有条件"));//条件 0:与 1:或 默认 0
        buildingList.add(new ServiceTypeBean("1","满足任意条件"));
        buildingAdapter = new PopHeadAdapter(buildingList, ZDHActivityAddMain.this);
        AppManager.getAppManager().addActivity(this);
        binding.recyclerview1.setNestedScrollingEnabled(false);
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.tiaojian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup = QMUIPopups.listPopup(ZDHActivityAddMain.this, (int) (binding.ddffg.getWidth()*1.8), 260, buildingAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("LoginActivity", "position:" + position);
                        binding.ddffg.setText(buildingList.get(position).getTypeName());
                        louDongId=buildingList.get(position).getTypeId();
                        popup.dismiss();
                    }
                }).edgeProtection(QMUIDisplayHelper.dp2px(ZDHActivityAddMain.this, 20))
                        // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(ZDHActivityAddMain.this, 4))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .show(binding.tiaojian);
            }
        });

        intitdata();
        binding.add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ZDHActivityAddMain.this, AddDangActivity.class));
            }
        });
        binding.add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ZDHActivityAddMain.this, AddJiuActivity.class));
            }
        });

        binding.baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ZDHActivityAddMain.this);
                builder.setTitle("设置自动化名称")
                        .setSkinManager(QMUISkinManager.defaultInstance(ZDHActivityAddMain.this))
                        .setPlaceholder("请输入自动化名称")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                CharSequence text = builder.getEditText().getText();
                                if (text != null && text.length() > 0) {
                                    qmuiTipDialog = new QMUITipDialog.Builder(ZDHActivityAddMain.this)
                                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                            .setTipWord("保存中...")
                                            .create();
                                    qmuiTipDialog.show();
                                    loginApiun(text.toString());
                                }
                                dialog.dismiss();
                            }
                        })
                        .create(R.style.QMUI_Dialog).show();

            }
        });

    }

    private void intitdata(){
        QMUIRVItemSwipeAction swipeAction = new QMUIRVItemSwipeAction(true, new QMUIRVItemSwipeAction.Callback() {
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
                    mAdapter.remove(selected.getAdapterPosition());
                }else{
                    swipeAction.clear();
                }
            }
        });
        swipeAction.attachToRecyclerView(binding.recyclerview1);
        binding.recyclerview1.setLayoutManager(new LinearLayoutManager(ZDHActivityAddMain.this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAdapter = new Adapter(ZDHActivityAddMain.this);
        binding.recyclerview1.setAdapter(mAdapter);

        QMUIRVItemSwipeAction swipeAction2 = new QMUIRVItemSwipeAction(true, new QMUIRVItemSwipeAction.Callback() {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter2.remove(viewHolder.getAdapterPosition());
            }

            @Override
            public int getSwipeDirection(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return QMUIRVItemSwipeAction.SWIPE_LEFT;
            }

            @Override
            public void onClickAction(QMUIRVItemSwipeAction swipeAction2, RecyclerView.ViewHolder selected, QMUISwipeAction action) {
                super.onClickAction(swipeAction2, selected, action);
              //  Toast.makeText(ZDHActivityAddMain.this,
                   //     "你点击了第 " + selected.getAdapterPosition() + " 个 item 的" + action.getText(),
                    //    Toast.LENGTH_SHORT).show();
                if(mAdapter2.mAction1 == action){
                    mAdapter2.remove(selected.getAdapterPosition());
                }else{
                    swipeAction2.clear();
                }
            }
        });
        swipeAction2.attachToRecyclerView(binding.recyclerview2);
        binding.recyclerview2.setLayoutManager(new LinearLayoutManager(ZDHActivityAddMain.this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAdapter2 = new Adapter2(ZDHActivityAddMain.this);
        binding.recyclerview2.setAdapter(mAdapter2);

    }


    static class Adapter extends RecyclerView.Adapter<QMUISwipeViewHolder>{

        private List<ActionsBean> mData = new ArrayList<>();

        final QMUISwipeAction mAction1;
     //   final QMUISwipeAction mAction2;

        public Adapter(Context context){
            QMUISwipeAction.ActionBuilder builder = new QMUISwipeAction.ActionBuilder()
                    .textSize(QMUIDisplayHelper.sp2px(context, 14))
                    .textColor(Color.WHITE)
                    .paddingStartEnd(QMUIDisplayHelper.dp2px(MyApplication.myApplication, 14));
             mAction1 = builder.text("删除").backgroundColor(Color.RED).build();
//            mAction1 = builder
//                    .backgroundColor(Color.RED)
//                    .icon(ContextCompat.getDrawable(context, R.drawable.icon_quick_action_delete_line))
//                    .orientation(QMUISwipeAction.ActionBuilder.VERTICAL)
//                    .reverseDrawOrder(false)
//                    .build();
//            mAction2 = builder
//                    .backgroundColor(Color.BLUE)
//                    .icon(ContextCompat.getDrawable(context, R.drawable.icon_quick_action_share))
//                    .orientation(QMUISwipeAction.ActionBuilder.VERTICAL)
//                    .reverseDrawOrder(true)
//                    .build();
        }

        public List<ActionsBean> getmData(){
            return mData;
        }
        public void setData(@Nullable List<ActionsBean> list) {
           // mData.clear();
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

        public void add(int pos, ActionsBean item) {
            mData.add(pos, item);
            notifyItemInserted(pos);
        }

        public void prepend(@NonNull List<ActionsBean> items){
            mData.addAll(0, items);
            notifyDataSetChanged();
        }

        public void append(@NonNull ActionsBean items){
            mData.add(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QMUISwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_12, parent, false);
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
            TextView miaoshu = holder.itemView.findViewById(R.id.miaoshu);
            miaoshu.setText(mData.get(position).getMiaoshu());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    static class Adapter2 extends RecyclerView.Adapter<QMUISwipeViewHolder>{

        private List<ActionsBean> mData = new ArrayList<>();

        final QMUISwipeAction mAction1;
        //   final QMUISwipeAction mAction2;

        public Adapter2(Context context){
            QMUISwipeAction.ActionBuilder builder = new QMUISwipeAction.ActionBuilder()
                    .textSize(QMUIDisplayHelper.sp2px(context, 14))
                    .textColor(Color.WHITE)
                    .paddingStartEnd(QMUIDisplayHelper.dp2px(MyApplication.myApplication, 14));
            mAction1 = builder.text("删除").backgroundColor(Color.RED).build();
//            mAction1 = builder
//                    .backgroundColor(Color.RED)
//                    .icon(ContextCompat.getDrawable(context, R.drawable.icon_quick_action_delete_line))
//                    .orientation(QMUISwipeAction.ActionBuilder.VERTICAL)
//                    .reverseDrawOrder(false)
//                    .build();
//            mAction2 = builder
//                    .backgroundColor(Color.BLUE)
//                    .icon(ContextCompat.getDrawable(context, R.drawable.icon_quick_action_share))
//                    .orientation(QMUISwipeAction.ActionBuilder.VERTICAL)
//                    .reverseDrawOrder(true)
//                    .build();
        }

        public List<ActionsBean> getmData(){
            return mData;
        }
        public void setData(@Nullable List<ActionsBean> list) {
            // mData.clear();
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

        public void add(int pos, ActionsBean item) {
            mData.add(pos, item);
            notifyItemInserted(pos);
        }

        public void prepend(@NonNull List<ActionsBean> items){
            mData.addAll(0, items);
            notifyDataSetChanged();
        }

        public void append(@NonNull ActionsBean items){
            mData.add(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QMUISwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_12, parent, false);
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
            TextView miaoshu = holder.itemView.findViewById(R.id.miaoshu);
            miaoshu.setText(mData.get(position).getMiaoshu());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG5(ActionsBean msgWarp) {
        Log.d("MainActivity", "收到ActionsBean：" + msgWarp.toString());
        try {
            if (msgWarp.getTriggerDefinitionId()!=null){
                mAdapter.append(msgWarp);
            }else {
                mAdapter2.append(msgWarp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void loginApiun(String name){
        List<ActionsBean> a1 = mAdapter.getmData();
        List<ActionsBean> a2 = mAdapter2.getmData();

        StringBuilder dids1 = new StringBuilder();
        StringBuilder dids2 = new StringBuilder();
        String url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/create";

        JSONObject jsonAll =new  JSONObject();//外面最大的对象
        JSONArray jsonArray1=new JSONArray();//大数组
        for (ActionsBean actionsBean : a1) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("triggerDefinitionId",actionsBean.getTriggerDefinitionId());
            jsonObject.put("subjectId",actionsBean.getSubjectId());
            jsonObject.put("model",actionsBean.getModel());
            jsonObject.put("params",actionsBean.getParams());
            jsonArray1.add(jsonObject);
            if (actionsBean.getSubjectId()!=null && !actionsBean.getSubjectId().equals("")){
                dids1.append(actionsBean.getSubjectId());
                dids1.append(",");
            }
        }
        JSONArray jsonArray2=new JSONArray();//大数组
        for (ActionsBean actionsBean : a2) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("actionDefinitionId",actionsBean.getActionDefinitionId());
            jsonObject.put("subjectId",actionsBean.getSubjectId());
            jsonObject.put("model",actionsBean.getModel());
            jsonObject.put("params",actionsBean.getParams());
            jsonArray2.add(jsonObject);
            if (actionsBean.getSubjectId()!=null && !actionsBean.getSubjectId().equals("")){
                dids2.append(actionsBean.getSubjectId());
                dids2.append(",");
            }
        }
        jsonAll.put("conditions", jsonArray1);
        jsonAll.put("actions", jsonArray2);
        jsonAll.put("relation", louDongId);

        Log.d("ZDHActivityAddMain", "jsonAll:" + jsonAll.toJSONString());
        //json.put("installCode", "")
        //  RequestBody requestBody = RequestBody.create(json.toString(),JSONTYPE);
        String time= String.valueOf(System.currentTimeMillis());
        HashMap<String, String> header = new HashMap<>(4);

        header.put(CommonRequest.REQUEST_HEADER_APPID,MyApplication.APPID);
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
        String sign= FilterContext.createSign(header,MyApplication.APPKEY, false);
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
                            if (!ZDHActivityAddMain.this.isFinishing()){
                                if (qmuiTipDialog!=null)
                                    qmuiTipDialog.dismiss();
                            }
                        }
                    });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "解除绑定设备:"+stA);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    Log.d("ZDHActivityAddMain", jsonss+"解密");
                    jsonss = jsonss.substring(1,jsonss.length()-1);
                    loginSave("",dids2.toString()+dids1.toString(),0,name,jsonss);

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!ZDHActivityAddMain.this.isFinishing()){
                                if (qmuiTipDialog!=null)
                                    qmuiTipDialog.dismiss();
                            }
                        }
                    });
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }

    private void loginSave(String id,String did,int status,String name,String linkageId){
        String url = Consts.URL2+"/app/lvmi/linkageSave";
        JSONObject json =new JSONObject();
        try {
            if (id!=""){
                json.put("id", id);
            }
            json.put("did", did);
            json.put("name", name);
            json.put("status", status);
            json.put("linkageId", linkageId);
        } catch (Exception e) {
            Log.d("Fragment3", e.getMessage()+"AllConnects");
        }
        Log.d("ZDHActivityAddMain", json.toString()+"参数");
        Request builder = new  Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(json.toString(),JSONTYPE)).url(url).build();

        MyApplication.okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!ZDHActivityAddMain.this.isFinishing()){
                            if (qmuiTipDialog!=null)
                                qmuiTipDialog.dismiss();
                        }
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!ZDHActivityAddMain.this.isFinishing()){
                                if (qmuiTipDialog!=null)
                                    qmuiTipDialog.dismiss();
                            }
                        }
                    });
                    SystemClock.sleep(500);
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "新增或修改自动化:"+stA);
                     int ssdd = JSON.parseObject(stA).getInteger("code");
                     if (ssdd==1){
                       // ToastUtils.setMessage("添加成功",binding.fanhui);
                        // SystemClock.sleep(1600);
                         finish();
                      }else {
                         ToastUtils.setMessage("添加失败",binding.fanhui);
                     }
                } catch (Exception e) {
                    Log.d("WGSettingActivity", e.getMessage()+"请求结果异常");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}