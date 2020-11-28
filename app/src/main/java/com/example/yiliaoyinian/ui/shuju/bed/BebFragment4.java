package com.example.yiliaoyinian.ui.shuju.bed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
public class BebFragment4 extends Fragment implements View.OnClickListener {

//    @BindView(R.id.iv_tip1)
//    ImageView ivTip1;
//    @BindView(R.id.iv_tip2)
//    ImageView ivTip2;
//    @BindView(R.id.tv_curPre)
//    TextView tvCurPre;
//    @BindView(R.id.btn_start)
      Button btnStart;
//    @BindView(R.id.ly_left)
//    LinearLayout lyLeft;
//    @BindView(R.id.tv_pre)
//    TextView tvPre;
//    @BindView(R.id.ly_pre)
//    LinearLayout lyPre;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.ly_time)
//    LinearLayout lyTime;
//    @BindView(R.id.btn_reduce)
//    ImageButton btnReduce;
//    @BindView(R.id.btn_add)
//    ImageButton btnAdd;
//    @BindView(R.id.btn_back)
//    Button btnBack;
//    private Unbinder unbinder;

    private GizWifiDevice gizWifiDevice=null;

    public BebFragment4(GizWifiDevice msgWarp) {
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
        View view = inflater.inflate(R.layout.fragment_beb4, container, false);
       // @OnClick({R.id.iv_tip1, R.id.iv_tip2, R.id.tv_curPre, R.id.btn_start, R.id.tv_pre, R.id.ly_pre, R.id.tv_time, R.id.ly_time, R.id.btn_reduce, R.id.btn_add, R.id.btn_back})
        btnStart=view.findViewById(R.id.btn_start);
        view.findViewById(R.id.iv_tip1).setOnClickListener(this);
        view.findViewById(R.id.iv_tip2).setOnClickListener(this);
        view.findViewById(R.id.tv_curPre).setOnClickListener(this);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
        view.findViewById(R.id.tv_pre).setOnClickListener(this);
        view.findViewById(R.id.ly_pre).setOnClickListener(this);
        view.findViewById(R.id.tv_time).setOnClickListener(this);
        view.findViewById(R.id.ly_time).setOnClickListener(this);
        view.findViewById(R.id.btn_reduce).setOnClickListener(this);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);

        return view;
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    private boolean isStart=true;


    private boolean tryRun(int sn) {
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
            case R.id.iv_tip1:
                break;
            case R.id.iv_tip2:
                break;
            case R.id.tv_curPre:
                break;
            case R.id.btn_start:
                isStart=!isStart;
                if (isStart){
                    btnStart.setBackgroundResource(R.drawable.console_bg_boot);
                    btnStart.setText("启动");
                    if (tryRun(7)) {
                        send(new byte[]{0x11, 0x00, 0x00, 0x01, (byte) 5, (byte) 10, 0x00, 0x00}, 7);
                    }

                }else {
                    btnStart.setBackgroundResource(R.drawable.console_bg_negative);
                    btnStart.setText("停止");
                    if (tryRun(8)) {
                        send(new byte[]{0x11, 0x00, 0x00, 0x00, (byte) 5, (byte) 10, 0x00, 0x00}, 8);
                    }
                }

                break;
//            case R.id.ly_left:
//                break;
            case R.id.tv_pre:
                break;
            case R.id.ly_pre:
                break;
            case R.id.tv_time:
                break;
            case R.id.ly_time:
                break;
            case R.id.btn_reduce:
                break;
            case R.id.btn_add:
                break;
            case R.id.btn_back:
                EventBus.getDefault().post("BebFragment4");
                break;
        }
    }
}
