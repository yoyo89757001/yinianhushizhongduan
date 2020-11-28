package com.example.yiliaoyinian.Beans;

import java.util.List;

public class LuMIDevBean {


    /**
     * code : 1
     * data : [{"auroraId":"140fe1da9e277c13f45","bindKey":"randEhbjP11u0nWl","createDate":1604573204000,"did":"lumi1.54ef44ca1f79","eventType":"gateway_unbind","firmwareVersion":"3.0.7_0005.0515","id":"1324302116933378048","model":"lumi.gateway.acn01","modelType":1,"name":"Aqara网关 M1S","parentDid":"lumi1.54ef44ca1f79","pinlessUser":"13642730363","place":"2222","remarks":"防守打法","status":0,"updateDate":1604667513000}]
     * success : true
     */

    private int code;
    private boolean success;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * auroraId : 140fe1da9e277c13f45
         * bindKey : randEhbjP11u0nWl
         * createDate : 1604573204000
         * did : lumi1.54ef44ca1f79
         * eventType : gateway_unbind
         * firmwareVersion : 3.0.7_0005.0515
         * id : 1324302116933378048
         * model : lumi.gateway.acn01
         * modelType : 1
         * name : Aqara网关 M1S
         * parentDid : lumi1.54ef44ca1f79
         * pinlessUser : 13642730363
         * place : 2222
         * remarks : 防守打法
         * status : 0
         * updateDate : 1604667513000
         */

        private String auroraId;
        private String bindKey;
        private long createDate;
        private String did;
        private String eventType;
        private String firmwareVersion;
        private String id;
        private String model;
        private int modelType;
        private String name;
        private String parentDid;
        private String pinlessUser;
        private String place;
        private String remarks;
        private int status;
        private long updateDate;
        private String str1;

        public String getStr1() {
            return str1;
        }

        public void setStr1(String str1) {
            this.str1 = str1;
        }

        public String getAuroraId() {
            return auroraId;
        }

        public void setAuroraId(String auroraId) {
            this.auroraId = auroraId;
        }

        public String getBindKey() {
            return bindKey;
        }

        public void setBindKey(String bindKey) {
            this.bindKey = bindKey;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getFirmwareVersion() {
            return firmwareVersion;
        }

        public void setFirmwareVersion(String firmwareVersion) {
            this.firmwareVersion = firmwareVersion;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentDid() {
            return parentDid;
        }

        public void setParentDid(String parentDid) {
            this.parentDid = parentDid;
        }

        public String getPinlessUser() {
            return pinlessUser;
        }

        public void setPinlessUser(String pinlessUser) {
            this.pinlessUser = pinlessUser;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }
    }
}
