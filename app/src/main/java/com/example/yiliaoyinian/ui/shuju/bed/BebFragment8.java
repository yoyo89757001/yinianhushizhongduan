package com.example.yiliaoyinian.ui.shuju.bed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import com.example.yiliaoyinian.R;
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
public class BebFragment8 extends Fragment implements View.OnClickListener {

//    @BindView(R.id.ly_gasbag)
//    LinearLayout lyGasbag;
//    @BindView(R.id.ly_machine)
//    LinearLayout lyMachine;
//    @BindView(R.id.btn_stop)
//    Button btnStop;
//    @BindView(R.id.btn_back)
//    Button btnBack;


    private GizWifiDevice gizWifiDevice=null;

    public BebFragment8(GizWifiDevice msgWarp) {
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
        View view = inflater.inflate(R.layout.fragment_beb8, container, false);
    //  @OnClick({R.id.ly_gasbag, R.id.ly_machine, R.id.btn_stop, R.id.btn_back})
        view.findViewById(R.id.ly_gasbag).setOnClickListener(this);
        view.findViewById(R.id.ly_machine).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    //private int curSn=0;
    private boolean tryRun(int sn) {

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
            case R.id.ly_gasbag:
                if (tryRun(12)) {
                    send(new byte[]{0x16, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00}, 12);
                }
                break;
            case R.id.ly_machine:
                if (tryRun(12)) {
                    send(new byte[]{0x16, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 12);
                }
                break;
            case R.id.btn_stop:
                // curSn=0;
                if (tryRun(14)) {
                    send(new byte[]{0x16, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, 14);
                }
                // curSn=0;
                break;
            case R.id.btn_back:
                EventBus.getDefault().post("BebFragment8");
                break;
        }
    }
}
