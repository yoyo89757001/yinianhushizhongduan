package com.example.yiliaoyinian.ui.shuju.bed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class BebFragment5 extends Fragment {
    /**
     * 起背
     */
    private static final byte PRI_CMD_QB = Byte.parseByte("00000001", 2);
    /**
     * 起背复位
     */
    private static final byte PRI_CMD_QBFW = Byte.parseByte("00000010", 2);
    /**
     * 左翻身
     */
    private static final byte PRI_CMD_ZFS = Byte.parseByte("00001011", 2);
    /**
     * 左翻身复位
     */
    private static final byte PRI_CMD_ZFSFW = Byte.parseByte("00001100", 2);
    /**
     * 起背抬腿
     */
    private static final byte PRI_CMD_QBTT = Byte.parseByte("00000011", 2);
    /**
     * 起背抬腿复位
     */
    private static final byte PRI_CMD_QBTTFW = Byte.parseByte("00000100", 2);
    /**
     * 右翻身
     */
    private static final byte PRI_CMD_YFS = Byte.parseByte("00001001", 2);
    /**
     * 右翻身复位
     */
    private static final byte PRI_CMD_YFSFW = Byte.parseByte("00001010", 2);
    /**
     * 抬腿
     */
    private static final byte PRI_CMD_TT = Byte.parseByte("00000101", 2);
    /**
     * 抬腿复位
     */
    private static final byte PRI_CMD_TTFW = Byte.parseByte("00000110", 2);
    /**
     * 人体抬升
     */
    private static final byte PRI_CMD_RTTS = Byte.parseByte("00001101", 2);
    /**
     * 人体抬升复位
     */
    private static final byte PRI_CMD_RTTSFW = Byte.parseByte("00001110", 2);
    /**
     * 床体抬升
     */
    private static final byte PRI_CMD_CTTS = Byte.parseByte("00000111", 2);
    /**
     * 床体抬升复位
     */
    private static final byte PRI_CMD_CTTSFW = Byte.parseByte("00001000", 2);
    /**
     * 停止
     */
    private static final byte PRI_CMD_STOP = Byte.parseByte("00010001", 2);


    LinearLayout lyAction01;

    LinearLayout lyAction02;
    LinearLayout lyAction07;
    LinearLayout lyAction08;
    LinearLayout lyAction05;
    LinearLayout lyAction06;
    LinearLayout lyAction03;
    LinearLayout lyAction04;
    LinearLayout lyAction09;
    LinearLayout lyAction10;
    LinearLayout lyAction11;
    LinearLayout lyAction12;
    LinearLayout lyAction13;
    LinearLayout lyAction14;


    private GizWifiDevice gizWifiDevice=null;

    public BebFragment5(GizWifiDevice msgWarp) {
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beb5, container, false);

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("BebFragment5");
            }
        });
        lyAction01=view.findViewById(R.id.ly_action01);
        lyAction02=view.findViewById(R.id.ly_action02);
        lyAction03=view.findViewById(R.id.ly_action03);
        lyAction04=view.findViewById(R.id.ly_action04);
        lyAction05=view.findViewById(R.id.ly_action05);
        lyAction06=view.findViewById(R.id.ly_action06);
        lyAction07=view.findViewById(R.id.ly_action07);
        lyAction08=view.findViewById(R.id.ly_action08);
        lyAction09=view.findViewById(R.id.ly_action09);
        lyAction10=view.findViewById(R.id.ly_action10);
        lyAction11=view.findViewById(R.id.ly_action11);
        lyAction12=view.findViewById(R.id.ly_action12);
        lyAction13=view.findViewById(R.id.ly_action13);
        lyAction14=view.findViewById(R.id.ly_action14);


        lyAction01.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_QB);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction02.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_QBFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });

        lyAction03.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_ZFS);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction04.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_ZFSFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction05.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_QBTT);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction06.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_QBTTFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });

        lyAction07.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_YFS);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });

        lyAction08.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_YFSFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction09.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_TT);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_TTFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction11.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_RTTS);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_RTTSFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction13.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_CTTS);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        lyAction14.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendCmd(PRI_CMD_CTTSFW);
                        break;
                    case MotionEvent.ACTION_CANCEL:{
                        sendCmd(PRI_CMD_STOP);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        sendCmd(PRI_CMD_STOP);
                        break;
                }
                return true;
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }



    private void sendCmd(byte cmd ) {
        if (tryRun(9)) {
            byte[] binary = {0x15, 0x00, 0x00, cmd, 0x00, 0x00, 0x00, 0x00};
            send(binary, 9);
        }
    }

   // private int curSn=0;
    private boolean tryRun(int sn) {
//        if (curSn != 0) {
//            //设备忙碌中
//            ToastUtils.setMessage("设备忙碌中...",btnBack);
//            return false;
//        }
      //  curSn = sn;
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

}
