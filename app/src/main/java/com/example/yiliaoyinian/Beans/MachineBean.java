package com.example.yiliaoyinian.Beans;

import java.io.Serializable;
import java.util.List;

public class MachineBean {
    /**
     * success : true
     * result : [{"id":11,"deviceMac":"D44911138","deviceName":"护理A组","createTime":1592988083000,"deviceCode":"CMw5pzlf73f","userType":-1,"deviceType":"DEVICE_CAMERA","deviceModel":"OTHER","radarStatus":1,"deviceTypeName":"摄像头","nurseGroupId":4}]
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

    public static class ResultBean implements Serializable {
        /**
         * id : 11
         * deviceMac : D44911138
         * deviceName : 护理A组
         * createTime : 1592988083000
         * deviceCode : CMw5pzlf73f
         * userType : -1
         * deviceType : DEVICE_CAMERA
         * deviceModel : OTHER
         * radarStatus : 1
         * deviceTypeName : 摄像头
         * nurseGroupId : 4
         */

        private long id;
        private String deviceMac;
        private String deviceName;
        private long createTime;
        private String deviceCode;
        private int userType;
        private String deviceType;
        private String deviceModel;
        private int radarStatus;
        private String deviceTypeName;
        private int nurseGroupId;
        private String validateCode;
        private int type;
        private String did;

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getValidateCode() {
            return validateCode;
        }

        public void setValidateCode(String validateCode) {
            this.validateCode = validateCode;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDeviceMac() {
            return deviceMac;
        }

        public void setDeviceMac(String deviceMac) {
            this.deviceMac = deviceMac;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public int getRadarStatus() {
            return radarStatus;
        }

        public void setRadarStatus(int radarStatus) {
            this.radarStatus = radarStatus;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public int getNurseGroupId() {
            return nurseGroupId;
        }

        public void setNurseGroupId(int nurseGroupId) {
            this.nurseGroupId = nurseGroupId;
        }
    }//机器数据



}
