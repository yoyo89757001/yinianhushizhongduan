package com.example.yiliaoyinian.ui.fragments1.huanzhe;

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
import com.example.yiliaoyinian.Beans.HuanZheBean;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.ZhiXingJiLuMuAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DAFragment4 extends Fragment {

    private RecyclerView recyclerView;
    private ZhiXingJiLuMuAdapter adapter;
    private List<HuanZheBean.ResultBean.LogListBean> taskBeanList=new ArrayList<>();

    public DAFragment4() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(HuanZheBean.ResultBean msgWarp){
        taskBeanList.clear();
        taskBeanList.addAll(msgWarp.getLogList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_d_a4, container, false);

        EventBus.getDefault().register(this);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        adapter=new ZhiXingJiLuMuAdapter(R.layout.kuaidi_item2,taskBeanList);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        // 设置加载更多监听事件
//        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                //加载更多
//                Log.d("Fragment1_1", "加载更多8");
//                // 当前这次数据加载完毕，调用此方法
//                //  mAdapter.getLoadMoreModule().loadMoreComplete();
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//
//            }
//        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);

        super.onDestroyView();

    }


}
