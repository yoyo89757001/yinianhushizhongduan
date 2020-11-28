package com.example.yiliaoyinian.Beans;

public class MssagesInfoBean {

    /**
     * success : true
     * result : {"id":3,"title":"开始放假！！","publishTime":1593046524000,"publishUserId":1,"publishUserName":"超级管理员","createTime":1593046524000,"nurseGroupId":4,"orgId":2,"nurseGroupName":"护理A组","content":"放假啦啦啦啦啦啦啦","lookStatus":2}
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
         * id : 3
         * title : 开始放假！！
         * publishTime : 1593046524000
         * publishUserId : 1
         * publishUserName : 超级管理员
         * createTime : 1593046524000
         * nurseGroupId : 4
         * orgId : 2
         * nurseGroupName : 护理A组
         * content : 放假啦啦啦啦啦啦啦
         * lookStatus : 2
         */

        private int id;
        private String title;
        private long publishTime;
        private int publishUserId;
        private String publishUserName;
        private long createTime;
        private int nurseGroupId;
        private int orgId;
        private String nurseGroupName;
        private String content;
        private int lookStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getPublishUserId() {
            return publishUserId;
        }

        public void setPublishUserId(int publishUserId) {
            this.publishUserId = publishUserId;
        }

        public String getPublishUserName() {
            return publishUserName;
        }

        public void setPublishUserName(String publishUserName) {
            this.publishUserName = publishUserName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getNurseGroupId() {
            return nurseGroupId;
        }

        public void setNurseGroupId(int nurseGroupId) {
            this.nurseGroupId = nurseGroupId;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getNurseGroupName() {
            return nurseGroupName;
        }

        public void setNurseGroupName(String nurseGroupName) {
            this.nurseGroupName = nurseGroupName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLookStatus() {
            return lookStatus;
        }

        public void setLookStatus(int lookStatus) {
            this.lookStatus = lookStatus;
        }
    }
}
