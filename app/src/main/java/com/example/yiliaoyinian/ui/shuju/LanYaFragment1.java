package com.example.yiliaoyinian.ui.shuju;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yiliaoyinian.Beans.BleAllDataListBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.LanYa1Adapter;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
public class LanYaFragment1 extends Fragment {
    private RecyclerView recyclerView;
    private LanYa1Adapter adapter;
    private List<BleAllDataListBean.ResultBean> taskBeanList=new ArrayList<>();
    private String deviceType="";
    public LanYaFragment1(String deviceType) {
        this.deviceType=deviceType;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxttyyMSGs(String msgWarp){
       if (msgWarp.equals("saveInfoUpadtaJG")){
//           SaveInfoBean saveInfoBean=MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
//           if (saveInfoBean.getPatientCode()!=null && !saveInfoBean.getPatientCode().equals(""))
               link_getBPData();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lan_ya1, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
       // recyclerView.setNestedScrollingEnabled(false);
        EventBus.getDefault().register(this);
        adapter=new LanYa1Adapter(R.layout.lanya_item_data,taskBeanList);
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
//                Log.d("Fragment1_1", "加载更多6");
//                // 当前这次数据加载完毕，调用此方法
//                //  mAdapter.getLoadMoreModule().loadMoreComplete();
//                // 当前这次数据加载错误，调用此方法
//                //   mAdapter.getLoadMoreModule().loadMoreFail();
//
//            }
//        });
//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Log.d("Fragment1_1", "position:" + position);
//            }
//        });
//        SaveInfoBean saveInfoBean=MyApplication.myApplication.getSaveInfoBeanBox().get(123456);
//        if (saveInfoBean.getPatientCode()!=null && !saveInfoBean.getPatientCode().equals(""))

        link_getBPData();



        return view;
    }

    private void link_getBPData() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("token", MyApplication.myApplication.getToken())
                .get()
                .url(Consts.URL + "/api/measureData/getUnUpload?uploadStatus=1&deviceType="+deviceType);
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.setMessage("网络请求失败", recyclerView);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "已上传测量历史数据"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    BleAllDataListBean logingBe = gson.fromJson(jsonObject, BleAllDataListBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (getActivity()!=null)
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (logingBe.getResult().size()>0){
                                                taskBeanList.clear();
                                                logingBe.getResult().get(0).setType(1002);//蓝牙测量数据类型
                                                taskBeanList.addAll(logingBe.getResult());
                                                adapter.notifyDataSetChanged();
                                                EventBus.getDefault().post(logingBe.getResult());
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
                   // if (getActivity()!=null)
                     //   ToastUtils.setMessage("后台数据异常", recyclerView);
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
