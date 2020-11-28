package com.example.yiliaoyinian.Beans;

import java.util.List;

public class SignInBean {

    /**
     * success : true
     * result : {"totalNum":1,"signInList":[{"id":1,"userName":"护士1","userId":10,"type":1,"typeName":"患者签到","position":"颐和-1F-101-1,患者B","signTime":1592991361000,"createTime":1592991348000,"orgId":2}],"averageDate":1}
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
         * totalNum : 1
         * signInList : [{"id":1,"userName":"护士1","userId":10,"type":1,"typeName":"患者签到","position":"颐和-1F-101-1,患者B","signTime":1592991361000,"createTime":1592991348000,"orgId":2}]
         * averageDate : 1
         */

        private int totalNum;
        private int averageDate;
        private List<SignInListBean> signInList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getAverageDate() {
            return averageDate;
        }

        public void setAverageDate(int averageDate) {
            this.averageDate = averageDate;
        }

        public List<SignInListBean> getSignInList() {
            return signInList;
        }

        public void setSignInList(List<SignInListBean> signInList) {
            this.signInList = signInList;
        }

        public static class SignInListBean {
            /**
             * id : 1
             * userName : 护士1
             * userId : 10
             * type : 1
             * typeName : 患者签到
             * position : 颐和-1F-101-1,患者B
             * signTime : 1592991361000
             * createTime : 1592991348000
             * orgId : 2
             */

            private int id;
            private String userName;
            private int userId;
            private int type;
            private String typeName;
            private String position;
            private long signTime;
            private long createTime;
            private int orgId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public long getSignTime() {
                return signTime;
            }

            public void setSignTime(long signTime) {
                this.signTime = signTime;
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
        }
    }
}
