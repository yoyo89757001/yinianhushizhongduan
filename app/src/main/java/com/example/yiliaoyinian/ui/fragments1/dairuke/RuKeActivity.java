package com.example.yiliaoyinian.ui.fragments1.dairuke;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.yiliaoyinian.Beans.Budile1;
import com.example.yiliaoyinian.Beans.Budile2;
import com.example.yiliaoyinian.Beans.Budile3;
import com.example.yiliaoyinian.Beans.Budile4;
import com.example.yiliaoyinian.Beans.JuZhuBean;
import com.example.yiliaoyinian.Beans.RuKeBean;
import com.example.yiliaoyinian.Beans.RuKeBean2;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIFontFitTextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class RuKeActivity extends BaseActivity implements View.OnClickListener {



    QMUIFontFitTextView idid;

    QMUIFontFitTextView name;


    QMUIFontFitTextView age;

    QMUIFontFitTextView shenfenzheng;

    QMUIFloatLayout qmfl1;

    QMUIFloatLayout qmfl2;

    QMUIFloatLayout qmfl3;

    QMUIFloatLayout qmfl4;

    List<QMUIButton> qmuiButtonList1 = new ArrayList<>();
    List<QMUIButton> qmuiButtonList2 = new ArrayList<>();
    List<QMUIButton> qmuiButtonList3 = new ArrayList<>();
    List<QMUIButton> qmuiButtonList4 = new ArrayList<>();

    List<Budile1> budile1List1 = new ArrayList<>();
    List<Budile2> budile1List2 = new ArrayList<>();
    List<Budile3> budile1List3 = new ArrayList<>();
    List<Budile4> budile1List4 = new ArrayList<>();

    QMUIButton xiayibu;
    private RuKeBean.ResultBean resultBean=null;
    private  long b1=-1,b2=-1,b3=-1,b4=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ke);
        xiayibu=findViewById(R.id.xiayibu);
        idid=findViewById(R.id.idid);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        shenfenzheng=findViewById(R.id.shenfenzheng);
        qmfl1=findViewById(R.id.qmfl1);
        qmfl2=findViewById(R.id.qmfl2);
        qmfl3=findViewById(R.id.qmfl3);
        qmfl4=findViewById(R.id.qmfl4);
        xiayibu.setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);

        EventBus.getDefault().register(this);

        resultBean = (RuKeBean.ResultBean) getIntent().getSerializableExtra("person");
        if (resultBean!=null){
            name.setText("姓名: "+resultBean.getPatientName());
            idid.setText("ID: "+resultBean.getPatientCode());
            shenfenzheng.setText("身份证: "+resultBean.getIdCard());
            age.setText("年龄: "+resultBean.getAge()+"岁");
        }


        qmfl1.setChildHorizontalSpacing(QMUIDisplayHelper.dp2px(this, 12));
        qmfl1.setChildVerticalSpacing(QMUIDisplayHelper.dp2px(this, 16));

        qmfl2.setChildHorizontalSpacing(QMUIDisplayHelper.dp2px(this, 12));
        qmfl2.setChildVerticalSpacing(QMUIDisplayHelper.dp2px(this, 16));

        qmfl3.setChildHorizontalSpacing(QMUIDisplayHelper.dp2px(this, 12));
        qmfl3.setChildVerticalSpacing(QMUIDisplayHelper.dp2px(this, 16));

        qmfl4.setChildHorizontalSpacing(QMUIDisplayHelper.dp2px(this, 12));
        qmfl4.setChildVerticalSpacing(QMUIDisplayHelper.dp2px(this, 16));

        xiayibu.setRadius(QMUIDisplayHelper.dp2px(this, 25));
        xiayibu.setChangeAlphaWhenPress(true);//点击改变透明度

        link_getBuildDetail();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("rukechenggong")){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        String[] tag = view.getTag().toString().split(",");
        switch (tag[0]) {
            case "1"://第一组

                qmfl2.removeAllViews();
                qmuiButtonList2.clear();
                qmfl3.removeAllViews();
                qmuiButtonList3.clear();
                qmfl4.removeAllViews();
                qmuiButtonList4.clear();
                b2=-1; b3=-1; b4=-1;

                b1=Long.parseLong(tag[1]);
                addV1(Long.parseLong(tag[1]));//找到楼栋第一个id

                for (Budile2 budile2 : budile1List2) {//存放所有楼层
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile2.getBuildId()==b1){
                        b2=budile2.getId();
                        addV2(b2,b1);
                        break;
                    }
                }
                if (b2==-1)//没找到
                     return;

                ///////找到房间第一个id的下属也就是楼层的第一个值
                for (Budile3 budile3 : budile1List3) {//存放所有楼层
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile3.getBuildId()==b1 && budile3.getFloorId()==b2){//房间
                        //跟楼层和房间都相等
                        b3=budile3.getId();
                        addV3(b3,b1,b2);
                        break;
                    }
                }
                if (b3==-1)//没找到
                    return;

                ///////找到床第一个id的下属也就是楼层的第一个值
                for (Budile4 budile4 : budile1List4) {//存放所有楼层
                   // Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+"  "+budile4.getName());
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile4.getRoomId()==b3){//房间
                        //跟楼层和房间都相等
                        b4=budile4.getId();
                        addV4(b4,b3);
                        break;
                    }
                }

                break;
            case "2":

                qmfl3.removeAllViews();
                qmuiButtonList3.clear();
                qmfl4.removeAllViews();
                qmuiButtonList4.clear();
                b3=-1; b4=-1;
                for (Budile2 budile2 : budile1List2) {//存放所有楼层
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile2.getBuildId()==b1){
                        b2=Long.parseLong(tag[1]);
                        addV2(b2,b1);
                        break;
                    }
                }
                if (b2==-1)//没找到
                    return;

                ///////找到房间第一个id的下属也就是楼层的第一个值
                for (Budile3 budile3 : budile1List3) {//存放所有楼层
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile3.getBuildId()==b1 && budile3.getFloorId()==b2){//房间
                        //跟楼层和房间都相等
                        b3=budile3.getId();
                        addV3(b3,b1,b2);
                        break;
                    }
                }
                if (b3==-1)//没找到
                    return;

                ///////找到床第一个id的下属也就是楼层的第一个值
                for (Budile4 budile4 : budile1List4) {//存放所有楼层
                  //  Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+"  "+budile4.getName());
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile4.getRoomId()==b3){//房间
                        //跟楼层和房间都相等
                        b4=budile4.getId();
                        addV4(b4,b3);
                        break;
                    }
                }

                break;
            case "3":
                qmfl4.removeAllViews();
                qmuiButtonList4.clear();
                b4=-1;
                for (Budile3 budile3 : budile1List3) {//存放所有楼层
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile3.getBuildId()==b1 && budile3.getFloorId()==b2){//房间
                        //跟楼层和房间都相等
                        b3=Long.parseLong(tag[1]);
                        addV3(b3,b1,b2);
                        break;
                    }
                }
                if (b3==-1)//没找到
                    return;
                ///////找到床第一个id的下属也就是楼层的第一个值
                for (Budile4 budile4 : budile1List4) {//存放所有楼层
                   // Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+"  "+budile4.getName());
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile4.getRoomId()==b3){//房间
                        //跟楼层和房间都相等
                        b4=budile4.getId();
                        addV4(b4,b3);
                        break;
                    }
                }

                break;
            case "4":

                ///////找到床第一个id的下属也就是楼层的第一个值
                for (Budile4 budile4 : budile1List4) {//存放所有楼层
                  //  Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+"  "+budile4.getName());
                    //这里的作用是找到第一个id 默认第一个选中
                    if (budile4.getRoomId()==b3){//房间
                        //跟楼层和房间都相等
                        b4=Long.parseLong(tag[1]);
                        addV4(b4,b3);
                        break;
                    }
                }

                break;
        }
        if (view.getId()==R.id.xiayibu){
            JuZhuBean bean=new JuZhuBean();

            for (QMUIButton qmuiButton : qmuiButtonList1) {
                // Log.d("RuKeActivity1", qmuiButton.getTag().toString());
                String[] s1=qmuiButton.getTag().toString().split(",");
                if (s1[3].equals("1")){
                    bean.setL1(Long.parseLong(s1[1]));
                    bean.setL1Name(s1[2]);
                }
            }
            for (QMUIButton qmuiButton : qmuiButtonList2) {
                // Log.d("RuKeActivity2", qmuiButton.getTag().toString());
                String[] s1=qmuiButton.getTag().toString().split(",");
                if (s1[3].equals("1")){
                    bean.setL2(Long.parseLong(s1[1]));
                    bean.setL2Name(s1[2]);
                }
            }
            for (QMUIButton qmuiButton : qmuiButtonList3) {
                //  Log.d("RuKeActivity3", qmuiButton.getTag().toString());
                String[] s1=qmuiButton.getTag().toString().split(",");
                if (s1[3].equals("1")){
                    bean.setL3(Long.parseLong(s1[1]));
                    bean.setL3Name(s1[2]);
                }
            }
            for (QMUIButton qmuiButton : qmuiButtonList4) {
                // Log.d("RuKeActivity4", qmuiButton.getTag().toString());
                String[] s1=qmuiButton.getTag().toString().split(",");
                if (s1[3].equals("1")){
                    bean.setL4(Long.parseLong(s1[1]));
                    bean.setL4Name(s1[2]);
                }
            }
            if (!bean.isNill()){//有为空的
                ToastUtils.setMessage("楼层数据不完整",xiayibu);
                return;
            }

            Intent intent = new Intent(this, RuKeNextActivity.class);
            intent.putExtra("JuZhu",bean);//直接存入被序列化的对象实例
            intent.putExtra("person",resultBean);
            startActivity(intent);
        }
        if (view.getId()==R.id.fanhui){
            finish();
        }


    }



    private void link_getBuildDetail() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/build/getBuildDetail");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage() + call.request().url());
                if (!isFinishing()) {
                    ToastUtils.setMessage("网络请求失败", name);
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "入科" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    RuKeBean2 logingBe = gson.fromJson(jsonObject, RuKeBean2.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing()) {
                                for (RuKeBean2.ResultBean resultBean : logingBe.getResult()) {
                                    Budile1 budile1=new Budile1();
                                    budile1.setId(resultBean.getId());
                                    budile1.setName(resultBean.getBuildName());
                                    budile1List1.add(budile1);
                                    if (resultBean.getFloors()==null)
                                        continue;
                                    for (RuKeBean2.ResultBean.FloorsBean floorsBean : resultBean.getFloors()) {
                                        Budile2 budile2=new Budile2();
                                        budile2.setId(floorsBean.getId());
                                        budile2.setName(floorsBean.getFloorName());
                                        budile2.setBuildId(floorsBean.getBuildId());
                                        budile1List2.add(budile2);
                                        if (floorsBean.getRoomList()==null)
                                            continue;
                                        for (RuKeBean2.ResultBean.FloorsBean.RoomListBean roomListBean : floorsBean.getRoomList()) {
                                            Budile3 budile3=new Budile3();
                                            budile3.setId(roomListBean.getId());
                                            budile3.setName(roomListBean.getRoomName());
                                            budile3.setFloorId(roomListBean.getFloorId());
                                            budile3.setBuildId(roomListBean.getBuildId());
                                            budile1List3.add(budile3);
                                            if (roomListBean.getBedList()==null)
                                                continue;
                                            for (RuKeBean2.ResultBean.FloorsBean.RoomListBean.BedListBean bedListBean : roomListBean.getBedList()) {
                                                Budile4 budile4=new Budile4();
                                                budile4.setId(bedListBean.getId());
                                                budile4.setName(bedListBean.getBedName());
                                                budile4.setRoomId(bedListBean.getRoomId());
                                                budile1List4.add(budile4);
                                            }
                                        }
                                    }
                                }
//                                for (Budile4 budile4 : budile1List4) {
//                                    Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+budile4.getName());
//                                }
                                //设置
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (budile1List1.size()>0) //存放所有楼栋
                                            {   //这里的作用是找到第一个id 默认第一个选中
                                                b1=budile1List1.get(0).getId();
                                                addV1(b1);//找到楼栋第一个id
                                            } else//没找到
                                                return;
                                            ///////找到楼栋第一个id的下属也就是楼层的第一个值
                                            for (Budile2 budile2 : budile1List2) {//存放所有楼层
                                                //这里的作用是找到第一个id 默认第一个选中
                                                if (budile2.getBuildId()==b1){
                                                    b2=budile2.getId();
                                                    addV2(budile2.getId(),b1);
                                                    break;
                                                }
                                            }
                                            if (b2==-1)//没找到
                                                return;
                                            ///////找到房间第一个id的下属也就是楼层的第一个值
                                            for (Budile3 budile3 : budile1List3) {//存放所有楼层
                                                //这里的作用是找到第一个id 默认第一个选中
                                                if (budile3.getBuildId()==b1 && budile3.getFloorId()==b2){//房间
                                                    //跟楼层和房间都相等
                                                    b3=budile3.getId();
                                                    addV3(budile3.getId(),b1,b2);
                                                    break;
                                                }
                                            }
                                            if (b3==-1)//没找到
                                                return;
                                            ///////找到床第一个id的下属也就是楼层的第一个值
                                            for (Budile4 budile4 : budile1List4) {//存放所有楼层
                                              //  Log.d("RuKeActivity", "budile4.getId():" + budile4.getId()+"  "+budile4.getName());
                                                //这里的作用是找到第一个id 默认第一个选中
                                                if (budile4.getRoomId()==b3){//房间
                                                    //跟楼层和房间都相等
                                                    addV4(budile4.getId(),b3);
                                                    break;
                                                }
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), name);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), name);
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }

    private void addV1(long id){
        qmfl1.removeAllViews();
        qmuiButtonList1.clear();
        for (Budile1 budile1 : budile1List1) {
            if (budile1.getId()==id){
                QMUIButton button = new QMUIButton(RuKeActivity.this);
                qmfl1.addView(button);
                QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                button.setLayoutParams(params);
                button.setOnClickListener(RuKeActivity.this);
                button.setTag("1,"+budile1.getId()+","+budile1.getName()+",1");//组/id/名称/是否选中,1选中 0 未选中
                button.setTextColor(Color.parseColor("#ffffff"));
                button.setBackgroundColor(Color.parseColor("#ff59B683"));
                button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                button.setChangeAlphaWhenPress(true);//点击改变透明度
                button.setTextSize(12);
                button.setText(budile1.getName());
                button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                qmuiButtonList1.add(button);
            }else {
                QMUIButton button = new QMUIButton(RuKeActivity.this);
                qmfl1.addView(button);
                QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                button.setLayoutParams(params);
                button.setOnClickListener(RuKeActivity.this);
                button.setTag("1,"+budile1.getId()+","+budile1.getName()+",0");
                button.setTextColor(Color.parseColor("#FFB6B6B6"));
                button.setBackground(null);
                button.setBorderColor(Color.parseColor("#FFB6B6B6"));
                button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                button.setChangeAlphaWhenPress(true);//点击改变透明度
                button.setTextSize(12);
                button.setText(budile1.getName());
                button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                qmuiButtonList1.add(button);
            }
        }
    }

    private void addV2(long id,long b1){
        //id 是需要选中的  b1是跟父id相等的
        qmfl2.removeAllViews();
        qmuiButtonList2.clear();
        for (Budile2 budile2 : budile1List2) {
            if (budile2.getBuildId()==b1){
                if (budile2.getId()==id){//传进来的是自己的id不是父id
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl2.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("2,"+budile2.getId()+","+budile2.getName()+",1");//组/id/名称/是否选中,1选中 0 未选中
                    button.setTextColor(Color.parseColor("#ffffff"));
                    button.setBackgroundColor(Color.parseColor("#ff59B683"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile2.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList2.add(button);
                }else {
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl2.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("2,"+budile2.getId()+","+budile2.getName()+",0");
                    button.setTextColor(Color.parseColor("#FFB6B6B6"));
                    button.setBackground(null);
                    button.setBorderColor(Color.parseColor("#FFB6B6B6"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile2.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList2.add(button);
                }
            }
        }
    }

    private void addV3(long id,long b1,long b2){
        //id 是需要选中的  b1是跟父id相等的
        qmfl3.removeAllViews();
        qmuiButtonList3.clear();
        for (Budile3 budile3 : budile1List3) {
            if (budile3.getBuildId()==b1 && budile3.getFloorId()==b2){
                if (budile3.getId()==id){//传进来的是自己的id不是父id
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl3.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("3,"+budile3.getId()+","+budile3.getName()+",1");//组/id/名称/是否选中,1选中 0 未选中
                    button.setTextColor(Color.parseColor("#ffffff"));
                    button.setBackgroundColor(Color.parseColor("#ff59B683"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile3.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList3.add(button);
                }else {
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl3.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("3,"+budile3.getId()+","+budile3.getName()+",0");
                    button.setTextColor(Color.parseColor("#FFB6B6B6"));
                    button.setBackground(null);
                    button.setBorderColor(Color.parseColor("#FFB6B6B6"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile3.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList3.add(button);
                }
            }
        }
    }

    private void addV4(long id,long b3){
        //id 是需要选中的  b1是跟父id相等的
        qmfl4.removeAllViews();
        qmuiButtonList4.clear();
        for (Budile4 budile4 : budile1List4) {
            if (budile4.getRoomId()==b3){
                if (budile4.getId()==id){//传进来的是自己的id不是父id
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl4.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("4,"+budile4.getId()+","+budile4.getName()+",1");//组/id/名称/是否选中,1选中 0 未选中
                    button.setTextColor(Color.parseColor("#ffffff"));
                    button.setBackgroundColor(Color.parseColor("#ff59B683"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile4.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList4.add(button);
                }else {
                    QMUIButton button = new QMUIButton(RuKeActivity.this);
                    qmfl4.addView(button);
                    QMUIFloatLayout.LayoutParams params = button.getLayoutParams();
                    params.width = QMUIDisplayHelper.dp2px(RuKeActivity.this, 76);
                    params.height = QMUIDisplayHelper.dp2px(RuKeActivity.this, 30);
                    button.setLayoutParams(params);
                    button.setOnClickListener(RuKeActivity.this);
                    button.setTag("4,"+budile4.getId()+","+budile4.getName()+",0");
                    button.setTextColor(Color.parseColor("#FFB6B6B6"));
                    button.setBackground(null);
                    button.setBorderColor(Color.parseColor("#FFB6B6B6"));
                    button.setRadius(QMUIDisplayHelper.dp2px(RuKeActivity.this, 15));
                    button.setChangeAlphaWhenPress(true);//点击改变透明度
                    button.setTextSize(12);
                    button.setText(budile4.getName());
                    button.setPadding(1, 1, 1, 1);// 让数字全部显示出来
                    qmuiButtonList4.add(button);
                }
            }
        }
    }

}
