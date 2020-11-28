package com.example.yiliaoyinian.Beans;

import java.util.List;

public class XiTongTongZhiBean {

    /**
     * success : true
     * result : {"page":1,"rows":15,"totalRecord":1,"data":[{"id":3,"title":"开始放假！！","publishTime":1593046524000,"publishUserId":1,"publishUserName":"超级管理员","createTime":1593046524000,"nurseGroupId":4,"orgId":2,"nurseGroupName":"护理A组","content":"放假啦啦啦啦啦啦啦","lookStatus":2}],"pageCount":1,"totalPage":1,"prePage":1,"nextPage":1,"firstPage":true,"lastPage":true}
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
         * rows : 15
         * totalRecord : 1
         * data : [{"id":3,"title":"开始放假！！","publishTime":1593046524000,"publishUserId":1,"publishUserName":"超级管理员","createTime":1593046524000,"nurseGroupId":4,"orgId":2,"nurseGroupName":"护理A组","content":"放假啦啦啦啦啦啦啦","lookStatus":2}]
         * pageCount : 1
         * totalPage : 1
         * prePage : 1
         * nextPage : 1
         * firstPage : true
         * lastPage : true
         */

        private int page;
        private int rows;
        private int totalRecord;
        private int pageCount;
        private int totalPage;
        private int prePage;
        private int nextPage;
        private boolean firstPage;
        private boolean lastPage;
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

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
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

            private long id;
            private String title;
            private long publishTime;
            private long publishUserId;
            private String publishUserName;
            private long createTime;
            private int nurseGroupId;
            private int orgId;
            private String nurseGroupName;
            private String content;
            private int lookStatus;

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

            public long getPublishUserId() {
                return publishUserId;
            }

            public void setPublishUserId(long publishUserId) {
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
}
