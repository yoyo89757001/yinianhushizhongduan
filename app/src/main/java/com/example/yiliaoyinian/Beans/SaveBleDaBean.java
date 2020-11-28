package com.example.yiliaoyinian.Beans;

public class SaveBleDaBean {


    /**
     * success : true
     * result : {"id":45,"diastolic":111,"systolic":90,"heartrateM":70,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"w23ju3gwjqpff","userCode":"17408128","createTime":1593398606082,"deleteStatus":0,"dataType":2,"deviceMac":"860298048104813","nurseGroupId":4,"nurseGroupName":"护理A组","uploadStatus":2,"bedInfo":"颐和-1F-101-1","measureUserName":"患者B"}
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
         * id : 45
         * diastolic : 111
         * systolic : 90
         * heartrateM : 70
         * deviceCode : BP5mdmmgn2
         * deviceName : 护理A组
         * deviceType : DEVICE_BP
         * dataCode : w23ju3gwjqpff
         * userCode : 17408128
         * createTime : 1593398606082
         * deleteStatus : 0
         * dataType : 2
         * deviceMac : 860298048104813
         * nurseGroupId : 4
         * nurseGroupName : 护理A组
         * uploadStatus : 2
         * bedInfo : 颐和-1F-101-1
         * measureUserName : 患者B
         */

        private int id;
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
        private String deviceMac;
        private int nurseGroupId;
        private String nurseGroupName;
        private int uploadStatus;
        private String bedInfo;
        private String measureUserName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getDeviceMac() {
            return deviceMac;
        }

        public void setDeviceMac(String deviceMac) {
            this.deviceMac = deviceMac;
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

        public int getUploadStatus() {
            return uploadStatus;
        }

        public void setUploadStatus(int uploadStatus) {
            this.uploadStatus = uploadStatus;
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
    }
}
