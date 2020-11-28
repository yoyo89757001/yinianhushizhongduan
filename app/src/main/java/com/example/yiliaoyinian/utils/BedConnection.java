package com.example.yiliaoyinian.utils;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Closeable;

/**
 * 床机连接，可以与床机通讯
 */
public interface BedConnection extends Closeable {

    /**
     * 最小气压
     */
    int PRE_MIN = 4;
    /**
     * 最大气压
     */
    int PRE_MAX = 20;
    /**
     * 最小时间
     */
    int TIME_MIN = 1;
    /**
     * 最大时间
     */
    int TIME_MAX = 99;

    /**
     * 起背
     */
    int CMD_QB = 1;
    /**
     * 起背复位
     */
    int CMD_QBFW = 2;
    /**
     * 左翻身
     */
    int CMD_ZFS = 3;
    /**
     * 左翻身复位
     */
    int CMD_ZFSFW = 4;
    /**
     * 起背抬腿
     */
    int CMD_QBTT = 5;
    /**
     * 起背抬腿复位
     */
    int CMD_QBTTFW = 6;
    /**
     * 右翻身
     */
    int CMD_YFS = 7;
    /**
     * 右翻身复位
     */
    int CMD_YFSFW = 8;
    /**
     * 抬腿
     */
    int CMD_TT = 9;
    /**
     * 抬腿复位
     */
    int CMD_TTFW = 10;
    /**
     * 人体抬升
     */
    int CMD_RTTS = 11;
    /**
     * 人体抬升复位
     */
    int CMD_RTTSFW = 12;
    /**
     * 床体抬升
     */
    int CMD_CTTS = 13;
    /**
     * 床体抬升复位
     */
    int CMD_CTTSFW = 14;
    /**
     * 停止
     */
    int CMD_STOP = 15;

    void addListener(@NonNull Listener listener);

    void removeListener(@NonNull Listener listener);

    /**
     * 开始气动波浪悬浮
     *
     * @param qy       气压
     * @param callback 回调
     */
    void startQdblxf( int qy, @Nullable CmdCallback callback);

    /**
     * 停止气动波浪悬浮
     *
     * @param qy       气压
     * @param callback 回调
     */
    void stopQdblxf( int qy, @Nullable CmdCallback callback);

    /**
     * 开始电动半翻身
     *
     * @param callback 回调
     */
    void startDdbfs(@Nullable CmdCallback callback);

    /**
     * 开始电动全翻身
     *
     * @param callback 回调
     */
    void startDdqfs(@Nullable CmdCallback callback);

    /**
     * 停止电动循环翻身
     *
     * @param yes      是否下降到初始化位置
     * @param callback 回调
     */
    void stopDdfs(boolean yes, @Nullable CmdCallback callback);

    /**
     * 开始人体抬升
     *
     * @param callback 回调
     */
    void startRtts(@Nullable CmdCallback callback);

    /**
     * 开始人体抬升
     *
     * @param yes      是否下降到初始化位置
     * @param callback 回调
     */
    void stopRtts(boolean yes, @Nullable CmdCallback callback);

    /**
     * 开始四肢空气波
     *
     * @param pressure 压力（kPa）
     * @param time     时间（min）
     */
    void startKqb(@Nullable CmdCallback callback, int pressure, int time);

    /**
     * 停止四肢空气波
     *
     * @param callback 回调
     */
    void stopKqb(@Nullable CmdCallback callback, int pressure, int time);

    /**
     * 执行电动模式命令
     *
     * @param cmd      命令
     * @param callback 回调
     */
    void runDdms(int cmd, @Nullable CmdCallback callback);

    /**
     * 开始腿部运动模式
     *
     * @param callback 回调
     */
    void startTbyd(@Nullable CmdCallback callback);

    /**
     * 停止腿部运动模式
     *
     * @param callback 回调
     */
    void stopTbyd(@Nullable CmdCallback callback);

    /**
     * 开始一键复位
     *
     * @param qnfw     气囊复位
     * @param jxfw     机械复位
     * @param callback 回调
     */
    void startFw(boolean qnfw, boolean jxfw, @Nullable CmdCallback callback);

    /**
     * 停止复位
     *
     * @param callback 回调
     */
    void stopFw(@Nullable CmdCallback callback);

    /**
     * 开始气动悬浮模式
     */
    void startQdxf(@Nullable CmdCallback callback);

    /**
     * 停止启动悬浮模式
     */
    void stopQdxf(@Nullable CmdCallback callback);

    /**
     * 获取床机状态
     *
     * @return 床机状态
     */
    BedStatus getBedStatus();

    /**
     * 设置是否自动重连
     *
     * @param autoReconnect true，自动重连
     */
    void setAutoReconnect(boolean autoReconnect);

    /**
     * 判断是否自动重连
     *
     * @return true，自动重连
     */
    boolean isAutoReconnect();

    /**
     * 判断是否处于忙碌
     *
     * @return true，忙碌中
     */
    boolean isBusy();

    @Override
    void close();

    /**
     * 命令回调
     */
    interface CmdCallback {

        /**
         * 命令执行成功
         */
        void onSuccess();

        /**
         * 执行失败
         *
         * @param e 异常
         */
        void onFailed(@NonNull Throwable e);
    }

    /**
     * 接收器
     */
    interface Listener {

        /**
         * 接收一个床机状态
         *
         * @param bedStatus 床机状态
         */
        void onReceive(BedStatus bedStatus);

        /**
         * 连接丢失，连接断开
         *
         * @param e 断开的异常
         */
        void onLoss(@NonNull Throwable e);
    }
}
