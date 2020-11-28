package com.example.yiliaoyinian.Beans;

import java.util.List;

public class LouDongBean {


    /**
     * success : true
     * result : [{"bedId":14,"roomId":6,"floorId":14,"buildId":4,"bedName":"1","bedPrice":1000,"roomName":"101","orientation":"朝东","floorName":"1F","floorCode":"1F","buildName":"颐和","nurseGroupName":""},{"bedId":15,"roomId":6,"floorId":14,"buildId":4,"bedName":"2","bedPrice":1000,"roomName":"101","orientation":"朝东","floorName":"1F","floorCode":"1F","buildName":"颐和","nurseGroupName":""},{"bedId":16,"roomId":6,"floorId":14,"buildId":4,"bedName":"3","bedPrice":1000,"roomName":"101","orientation":"朝东","floorName":"1F","floorCode":"1F","buildName":"颐和","nurseGroupName":""},{"bedId":17,"roomId":6,"floorId":14,"buildId":4,"bedName":"4","bedPrice":1000,"roomName":"101","orientation":"朝东","floorName":"1F","floorCode":"1F","buildName":"颐和","nurseGroupName":""}]
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
         * bedId : 14
         * roomId : 6
         * floorId : 14
         * buildId : 4
         * bedName : 1
         * bedPrice : 1000.0
         * roomName : 101
         * orientation : 朝东
         * floorName : 1F
         * floorCode : 1F
         * buildName : 颐和
         * nurseGroupName :
         */

        private long bedId;
        private long roomId;
        private long floorId;
        private long buildId;
        private String bedName;
        private double bedPrice;
        private String roomName;
        private String orientation;
        private String floorName;
        private String floorCode;
        private String buildName;
        private String nurseGroupName;

        public long getBedId() {
            return bedId;
        }

        public void setBedId(long bedId) {
            this.bedId = bedId;
        }

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }

        public long getFloorId() {
            return floorId;
        }

        public void setFloorId(long floorId) {
            this.floorId = floorId;
        }

        public long getBuildId() {
            return buildId;
        }

        public void setBuildId(long buildId) {
            this.buildId = buildId;
        }

        public void setBuildId(int buildId) {
            this.buildId = buildId;
        }

        public String getBedName() {
            return bedName;
        }

        public void setBedName(String bedName) {
            this.bedName = bedName;
        }

        public double getBedPrice() {
            return bedPrice;
        }

        public void setBedPrice(double bedPrice) {
            this.bedPrice = bedPrice;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getFloorName() {
            return floorName;
        }

        public void setFloorName(String floorName) {
            this.floorName = floorName;
        }

        public String getFloorCode() {
            return floorCode;
        }

        public void setFloorCode(String floorCode) {
            this.floorCode = floorCode;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getNurseGroupName() {
            return nurseGroupName;
        }

        public void setNurseGroupName(String nurseGroupName) {
            this.nurseGroupName = nurseGroupName;
        }
    }
}
