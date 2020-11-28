package com.example.yiliaoyinian.Beans;

import java.util.List;

public class HuanZheBean0 {

    /**
     * success : true
     * result : [{"id":"id","patientName":"患者名字","gender":"性别，1-男，2-女","checkInTime":"入住时间","buildName":"楼栋名称","floorName":"楼层名称","roomName":"房间名称","bedName":"床位名称","doctorName":"医生名称","patientCode":"患者编码，ID","illness":"病情描述"}]
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
         * id : id
         * patientName : 患者名字
         * gender : 性别，1-男，2-女
         * checkInTime : 入住时间
         * buildName : 楼栋名称
         * floorName : 楼层名称
         * roomName : 房间名称
         * bedName : 床位名称
         * doctorName : 医生名称
         * patientCode : 患者编码，ID
         * illness : 病情描述
         */

        private long id;
        private String patientName;
        private int gender;
        private String checkInTime;
        private String buildName;
        private String floorName;
        private String roomName;
        private String bedName;
        private String doctorName;
        private String patientCode;
        private String illness;

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public String getCheckInTime() {
            return checkInTime;
        }

        public void setCheckInTime(String checkInTime) {
            this.checkInTime = checkInTime;
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

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public String getIllness() {
            return illness;
        }

        public void setIllness(String illness) {
            this.illness = illness;
        }
    }
}
