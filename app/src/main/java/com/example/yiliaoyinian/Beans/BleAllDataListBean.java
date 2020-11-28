package com.example.yiliaoyinian.Beans;

import java.util.List;

public class BleAllDataListBean {


    /**
     * success : true
     * result : [{"id":41,"diastolic":110,"systolic":63,"heartrateM":70,"deviceCode":"BBPnulgkswt","deviceName":"蓝牙血压计","deviceType":"DEVICE_BBP","dataCode":"wq8rbxgdx","userCode":"17408128","createTime":1593263079000,"deleteStatus":1,"dataType":2,"nurseGroupId":4,"nurseGroupName":"护理A组"},{"id":42,"diastolic":100,"systolic":53,"heartrateM":72,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq84vvcew","userCode":"17408128","createTime":1593265900000,"deleteStatus":1,"dataType":2,"nurseGroupId":4,"nurseGroupName":"护理A组"},{"id":43,"diastolic":105,"systolic":53,"heartrateM":82,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq84v2syn","userCode":"17408128","createTime":1593266002000,"deleteStatus":0,"dataType":2,"nurseGroupId":4,"nurseGroupName":"护理A组"},{"id":44,"diastolic":106,"systolic":67,"heartrateM":76,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq87w7esz","userCode":"17408128","createTime":1593307078000,"deleteStatus":0,"dataType":2,"nurseGroupId":4,"nurseGroupName":"护理A组"},{"id":46,"diastolic":100,"systolic":90,"heartrateM":90,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"w23ju3gwrlnwg","userCode":"17408128","createTime":1593402970000,"deleteStatus":0,"dataType":2,"nurseGroupId":4,"nurseGroupName":"护理A组"}]
     * code : 1
     */

    private boolean success;
    private int code;
    private List<ResultBean> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 41
         * diastolic : 110
         * systolic : 63
         * heartrateM : 70
         * deviceCode : BBPnulgkswt
         * deviceName : 蓝牙血压计
         * deviceType : DEVICE_BBP
         * dataCode : wq8rbxgdx
         * userCode : 17408128
         * createTime : 1593263079000
         * deleteStatus : 1
         * dataType : 2
         * nurseGroupId : 4
         * nurseGroupName : 护理A组
         */

        private long id;
        private int diastolic;
        private int systolic;
        private int heartrateM;
        private String deviceCode;
        private String deviceName;
        private String deviceType;
        private String dataCode;
        private String userCode;
        private long createTime;
        private int deleteStatus;
        private int dataType;
        private int nurseGroupId;
        private String nurseGroupName;
        private String bedInfo;
        private String measureUserName;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBedInfo() {
            return bedInfo;
        }

        public void setBedInfo(String bedInfo) {
            this.bedInfo = bedInfo;
        }

        public String getMeasureUserName() {
            return measureUserName;
        }

        public void setMeasureUserName(String measureUserName) {
            this.measureUserName = measureUserName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(int diastolic) {
            this.diastolic = diastolic;
        }

        public int getSystolic() {
            return systolic;
        }

        public void setSystolic(int systolic) {
            this.systolic = systolic;
        }

        public int getHeartrateM() {
            return heartrateM;
        }

        public void setHeartrateM(int heartrateM) {
            this.heartrateM = heartrateM;
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

        public String getDataCode() {
            return dataCode;
        }

        public void setDataCode(String dataCode) {
            this.dataCode = dataCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(int deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
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
    }
}
