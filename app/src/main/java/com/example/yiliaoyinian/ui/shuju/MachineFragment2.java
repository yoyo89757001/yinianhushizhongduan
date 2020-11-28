package com.example.yiliaoyinian.ui.shuju;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yiliaoyinian.Beans.MachineBean;

import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.MachineAdapter;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigBle2Activity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigBleActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigCameraActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigSleepActivity;
import com.example.yiliaoyinian.ui.shuju.leida.ShenWuLeiDaActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class MachineFragment2 extends Fragment {

    private RecyclerView recyclerView;
    private MachineAdapter adapter;
    private Vector<MachineBean.ResultBean> taskBeanList = new Vector<>();


    public MachineFragment2() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sswxMSGsddddfff(List<MachineBean.ResultBean> msgWarp){
        try {
            if (msgWarp!=null && msgWarp.size()>0 && msgWarp.get(0).getType()==1001){
                taskBeanList.clear();
                for (MachineBean.ResultBean resultBean : msgWarp) {
                    if (resultBean.getDeviceType().equals("DEVICE_BBP")||resultBean.getDeviceType().equals("DEVICE_BP")){
                        taskBeanList.add(resultBean);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine2, container, false);
        EventBus.getDefault().register(this);
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MachineAdapter(R.layout.machine_data_item, taskBeanList,2);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (taskBeanList.get(position).getDeviceType()){
                    case "DEVICE_BBP"://蓝牙血压计
                        startActivity(new Intent(getActivity(), LanYaXueYaJiActivity.class)
                                .putExtra("deviceCode",taskBeanList.get(position).getDeviceCode())
                                .putExtra("type",1)
                                .putExtra("deviceType",taskBeanList.get(position).getDeviceType()));
                        break;
                    case "DEVICE_BP"://血压计
                        startActivity(new Intent(getActivity(), LanYaXueYaJiActivity.class)
                                .putExtra("deviceCode",taskBeanList.get(position).getDeviceCode())
                                .putExtra("type",2)
                                .putExtra("deviceType",taskBeanList.get(position).getDeviceType()));
                        break;
                    case "DEVICE_CAMERA"://摄像头

                        break;
                    case "DEVICE_RADAR"://睡眠监测
                        startActivity(new Intent(getActivity(), ShenWuLeiDaActivity.class)
                                .putExtra("deviceCode",taskBeanList.get(position).getDeviceCode()));
                        break;
                }
            }
        });
        adapter.addChildClickViewIds(R.id.k11);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.k11) {
                    switch (taskBeanList.get(position).getDeviceType()){
                        case "DEVICE_BBP"://蓝牙血压计
                            Intent intent1 = new Intent(getActivity(), DeviceConfigBleActivity.class);
                            intent1.putExtra("person1",taskBeanList.get(position));//直接存入被序列化的对象实例
                            startActivity(intent1);
                            break;
                        case "DEVICE_BP"://血压计
                            Intent intent2 = new Intent(getActivity(), DeviceConfigBle2Activity.class);
                            intent2.putExtra("person1",taskBeanList.get(position));//直接存入被序列化的对象实例
                            startActivity(intent2);
                            break;
                        case "DEVICE_CAMERA"://摄像头
                            Intent intent = new Intent(getActivity(), DeviceConfigCameraActivity.class);
                            intent.putExtra("person1",taskBeanList.get(position));//直接存入被序列化的对象实例
                            startActivity(intent);
                            break;
                        case "DEVICE_RADAR"://睡眠监测
                            Intent intent3 = new Intent(getActivity(), DeviceConfigSleepActivity.class);
                            intent3.putExtra("person1",taskBeanList.get(position));//直接存入被序列化的对象实例
                            startActivity(intent3);
                            break;
                    }

                }
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
