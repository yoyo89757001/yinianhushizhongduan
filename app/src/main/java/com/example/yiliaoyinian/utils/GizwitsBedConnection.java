package com.example.yiliaoyinian.utils;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yiliaoyinian.BuildConfig;
import com.gizwits.gizwifisdk.api.GizWifiDevice;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;



/**
 * 机智云床机连接
 */
public class GizwitsBedConnection implements BedConnection {

    /**
     * 合法的字节数量（接收到的数据）
     */
    private static final int VALID_BYTE_COUNT = 18;

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

    private static final String TAG = GizwitsBedConnection.class.getSimpleName();
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();
    private GizWifiDevice device;
    private BedStatus bedStatus;
    private boolean autoReconnect; // 自动重连
    private int curSn;
    private CmdCallback curCallback;

    public GizwitsBedConnection(@NonNull GizWifiDevice device) {
        this.device = device;
    }

    @Override
    public void addListener(@NonNull Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(@NonNull Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void startQdblxf( int qy, @Nullable CmdCallback callback) {
        if (tryRun(1, callback)) {
            send(new byte[]{0x10, 0x00, 0x00, 0x01, (byte) qy, 0x00, 0x00, 0x00}, 1);
        }
    }

    @Override
    public void stopQdblxf( int qy, @Nullable CmdCallback callback) {
        if (tryRun(2, callback)) {
            send(new byte[]{0x10, 0x00, 0x00, 0x00, (byte)qy, 0x00, 0x00, 0x00}, 2);
        }
    }

    @Override
    public void startDdbfs(@Nullable CmdCallback callback) {
        if (tryRun(3, callback)) {
            send(new byte[]{0x12, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 3);
        }
    }

    @Override
    public void startDdqfs(@Nullable CmdCallback callback) {
        if (tryRun(15, callback)) {
            send(new byte[]{0x12, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00}, 15);
        }
    }

    @Override
    public void stopDdfs(boolean yes, @Nullable CmdCallback callback) {
        if (tryRun(4, callback)) {
            byte v = yes ? (byte) 0x04 : (byte) 0x00;
            send(new byte[]{0x12, 0x00, 0x00, v, 0x00, 0x00, 0x00, 0x00}, 4);
        }
    }

    @Override
    public void startRtts(@Nullable CmdCallback callback) {
        if (tryRun(5, callback)) {
            send(new byte[]{0x14, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 5);
        }
    }

    @Override
    public void stopRtts(boolean yes, @Nullable CmdCallback callback) {
        if (tryRun(6, callback)) {
            byte v = yes ? (byte) 0x02 : (byte) 0x00;
            send(new byte[]{0x14, 0x00, 0x00, v, 0x00, 0x00, 0x00, 0x00}, 6);
        }
    }

    @Override
    public void startKqb(@Nullable CmdCallback callback, int pressure, int time) {
        if (tryRun(7, callback)) {
            send(new byte[]{0x11, 0x00, 0x00, 0x01, (byte) pressure, (byte) time, 0x00, 0x00}, 7);
        }
    }

    @Override
    public void stopKqb(@Nullable CmdCallback callback, int pressure, int time) {
        if (tryRun(8, callback)) {
            send(new byte[]{0x11, 0x00, 0x00, 0x00, (byte) pressure, (byte) time, 0x00, 0x00}, 8);
        }
    }

    @Override
    public void runDdms(int cmd, @Nullable CmdCallback callback) {
        switch (cmd) {
            case CMD_QB:
                sendCmd(PRI_CMD_QB, callback);
                break;
            case CMD_QBFW:
                sendCmd(PRI_CMD_QBFW, callback);
                break;
            case CMD_ZFS:
                sendCmd(PRI_CMD_ZFS, callback);
                break;
            case CMD_ZFSFW:
                sendCmd(PRI_CMD_ZFSFW, callback);
                break;
            case CMD_QBTT:
                sendCmd(PRI_CMD_QBTT, callback);
                break;
            case CMD_QBTTFW:
                sendCmd(PRI_CMD_QBTTFW, callback);
                break;
            case CMD_YFS:
                sendCmd(PRI_CMD_YFS, callback);
                break;
            case CMD_YFSFW:
                sendCmd(PRI_CMD_YFSFW, callback);
                break;
            case CMD_TT:
                sendCmd(PRI_CMD_TT, callback);
                break;
            case CMD_TTFW:
                sendCmd(PRI_CMD_TTFW, callback);
                break;
            case CMD_RTTS:
                sendCmd(PRI_CMD_RTTS, callback);
                break;
            case CMD_RTTSFW:
                sendCmd(PRI_CMD_RTTSFW, callback);
                break;
            case CMD_CTTS:
                sendCmd(PRI_CMD_CTTS, callback);
                break;
            case CMD_CTTSFW:
                sendCmd(PRI_CMD_CTTSFW, callback);
                break;
            case CMD_STOP:
                sendCmd(PRI_CMD_STOP, callback);
                break;
        }
    }

    @Override
    public void startTbyd(@Nullable CmdCallback callback) {
        if (tryRun(10, callback)) {
            send(new byte[]{0x13, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 10);
        }
    }

    @Override
    public void stopTbyd(@Nullable CmdCallback callback) {
        if (tryRun(11, callback)) {
            send(new byte[]{0x13, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, 11);
        }
    }

    @Override
    public void startFw(boolean qnfw, boolean jxfw, @Nullable CmdCallback callback) {
        if (!qnfw && !jxfw) {
            if (null != callback) {
                callback.onFailed(new IllegalArgumentException("Unsupported args!"));
            }
            return;
        }
        if (tryRun(12, callback)) {
            byte type;
            if (qnfw && jxfw) {
                type = 0x01;
            } else if (qnfw) {
                type = 0x02;
            } else {
                type = 0x03;
            }
            send(new byte[]{0x16, 0x00, 0x00, type, 0x00, 0x00, 0x00, 0x00}, 12);
        }
    }

    @Override
    public void stopFw(@Nullable CmdCallback callback) {
        if (tryRun(14, callback)) {
            send(new byte[]{0x16, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, 14);
        }
    }

    @Override
    public void startQdxf(@Nullable CmdCallback callback) {
        if (tryRun(15, callback)) {
            send(new byte[]{0x18, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00}, 15);
        }
    }

    @Override
    public void stopQdxf(@Nullable CmdCallback callback) {
        if (tryRun(16, callback)) {
            send(new byte[]{0x18, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, 16);
        }
    }

    @Override
    public BedStatus getBedStatus() {
        return bedStatus;
    }

    @Override
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    @Override
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    @Override
    public boolean isBusy() {
        return curSn != 0;
    }

    @Override
    public void close() {
        if (null != device) {
            device.setSubscribe(BuildConfig.GIZWITS_PRODUCT_SECRET_BED, false);
            device = null;
        }
    }

    private void sendCmd(byte cmd, CmdCallback callback) {
        if (tryRun(9, callback)) {
            byte[] binary = {0x15, 0x00, 0x00, cmd, 0x00, 0x00, 0x00, 0x00};
            send(binary, 9);
        }
    }

    private void send(byte[] binary, int sn) {
        if (null != device) {
            Log.d(TAG, String.format("send:Raw:sn=%s,cmd=%s", sn, Arrays.toString(binary)));
            Log.d(TAG, String.format("send:sn=%s,cmd=%s", sn, cmdToString(binary)));
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("binary", binary);
            device.write(data, sn);
        }
    }

    /**
     * 接收到设备上发数据
     *
     * @param data 数据
     */
    void onReceive(ConcurrentHashMap<String, Object> data, int sn) {
        byte[] binary = (byte[]) data.get("binary");
        if (null != binary && binary.length >= VALID_BYTE_COUNT) {
            Log.d(TAG, "onReceive:Raw: " + Arrays.toString(binary));
            Log.d(TAG, "onReceive: " + binaryToString(binary));
            if (null == bedStatus) bedStatus = new BedStatus();
            bedStatus.setOperate(binary[15] == 0);
            bedStatus.setQdblxfStart(readBit(binary, 1, 0) == 1);
            bedStatus.setQdblxfStop(readBit(binary, 1, 1) == 1);
            bedStatus.setDdbfsStart(readBit(binary, 3, 0) == 1);
            bedStatus.setDdqfsStart(readBit(binary, 3, 1) == 1);
            bedStatus.setDdfsStop(readBit(binary, 3, 2) == 1);
            bedStatus.setRttsStart(readBit(binary, 5, 0) == 1);
            bedStatus.setRttsStop(readBit(binary, 5, 1) == 1);
            bedStatus.setKqbStart(readBit(binary, 2, 0) == 1);
            bedStatus.setKqbStop(readBit(binary, 2, 1) == 1);
            bedStatus.setKqbQn1(readBit(binary, 2, 2) == 1);
            bedStatus.setKqbQn2(readBit(binary, 2, 3) == 1);
            bedStatus.setKqbQn3(readBit(binary, 2, 4) == 1);
            bedStatus.setKqbQn4(readBit(binary, 2, 5) == 1);
            bedStatus.setKqbPre(binary[12]);
            bedStatus.setKqbCurPre(binary[13]);
            bedStatus.setKqbTime(binary[14]);
            if (binary[9] == PRI_CMD_QB) {
                bedStatus.setDdCmd(CMD_QB);

            } else if (binary[9] == PRI_CMD_QBFW) {
                bedStatus.setDdCmd(CMD_QBFW);

            } else if (binary[9] == PRI_CMD_ZFS) {
                bedStatus.setDdCmd(CMD_ZFS);

            } else if (binary[9] == PRI_CMD_ZFSFW) {
                bedStatus.setDdCmd(CMD_ZFSFW);

            } else if (binary[9] == PRI_CMD_QBTT) {
                bedStatus.setDdCmd(CMD_QBTT);

            } else if (binary[9] == PRI_CMD_QBTTFW) {
                bedStatus.setDdCmd(CMD_QBTTFW);

            } else if (binary[9] == PRI_CMD_YFS) {
                bedStatus.setDdCmd(CMD_YFS);

            } else if (binary[9] == PRI_CMD_YFSFW) {
                bedStatus.setDdCmd(CMD_YFSFW);

            } else if (binary[9] == PRI_CMD_TT) {
                bedStatus.setDdCmd(CMD_TT);

            } else if (binary[9] == PRI_CMD_TTFW) {
                bedStatus.setDdCmd(CMD_TTFW);

            } else if (binary[9] == PRI_CMD_RTTS) {
                bedStatus.setDdCmd(CMD_RTTS);

            } else if (binary[9] == PRI_CMD_RTTSFW) {
                bedStatus.setDdCmd(CMD_RTTSFW);

            } else if (binary[9] == PRI_CMD_CTTS) {
                bedStatus.setDdCmd(CMD_CTTS);

            } else if (binary[9] == PRI_CMD_CTTSFW) {
                bedStatus.setDdCmd(CMD_CTTSFW);

            } else {
                bedStatus.setDdCmd(CMD_STOP);
            }
            bedStatus.setTbydStart(readBit(binary, 4, 0) == 1);
            bedStatus.setTbydStop(readBit(binary, 4, 1) == 1);
            bedStatus.setQnfw(readBit(binary, 8, 0) == 1);
            bedStatus.setJxfw(readBit(binary, 8, 1) == 1);
            bedStatus.setFwStop(readBit(binary, 8, 2) == 1);
          //  bedStatus.setQdblxfPre(QdblxfQy.parse(binary[17]));
            bedStatus.setQdxfStart(readBit(binary, 7, 0) == 1);
            bedStatus.setQdxfStop(readBit(binary, 7, 1) == 1);
            synchronized (listeners) {
                for (Listener l : listeners) {
                    l.onReceive(bedStatus);
                }
            }
        }
        if (sn == curSn) {
            curSn = 0;
            if (null != curCallback) {
                curCallback.onSuccess();
                curCallback = null;
            }
        }
    }

    private int readBit(byte[] binary, int page, int bitIndex) {
        byte value = binary[page + 3];
        return (value >>> bitIndex) & 1;
    }

//    void onFailed(GizWifiErrorCode code, int sn) {
//        System.out.println("bed.onFailed:" + code);
//        if (sn == curSn) {
//            curSn = 0;
//            if (null != curCallback) {
//                curCallback.onFailed(new MessageException("操作失败"));
//                curCallback = null;
//            }
//        }
//    }

    /**
     * 连接断开
     *
     * @param e 异常
     */
    void onLoss(Throwable e) {
        synchronized (listeners) {
            for (Listener l : listeners) {
                l.onLoss(e);
            }
        }
        tryReconnect();
    }

    /**
     * 尝试重新连接
     */
    void tryReconnect() {
        if (autoReconnect && null != device) {
            // 自动重连
            device.setSubscribe(BuildConfig.GIZWITS_PRODUCT_SECRET_BED, true);
        }
    }

    private boolean tryRun(int sn, CmdCallback callback) {
        if (curSn != 0) {
            if (null != callback) {
                callback.onFailed(new Throwable("设备忙碌中"));
            }
            return false;
        }
        curSn = sn;
        curCallback = callback;
        return true;
    }

    private String cmdToString(byte[] cmd) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("0x%02X", cmd[0])).append(' ');
        builder.append(String.format("0x%02X", cmd[1])).append(' ');
        builder.append(String.format("0x%02X", cmd[2])).append(' ');
        builder.append(byteToBinary(cmd[3])).append(' ');
        builder.append(byteToInt(cmd[4])).append(' ');
        builder.append(byteToInt(cmd[5])).append(' ');
        builder.append(byteToInt(cmd[6])).append(' ');
        return builder.toString();
    }

    private String binaryToString(byte[] binary) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("0x%02X", binary[0])).append(' ');
        builder.append(String.format("0x%02X", binary[1])).append(' ');
        builder.append(String.format("0x%02X", binary[2])).append(' ');
        for (int i = 0; i < 9; i++) {
            builder.append(byteToBinary(binary[3 + i])).append(' ');
        }
        builder.append(String.format("%d", binary[12])).append(' ');
        builder.append(String.format("%d", binary[13])).append(' ');
        builder.append(String.format("%d", binary[14])).append(' ');
        builder.append(String.format("0x%02X", binary[15])).append(' ');
        builder.append(String.format("0x%02X", binary[16])).append(' ');
        return builder.toString();
    }

    private int byteToInt(byte value) {
        return ((int) value) & 0xFF;
    }

    private String byteToBinary(byte value) {
        int iv = byteToInt(value);
        String str = Integer.toString(iv, 2);
        if (str.length() < 8) {
            int count = 8 - str.length();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                builder.append('0');
            }
            builder.append(str);
            return builder.toString();
        }
        return str;
    }
}
