package com.example.yiliaoyinian.utils;

import java.io.Serializable;

/**
 * 床机状态
 */
public class BedStatus implements Cloneable, Serializable {

    private boolean operate; // 表示app端是否可以操作
    private boolean qdblxfStart; // 气动波浪悬浮启动状态
    private boolean qdblxfStop; // 启动波浪悬浮停止状态
    private int qdblxfPre; // 启动波浪悬浮气压
    private boolean ddbfsStart; // 电动半翻身
    private boolean ddqfsStart; // 电动全翻身
    private boolean ddfsStop; // 电动翻身停止
    private boolean rttsStart; // 人体抬升启动
    private boolean rttsStop; // 人体抬升停止
    private boolean kqbStart; // 四肢空气波启动
    private boolean kqbStop; // 四肢空气波停止
    private boolean kqbQn1; // 空气波，气囊1
    private boolean kqbQn2; // 空气波，气囊2
    private boolean kqbQn3; // 空气波，气囊3
    private boolean kqbQn4; // 空气波，气囊4
    private int kqbPre; // 空气波设置的压力
    private int kqbTime; // 空气波设置的时间
    private int kqbCurPre; // 空气波当前实时压力
    private int ddCmd; // 电动模式正在执行的命令
    private boolean tbydStart; // 腿部运动启动状态
    private boolean tbydStop; // 腿部运动停止状态
    private boolean qnfw; // 气囊复位
    private boolean jxfw; // 机械复位
    private boolean fwStop; // 复位停止
    private boolean qdxfStart; // 气动悬浮是否启动
    private boolean qdxfStop; // 启动悬浮是否停止

    @Override
    public BedStatus clone() {
        try {
            return (BedStatus) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isOperate() {
        return operate;
    }

    public void setOperate(boolean operate) {
        this.operate = operate;
    }

    public boolean isQdblxfStart() {
        return qdblxfStart;
    }

    public void setQdblxfStart(boolean qdblxfStart) {
        this.qdblxfStart = qdblxfStart;
    }

    public int getQdblxfPre() {
        return qdblxfPre;
    }

    public void setQdblxfPre(int qdblxfPre) {
        this.qdblxfPre = qdblxfPre;
    }

    public boolean isRttsStart() {
        return rttsStart;
    }

    public void setRttsStart(boolean rttsStart) {
        this.rttsStart = rttsStart;
    }

    public boolean isKqbStart() {
        return kqbStart;
    }

    public void setKqbStart(boolean kqbStart) {
        this.kqbStart = kqbStart;
    }

    public int getKqbPre() {
        return kqbPre;
    }

    public void setKqbPre(int kqbPre) {
        this.kqbPre = kqbPre;
    }

    public int getDdCmd() {
        return ddCmd;
    }

    public void setDdCmd(int ddCmd) {
        this.ddCmd = ddCmd;
    }

    public boolean isTbydStart() {
        return tbydStart;
    }

    public void setTbydStart(boolean tbydStart) {
        this.tbydStart = tbydStart;
    }

    public boolean isQdblxfStop() {
        return qdblxfStop;
    }

    public void setQdblxfStop(boolean qdblxfStop) {
        this.qdblxfStop = qdblxfStop;
    }

    public boolean isRttsStop() {
        return rttsStop;
    }

    public void setRttsStop(boolean rttsStop) {
        this.rttsStop = rttsStop;
    }

    public boolean isKqbStop() {
        return kqbStop;
    }

    public void setKqbStop(boolean kqbStop) {
        this.kqbStop = kqbStop;
    }

    public int getKqbTime() {
        return kqbTime;
    }

    public void setKqbTime(int kqbTime) {
        this.kqbTime = kqbTime;
    }

    public int getKqbCurPre() {
        return kqbCurPre;
    }

    public void setKqbCurPre(int kqbCurPre) {
        this.kqbCurPre = kqbCurPre;
    }

    public boolean isTbydStop() {
        return tbydStop;
    }

    public void setTbydStop(boolean tbydStop) {
        this.tbydStop = tbydStop;
    }

    public boolean isQnfw() {
        return qnfw;
    }

    public void setQnfw(boolean qnfw) {
        this.qnfw = qnfw;
    }

    public boolean isJxfw() {
        return jxfw;
    }

    public void setJxfw(boolean jxfw) {
        this.jxfw = jxfw;
    }

    public boolean isFwStop() {
        return fwStop;
    }

    public void setFwStop(boolean fwStop) {
        this.fwStop = fwStop;
    }

    public boolean isDdbfsStart() {
        return ddbfsStart;
    }

    public void setDdbfsStart(boolean ddbfsStart) {
        this.ddbfsStart = ddbfsStart;
    }

    public boolean isDdqfsStart() {
        return ddqfsStart;
    }

    public void setDdqfsStart(boolean ddqfsStart) {
        this.ddqfsStart = ddqfsStart;
    }

    public boolean isDdfsStop() {
        return ddfsStop;
    }

    public void setDdfsStop(boolean ddfsStop) {
        this.ddfsStop = ddfsStop;
    }

    public boolean isKqbQn1() {
        return kqbQn1;
    }

    public void setKqbQn1(boolean kqbQn1) {
        this.kqbQn1 = kqbQn1;
    }

    public boolean isKqbQn2() {
        return kqbQn2;
    }

    public void setKqbQn2(boolean kqbQn2) {
        this.kqbQn2 = kqbQn2;
    }

    public boolean isKqbQn3() {
        return kqbQn3;
    }

    public void setKqbQn3(boolean kqbQn3) {
        this.kqbQn3 = kqbQn3;
    }

    public boolean isKqbQn4() {
        return kqbQn4;
    }

    public void setKqbQn4(boolean kqbQn4) {
        this.kqbQn4 = kqbQn4;
    }

    public boolean isQdxfStart() {
        return qdxfStart;
    }

    public void setQdxfStart(boolean qdxfStart) {
        this.qdxfStart = qdxfStart;
    }

    public boolean isQdxfStop() {
        return qdxfStop;
    }

    public void setQdxfStop(boolean qdxfStop) {
        this.qdxfStop = qdxfStop;
    }
}
