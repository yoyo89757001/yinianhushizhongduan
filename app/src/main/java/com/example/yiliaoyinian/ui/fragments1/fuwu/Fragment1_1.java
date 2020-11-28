package com.example.yiliaoyinian.ui.fragments1.fuwu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.yiliaoyinian.R;

import com.example.yiliaoyinian.views.FuWuFragmentPagerAdapter;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1_1 extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    QMUIButton addserver;
    ViewPager viewpage;

    TextView t1;
    View v1;
    TextView t2;
    View v2;


    public Fragment1_1() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1_1, container, false);
        viewpage=view.findViewById(R.id.viewpage_fuwu);
        addserver=view.findViewById(R.id.addserver);
        t1=view.findViewById(R.id.t1);
        v1=view.findViewById(R.id.v1);
        t2=view.findViewById(R.id.t2);
        v2=view.findViewById(R.id.v2);
        // @OnClick({R.id.tl1, R.id.tl2,R.id.addserver})
        view.findViewById(R.id.tl1).setOnClickListener(this);
        view.findViewById(R.id.tl2).setOnClickListener(this);
        view.findViewById(R.id.addserver).setOnClickListener(this);


        if (getActivity() != null) {
            FuWuFragmentPagerAdapter myFragmentPagerAdapter = new FuWuFragmentPagerAdapter(getChildFragmentManager());
            viewpage.setAdapter(myFragmentPagerAdapter);
            //设置当前页的ID
            viewpage.setCurrentItem(0);
            //添加翻页监听事件
            viewpage.addOnPageChangeListener(this);
            viewpage.setOffscreenPageLimit(2);
        }
        addserver.setBorderColor(Color.parseColor("#59B683"));
        addserver.setRadius(QMUIDisplayHelper.dp2px(getActivity(), 13));
        addserver.setChangeAlphaWhenPress(true);//点击改变透明度

        return view;
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        Log.d("Fragment1", "state:" + state);
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    t1.setTextColor(Color.parseColor("#FF59B683"));
                    v1.setVisibility(View.VISIBLE);
                    break;
                }
                case PAGE_TWO: {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tl1:
                resetSelected();
                t1.setTextColor(Color.parseColor("#FF59B683"));
                v1.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_ONE);

                break;
            case R.id.tl2:
                resetSelected();
                t2.setTextColor(Color.parseColor("#FF59B683"));
                v2.setVisibility(View.VISIBLE);
                viewpage.setCurrentItem(PAGE_TWO);

                break;
            case R.id.addserver:
                startActivity(new Intent(getActivity(),AddServerActivity.class));
                break;
        }
    }
}
