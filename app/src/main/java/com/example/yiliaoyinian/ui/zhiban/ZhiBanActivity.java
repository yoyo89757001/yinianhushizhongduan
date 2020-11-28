package com.example.yiliaoyinian.ui.zhiban;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.ErrorBean;
import com.example.yiliaoyinian.Beans.HuGongBean;
import com.example.yiliaoyinian.Beans.PaiBanShowBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.BianQianAdapter;
import com.example.yiliaoyinian.adapter.PopHeadBlackAdapter;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ZhiBanActivity extends BaseActivity implements CalendarView.OnYearChangeListener, CalendarView.OnCalendarSelectListener, View.OnClickListener {


    CalendarView calendarView;
    TextView riqi;
    LinearLayout ll;
    QMUIRadiusImageView head;
    TextView name;
    TextView sex;
    TextView age;
    TextView ruzhishijian;
    private int year, month;
    private List<PaiBanShowBean> paiBanXiangqingListTemp = new ArrayList<>();
    private List<PaiBanShowBean> paiBanXiangqingList = new ArrayList<>();
    private PopHeadBlackAdapter paiBanXiangqingAdapter = null;
    private float x = 0, y = 0;
    //private int nian,yue,ri=-1;
    List<Integer> yueList=new ArrayList<>();
    List<String> riList=new ArrayList<>();//让他来判断是属于那个时间段的排班
    List<Integer> nianList=new ArrayList<>();

    private RecyclerView recyclerView;
    private BianQianAdapter adapter;//便签数据适配器
    private List<HuGongBean.ResultBean.NoteListBean> bianQianBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_ban);
       //  @OnClick({R.id.left_im, R.id.right_im, R.id.jiahao, R.id.fanhui})
        findViewById(R.id.left_im).setOnClickListener(this);
        calendarView=findViewById(R.id.calendarView);
        riqi=findViewById(R.id.riqi);
        ll=findViewById(R.id.ll);
        head=findViewById(R.id.head);
        name=findViewById(R.id.name);
        sex=findViewById(R.id.sex);
        age=findViewById(R.id.age);
        ruzhishijian=findViewById(R.id.ruzhishijian);


        calendarView.setOnYearChangeListener(this);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnlyCurrentMode();
        year = calendarView.getCurYear();
        month = calendarView.getCurMonth();

        riqi.setText(year + "年" + month + "月");

        paiBanXiangqingAdapter = new PopHeadBlackAdapter(paiBanXiangqingList, ZhiBanActivity.this);


        recyclerView = findViewById(R.id.recyclerview);
        adapter = new BianQianAdapter(R.layout.bianqian_item, bianQianBeanList);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1 = LayoutInflater.from(this).inflate(R.layout.anull_data, null);
        adapter.setEmptyView(view1);
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多18");
//                // 当前这次数据加载完毕，调用此方法
//                //  adapter.getLoadMoreModule().loadMoreComplete();
//                //adapter.getLoadMoreModule().loadMoreEnd(true);//隐藏没有更多数据
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//            }
//        });
        // 先注册需要点击的子控件id（注意，请不要写在convert方法里）
        adapter.addChildClickViewIds(R.id.shanchu);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.shanchu) {
                    Log.d("DAFragment3", "position:" + position);
                    new CommomDialog(ZhiBanActivity.this, R.style.dialogs, "是否删除该便签?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            Log.d("DAFragment3", "confirm:" + confirm);
                            if (confirm) {
                                //删除
                                link_delete(bianQianBeanList.get(position).getId()+"",position);
                                dialog.dismiss();
                            }
                        }
                    }).setTitle("确认").setPositiveButton("确定").show();
                }
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);

                Intent intent=new Intent(ZhiBanActivity.this, AddBianQianActivity.class);
                intent.putExtra("id",bianQianBeanList.get(position).getId());
                intent.putExtra("title",bianQianBeanList.get(position).getNoteTitle());
                intent.putExtra("conetn",bianQianBeanList.get(position).getNoteContent());
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        link_getNurseInfo();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("ZhiBanActivity", "ev.getRawX():" + ev.getX());
//        Log.d("ZhiBanActivity", "ev.getRawY():" + ev.getY());
//        Log.d("ZhiBanActivity", "ev.getRawX():" + ev.getRawX());
//        Log.d("ZhiBanActivity", "ev.getRawY():" + ev.getRawY());
        y = ev.getRawY();
        x = ev.getRawX();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onYearChange(int year) {
        Log.d("ZhiBanActivity", "year:" + year);
        this.year = year;

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //Log.d("ZhiBanActivity", "isClick:" + isClick);
       // Log.d("ZhiBanActivity", "calendar.getDay():" + calendar.getMonth());
        //Log.d("ZhiBanActivity", "calendar.getMonth():" + calendar.getMonth());
        if (isClick) {//点击事件

            try {
                boolean isA=false;boolean isB=false;boolean isC=false;
                for (Integer integer : nianList) {
                    if (calendar.getYear()==integer){
                        isA=true;
                        break;
                    }
                }
                for (Integer integer : yueList) {
                    if (calendar.getMonth() == integer) {
                        isB = true;
                        break;
                    }
                }
                long idid=-1;
                for (String integer : riList) {
                    String[] myDatas = integer.split(",");//第一个是日期，第二个是属于那个范围id
                    if (myDatas.length==2){
                        idid=Long.parseLong(myDatas[1]);
                        if (calendar.getDay()==Integer.parseInt(myDatas[0])){
                            isC=true;
                            break;
                        }
                    }
                }

                if (isA && isB && isC){
                    TextView view = new TextView(this);
                    view.setWidth(1);
                    view.setHeight(1);
                    view.setX(x);
                    view.setY(y);
                    ll.addView(view);

                    paiBanXiangqingList.clear();
                    for (PaiBanShowBean showBean : paiBanXiangqingListTemp) {
                        if (showBean.getId()==idid){
                            paiBanXiangqingList.add(showBean);
                        }
                    }
                    paiBanXiangqingAdapter.notifyDataSetChanged();
                    QMUIPopups.listPopup(this, QMUIDisplayHelper.dp2px(this, 150), QMUIDisplayHelper.dp2px(this, 120), paiBanXiangqingAdapter, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("LoginActivity", "position:" + position);
                        }
                    }).onDismiss(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            ll.removeView(view);
                        }
                    }).edgeProtection(QMUIDisplayHelper.dp2px(this, 10))
                            // .offsetX(QMUIDisplayHelper.dp2px(this, 20))
                            .offsetYIfBottom(QMUIDisplayHelper.dp2px(this, 2))
                            .shadow(true)
                            .arrow(true)
                            .bgColor(Color.parseColor("#333333"))
                            .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                            .show(view);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {//选择月份事件
            month = calendar.getMonth();
            riqi.setText(year + "年" + month + "月");

        }


    }




    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    private void link_getNurseInfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseWorker/getNurseInfo");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing())
                    ToastUtils.setMessage("网络请求失败", recyclerView);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "护工" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    HuGongBean logingBe = gson.fromJson(jsonObject, HuGongBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            name.setText(logingBe.getResult().getNurseName());
                                            if (logingBe.getResult().getGender() == 1) {
                                                sex.setText("男");
                                            }else {
                                                sex.setText("女");
                                            }
                                            age.setText(logingBe.getResult().getAge()+"岁");
                                            ruzhishijian.setText("入职时间:"+ DateUtils.time(logingBe.getResult().getEntryTime()+""));
                                            Glide.with(ZhiBanActivity.this)
                                                    .load(logingBe.getResult().getHeadImg())
                                                    //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                                    .into(head);
                                            bianQianBeanList.clear();
                                            bianQianBeanList.addAll(logingBe.getResult().getNoteList());
                                            adapter.notifyDataSetChanged();

                                            paiBanXiangqingListTemp.clear();//点击弹出的列表
                                            nianList.clear();
                                            yueList.clear();
                                            riList.clear();
                                            //排版列表很多个
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Map<String, Calendar> map = new HashMap<>();
                                                        for (HuGongBean.ResultBean.ScheduleListBean scheduleListBean : logingBe.getResult().getScheduleList()) {
                                                            //每个排版列表里面有开始时间和结束时间
                                                            List<String> allTimeList=DateUtils.getEveryday(DateUtils.time1(scheduleListBean.getStartDate()+""),DateUtils.time1(scheduleListBean.getEndDate()+""));
                                                            for (String s : allTimeList) {//每个日期
                                                                String[] dd=s.split("-");
                                                                if (dd.length==3){
                                                                    int year = Integer.parseInt(dd[0]);
                                                                    int month = Integer.parseInt(dd[1]);
                                                                    int ri = Integer.parseInt(dd[2]);
                                                                    nianList.add(year);
                                                                    yueList.add(month);
                                                                    riList.add(ri+","+scheduleListBean.getId());//加上id知道这一天属于那个大范围排班,方便后面显示排班内容
                                                                    map.put(getSchemeCalendar(year, month, ri, 0xFFF2F2F2, "").toString(),
                                                                            getSchemeCalendar(year, month, ri, 0xFFF2F2F2, ""));
                                                                }
                                                            }
                                                            //排班点击显示处理
                                                            for (HuGongBean.ResultBean.ScheduleListBean.ShiftListBean shiftListBean : scheduleListBean.getShiftList()) {
                                                                PaiBanShowBean showBean=new PaiBanShowBean();
                                                                showBean.setContent(shiftListBean.getShiftName()+":"+shiftListBean.getShiftTime());
                                                                showBean.setId(scheduleListBean.getId());
                                                                paiBanXiangqingListTemp.add(showBean);
                                                            }
                                                        }
                                                        //循环完了
                                                        //此方法在巨大的数据量上不影响遍历性能，推荐使用
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                calendarView.setSchemeDate(map);
                                                            }
                                                        });
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();


                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing()) {
                                if (!isFinishing())
                                    ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                            }
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }




    private void link_delete(String id,int p) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nurseNote/delete?id="+id);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
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
                                            ToastUtils.setMessage("删除成功", name);
                                            bianQianBeanList.remove(p);
                                            adapter.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), name);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), name);
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
            case R.id.left_im:
                calendarView.scrollToPre();//上一个月

                break;
            case R.id.right_im:
                calendarView.scrollToNext();//下一个月

                break;
            case R.id.jiahao:
                startActivity(new Intent(this, AddBianQianActivity.class));
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
