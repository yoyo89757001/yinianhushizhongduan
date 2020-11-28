package com.example.yiliaoyinian.ui.lumi.zidonghua;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yiliaoyinian.Beans.ActionsBean;
import com.example.yiliaoyinian.Beans.Auto7;
import com.example.yiliaoyinian.Beans.CardBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.ZhiXingBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.databinding.ActivityZhiXingListBinding;
import com.example.yiliaoyinian.utils.AESUtil;
import com.example.yiliaoyinian.utils.CommonRequest;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.FilterContext;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;




public class ZhiXingListActivity extends AppCompatActivity {
    private ActivityZhiXingListBinding binding=null;
    private WGInfoSave wgInfoSave=null;
    private BianQianAdapter bianQianAdapter=null;
    private List<ZhiXingBean> dangBeanList=new ArrayList<>();
    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private ArrayList<CardBean> cardItemLD = new ArrayList<>();
    private QMUITipDialog qmuiTipDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityZhiXingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
        wgInfoSave= (WGInfoSave) getIntent().getSerializableExtra("WGInfoSave");
        if (wgInfoSave!=null){
            try {
                binding.myTitle.setText(wgInfoSave.getName());

            }catch (Exception e){
                e.printStackTrace();
            }


        }
        binding.fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bianQianAdapter=new BianQianAdapter(R.layout.add_dang_item_zidonglist,dangBeanList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerview.setAdapter(bianQianAdapter);

        bianQianAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (dangBeanList.get(position).getParams()!=null && dangBeanList.get(position).getParams().size()>0){
                    switch (dangBeanList.get(position).getParams().get(0).getOptions()){
                        case 2://键值对数据（铃声/模式/预设音量等级等）
                            if (dangBeanList.get(position).getParams().size()>1){
                                startActivity(new Intent(ZhiXingListActivity.this,WGPlay1Activity.class).
                                        putExtra("WGInfoSave",wgInfoSave).
                                        putExtra("getParamEnum",dangBeanList.get(position).getParams().get(0).getParamEnum()).
                                        putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
                                        putExtra("setModel",dangBeanList.get(position).getModel()).
                                        putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
                                        putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()).
                                        putExtra("getParamId2",dangBeanList.get(position).getParams().get(1).getParamId()));
                            }else {
                                startActivity(new Intent(ZhiXingListActivity.this,WGPlay1Activity.class).
                                        putExtra("WGInfoSave",wgInfoSave).
                                        putExtra("getParamEnum",dangBeanList.get(position).getParams().get(0).getParamEnum()).
                                        putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
                                        putExtra("setModel",dangBeanList.get(position).getModel()).
                                        putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
                                        putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()));
                            }

                            break;
                        case 6://颜色
                            startActivity(new Intent(ZhiXingListActivity.this,ColorSelectActivity.class).
                                    putExtra("WGInfoSave",wgInfoSave).
                                    putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
                                    putExtra("setModel",dangBeanList.get(position).getModel()).
                                    putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
                                    putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()));
                            break;
                        case 5:
                            OptionsPickerView pvOptionsLD = new OptionsPickerBuilder(ZhiXingListActivity.this, new OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    //返回的分别是三个级别的选中位置
                                    final String tx = String.valueOf(cardItemLD.get(options1).getId());
                                    Log.d("DongZuoListActivity", tx);
                                    ActionsBean actionsBean=new ActionsBean();
                                    actionsBean.setSubjectId(wgInfoSave.getDid());
                                    actionsBean.setActionDefinitionId(dangBeanList.get(position).getActionDefinitionId());//用来区分动作和触发事件
                                    actionsBean.setModel(dangBeanList.get(position).getModel());
                                    actionsBean.setName(wgInfoSave.getName());
                                    actionsBean.setMiaoshu(dangBeanList.get(position).getActionName()+"("+tx+"%)");
                                    JSONArray jsonArray=new JSONArray();
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put("value",tx);
                                    jsonObject.put("paramId",dangBeanList.get(position).getParams().get(0).getParamId());
                                    jsonArray.add(jsonObject);
                                    actionsBean.setParams(jsonArray);
                                    Log.d("DingShiActivity", actionsBean.toString()+"是是是");
                                    EventBus.getDefault().post("finishfinish");
                                    EventBus.getDefault().post(actionsBean);

                                }
                            })
                                    .setTitleText("")
                                    .setCancelText("取消")//取消按钮文字
                                    .setSubmitText("确定")//确认按钮文字
                                    .setContentTextSize(16)//滚轮文字大小
                                    .setTitleSize(20)//标题文字大小
                                    .setTitleText("")//标题文字
                                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                                    .setTitleColor(Color.WHITE)//标题文字颜色
                                    .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                                    .setCancelColor(Color.WHITE)//取消按钮文字颜色
                                    .setTitleBgColor(0xFF59B683)//标题背景颜色 Night mode
                                    .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                                    .build();
                            pvOptionsLD.setPicker(cardItemLD);//一级选择器
                            //    pvOptions.setPicker(options1Items, options2Items);//二级选择器
                            //   pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                            pvOptionsLD.show();
                            break;
                        case 7://色温
                            startActivity(new Intent(ZhiXingListActivity.this,SeWenSelectActivity.class).
                                    putExtra("WGInfoSave",wgInfoSave).
                                    putExtra("setActionDefinitionId",dangBeanList.get(position).getActionDefinitionId()).
                                    putExtra("setModel",dangBeanList.get(position).getModel()).
                                    putExtra("setMiaoshu",dangBeanList.get(position).getActionName()).
                                    putExtra("getParamId",dangBeanList.get(position).getParams().get(0).getParamId()));
                            break;
                        case 8://时间

                            break;
                        case 13://光照度
                            OptionsPickerView pvOptions = new OptionsPickerBuilder(ZhiXingListActivity.this, new OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    //返回的分别是三个级别的选中位置
                                    final String tx = String.valueOf(cardItem.get(options1).getId());
                                    Log.d("DongZuoListActivity", tx);

                                }
                            })
                            .setTitleText("")
                            .setCancelText("取消")//取消按钮文字
                            .setSubmitText("确定")//确认按钮文字
                            .setContentTextSize(16)//滚轮文字大小
                            .setTitleSize(20)//标题文字大小
                            .setTitleText("")//标题文字
                            .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                            .setTitleColor(Color.WHITE)//标题文字颜色
                            .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                            .setCancelColor(Color.WHITE)//取消按钮文字颜色
                            .setTitleBgColor(0xFF59B683)//标题背景颜色 Night mode
                            .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                            .build();

                            pvOptions.setPicker(cardItem);//一级选择器
                            //    pvOptions.setPicker(options1Items, options2Items);//二级选择器
                            //   pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                            pvOptions.show();
                            break;
                        case 14://时长
                            TimePickerView pickerView = new TimePickerBuilder(ZhiXingListActivity.this, new OnTimeSelectListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {

                                    Log.d("DongZuoListActivity", "date:" + date.getTime());

                                    Log.d("DongZuoListActivity", "date:" + DateUtils.ti(date.getTime()+""));
                                }
                            })
                                    .setType(new boolean[]{false, false, false, true, true, false})//分别对应年月日时分秒，默认全部显示
                                    .setCancelText("取消")//取消按钮文字
                                    .setSubmitText("确定")//确认按钮文字
                                    .setContentTextSize(16)//滚轮文字大小
                                    .setTitleSize(20)//标题文字大小
                                    .setTitleText("")//标题文字
                                    .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                                    .isCyclic(true)//是否循环滚动
                                    .setTitleColor(Color.WHITE)//标题文字颜色
                                    .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                                    .setCancelColor(Color.WHITE)//取消按钮文字颜色
                                    .setTitleBgColor(0xFF59B683)//标题背景颜色 Night mode
                                    .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                                    //.setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                                   // .setRangDate(startDate,endDate)//起始终止年月日设定
                                    .setLabel("年","月","日","时","分","秒")
                                    .isDialog(false)//是否显示为对话框样式
                                    .build();
                            pickerView.show();
                            break;

                    }

                }else {
                 Log.d("DongZuoListActivity", "没有参数");
                    ActionsBean actionsBean=new ActionsBean();
                    actionsBean.setSubjectId(wgInfoSave.getDid());
                    actionsBean.setActionDefinitionId(dangBeanList.get(position).getActionDefinitionId());
                    actionsBean.setModel(dangBeanList.get(position).getModel());
                    actionsBean.setName(wgInfoSave.getName());
                    actionsBean.setMiaoshu(dangBeanList.get(position).getActionName());
                    JSONArray jsonArray=new JSONArray();
                    actionsBean.setParams(jsonArray);
                    Log.d("DingShiActivity", actionsBean.toString()+"是是是");
                    EventBus.getDefault().post(actionsBean);
                    EventBus.getDefault().post("finishfinish");

                }
            }
        });

        if (wgInfoSave!=null && wgInfoSave.getModle()!=null){
            qmuiTipDialog = new QMUITipDialog.Builder(ZhiXingListActivity.this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("加载中...")
                    .create();
            qmuiTipDialog.show();
            loginApi(wgInfoSave.getModle());
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<11;i++){
                    CardBean cardBean=new CardBean(i*10,(i*10)+" Lux");
                    cardItem.add(cardBean);
                }
                int a= 100;

                while (true){
                    if (a>1000){
                        return;
                    }
                    a+=50;
                    CardBean cardBean=new CardBean(a,a+" Lux");
                    cardItem.add(cardBean);
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<100;i++){
                    CardBean cardBean=new CardBean(i,(i)+" %");
                    cardItemLD.add(cardBean);
                }
            }
        }).start();


    }

    private static class BianQianAdapter extends BaseQuickAdapter<ZhiXingBean, BaseViewHolder> implements LoadMoreModule {


        public BianQianAdapter(int layoutResId, @Nullable List<ZhiXingBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, ZhiXingBean taskBean) {
            baseViewHolder.setText(R.id.name,taskBean.getActionName());
            baseViewHolder.setBackgroundResource(R.id.image,R.drawable.jiedian);

            //   baseViewHolder.setText(R.id.content_tv,taskBean.getNoteContent());


//        Glide.with(mContext)
//                .load(item.getHeadImage())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into((ImageView) helper.getView(R.id.touxiang));
//        //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
        }
    }


    private void loginApi(String action){
        String  url = "https://aiot-open-3rd.aqara.cn/v2.0/open/ifttt/action/definition/query?models="+action;

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
                        if (qmuiTipDialog!=null)
                            qmuiTipDialog.dismiss();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    String stA = response.body().string();
                    Log.d("WGSettingActivity", "动作列表:"+stA);
                    Auto7 healthBean = JSON.parseObject(stA, Auto7.class);
                    String jsonss = AESUtil.decryptCbc(healthBean.getResult(), AESUtil.getAESKey(MyApplication.APPKEY));
                    Log.d("WGSettingActivity", "动作列表解密:"+jsonss);
                    JSONArray json = JSON.parseArray(jsonss);
                    for (Object o : json) {
                        ZhiXingBean healthBean1 = JSON.parseObject(o.toString(), ZhiXingBean.class);
                        dangBeanList.add(healthBean1);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bianQianAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qmuiTipDialog!=null)
                                qmuiTipDialog.dismiss();
                        }
                    });
                }
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishs(String finish) {
        if (finish.equals("finishfinish")){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}