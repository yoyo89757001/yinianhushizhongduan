package com.example.yiliaoyinian.services;

import android.content.Context;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;

/**
 * 摄像头连接，可以操控摄像头
 */
public interface CameraConnection extends Closeable {

    int PTZ_ORIENTATION_LEFT = 0x01;
    int PTZ_ORIENTATION_UP = 0x02;
    int PTZ_ORIENTATION_RIGHT = 0x03;
    int PTZ_ORIENTATION_DOWN = 0x04;
    int PTZ_ACTION_START = 0x01;
    int PTZ_ACTION_STOP = 0x02;

    int getVideoWidth();

    int getVideoHeight();

    /**
     * 开始播放
     */
    void startPlaying();

    /**
     * 判断是否处于播放中
     *
     * @return true，播放中
     */
    boolean isPlaying();

    /**
     * 暂停播放
     */
    void pausePlay();

    /**
     * 判断声音是否开启
     *
     * @return true，声音已经开启
     */
    boolean isSoundEnable();

    /**
     * 设置声音是否开启
     *
     * @param enable true，声音开启
     */
    void setSoundEnable(boolean enable);

    /**
     * 判断通话是否开启
     *
     * @return true，通话已经开启
     */
    boolean isVoiceEnable();

    /**
     * 设置通话是否开启
     *
     * @param enable true，通话已经开启
     */
    void setVoiceEnable(boolean enable);

    /**
     * 设置视频质量
     *
     * @param videoLevel 视频质量
     */
    void setVideoLevel(@NonNull VideoLevel videoLevel);

    /**
     * 获取当前视频的质量
     *
     * @return 视频质量
     */
    VideoLevel getVideoLevel();

    /**
     * 获取支持的视频质量
     *
     * @return 支持的视频质量
     */
    VideoLevel[] supportVideoLevels();

    /**
     * 判断是否已经连接
     *
     * @return true，已经连接
     */
    boolean isConnected();

    /**
     * 关闭连接
     */
    @Override
    void close();

    /**
     * 设置状态更改监听器
     *
     * @param listener 状态更改监听器
     */
    void setOnStatusChangedListener(@Nullable OnStatusChangedListener listener);

    /**
     * 绑定图像显示
     *
     * @param context 上下文对象
     * @param binder  绑定器
     */
    void bindSurface(@NonNull Context context, @NonNull SurfaceBinder binder);

    /**
     * 解绑图像显示
     */
    void unbindSurface();

    /**
     * 发送ptz指令控制摄像头旋转
     *
     * @param cmds ptz命令
     */
    void sendPTZCmds(@NonNull List<PTZCmd> cmds);

    /**
     * 获取有效的速度比例
     *
     * @param speedScale 速度比例
     * @return 有效的速度比例
     */
    float validPTZSpeedScale(float speedScale);

    /**
     * PTZ命令
     */
    class PTZCmd implements Serializable, Cloneable {
        /**
         * 活动：开始或停止
         */
        public int action;
        /**
         * 方向
         */
        public int orientation;
        /**
         * 速度比例：[0,1]
         */
        public float speedScale;

        public PTZCmd(int action, int orientation, float speedScale) {
            this.action = action;
            this.orientation = orientation;
            this.speedScale = speedScale;
        }

        public PTZCmd() {
        }

        @Override
        public PTZCmd clone() {
            try {
                return (PTZCmd) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == this) return true;
            if (obj instanceof PTZCmd) {
                PTZCmd other = (PTZCmd) obj;
                return action == other.action &&
                        orientation == other.orientation &&
                        speedScale == other.speedScale;
            }
            return false;
        }
    }

    /**
     * 状态发生更改监听器
     */
    interface OnStatusChangedListener {

        /**
         * 状态发生更改时触发
         *
         * @param connection 摄像头连接
         */
        void onStatusChanged(@NonNull CameraConnection connection);

        /**
         * 离线
         *
         * @param connection 连接
         */
        void onOffline(@NonNull CameraConnection connection);
    }

    interface SurfaceBinder {

        void onBind(@NonNull SurfaceView view);
    }
}
