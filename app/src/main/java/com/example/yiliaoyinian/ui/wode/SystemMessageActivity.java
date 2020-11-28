package com.example.yiliaoyinian.ui.wode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.yiliaoyinian.Beans.XiTongTongZhiBean;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.XiTongTongZhiAdapter;
import com.example.yiliaoyinian.ui.BaseActivity;
import com.example.yiliaoyinian.utils.Consts;
import com.example.yiliaoyinian.utils.DialogManager;
import com.example.yiliaoyinian.utils.GsonUtil;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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



public class SystemMessageActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private XiTongTongZhiAdapter adapter;
    private List<XiTongTongZhiBean.ResultBean.DataBean> xiTongTongZhiBeanList = new ArrayList<>();
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new XiTongTongZhiAdapter(R.layout.tongzhi_xitong_item, xiTongTongZhiBeanList);
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
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
                Log.d("Fragment1_1", "加载更多1");
                // 当前这次数据加载完毕，调用此方法
                //  adapter.getLoadMoreModule().loadMoreComplete();
                //adapter.getLoadMoreModule().loadMoreEnd(true);//隐藏没有更多数据
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
                startActivity(new Intent(SystemMessageActivity.this, MessageInfoActivity.class).putExtra("idid",xiTongTongZhiBeanList.get(position).getId()));
            }
        });

        link_complete(page);

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
                .url(Consts.URL + "/api/sysMessage/getListByPage");
        // step 3：创建 Call 对象
        Call call = MyApplication.okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (!isFinishing()){
                    ToastUtils.setMessage("网络请求失败", recyclerView);
                }
                adapter.getLoadMoreModule().loadMoreComplete();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LoginActivity", "系统通知"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    XiTongTongZhiBean logingBe = gson.fromJson(jsonObject, XiTongTongZhiBean.class);
                    if (logingBe.isSuccess()) {
                        if (logingBe.getCode() == 1) {
                            if (logingBe.getResult()!=null)
                            if (!isFinishing())
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (logingBe.getResult().getData()!=null && logingBe.getResult().getData().size()>0){
                                            for (XiTongTongZhiBean.ResultBean.DataBean dataBean : logingBe.getResult().getData()) {
                                                xiTongTongZhiBeanList.add(dataBean);
                                                adapter.getLoadMoreModule().loadMoreComplete();
                                            }
                                        }else {
                                            adapter.getLoadMoreModule().loadMoreComplete();
                                            adapter.getLoadMoreModule().loadMoreEnd(true);
                                            ToastUtils.setMessage("没有更多消息了", recyclerView);
                                        }

                                    }
                                });
                        } else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    } else {
                        if (logingBe.getCode() == 102) {
                            //token过期
                            if (!isFinishing())
                                DialogManager.getAppManager().showToken();
                        }else {
                            if (!isFinishing())
                                ToastUtils.setMessage(jsonObject.get("errorMsg").getAsString(), recyclerView);
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异hhh常");
                }
            }
        });
    }
}
