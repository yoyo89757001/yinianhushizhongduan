package com.example.yiliaoyinian.ui.shuju.bed;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;


import com.example.yiliaoyinian.R;
import com.example.yiliaoyinian.dialog.CommomDialog;

import com.example.yiliaoyinian.utils.ToastUtils;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizWifiDeviceListener;

import org.greenrobot.eventbus.EventBus;


import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;



import static com.example.yiliaoyinian.utils.Utils.cmdToString;

/**
 * A simple {@link Fragment} subclass.
 */
public class BebFragment2 extends Fragment implements View.OnClickListener {

//    @BindView(R.id.ly_start)
//    LinearLayout lyStart;
//    @BindView(R.id.btn_half)
//    Button btnHalf;
//    @BindView(R.id.btn_all)
//    Button btnAll;
//    @BindView(R.id.btn_stop)
//    Button btnStop;
//    @BindView(R.id.btn_back)
    Button btnBack;


    private GizWifiDevice gizWifiDevice=null;

    public BebFragment2(GizWifiDevice msgWarp) {
        if (msgWarp!=null){
            gizWifiDevice=msgWarp;
            // 实现回调
            GizWifiDeviceListener mListener = new GizWifiDeviceListener() {

                @Override
                public void didReceiveAttrStatus(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> attrStatus, ConcurrentHashMap<String, Object> adapterAttrStatus, int sn) {
                    if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                        if (sn == 1) {
                            Log.d("BebFragment1", "命令序号相符，开灯指令执行成功2");

                        }
                    } else {
                        Log.d("BebFragment1", "指令执行失败2");
                    }
                }
            };
            gizWifiDevice.setListener(mListener);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beb2, container, false);
       // @OnClick({R.id.ly_start, R.id.btn_half, R.id.btn_all, R.id.btn_stop, R.id.btn_back})
        view.findViewById(R.id.ly_start).setOnClickListener(this);
        view.findViewById(R.id.btn_half).setOnClickListener(this);
        view.findViewById(R.id.btn_all).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        btnBack=view.findViewById(R.id.btn_back);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private int curSn=0;
    private boolean tryRun(int sn) {
        if (curSn != 0) {
            //设备忙碌中
            ToastUtils.setMessage("设备忙碌中...",btnBack);
            return false;
        }
        curSn = sn;
        return true;
    }

    private void send(byte[] binary, int sn) {
        if (null != gizWifiDevice) {
            Log.d("BebFragment1", String.format("send:Raw:sn=%s,cmd=%s", sn, Arrays.toString(binary)));
            Log.d("BebFragment1", String.format("send:sn=%s,cmd=%s", sn, cmdToString(binary)));
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("binary", binary);
            gizWifiDevice.write(data, sn);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_start: {
                if (tryRun(15)) {
                    send(new byte[]{0x12, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00}, 15);
                }
                break;
            }
            case R.id.btn_half:
                if (tryRun(3)) {
                    send(new byte[]{0x12, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 3);
                }
                break;
            case R.id.btn_all:
                if (tryRun(15)) {
                    send(new byte[]{0x12, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00}, 15);
                }
                break;
            case R.id.btn_stop:
                curSn=0;
                new CommomDialog(getActivity(), R.style.dialogs, "是否下降到初始化位置?", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Log.d("DAFragment3", "confirm:" + confirm);
                        if (confirm) {
                            //退出动作
                            if (tryRun(4)) {
                                byte v = (byte) 0x04;
                                send(new byte[]{0x12, 0x00, 0x00, v, 0x00, 0x00, 0x00, 0x00}, 4);
                            }
                            curSn=0;
                            dialog.dismiss();
                        }else {
                            if (tryRun(4)) {
                                byte v = (byte) 0x00;
                                send(new byte[]{0x12, 0x00, 0x00, v, 0x00, 0x00, 0x00, 0x00}, 4);
                            }
                            curSn=0;
                            dialog.dismiss();
                        }
                    }
                }).setTitle("确认").setPositiveButton("确定").show();
                break;
            case R.id.btn_back:
                EventBus.getDefault().post("BebFragment2");
                break;
        }
    }
}
