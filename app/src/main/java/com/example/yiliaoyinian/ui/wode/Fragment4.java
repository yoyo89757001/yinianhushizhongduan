package com.example.yiliaoyinian.ui.wode;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.yiliaoyinian.Beans.WenXianBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.WenXianAdapter;

import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment implements View.OnClickListener {


    QMUIRadiusImageView head;

    TextView name;

    LinearLayout ziliaoLl;

    TextView gengduo;
    private RecyclerView recyclerView;
    private WenXianAdapter adapter;
    private List<WenXianBean.ResultBean.DataBean> taskBeanList = new ArrayList<>();
    private int page=1;
    private SmartRefreshLayout refreshLayout;

    public Fragment4() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        // @OnClick({R.id.ziliao_ll, R.id.gengduo,R.id.laba,R.id.head})
        view.findViewById(R.id.ziliao_ll).setOnClickListener(this);
        view.findViewById(R.id.head).setOnClickListener(this);
        view.findViewById(R.id.laba).setOnClickListener(this);

        head = view.findViewById(R.id.head);
        name = view.findViewById(R.id.name);
        ziliaoLl = view.findViewById(R.id.ziliao_ll);
        gengduo = view.findViewById(R.id.gengduo);

        recyclerView = view.findViewById(R.id.recyclerview);
        refreshLayout=view.findViewById(R.id.refreshLayout);

        adapter = new WenXianAdapter(R.layout.wenxian_item, taskBeanList);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                page=1;
                taskBeanList.clear();
                link_complete(page);
            }
        });
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(true);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        // 设置加载更多监听事件
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
                Log.d("Fragment1_1", "加载更多9");
                // 当前这次数据加载完毕，调用此方法
                //  mAdapter.getLoadMoreModule().loadMoreComplete();
                // 当前这次数据加载错误，调用此方法
                //   mAdapter.getLoadMoreModule().loadMoreFail();
                page++;
                link_complete(page);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);
                startActivity(new Intent(getActivity(),WenZhangInfoActivity.class).putExtra("idid",taskBeanList.get(position).getId()));
            }
        });
        //头像
        if (getActivity()!=null)
        Glide.with(getActivity())
                .load(MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getHeadImg())
                .error(R.drawable.defo_bg)
                //.apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(head);
        name.setText(MyApplication.myApplication.getSaveInfoBeanBox().get(123456).getNurseName());

        link_complete(page);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    private void link_complete(int pageNum) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("pageNum", pageNum);
            object.put("pageSize", 15);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .post(body)
                .url(Consts.URL + "/api/mjArticle/getArticleList");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    ToastUtils.setMessage("网络请求失败", recyclerView);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.getLoadMoreModule().loadMoreComplete();
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
                    Log.d("LoginActivity", "文章列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    WenXianBean logingBe = gson.fromJson(jsonObject, WenXianBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1 && logingBe.getResult()!=null) {
                            if (logingBe.getResult()!=null)
                                if (getActivity()!=null)
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (logingBe.getResult().getData()!=null && logingBe.getResult().getData().size()>0){
                                                    for (WenXianBean.ResultBean.DataBean dataBean : logingBe.getResult().getData()) {
                                                        taskBeanList.add(dataBean);
                                                        adapter.getLoadMoreModule().loadMoreComplete();
                                                    }
                                                }else {
                                                    adapter.getLoadMoreModule().loadMoreComplete();
                                                    adapter.getLoadMoreModule().loadMoreEnd(true);
                                                   // ToastUtils.setMessage("没有更多消息了", recyclerView);
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        } else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (getActivity()!=null)
                                DialogManager.getAppManager().showToken();
                        }else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }finally {
                    if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefresh();
                        }
                    });

                }
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ziliao_ll:
                startActivity(new Intent(getActivity(),WoDeZiLiaoActivity.class));
                break;
            case R.id.gengduo:

                break;
            case R.id.laba:
                startActivity(new Intent(getActivity(),SystemMessageActivity.class));
                break;
            case R.id.head:
                startActivity(new Intent(getActivity(),WoDeZiLiaoActivity.class));
                break;
        }
    }
}

