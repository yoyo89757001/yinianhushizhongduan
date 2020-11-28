package com.example.yiliaoyinian.Beans;

import java.util.List;

public class Auth01 {

    @Override
    public String toString() {


        return "Auth01{" +
                "parentDid='" + parentDid + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", model='" + model + '\'' +
                ", modelType=" + modelType +
                ", state=" + state +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", did='" + did + '\'' +
                '}';
    }

    /**
         * parentDid : lumi1.7811dc931b93
         * createTime : 1562214167262
         * timeZone :
         * updateTime : 1562214167268
         * model : lumi.plug.v1
         * modelType : 3
         * state : 0
         * firmwareVersion : 22
         * did : lumi.158d0002372fb3
         */



        private String parentDid;
        private String createTime;
        private String timeZone;
        private String updateTime;
        private String model;
        private int modelType;
        private int state;
        private String firmwareVersion;
        private String did;

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

}
