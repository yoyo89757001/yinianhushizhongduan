package com.example.yiliaoyinian.Beans;

public class TaskInFoBean {


    /**
     * success : true
     * result : {"id":4,"serviceName":"","serviceContent":"","serviceType":1,"itemType":1,"itemName":"吃饭","createTime":1592709929000,"bedId":14,"patientId":6,"startTime":1591845892000,"endTime":1594178692000,"patientName":"患者A"}
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
         * id : 4
         * serviceName :
         * serviceContent :
         * serviceType : 1
         * itemType : 1
         * itemName : 吃饭
         * createTime : 1592709929000
         * bedId : 14
         * patientId : 6
         * startTime : 1591845892000
         * endTime : 1594178692000
         * patientName : 患者A
         */


        private String finishUser;

        public String getFinishUser() {
            return finishUser;
        }

        public void setFinishUser(String finishUser) {
            this.finishUser = finishUser;
        }

        private long id;
        private String serviceName;
        private String serviceContent;
        private int serviceType;
        private int itemType;
        private String itemName;
        private long createTime;
        private long bedId;
        private long patientId;
        private long startTime;
        private long endTime;
        private String patientName;
        private String doctorName;
        private String nurseLevelName;
        private String bedName;
        private int gender;
        private String floorName;
        private String roomName;
        private long finishTime;

        public long getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(long finishTime) {
            this.finishTime = finishTime;
        }

        public String getFloorName() {
            return floorName;
        }

        public void setFloorName(String floorName) {
            this.floorName = floorName;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getNurseLevelName() {
            return nurseLevelName;
        }

        public void setNurseLevelName(String nurseLevelName) {
            this.nurseLevelName = nurseLevelName;
        }

        public String getBedName() {
            return bedName;
        }

        public void setBedName(String bedName) {
            this.bedName = bedName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getBedId() {
            return bedId;
        }

        public void setBedId(long bedId) {
            this.bedId = bedId;
        }

        public long getPatientId() {
            return patientId;
        }

        public void setPatientId(long patientId) {
            this.patientId = patientId;
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

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }
    }
}
