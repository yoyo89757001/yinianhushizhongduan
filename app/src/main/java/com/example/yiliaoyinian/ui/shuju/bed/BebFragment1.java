package com.example.yiliaoyinian.ui.shuju.bed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.yiliaoyinian.R;
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
public class BebFragment1 extends Fragment implements View.OnClickListener {


    LinearLayout lyStart;
    TextView tvPre;
    LinearLayout lyPre;
//    @BindView(R.id.btn_reduce)
//    ImageButton btnReduce;
//    @BindView(R.id.btn_add)
//    ImageButton btnAdd;
//    @BindView(R.id.btn_stop)
//    Button btnStop;
//    @BindView(R.id.btn_back)
    Button btnBack;

    private GizWifiDevice gizWifiDevice=null;
    private int qiya=5;

    public BebFragment1(GizWifiDevice msgWarp) {
        if (msgWarp!=null){
            gizWifiDevice=msgWarp;
            // 实现回调
            GizWifiDeviceListener mListener = new GizWifiDeviceListener() {
                @Override
                public void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
                    if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                        if (sn == 1) {
                            Log.d("BebFragment1", "命令序号相符，开灯指令执行成功2");

                        }
                    } else {
                        Log.d("BebFragment1", "指令执行失败2");
                    }
                }

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
        View view = inflater.inflate(R.layout.fragment_beb1, container, false);
        //  @OnClick({R.id.btn_reduce, R.id.btn_add, R.id.btn_stop, R.id.btn_back,R.id.ly_start})
        view.findViewById(R.id.btn_reduce).setOnClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        view.findViewById(R.id.ly_start).setOnClickListener(this);
        lyStart=view.findViewById(R.id.ly_start);
        tvPre=view.findViewById(R.id.tv_pre);
        lyPre=view.findViewById(R.id.ly_pre);
        btnBack=view.findViewById(R.id.btn_back);

        return view;
    }


    @Override
    public void onDestroyView() {
        Log.d("BebFragment1", "销毁onDestroyView");

        super.onDestroyView();
    }



    private int curSn=0;
    private boolean tryRun(int sn) {
        Log.d("BebFragment1", "curSn:" + curSn);
        if (curSn != 0) {
            //设备忙碌中
            ToastUtils.setMessage("设备忙碌中...",btnBack);
            return false;
        }
        curSn = sn;
        return true;
    }

    private void send(byte[] binary, int sn) {
        Log.d("BebFragment1", "gizWifiDevice:" + gizWifiDevice);
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
            case R.id.ly_start:
                if (tryRun(1)) {
                    send(new byte[]{0x10, 0x00, 0x00, 0x01, (byte) Integer.parseInt(tvPre.getText().toString()), 0x00, 0x00, 0x00}, 1);
                }
                break;
            case R.id.btn_reduce:
                curSn=0;
                qiya=qiya-1;
                if (qiya<1){
                    qiya=1;
                }
                tvPre.setText(qiya+"");
                break;
            case R.id.btn_add:
                curSn=0;
                qiya=qiya+1;
                if (qiya>10){
                    qiya=10;
                }
                tvPre.setText(qiya+"");
                break;
            case R.id.btn_stop:
                curSn=0;
                if (tryRun(2)) {
                    send(new byte[]{0x10, 0x00, 0x00, 0x00, (byte) Integer.parseInt(tvPre.getText().toString()), 0x00, 0x00, 0x00}, 2);
                }
                curSn=0;
                break;
            case R.id.btn_back:
                EventBus.getDefault().post("BebFragment1");
                break;
        }
    }
}
