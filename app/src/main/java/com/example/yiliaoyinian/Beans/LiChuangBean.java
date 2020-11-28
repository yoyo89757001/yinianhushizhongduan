package com.example.yiliaoyinian.Beans;

public class LiChuangBean {


    /**
     * title : 生物雷达告警
     * serviceId : 14
     * msgType : 4
     * data : 您的生物雷达发生离床告警设备备注:护理A组设备序列号:RDaqnjqsvjk请及时前往查看
     * pushDate : 1595303119497
     */

    private String title;
    private String serviceId;
    private int msgType;
    private String data;
    private long pushDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getPushDate() {
        return pushDate;
    }

    public void setPushDate(long pushDate) {
        this.pushDate = pushDate;
    }
}
