package com.example.yiliaoyinian.Beans;

import java.util.List;

public class HuGongBean {


    /**
     * success : true
     * result : {"id":10,"nurseName":"护士1","nurseCode":"123456","idCard":"1548756659484","birthDate":1402329600000,"gender":2,"nation":"汉族","phone":"18888888888","deptCode":"hl_1","deptName":"护理部","category":2,"staffStatus":1,"headImg":"http://118.190.246.182/group1/M00/00/00/rB-zyV7tg3yALv9wAABoawgSEOE774.jpg","address":"","imeiCode":"33333","maritalStatus":1,"educationLevel":4,"mailbox":"","entryTime":1593446400000,"school":"","specialty":"","censusAddress":"2626","memo":"","createTime":1592624016000,"orgId":2,"password":"bbee526b26ca11f78b75fb614c9eb2a3f71c1d5586c1f15920fe2764809f547b","salt":"yYiVAb0wq3Jwt4BNWw0A","status":1,"age":26,"noteList":[{"id":2,"noteTitle":"嘎嘎嘎嘎","noteContent":"CD好的好的回到家居军聚聚聚聚聚会聚聚句聚聚句查查吃你你你你你你；噢你顿地你内你电脑睇你地忽悠花都好搞不好都好好的好的好的点解点解多大的勇气","createTime":1593677172000,"nurseId":10},{"id":3,"noteTitle":"方法v发反反复复","noteContent":"反反复复覅发","createTime":1593677799000,"nurseId":10},{"id":4,"noteTitle":"vv拒绝vvv发","noteContent":"CC警察局","createTime":1593678066000,"nurseId":10}],"scheduleList":[{"id":7,"startDate":1593532800000,"endDate":1594742400000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"}]},{"id":8,"startDate":1594828800000,"endDate":1596124800000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"},{"shiftTime":"14:00-20:00","shiftName":"晚班"}]},{"id":8,"startDate":1594828800000,"endDate":1596124800000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"},{"shiftTime":"14:00-20:00","shiftName":"晚班"}]}]}
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
         * id : 10
         * nurseName : 护士1
         * nurseCode : 123456
         * idCard : 1548756659484
         * birthDate : 1402329600000
         * gender : 2
         * nation : 汉族
         * phone : 18888888888
         * deptCode : hl_1
         * deptName : 护理部
         * category : 2
         * staffStatus : 1
         * headImg : http://118.190.246.182/group1/M00/00/00/rB-zyV7tg3yALv9wAABoawgSEOE774.jpg
         * address :
         * imeiCode : 33333
         * maritalStatus : 1
         * educationLevel : 4
         * mailbox :
         * entryTime : 1593446400000
         * school :
         * specialty :
         * censusAddress : 2626
         * memo :
         * createTime : 1592624016000
         * orgId : 2
         * password : bbee526b26ca11f78b75fb614c9eb2a3f71c1d5586c1f15920fe2764809f547b
         * salt : yYiVAb0wq3Jwt4BNWw0A
         * status : 1
         * age : 26
         * noteList : [{"id":2,"noteTitle":"嘎嘎嘎嘎","noteContent":"CD好的好的回到家居军聚聚聚聚聚会聚聚句聚聚句查查吃你你你你你你；噢你顿地你内你电脑睇你地忽悠花都好搞不好都好好的好的好的点解点解多大的勇气","createTime":1593677172000,"nurseId":10},{"id":3,"noteTitle":"方法v发反反复复","noteContent":"反反复复覅发","createTime":1593677799000,"nurseId":10},{"id":4,"noteTitle":"vv拒绝vvv发","noteContent":"CC警察局","createTime":1593678066000,"nurseId":10}]
         * scheduleList : [{"id":7,"startDate":1593532800000,"endDate":1594742400000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"}]},{"id":8,"startDate":1594828800000,"endDate":1596124800000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"},{"shiftTime":"14:00-20:00","shiftName":"晚班"}]},{"id":8,"startDate":1594828800000,"endDate":1596124800000,"shiftList":[{"shiftTime":"08:30-12:00","shiftName":"早班"},{"shiftTime":"14:00-20:00","shiftName":"晚班"}]}]
         */

        private long id;
        private String nurseName;
        private String nurseCode;
        private String idCard;
        private long birthDate;
        private int gender;
        private String nation;
        private String phone;
        private String deptCode;
        private String deptName;
        private int category;
        private int staffStatus;
        private String headImg;
        private String address;
        private String imeiCode;
        private int maritalStatus;
        private int educationLevel;
        private String mailbox;
        private long entryTime;
        private String school;
        private String specialty;
        private String censusAddress;
        private String memo;
        private long createTime;
        private int orgId;
        private String password;
        private String salt;
        private int status;
        private int age;
        private List<NoteListBean> noteList;
        private List<ScheduleListBean> scheduleList;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNurseName() {
            return nurseName;
        }

        public void setNurseName(String nurseName) {
            this.nurseName = nurseName;
        }

        public String getNurseCode() {
            return nurseCode;
        }

        public void setNurseCode(String nurseCode) {
            this.nurseCode = nurseCode;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public long getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(long birthDate) {
            this.birthDate = birthDate;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getStaffStatus() {
            return staffStatus;
        }

        public void setStaffStatus(int staffStatus) {
            this.staffStatus = staffStatus;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImeiCode() {
            return imeiCode;
        }

        public void setImeiCode(String imeiCode) {
            this.imeiCode = imeiCode;
        }

        public int getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(int maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public int getEducationLevel() {
            return educationLevel;
        }

        public void setEducationLevel(int educationLevel) {
            this.educationLevel = educationLevel;
        }

        public String getMailbox() {
            return mailbox;
        }

        public void setMailbox(String mailbox) {
            this.mailbox = mailbox;
        }

        public long getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(long entryTime) {
            this.entryTime = entryTime;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getCensusAddress() {
            return censusAddress;
        }

        public void setCensusAddress(String censusAddress) {
            this.censusAddress = censusAddress;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<NoteListBean> getNoteList() {
            return noteList;
        }

        public void setNoteList(List<NoteListBean> noteList) {
            this.noteList = noteList;
        }

        public List<ScheduleListBean> getScheduleList() {
            return scheduleList;
        }

        public void setScheduleList(List<ScheduleListBean> scheduleList) {
            this.scheduleList = scheduleList;
        }

        public static class NoteListBean {
            /**
             * id : 2
             * noteTitle : 嘎嘎嘎嘎
             * noteContent : CD好的好的回到家居军聚聚聚聚聚会聚聚句聚聚句查查吃你你你你你你；噢你顿地你内你电脑睇你地忽悠花都好搞不好都好好的好的好的点解点解多大的勇气
             * createTime : 1593677172000
             * nurseId : 10
             */

            private long id;
            private String noteTitle;
            private String noteContent;
            private long createTime;
            private long nurseId;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getNoteTitle() {
                return noteTitle;
            }

            public void setNoteTitle(String noteTitle) {
                this.noteTitle = noteTitle;
            }

            public String getNoteContent() {
                return noteContent;
            }

            public void setNoteContent(String noteContent) {
                this.noteContent = noteContent;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getNurseId() {
                return nurseId;
            }

            public void setNurseId(long nurseId) {
                this.nurseId = nurseId;
            }
        }

        public static class ScheduleListBean {
            /**
             * id : 7
             * startDate : 1593532800000
             * endDate : 1594742400000
             * shiftList : [{"shiftTime":"08:30-12:00","shiftName":"早班"}]
             */

            private long id;
            private long startDate;
            private long endDate;
            private List<ShiftListBean> shiftList;

            public long getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getStartDate() {
                return startDate;
            }

            public void setStartDate(long startDate) {
                this.startDate = startDate;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public List<ShiftListBean> getShiftList() {
                return shiftList;
            }

            public void setShiftList(List<ShiftListBean> shiftList) {
                this.shiftList = shiftList;
            }

            public static class ShiftListBean {
                /**
                 * shiftTime : 08:30-12:00
                 * shiftName : 早班
                 */

                private String shiftTime;
                private String shiftName;

                public String getShiftTime() {
                    return shiftTime;
                }

                public void setShiftTime(String shiftTime) {
                    this.shiftTime = shiftTime;
                }

                public String getShiftName() {
                    return shiftName;
                }

                public void setShiftName(String shiftName) {
                    this.shiftName = shiftName;
                }
            }
        }
    }
}
