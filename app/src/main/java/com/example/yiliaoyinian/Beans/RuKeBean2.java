package com.example.yiliaoyinian.Beans;

import java.util.List;

public class RuKeBean2 {


    /**
     * success : true
     * result : [{"id":4,"buildName":"颐和","buildCode":"101","remark":"护理住院","createTime":1592706791000,"floors":[{"id":14,"floorName":"1F","floorCode":"1F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2,"roomList":[{"id":6,"roomName":"101","roomLevel":2,"orientation":"朝东","roomLighted":1,"roomAirness":1,"createTime":1592707230000,"buildId":4,"buildCode":"101","floorId":14,"orgId":2,"bedNum":4,"bedList":[{"id":14,"bedName":"1","bedPrice":1000,"createTime":1592707338000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1},{"id":15,"bedName":"2","bedPrice":1000,"createTime":1592720575000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":16,"bedName":"3","bedPrice":1000,"createTime":1592720593000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":17,"bedName":"4","bedPrice":1000,"createTime":1592720617000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1}]}]},{"id":15,"floorName":"2F","floorCode":"2F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":16,"floorName":"3F","floorCode":"3F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":17,"floorName":"4F","floorCode":"4F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":18,"floorName":"5F","floorCode":"5F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2}]},{"id":5,"buildName":"康宁","buildCode":"1002","remark":"","createTime":1592873288000,"floors":[]}]
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
         * id : 4
         * buildName : 颐和
         * buildCode : 101
         * remark : 护理住院
         * createTime : 1592706791000
         * floors : [{"id":14,"floorName":"1F","floorCode":"1F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2,"roomList":[{"id":6,"roomName":"101","roomLevel":2,"orientation":"朝东","roomLighted":1,"roomAirness":1,"createTime":1592707230000,"buildId":4,"buildCode":"101","floorId":14,"orgId":2,"bedNum":4,"bedList":[{"id":14,"bedName":"1","bedPrice":1000,"createTime":1592707338000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1},{"id":15,"bedName":"2","bedPrice":1000,"createTime":1592720575000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":16,"bedName":"3","bedPrice":1000,"createTime":1592720593000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":17,"bedName":"4","bedPrice":1000,"createTime":1592720617000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1}]}]},{"id":15,"floorName":"2F","floorCode":"2F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":16,"floorName":"3F","floorCode":"3F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":17,"floorName":"4F","floorCode":"4F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2},{"id":18,"floorName":"5F","floorCode":"5F","createTime":1592706803000,"buildCode":"101","buildId":4,"orgId":2}]
         */

        private long id;
        private String buildName;
        private String buildCode;
        private String remark;
        private long createTime;
        private List<FloorsBean> floors;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getBuildCode() {
            return buildCode;
        }

        public void setBuildCode(String buildCode) {
            this.buildCode = buildCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public List<FloorsBean> getFloors() {
            return floors;
        }

        public void setFloors(List<FloorsBean> floors) {
            this.floors = floors;
        }

        public static class FloorsBean {
            /**
             * id : 14
             * floorName : 1F
             * floorCode : 1F
             * createTime : 1592706803000
             * buildCode : 101
             * buildId : 4
             * orgId : 2
             * roomList : [{"id":6,"roomName":"101","roomLevel":2,"orientation":"朝东","roomLighted":1,"roomAirness":1,"createTime":1592707230000,"buildId":4,"buildCode":"101","floorId":14,"orgId":2,"bedNum":4,"bedList":[{"id":14,"bedName":"1","bedPrice":1000,"createTime":1592707338000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1},{"id":15,"bedName":"2","bedPrice":1000,"createTime":1592720575000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":16,"bedName":"3","bedPrice":1000,"createTime":1592720593000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":17,"bedName":"4","bedPrice":1000,"createTime":1592720617000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1}]}]
             */

            private long id;
            private String floorName;
            private String floorCode;
            private long createTime;
            private String buildCode;
            private long buildId;
            private long orgId;
            private List<RoomListBean> roomList;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getBuildCode() {
                return buildCode;
            }

            public void setBuildCode(String buildCode) {
                this.buildCode = buildCode;
            }

            public long getBuildId() {
                return buildId;
            }

            public void setBuildId(long buildId) {
                this.buildId = buildId;
            }

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public List<RoomListBean> getRoomList() {
                return roomList;
            }

            public void setRoomList(List<RoomListBean> roomList) {
                this.roomList = roomList;
            }

            public static class RoomListBean {
                /**
                 * id : 6
                 * roomName : 101
                 * roomLevel : 2
                 * orientation : 朝东
                 * roomLighted : 1
                 * roomAirness : 1
                 * createTime : 1592707230000
                 * buildId : 4
                 * buildCode : 101
                 * floorId : 14
                 * orgId : 2
                 * bedNum : 4
                 * bedList : [{"id":14,"bedName":"1","bedPrice":1000,"createTime":1592707338000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1},{"id":15,"bedName":"2","bedPrice":1000,"createTime":1592720575000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":16,"bedName":"3","bedPrice":1000,"createTime":1592720593000,"orgId":2,"roomId":6,"nurseGroupName":"","type":0},{"id":17,"bedName":"4","bedPrice":1000,"createTime":1592720617000,"orgId":2,"roomId":6,"nurseGroupName":"","type":1}]
                 */

                private int id;
                private String roomName;
                private int roomLevel;
                private String orientation;
                private int roomLighted;
                private int roomAirness;
                private long createTime;
                private int buildId;
                private String buildCode;
                private int floorId;
                private int orgId;
                private int bedNum;
                private List<BedListBean> bedList;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getRoomName() {
                    return roomName;
                }

                public void setRoomName(String roomName) {
                    this.roomName = roomName;
                }

                public int getRoomLevel() {
                    return roomLevel;
                }

                public void setRoomLevel(int roomLevel) {
                    this.roomLevel = roomLevel;
                }

                public String getOrientation() {
                    return orientation;
                }

                public void setOrientation(String orientation) {
                    this.orientation = orientation;
                }

                public int getRoomLighted() {
                    return roomLighted;
                }

                public void setRoomLighted(int roomLighted) {
                    this.roomLighted = roomLighted;
                }

                public int getRoomAirness() {
                    return roomAirness;
                }

                public void setRoomAirness(int roomAirness) {
                    this.roomAirness = roomAirness;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public int getBuildId() {
                    return buildId;
                }

                public void setBuildId(int buildId) {
                    this.buildId = buildId;
                }

                public String getBuildCode() {
                    return buildCode;
                }

                public void setBuildCode(String buildCode) {
                    this.buildCode = buildCode;
                }

                public int getFloorId() {
                    return floorId;
                }

                public void setFloorId(int floorId) {
                    this.floorId = floorId;
                }

                public int getOrgId() {
                    return orgId;
                }

                public void setOrgId(int orgId) {
                    this.orgId = orgId;
                }

                public int getBedNum() {
                    return bedNum;
                }

                public void setBedNum(int bedNum) {
                    this.bedNum = bedNum;
                }

                public List<BedListBean> getBedList() {
                    return bedList;
                }

                public void setBedList(List<BedListBean> bedList) {
                    this.bedList = bedList;
                }

                public static class BedListBean {
                    /**
                     * id : 14
                     * bedName : 1
                     * bedPrice : 1000.0
                     * createTime : 1592707338000
                     * orgId : 2
                     * roomId : 6
                     * nurseGroupName :
                     * type : 1
                     */

                    private int id;
                    private String bedName;
                    private double bedPrice;
                    private long createTime;
                    private int orgId;
                    private int roomId;
                    private String nurseGroupName;
                    private int type;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
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

                    public long getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(long createTime) {
                        this.createTime = createTime;
                    }

                    public int getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(int orgId) {
                        this.orgId = orgId;
                    }

                    public int getRoomId() {
                        return roomId;
                    }

                    public void setRoomId(int roomId) {
                        this.roomId = roomId;
                    }

                    public String getNurseGroupName() {
                        return nurseGroupName;
                    }

                    public void setNurseGroupName(String nurseGroupName) {
                        this.nurseGroupName = nurseGroupName;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }
            }
        }
    }
}
