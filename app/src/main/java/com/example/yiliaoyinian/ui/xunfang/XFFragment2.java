package com.example.yiliaoyinian.ui.xunfang;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.XunFangBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.XunFangAdapter;
import com.example.yiliaoyinian.utils.AppManager;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DateUtils;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

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
public class XFFragment2 extends Fragment {
    private RecyclerView recyclerView;
    private XunFangAdapter adapter;
    private List<XunFangBean.ResultBean.PatrolHouseListBean> signInBeanList=new ArrayList<>();
    private String time=null;
    private int qiandao=0;
    private SmartRefreshLayout refreshLayout;

    public XFFragment2() {
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        String[] msg=msgWarp.split(",");
        if (msg.length==2){
            if (msg[0].equals("1")){
                //请求数据。
                if (msg[1].equals("sa")){//滑动
                    EventBus.getDefault().post("1,"+qiandao+","+time);
                }else {//更新月份
                    time=msg[1];
                    link_getPendService(time);
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_x_f2, container, false);
        EventBus.getDefault().register(this);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        time= DateUtils.time2(System.currentTimeMillis()+"");
        recyclerView=view.findViewById(R.id.recyclerview);
        adapter=new XunFangAdapter(R.layout.sign_in_item2,signInBeanList);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                link_getPendService(time);
            }
        });
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多10");
//                // 当前这次数据加载完毕，调用此方法
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//            }
//        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);

            }
        });

        link_getPendService(time);
        return view;
    }

    private Call call;
    private void link_getPendService(String time) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/patrol/getByMonth?month="+time);
        // step 3：创建 Call 对象
        call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage()+call.request().url());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败",recyclerView);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefresh();
                        }
                    });

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
                    XunFangBean logingBe = gson.fromJson(jsonObject, XunFangBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){

                            if (getActivity()!=null){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            qiandao=logingBe.getResult().getTotalNum();
                                            signInBeanList.clear();
                                            signInBeanList.addAll(logingBe.getResult().getPatrolHouseList());
                                            adapter.notifyDataSetChanged();

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),recyclerView);
                        }
                    }else {
                        if (logingBe.getCode()==102){
                            Log.d("UnFinshFragment", "进来");
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),recyclerView);
                        }

                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    AppManager.getAppManager().currentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            QMUITipDialog qmuiTipDialog2 = new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                                    .setTipWord("后台数据异常")
                                    .create();
                            qmuiTipDialog2.show();
                            recyclerView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    qmuiTipDialog2.dismiss();
                                }
                            }, 2500);
                        }
                    });

                }finally {
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.finishRefresh();
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
