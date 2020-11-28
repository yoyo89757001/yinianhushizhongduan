package com.example.yiliaoyinian.Beans;

public class CheckBBean {

    /**
     * success : true
     * result : {"id":7,"patientName":"患者B","gender":1,"nurseLevelName":"介护1级","buildName":"颐和","floorName":"1F","roomName":"101","bedName":"1","doctorName":"周云","age":86,"illness":"康复护理","patientCode":"17408128"}
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
         * id : 7
         * patientName : 患者B
         * gender : 1
         * nurseLevelName : 介护1级
         * buildName : 颐和
         * floorName : 1F
         * roomName : 101
         * bedName : 1
         * doctorName : 周云
         * age : 86
         * illness : 康复护理
         * patientCode : 17408128
         */

        private int id;
        private String patientName;
        private int gender;
        private String nurseLevelName;
        private String buildName;
        private String floorName;
        private String roomName;
        private String bedName;
        private String doctorName;
        private int age;
        private String illness;
        private String patientCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNurseLevelName() {
            return nurseLevelName;
        }

        public void setNurseLevelName(String nurseLevelName) {
            this.nurseLevelName = nurseLevelName;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
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

        public String getBedName() {
            return bedName;
        }

        public void setBedName(String bedName) {
            this.bedName = bedName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getIllness() {
            return illness;
        }

        public void setIllness(String illness) {
            this.illness = illness;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }
    }
}
