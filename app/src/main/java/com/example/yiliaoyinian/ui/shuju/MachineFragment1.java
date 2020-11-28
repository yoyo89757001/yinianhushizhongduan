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
import com.example.yiliaoyinian.Beans.LuMIDevBean;
import com.example.yiliaoyinian.Beans.MachineBean;
import com.example.yiliaoyinian.Beans.WGInfoSave;
import com.example.yiliaoyinian.Beans.WGInfoSave_;
import com.example.yiliaoyinian.MyApplication;
import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.adapter.MachineAdapter;
import com.example.yiliaoyinian.ui.lumi.wangguan.WGInfosListActivity;
import com.example.yiliaoyinian.ui.lumi.wangguan.WGSettingActivity;
import com.example.yiliaoyinian.ui.shuju.bed.DeviceConfigBedActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigBle2Activity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigBleActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigCameraActivity;
import com.example.yiliaoyinian.ui.shuju.camera.DeviceConfigSleepActivity;
import com.example.yiliaoyinian.ui.shuju.camera.EZRealPlayActivity;
import com.example.yiliaoyinian.ui.shuju.bed.EZRealPlayToBedActivity;
import com.example.yiliaoyinian.ui.shuju.leida.ShenWuLeiDaActivity;
import com.example.yiliaoyinian.utils.EZUtils;
import com.example.yiliaoyinian.utils.ToastUtils;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZDeviceInfo;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import java.util.Vector;




/**
 * A simple {@link Fragment} subclass.
 */
public class MachineFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private MachineAdapter adapter;
    private Vector<MachineBean.ResultBean> taskBeanList = new Vector<>();
    private Vector<EZDeviceInfo>  deviceInfoList=new Vector<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sswxMSGsddddfff11(List<MachineBean.ResultBean> msgWarp){
        try {
            taskBeanList.clear();
            if (msgWarp!=null && msgWarp.size()>0 && msgWarp.get(0).getType()==1001){
                taskBeanList.addAll(msgWarp);
                adapter.notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<EZDeviceInfo> list= null;
                        try {
                            try {
                                list = EZOpenSDK.getInstance().getDeviceList(0,50);
                            } catch (BaseException e) {
                                e.printStackTrace();
                            }
                            if (list.size()>0){
                                deviceInfoList.clear();
                            }
                            for (EZDeviceInfo ezDeviceInfo : list) {
                                for (MachineBean.ResultBean resultBean : msgWarp) {
                                    if (resultBean.getDeviceType().equals("DEVICE_CAMERA")){
                                        if (ezDeviceInfo.getDeviceSerial().equals(resultBean.getDeviceMac())){
                                            deviceInfoList.add(ezDeviceInfo);
                                        }
                                    }
                                }
                            }
                        } catch (NullPointerException e ) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sswxMSGsdf11(LuMIDevBean luMIDevBean){
        for (LuMIDevBean.DataDTO datum : luMIDevBean.getData()) {
            MachineBean.ResultBean resultBean=new MachineBean.ResultBean();
            resultBean.setDid(datum.getDid());//唯一id
            resultBean.setDeviceName(datum.getName());
            resultBean.setDeviceModel(datum.getModel()+"");//
            resultBean.setDeviceType(datum.getModelType()+"");//设备类型 1 网关 3 子设备
            taskBeanList.add(resultBean);
            final LuMIDevBean.DataDTO datumdd=datum;
            new Thread(new Runnable() {
               final private LuMIDevBean.DataDTO dat=datumdd;
                @Override
                public void run() {
                    WGInfoSave save= MyApplication.myApplication.getWgInfoSaveBox().query().equal(WGInfoSave_.did,dat.getDid()).build().findFirst();
                    if (save!=null){
                        save.setName(dat.getName());
                        save.setWeizhi(dat.getPlace());
                        save.setPhoto(dat.getStr1());
                        MyApplication.myApplication.getWgInfoSaveBox().put(save);
                    }else {
                        WGInfoSave infoSave=new WGInfoSave(dat.getModelType(),dat.getParentDid(),dat.getStatus(),dat.getDid(),dat.getModel(),
                                dat.getFirmwareVersion(),dat.getName(),true,"ffffff",50,60,"","","","",dat.getPlace());
                        infoSave.setPhoto(dat.getStr1());
                        MyApplication.myApplication.getWgInfoSaveBox().put(infoSave);
                    }
                }
            }).start();
        }
        adapter.notifyDataSetChanged();
    }

    public MachineFragment1() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine1, container, false);
        EventBus.getDefault().register(this);
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MachineAdapter(R.layout.machine_data_item, taskBeanList,1);
        // 获取模块
        // adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setAutoLoadMore(false);//第一次不调用加载更多方法
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);//加载完成不满一屏自动加载
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        View view1= LayoutInflater.from(getActivity()).inflate(R.layout.anull_data,null);
        adapter.setEmptyView(view1);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Log.d("Fragment1_1", "position:" + position);
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
                        if (deviceInfoList.size()<=0){
                            ToastUtils.setSuccess("获取摄像头信息失败,请重试",recyclerView);
                            EventBus.getDefault().post("ref_device_list");
                            return;
                        }
                        for (EZDeviceInfo ezDeviceInfo : deviceInfoList) {
                            if (ezDeviceInfo.getDeviceSerial().equals(taskBeanList.get(position).getDeviceMac())){
                                startActivity(new Intent(getActivity(), EZRealPlayActivity.class)
                                        .putExtra(IntentConsts.EXTRA_DEVICE_INFO,ezDeviceInfo)
                                        .putExtra("getValidateCode",taskBeanList.get(position).getValidateCode())
                                        .putExtra("titlename",taskBeanList.get(position).getDeviceName())
                                        .putExtra(IntentConsts.EXTRA_CAMERA_INFO, EZUtils.getCameraInfoFromDevice(ezDeviceInfo, 0)));
                                break;
                            }
                        }
                        break;
                    case "DEVICE_RADAR"://睡眠监测
                        startActivity(new Intent(getActivity(), ShenWuLeiDaActivity.class)
                                .putExtra("deviceCode",taskBeanList.get(position).getDeviceCode()));
                        break;
                    case "DEVICE_BED"://床机
                        startActivity(new Intent(getActivity(), EZRealPlayToBedActivity.class)
                                .putExtra("deviceCode",taskBeanList.get(position).getDeviceCode())
                                .putExtra("deviceMacJZY",taskBeanList.get(position).getDeviceMac()));
                        break;
                    case "1"://网关
                        Intent intent5 = new Intent(getActivity(), WGSettingActivity.class);
                        intent5.putExtra("did",taskBeanList.get(position).getDid());
                        startActivity(intent5);
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
                        case "DEVICE_BED"://床机
                            Intent intent4 = new Intent(getActivity(), DeviceConfigBedActivity.class);
                            intent4.putExtra("person1",taskBeanList.get(position));//直接存入被序列化的对象实例
                            startActivity(intent4);
                            break;
                        case "1"://网关
                            Intent intent5 = new Intent(getActivity(), WGInfosListActivity.class);
                            intent5.putExtra("did",taskBeanList.get(position).getDid());
                            startActivity(intent5);
                            break;
                        default:{
                            Intent intent0 = new Intent(getActivity(), WGSettingActivity.class);
                            intent0.putExtra("did","lumi1.54ef44ca1f79");//直接存入被序列化的对象实例
                            startActivity(intent0);
                        }
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
