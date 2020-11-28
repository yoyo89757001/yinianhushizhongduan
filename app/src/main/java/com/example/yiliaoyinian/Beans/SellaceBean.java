package com.example.yiliaoyinian.Beans;

import java.util.List;

public class SellaceBean {


    /**
     * success : true
     * result : {"page":1,"rows":10,"totalRecord":2,"data":[{"id":6,"patientName":"患者A","gender":1,"idCard":"5415481515","phone":"13562548854","address":"","inDeposit":10000,"remark":"23232","socialSecurityNo":"63333","createTime":1592624799000,"admissionSource":2,"source":2,"checkInTime":1592582400000,"status":1,"nurseLevelId":4,"nurseLevelName":"介护1级","nurseGroupName":"","buildId":4,"buildName":"颐和","floorId":14,"floorName":"1F","roomId":6,"roomName":"101","bedId":14,"bedName":"1","orgId":2,"doctorName":"宋坤华","nurseDeptId":4,"nurseDeptName":"康复科","age":95,"illness":"康复护理","patientCode":"18575944"},{"id":8,"patientName":"患者C","gender":2,"idCard":"24234242324","phone":"1454345554","address":"广州","inDeposit":12000,"remark":"","socialSecurityNo":"2423423432","createTime":1592720534000,"admissionSource":2,"source":2,"checkInTime":1592582400000,"status":1,"nurseLevelId":5,"nurseLevelName":"介护Ⅱ级","nurseGroupName":"","buildId":4,"buildName":"颐和","floorId":14,"floorName":"1F","roomId":6,"roomName":"101","bedId":17,"bedName":"4","orgId":2,"doctorName":"宋坤华","nurseDeptId":4,"nurseDeptName":"康复科","age":98,"illness":"嘎嘎嘎嘎嘎","patientCode":"16074016"}],"pageCount":2,"totalPage":1,"prePage":1,"nextPage":1,"lastPage":true,"firstPage":true}
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
         * page : 1
         * rows : 10
         * totalRecord : 2
         * data : [{"id":6,"patientName":"患者A","gender":1,"idCard":"5415481515","phone":"13562548854","address":"","inDeposit":10000,"remark":"23232","socialSecurityNo":"63333","createTime":1592624799000,"admissionSource":2,"source":2,"checkInTime":1592582400000,"status":1,"nurseLevelId":4,"nurseLevelName":"介护1级","nurseGroupName":"","buildId":4,"buildName":"颐和","floorId":14,"floorName":"1F","roomId":6,"roomName":"101","bedId":14,"bedName":"1","orgId":2,"doctorName":"宋坤华","nurseDeptId":4,"nurseDeptName":"康复科","age":95,"illness":"康复护理","patientCode":"18575944"},{"id":8,"patientName":"患者C","gender":2,"idCard":"24234242324","phone":"1454345554","address":"广州","inDeposit":12000,"remark":"","socialSecurityNo":"2423423432","createTime":1592720534000,"admissionSource":2,"source":2,"checkInTime":1592582400000,"status":1,"nurseLevelId":5,"nurseLevelName":"介护Ⅱ级","nurseGroupName":"","buildId":4,"buildName":"颐和","floorId":14,"floorName":"1F","roomId":6,"roomName":"101","bedId":17,"bedName":"4","orgId":2,"doctorName":"宋坤华","nurseDeptId":4,"nurseDeptName":"康复科","age":98,"illness":"嘎嘎嘎嘎嘎","patientCode":"16074016"}]
         * pageCount : 2
         * totalPage : 1
         * prePage : 1
         * nextPage : 1
         * lastPage : true
         * firstPage : true
         */

        private int page;
        private int rows;
        private int totalRecord;
        private int pageCount;
        private int totalPage;
        private int prePage;
        private int nextPage;
        private boolean lastPage;
        private boolean firstPage;
        private List<DataBean> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 6
             * patientName : 患者A
             * gender : 1
             * idCard : 5415481515
             * phone : 13562548854
             * address :
             * inDeposit : 10000
             * remark : 23232
             * socialSecurityNo : 63333
             * createTime : 1592624799000
             * admissionSource : 2
             * source : 2
             * checkInTime : 1592582400000
             * status : 1
             * nurseLevelId : 4
             * nurseLevelName : 介护1级
             * nurseGroupName :
             * buildId : 4
             * buildName : 颐和
             * floorId : 14
             * floorName : 1F
             * roomId : 6
             * roomName : 101
             * bedId : 14
             * bedName : 1
             * orgId : 2
             * doctorName : 宋坤华
             * nurseDeptId : 4
             * nurseDeptName : 康复科
             * age : 95
             * illness : 康复护理
             * patientCode : 18575944
             */

            private boolean selectType;
            private long id;
            private String patientName;
            private int gender;
            private String idCard;
            private String phone;
            private String address;
            private int inDeposit;
            private String remark;
            private String socialSecurityNo;
            private long createTime;
            private int admissionSource;
            private int source;
            private long checkInTime;
            private int status;
            private int nurseLevelId;
            private String nurseLevelName;
            private String nurseGroupName;
            private long buildId;
            private String buildName;
            private long floorId;
            private String floorName;
            private long roomId;
            private String roomName;
            private long bedId;
            private String bedName;
            private long orgId;
            private String doctorName;
            private int nurseDeptId;
            private String nurseDeptName;
            private int age;
            private String illness;
            private String patientCode;



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

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getInDeposit() {
                return inDeposit;
            }

            public void setInDeposit(int inDeposit) {
                this.inDeposit = inDeposit;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSocialSecurityNo() {
                return socialSecurityNo;
            }

            public void setSocialSecurityNo(String socialSecurityNo) {
                this.socialSecurityNo = socialSecurityNo;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getAdmissionSource() {
                return admissionSource;
            }

            public void setAdmissionSource(int admissionSource) {
                this.admissionSource = admissionSource;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public long getCheckInTime() {
                return checkInTime;
            }

            public void setCheckInTime(long checkInTime) {
                this.checkInTime = checkInTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getNurseLevelId() {
                return nurseLevelId;
            }

            public void setNurseLevelId(int nurseLevelId) {
                this.nurseLevelId = nurseLevelId;
            }

            public String getNurseLevelName() {
                return nurseLevelName;
            }

            public void setNurseLevelName(String nurseLevelName) {
                this.nurseLevelName = nurseLevelName;
            }

            public String getNurseGroupName() {
                return nurseGroupName;
            }

            public void setNurseGroupName(String nurseGroupName) {
                this.nurseGroupName = nurseGroupName;
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

            public boolean isSelectType() {
                return selectType;
            }

            public void setSelectType(boolean selectType) {
                this.selectType = selectType;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getBuildId() {
                return buildId;
            }

            public void setBuildId(long buildId) {
                this.buildId = buildId;
            }

            public long getFloorId() {
                return floorId;
            }

            public void setFloorId(long floorId) {
                this.floorId = floorId;
            }

            public long getRoomId() {
                return roomId;
            }

            public void setRoomId(long roomId) {
                this.roomId = roomId;
            }

            public long getBedId() {
                return bedId;
            }

            public void setBedId(long bedId) {
                this.bedId = bedId;
            }

            public long getOrgId() {
                return orgId;
            }

            public void setOrgId(long orgId) {
                this.orgId = orgId;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public int getNurseDeptId() {
                return nurseDeptId;
            }

            public void setNurseDeptId(int nurseDeptId) {
                this.nurseDeptId = nurseDeptId;
            }

            public String getNurseDeptName() {
                return nurseDeptName;
            }

            public void setNurseDeptName(String nurseDeptName) {
                this.nurseDeptName = nurseDeptName;
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
}
