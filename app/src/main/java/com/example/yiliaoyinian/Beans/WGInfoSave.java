package com.example.yiliaoyinian.Beans;


import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;



@Entity
public class WGInfoSave implements Serializable {

    public WGInfoSave(int modelType, String parentDid, int state, String did, String modle, String firmwareVersion, String name, boolean isOPen, String argb, int light, int soundValue, String ssid, String mac, String ip, String rssi, String weizhi) {
        this.modelType = modelType;
        this.parentDid = parentDid;
        this.state = state;
        this.did = did;
        this.modle = modle;
        this.firmwareVersion = firmwareVersion;
        this.name = name;
        this.isOPen = isOPen;
        this.argb = argb;
        this.light = light;
        this.soundValue = soundValue;
        this.ssid = ssid;
        this.mac = mac;
        this.ip = ip;
        this.rssi = rssi;
        this.weizhi = weizhi;
    }

    public WGInfoSave() {

    }

    @Id
    private Long id;
    private int modelType; //1：可挂子设备的网关；2：不可挂子设备的网关；3：子设备
    private String parentDid;
    private int state; //1在线，0离线
    private String did="";
    private String modle="";
    private String firmwareVersion=""; //固件版本
    private String name="";//设备名称
    private boolean isOPen;
    private String argb="ffffff";
    private int light=50;
    private int soundValue; //声音大小
    private String ssid="";
    private String mac="";
    private String ip="";
    private String rssi="-50";//信号强度
    private String weizhi="房间";//位置
    private String photo="";


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getParentDid() {
        return parentDid;
    }

    public void setParentDid(String parentDid) {
        this.parentDid = parentDid;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoundValue() {
        return soundValue;
    }

    public void setSoundValue(int soundValue) {
        this.soundValue = soundValue;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOPen() {
        return isOPen;
    }

    public void setOPen(boolean OPen) {
        isOPen = OPen;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getArgb() {
        return argb;
    }

    public void setArgb(String argb) {
        this.argb = argb;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public String getModle() {
        return modle;
    }

    public void setModle(String modle) {
        this.modle = modle;
    }

    @Override
    public String toString() {
        return "WGInfoSave{" +
                "id=" + id +
                ", modelType=" + modelType +
                ", parentDid='" + parentDid + '\'' +
                ", state=" + state +
                ", did='" + did + '\'' +
                ", modle='" + modle + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", name='" + name + '\'' +
                ", isOPen=" + isOPen +
                ", argb='" + argb + '\'' +
                ", light=" + light +
                ", soundValue=" + soundValue +
                ", ssid='" + ssid + '\'' +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", rssi='" + rssi + '\'' +
                ", weizhi='" + weizhi + '\'' +
                '}';
    }
}
