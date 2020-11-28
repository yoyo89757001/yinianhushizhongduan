package com.example.yiliaoyinian.Beans;

import java.util.List;

public class XunFangBean {

    /**
     * success :
     * result : {"patrolHouseList":[{"userName":"巡房用户","content":"巡房内容","patrolTime":"巡房时间","patrolPosition":"巡房位置","typeName":"类型名称"}],"totalNum":"总记录数","averageDate":"每天平均数"}
     * code :
     */

    private boolean success;
    private ResultBean result;
    private int code;



    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * patrolHouseList : [{"userName":"巡房用户","content":"巡房内容","patrolTime":"巡房时间","patrolPosition":"巡房位置","typeName":"类型名称"}]
         * totalNum : 总记录数
         * averageDate : 每天平均数
         */

        private int totalNum;
        private String averageDate;
        private List<PatrolHouseListBean> patrolHouseList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public String getAverageDate() {
            return averageDate;
        }

        public void setAverageDate(String averageDate) {
            this.averageDate = averageDate;
        }

        public List<PatrolHouseListBean> getPatrolHouseList() {
            return patrolHouseList;
        }

        public void setPatrolHouseList(List<PatrolHouseListBean> patrolHouseList) {
            this.patrolHouseList = patrolHouseList;
        }

        public static class PatrolHouseListBean {
            /**
             * userName : 巡房用户
             * content : 巡房内容
             * patrolTime : 巡房时间
             * patrolPosition : 巡房位置
             * typeName : 类型名称
             */

            private String userName;
            private String content;
            private String patrolTime;
            private String patrolPosition;
            private String typeName;
            private String nurseLevelName;
            private int age;
            private int gender;

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

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPatrolTime() {
                return patrolTime;
            }

            public void setPatrolTime(String patrolTime) {
                this.patrolTime = patrolTime;
            }

            public String getPatrolPosition() {
                return patrolPosition;
            }

            public void setPatrolPosition(String patrolPosition) {
                this.patrolPosition = patrolPosition;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }
        }
    }
}
