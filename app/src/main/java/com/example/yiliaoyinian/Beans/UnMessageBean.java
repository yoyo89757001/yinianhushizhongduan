package com.example.yiliaoyinian.Beans;

import java.util.List;

public class UnMessageBean {

    /**
     * success : true
     * result : [{"title":"开始放假！！","publishTime":1593046524000,"publishUserName":"超级管理员","nurseGroupName":"护理A组","content":"放假啦啦啦啦啦啦啦","lookStatus":2}]
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
         * title : 开始放假！！
         * publishTime : 1593046524000
         * publishUserName : 超级管理员
         * nurseGroupName : 护理A组
         * content : 放假啦啦啦啦啦啦啦
         * lookStatus : 2
         */

        private String title;
        private long publishTime;
        private String publishUserName;
        private String nurseGroupName;
        private String content;
        private int lookStatus;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
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

        public String getPublishUserName() {
            return publishUserName;
        }

        public void setPublishUserName(String publishUserName) {
            this.publishUserName = publishUserName;
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
