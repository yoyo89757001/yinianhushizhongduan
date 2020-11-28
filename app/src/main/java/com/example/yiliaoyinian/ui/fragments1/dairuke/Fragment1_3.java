package com.example.yiliaoyinian.ui.fragments1.dairuke;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.yiliaoyinian.Beans.RuKeBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.RuKeAdapter;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
public class Fragment1_3 extends Fragment {

    SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RuKeAdapter adapter;
    private List<RuKeBean.ResultBean> taskBeanList = new ArrayList<>();


    public Fragment1_3() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1_3, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        EventBus.getDefault().register(this);
        adapter = new RuKeAdapter(R.layout.zhiban_fragment1, taskBeanList);

        // 获取模块
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                link_getNoIn();
            }
        });
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.anull_data, null);
        adapter.setEmptyView(view1);
       // View view11 = LayoutInflater.from(getActivity()).inflate(R.layout.anull_data, null);
        //adapter.setEmptyView(view11);
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多3");
//                // 当前这次数据加载完毕，调用此方法
//                //  mAdapter.getLoadMoreModule().loadMoreComplete();
//                // 当前这次数据加载错误，调用此方法
//                //   adapter.getLoadMoreModule().loadMoreEnd(true);
//            }
//        });
        // 先注册需要点击的子控件id（注意，请不要写在convert方法里）
        adapter.addChildClickViewIds(R.id.ruke);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.ruke) {
                    Log.d("DAFragment3", "position:" + position);
                    Intent intent = new Intent(getActivity(), RuKeActivity.class);
                    intent.putExtra("person",taskBeanList.get(position));//直接存入被序列化的对象实例
                    startActivity(intent);
                }
            }
        });

        link_getNoIn();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("Fragment1_3")){
            refreshLayout.autoRefresh();
           // link_getNoIn();
        }
    }

    private void link_getNoIn() {//获取未入科患者列表
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/nursePatient/getNoIn");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage() + call.request().url());
                if (getActivity() != null) {
                    ToastUtils.setMessage("网络请求失败", recyclerView);
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
                    Log.d("LoginActivity", "入科" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    RuKeBean logingBe = gson.fromJson(jsonObject, RuKeBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            taskBeanList.clear();
                                            taskBeanList.addAll(logingBe.getResult());
                                            adapter.getLoadMoreModule().loadMoreComplete();
                                            adapter.getLoadMoreModule().loadMoreEnd(true);
                                            adapter.notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            DialogManager.getAppManager().showToken();
                        } else {
                            if (getActivity() != null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

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
