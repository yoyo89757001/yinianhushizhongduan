package com.example.yiliaoyinian.ui.xunfang;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;

import com.example.yiliaoyinian.R;

import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.views.XunFangFragmentPagerAdapter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;





public class XunFangJiLuActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnDateSetListener, View.OnClickListener {

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    ViewPager viewpage;
    TextView t1;
    View v1;
    TextView t2;
    View v2;
    TextView leftTv;//月份文字切换
    TextView rightTv;//右边签到次数及巡房次数说明
    LinearLayout leftLl;
  //  private List<ServiceTypeBean> xiangmuList = new ArrayList<>();
   // private PopHeadAdapter popHeadAdapterLX = null;
    //private QMUIPopup popup = null;
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xun_fang_ji_lu);
        //@OnClick({R.id.tl1, R.id.tl2, R.id.left_ll,R.id.fanhui})
        findViewById(R.id.tl1).setOnClickListener(this);
        findViewById(R.id.tl2).setOnClickListener(this);
        findViewById(R.id.left_ll).setOnClickListener(this);
        findViewById(R.id.fanhui).setOnClickListener(this);
        viewpage=findViewById(R.id.viewpage);
        t1=findViewById(R.id.t1);
        v1=findViewById(R.id.v1);
        t2=findViewById(R.id.t2);
        v2=findViewById(R.id.v2);
        leftTv=findViewById(R.id.left_tv);
        rightTv=findViewById(R.id.right_tv);
        leftLl=findViewById(R.id.left_ll);


        EventBus.getDefault().register(this);

        XunFangFragmentPagerAdapter myFragmentPagerAdapter = new XunFangFragmentPagerAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(2);
        leftTv.setText(DateUtils.time2(System.currentTimeMillis()+""));
        //popHeadAdapterLX = new PopHeadAdapter(xiangmuList, XunFangJiLuActivity.this);
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position=position;
        EventBus.getDefault().post(position+","+"sa");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        Log.d("Fragment1", "state:" + state);
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    EventBus.getDefault().post("1");
                    resetSelected();
                    t1.setTextColor(Color.parseColor("#FF59B683"));
                    v1.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_TWO: {
                    EventBus.getDefault().post("2");
                    resetSelected();
                    t2.setTextColor(Color.parseColor("#FF59B683"));
                    v2.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    private void resetSelected() {
        t1.setTextColor(Color.parseColor("#FFC1C1C2"));
        v1.setVisibility(View.GONE);
        t2.setTextColor(Color.parseColor("#FFC1C1C2"));
        v2.setVisibility(View.GONE);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        String[] msg=msgWarp.split(",");
        if (msg.length==3){
            if (msg[0].equals("0")){
               //第一页的数据
                leftTv.setText(msg[2]);
                rightTv.setText("总签到"+msg[1]+"次");
            }else {
                leftTv.setText(msg[2]);
                rightTv.setText("总巡房"+msg[1]+"次");
            }
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }



    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (timePickerView.getTag() == null)
            return;
        if (timePickerView.getTag().equals("ff2")) {
            leftTv.setText(DateUtils.time2(millseconds+""));
            Log.d("XunFangJiLuActivity", "millseconds:" + millseconds);
        }
        timePickerView.dismiss();
        EventBus.getDefault().post(position+","+DateUtils.time2(millseconds+""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tl1:
                EventBus.getDefault().post("1");
                resetSelected();
                t1.setTextColor(Color.parseColor("#FF59B683"));
                v1.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_ONE);

                break;
            case R.id.tl2:
                EventBus.getDefault().post("2");
                resetSelected();
                t2.setTextColor(Color.parseColor("#FF59B683"));
                v2.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_TWO);

                break;
            case R.id.left_ll://左边月份切换按钮

                TimePickerDialog mDialogAll2 = new TimePickerDialog.Builder()
                        .setCallBack(this)
                        .setCancelStringId("取消")
                        .setSureStringId("确定")
                        .setTitleStringId("选择时间")
                        .setYearText("年")
                        .setMonthText("月")
                        .setDayText("日")
                        .setHourText("时")
                        .setMinuteText("分")
                        .setCyclic(false)
                        .setMinMillseconds(DateUtils.dataOne("2018-1-1"))
                        .setMaxMillseconds(System.currentTimeMillis())
                        .setCurrentMillseconds(System.currentTimeMillis())
                        .setThemeColor(Color.parseColor("#FF59B683"))
                        .setType(Type.YEAR_MONTH)
                        .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                        .setWheelItemTextSelectorColor(Color.parseColor("#FF59B683"))
                        .setWheelItemTextSize(16)
                        .build();
                mDialogAll2.show(getSupportFragmentManager(), "ff2");

                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
