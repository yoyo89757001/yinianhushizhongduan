package com.example.yiliaoyinian.Beans;

import java.util.List;

public class UnFinshBean {//未完成任务
    /**
     * success : true
     * result : [{"serviceType":1,"patientName":"患者A","itemName":"吃饭","buildName":"颐和","startTime":1591845892000,"endTime":1594178692000,"floorName":"1F","serviceId":4,"serviceName":"","roomName":"101","bedName":"1"}]
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
         * serviceType : 1   1-医疗护理，2-康复，3-心理，4-照料，5-法律
         * patientName : 患者A
         * itemName : 吃饭
         * buildName : 颐和
         * startTime : 1591845892000
         * endTime : 1594178692000
         * floorName : 1F
         * serviceId : 4
         * serviceName :
         * roomName : 101
         * bedName : 1
         */
        private long id;
        private int serviceType;
        private String patientName;
        private String itemName;
        private String buildName;
        private long startTime;
        private long endTime;
        private String floorName;
        private long serviceId;
        private String serviceName;
        private String roomName;
        private String bedName;
        private String lastTime;

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getFloorName() {
            return floorName;
        }

        public void setFloorName(String floorName) {
            this.floorName = floorName;
        }

        public long getServiceId() {
            return serviceId;
        }

        public void setServiceId(long serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getBedName() {
            return bedName;
        }

        public void setBedName(String bedName) {
            this.bedName = bedName;
        }
    }//未完成任务

}
