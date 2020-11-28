package com.example.yiliaoyinian.ui.fragmengmain;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.yiliaoyinian.Beans.SaveInfoBean;
import com.example.yiliaoyinian.Beans.UnMessageBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;
import com.example.yiliaoyinian.ui.LoginActivity;
import com.example.yiliaoyinian.ui.wode.MessageInfoActivity;
import com.example.yiliaoyinian.ui.xunfang.XunFangJiLuActivity;
import com.example.yiliaoyinian.ui.zhiban.ZhiBanActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.example.yiliaoyinian.views.MyFragmentPagerAdapter1_1;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    TextView name;
    QMUIRadiusImageView head;
    TextView fuwu;
    TextView zhangzhe;
    TextView zhiban;
    ViewPager viewpage;
    ConstraintLayout jjk;

    private QMUIPopup mPopupWindow = null;
    LinearLayout topbutton;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    private List<UnMessageBean.ResultBean> unMessageBeanList = new ArrayList<>();
    private SimpleMarqueeView tv_marquee;

    public Fragment1() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp) {
        if (msgWarp.equals("MessageInfoActivity")) {
            link_getUnread();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // @OnClick({R.id.l1, R.id.l2, R.id.l3, R.id.topbutton, R.id.jjk})
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        viewpage=view.findViewById(R.id.viewpage);
        head=view.findViewById(R.id.head);
        zhangzhe=view.findViewById(R.id.zhangzhe);
        fuwu=view.findViewById(R.id.fuwu);
        zhiban=view.findViewById(R.id.zhiban);
        name=view.findViewById(R.id.name);
        jjk=view.findViewById(R.id.jjk);
        topbutton=view.findViewById(R.id.topbutton);
        view.findViewById(R.id.l1).setOnClickListener(this);
        view.findViewById(R.id.l2).setOnClickListener(this);
        view.findViewById(R.id.l3).setOnClickListener(this);
        view.findViewById(R.id.topbutton).setOnClickListener(this);
        view.findViewById(R.id.jjk).setOnClickListener(this);


        EventBus.getDefault().register(this);
        tv_marquee = view.findViewById(R.id.tv_marquee);

        tv_marquee.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                Log.d("Fragment1", "mView" + mView.getText().toString());
                Log.d("Fragment1", "mData" + mData);
                Log.d("Fragment1", "mPosition:" + mPosition);
                /**
                 * 注意：
                 * 当MarqueeView有子View时，mView：当前显示的子View，mData：mView所填充的数据，mPosition：mView的索引
                 * 当MarqueeView无子View时，mView：null，mData：null，mPosition：－1
                 */
                startActivity(new Intent(getActivity(), MessageInfoActivity.class).putExtra("idid", unMessageBeanList.get(mPosition).getId()));

            }
        });
        if (getActivity() != null) {
            MyFragmentPagerAdapter1_1 myFragmentPagerAdapter = new MyFragmentPagerAdapter1_1(getChildFragmentManager());
            viewpage.setAdapter(myFragmentPagerAdapter);
            //设置当前页的ID
            viewpage.setCurrentItem(0);
            //添加翻页监听事件
            viewpage.addOnPageChangeListener(this);
            viewpage.setOffscreenPageLimit(3);
        }

        //头像
        if (getActivity() != null)
            Glide.with(getActivity())
                    .load(MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getHeadImg())
                    .error(R.drawable.defo_bg)
                    //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(head);
        name.setText(MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getNurseName());


        link_getUnread();

        TextPaint tp3 = fuwu.getPaint();
        tp3.setFakeBoldText(true);

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
                    TextPaint tp3 = fuwu.getPaint();
                    tp3.setFakeBoldText(true);
                    fuwu.setTextColor(Color.parseColor("#333333"));
                    fuwu.setTextSize(20);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    TextPaint tp3 = zhangzhe.getPaint();
                    tp3.setFakeBoldText(true);
                    zhangzhe.setTextColor(Color.parseColor("#333333"));
                    zhangzhe.setTextSize(20);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    TextPaint tp3 = zhiban.getPaint();
                    tp3.setFakeBoldText(true);
                    zhiban.setTextColor(Color.parseColor("#333333"));
                    zhiban.setTextSize(20);
                    break;
                }
            }
        }
    }

    private void resetSelected() {
        fuwu.setTextColor(Color.parseColor("#FFC1C1C2"));
        zhangzhe.setTextColor(Color.parseColor("#FFC1C1C2"));
        zhiban.setTextColor(Color.parseColor("#FFC1C1C2"));
        TextPaint tp1 = fuwu.getPaint();
        tp1.setFakeBoldText(false);
        TextPaint tp2 = zhangzhe.getPaint();
        tp2.setFakeBoldText(false);
        TextPaint tp3 = zhiban.getPaint();
        tp3.setFakeBoldText(false);
        fuwu.setTextSize(15);
        zhangzhe.setTextSize(15);
        zhiban.setTextSize(15);

    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }



    private void showMenuPop(View view2) {
        if (null == getContext())
            return;
        View view = getLayoutInflater().inflate(R.layout.pop_head_name, null, false);
        LinearLayout l1 = view.findViewById(R.id.l1);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment1", "寻房记录");
                if (getContext() != null) {
                    getContext().startActivity(new Intent(getContext(), XunFangJiLuActivity.class));
                    if (mPopupWindow != null)
                        mPopupWindow.dismiss();
                }

            }
        });
        LinearLayout l2 = view.findViewById(R.id.l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment1", "值班表");
                if (getContext() != null) {
                    getContext().startActivity(new Intent(getContext(), ZhiBanActivity.class));
                    if (mPopupWindow != null)
                        mPopupWindow.dismiss();
                }
            }
        });
        LinearLayout l3 = view.findViewById(R.id.l3);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fragment1", "退出");
                new CommomDialog(getActivity(), R.style.dialogs, "请确认是否退出?", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            if (getActivity() != null) {
                                SaveInfoBean bean = MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
                                bean.setToken(null);
                                MyApplication.myApplication.getSaveInfoBeanBox().put(bean);
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                dialog.dismiss();
                                getActivity().finish();
                            }
                        }
                    }
                }).setTitle("确认").setPositiveButton("确定").show();
            }
        });
        if (mPopupWindow == null) {
            mPopupWindow = QMUIPopups.popup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 150), QMUIDisplayHelper.dp2px(getContext(), 132))
                    .view(view)
                    .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 16))
                    .shadow(true)
                    .arrow(true)
                    .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);

        }
        mPopupWindow.show(view2);
    }


    private void link_getUnread() {//获取未读消息
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/sysMessage/getUnread");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage() + call.request().url());
                if (getActivity() != null) {
                    ToastUtils.setMessage("网络请求失败", zhiban);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "未读消息" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    UnMessageBean logingBe = gson.fromJson(jsonObject, UnMessageBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult() != null) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            if (logingBe.getResult().size() > 0) {
                                                unMessageBeanList.clear();
                                                unMessageBeanList.addAll(logingBe.getResult());
                                                List<String> datas = new ArrayList<>();
                                                for (UnMessageBean.ResultBean bean : logingBe.getResult()) {
                                                    datas.add(bean.getTitle());
                                                }
                                                SimpleMF<String> marqueeFactory = new SimpleMF<String>(getActivity());
                                                marqueeFactory.setData(datas);
                                                tv_marquee.setMarqueeFactory(marqueeFactory);
                                                tv_marquee.startFlipping();
                                                jjk.setVisibility(View.VISIBLE);
                                            } else {
                                                List<String> datas = new ArrayList<>();
                                                SimpleMF<String> marqueeFactory = new SimpleMF<String>(getActivity());
                                                marqueeFactory.setData(datas);
                                                tv_marquee.setMarqueeFactory(marqueeFactory);
                                                tv_marquee.startFlipping();
                                                jjk.setVisibility(View.GONE);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zhiban);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            Log.d("UnFinshFragment", "进来");
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), zhiban);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topbutton:
                showMenuPop(topbutton);
                break;
            case R.id.l1:
                resetSelected();
                TextPaint tp3 = fuwu.getPaint();
                tp3.setFakeBoldText(true);
                fuwu.setTextColor(Color.parseColor("#333333"));
                fuwu.setTextSize(20);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.l2:
                resetSelected();
                TextPaint tp4 = zhangzhe.getPaint();
                tp4.setFakeBoldText(true);
                zhangzhe.setTextColor(Color.parseColor("#333333"));
                zhangzhe.setTextSize(20);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.l3:
                resetSelected();
                TextPaint tp5 = zhiban.getPaint();
                tp5.setFakeBoldText(true);
                zhiban.setTextColor(Color.parseColor("#333333"));
                zhiban.setTextSize(20);
                viewpage.setCurrentItem(PAGE_THREE);
                break;
            case R.id.jjk:

                break;
        }
    }
}
