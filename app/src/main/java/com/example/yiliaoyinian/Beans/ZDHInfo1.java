package com.example.yiliaoyinian.Beans;

public class ZDHInfo1 {

    public ZDHInfo1() {

    }

    @Override
    public String toString() {
        return "ZDHInfo1{" +
                "parentDid='" + parentDid + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", model='" + model + '\'' +
                ", modelType=" + modelType +
                ", state=" + state +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", did='" + did + '\'' +
                ", dongzuo='" + dongzuo + '\'' +
                ", name='" + name + '\'' +
                ", xingqi='" + xingqi + '\'' +
                ", shijianduan='" + shijianduan + '\'' +
                '}';
    }

    private String parentDid;
    private String createTime;
    private String timeZone;
    private String updateTime;
    private String model;
    private int modelType;
    private int state;
    private String firmwareVersion;
    private String did;
    private String dongzuo;
    private String name;
    private String xingqi;
    private String shijianduan;


    public String getParentDid() {
        return parentDid;
    }

    public void setParentDid(String parentDid) {
        this.parentDid = parentDid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDongzuo() {
        return dongzuo;
    }

    public void setDongzuo(String dongzuo) {
        this.dongzuo = dongzuo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXingqi() {
        return xingqi;
    }

    public void setXingqi(String xingqi) {
        this.xingqi = xingqi;
    }

    public String getShijianduan() {
        return shijianduan;
    }

    public void setShijianduan(String shijianduan) {
        this.shijianduan = shijianduan;
    }
}
