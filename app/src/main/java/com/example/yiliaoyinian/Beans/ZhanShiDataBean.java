package com.example.yiliaoyinian.Beans;

import java.util.List;

public class ZhanShiDataBean {


    /**
     * success : true
     * result : {"abnormalNum":3,"abnormalList":[{"id":157,"measureData":"血压测量值:舒张压为72,收缩压为141","measureTime":1593231569000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248036000,"deviceMac":"BHL0080079","channelId":2,"type":1,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1"},{"id":156,"measureData":"血压测量值:舒张压为53,收缩压为111","measureTime":1593231662000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248032000,"deviceMac":"BHL0080079","channelId":2,"type":2,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1"},{"id":155,"measureData":"血压测量值:舒张压为72,收缩压为159","measureTime":1593233020000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593222507000,"deviceMac":"BHL0080079","channelId":2,"type":1,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1"}],"measureNum":-2,"allNum":1}
     * code : 1
     */

    private boolean success;
    private ResultBean result;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * abnormalNum : 3
         * abnormalList : [{"id":157,"measureData":"血压测量值:舒张压为72,收缩压为141","measureTime":1593231569000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248036000,"deviceMac":"BHL0080079","channelId":2,"type":1,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1"},{"id":156,"measureData":"血压测量值:舒张压为53,收缩压为111","measureTime":1593231662000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248032000,"deviceMac":"BHL0080079","channelId":2,"type":2,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1"},{"id":155,"measureData":"血压测量值:舒张压为72,收缩压为159","measureTime":1593233020000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593222507000,"deviceMac":"BHL0080079","channelId":2,"type":1,"measuredType":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1"}]
         * measureNum : -2
         * allNum : 1
         */

        private int abnormalNum;
        private int measureNum;
        private int allNum;
        private List<AbnormalListBean> abnormalList;

        public int getAbnormalNum() {
            return abnormalNum;
        }

        public void setAbnormalNum(int abnormalNum) {
            this.abnormalNum = abnormalNum;
        }

        public int getMeasureNum() {
            return measureNum;
        }

        public void setMeasureNum(int measureNum) {
            this.measureNum = measureNum;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public List<AbnormalListBean> getAbnormalList() {
            return abnormalList;
        }

        public void setAbnormalList(List<AbnormalListBean> abnormalList) {
            this.abnormalList = abnormalList;
        }

        public static class AbnormalListBean {
            /**
             * id : 157
             * measureData : 血压测量值:舒张压为72,收缩压为141
             * measureTime : 1593231569000
             * deviceCode : BBPnulgkswt
             * deviceName : 血压计
             * deviceType : DEVICE_BBP
             * measuredUserCode : 17408128
             * createTime : 1593248036000
             * deviceMac : BHL0080079
             * channelId : 2
             * type : 1
             * measuredType : 2
             * nurseGroupId : 4
             * nurseGroupName : 护理A组
             * abnormalType : 1
             * bedInfo : 颐和-1F-101-1
             */

            private int id;
            private String measureData;
            private long measureTime;
            private String deviceCode;
            private String deviceName;
            private String deviceType;
            private String measuredUserCode;
            private long createTime;
            private String deviceMac;
            private int channelId;
            private int type;
            private int measuredType;
            private int nurseGroupId;
            private String nurseGroupName;
            private int abnormalType;
            private String bedInfo;
            private String measuredUserName;

            public String getMeasuredUserName() {
                return measuredUserName;
            }

            public void setMeasuredUserName(String measuredUserName) {
                this.measuredUserName = measuredUserName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMeasureData() {
                return measureData;
            }

            public void setMeasureData(String measureData) {
                this.measureData = measureData;
            }

            public long getMeasureTime() {
                return measureTime;
            }

            public void setMeasureTime(long measureTime) {
                this.measureTime = measureTime;
            }

            public String getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getMeasuredUserCode() {
                return measuredUserCode;
            }

            public void setMeasuredUserCode(String measuredUserCode) {
                this.measuredUserCode = measuredUserCode;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDeviceMac() {
                return deviceMac;
            }

            public void setDeviceMac(String deviceMac) {
                this.deviceMac = deviceMac;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getMeasuredType() {
                return measuredType;
            }

            public void setMeasuredType(int measuredType) {
                this.measuredType = measuredType;
            }

            public int getNurseGroupId() {
                return nurseGroupId;
            }

            public void setNurseGroupId(int nurseGroupId) {
                this.nurseGroupId = nurseGroupId;
            }

            public String getNurseGroupName() {
                return nurseGroupName;
            }

            public void setNurseGroupName(String nurseGroupName) {
                this.nurseGroupName = nurseGroupName;
            }

            public int getAbnormalType() {
                return abnormalType;
            }

            public void setAbnormalType(int abnormalType) {
                this.abnormalType = abnormalType;
            }

            public String getBedInfo() {
                return bedInfo;
            }

            public void setBedInfo(String bedInfo) {
                this.bedInfo = bedInfo;
            }
        }
    }
}
