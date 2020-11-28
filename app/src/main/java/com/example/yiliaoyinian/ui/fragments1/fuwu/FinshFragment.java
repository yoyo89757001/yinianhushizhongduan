package com.example.yiliaoyinian.ui.fragments1.fuwu;

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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.UnFinshBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.FinshAdapter;
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
public class FinshFragment extends Fragment {
    private RecyclerView recyclerView;
    private FinshAdapter adapter;
    private List<UnFinshBean.ResultBean> taskBeanList=new ArrayList<>();
    private SmartRefreshLayout refreshLayout;

    public FinshFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fuwu_fragment_task, container, false);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        EventBus.getDefault().register(this);
        recyclerView=view.findViewById(R.id.recyclerview);
        adapter=new FinshAdapter(R.layout.finsh_fragment1,taskBeanList);
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
                link_getPendService();
            }
        });
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多19");
//                // 当前这次数据加载完毕，调用此方法
//                //  mAdapter.getLoadMoreModule().loadMoreComplete();
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//            }
//        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);
                Intent intent=new Intent(getActivity(),TaskInfoActivity.class);
                intent.putExtra("id",taskBeanList.get(position).getId());
                intent.putExtra("type",2);//已完成
                intent.putExtra("title",taskBeanList.get(position).getItemName());
                intent.putExtra("name",taskBeanList.get(position).getPatientName());//未完成
                startActivity(intent);
            }
        });
        link_getPendService();

        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(String msgWarp){
        if (msgWarp.equals("UnFinshFragment")){
            link_getPendService();
        }
    }



    private Call call;
    private void link_getPendService() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL+"/api/nurseServiceLog/getList");
        // step 3：创建 Call 对象
        call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
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
                String ss=null;
                try {
                    ResponseBody body = response.body();
                    ss = body.string().trim();
                    Log.d("LoginActivity", ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    UnFinshBean logingBe = gson.fromJson(jsonObject, UnFinshBean.class);
                    if (logingBe.isSuccess()){
                        if (logingBe.getCode()==1 && logingBe.getResult()!=null){
                            if (getActivity()!=null){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            taskBeanList.clear();
                                            taskBeanList.addAll(logingBe.getResult());
                                            adapter.notifyDataSetChanged();
                                          //  adapter.getLoadMoreModule().loadMoreComplete();
                                          //  adapter.getLoadMoreModule().loadMoreEnd(true);
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
                            //token过期
                            DialogManager.getAppManager().showToken();
                        }else {
                            if (getActivity()!=null)
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(),recyclerView);
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
