package com.example.yiliaoyinian.Beans;

import java.util.List;

public class RuKeBean3 {

    /**
     * success : true
     * result : {"levelList":[{"id":4,"nurseLevelName":"介护1级","createTime":1592707558000,"orgId":2,"remark":"范德萨发生的","status":1,"itemsList":"吃饭,晨练"},{"id":5,"nurseLevelName":"介护Ⅱ级","createTime":1592709830000,"orgId":2,"remark":"","status":1,"itemsList":"吃饭,晨练,清疮"}],"workerList":[{"nurseName":"张医生","nurseCode":"1006","category":3,"staffStatus":1},{"nurseName":"李俊杰","nurseCode":"zm1005","category":3,"staffStatus":1},{"nurseName":"周云","nurseCode":"zy1001","category":3,"staffStatus":1},{"nurseName":"宋坤华","nurseCode":"skh1001","category":3,"staffStatus":1}],"departList":[{"id":1,"departName":"内科","departCode":"nk1001","remark":"fff"},{"id":2,"departName":"护理部","departCode":"1002","remark":""},{"id":3,"departName":"门诊部","departCode":"mz1003","remark":""},{"id":4,"departName":"康复科","departCode":"kf1001","remark":""}]}
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
        private List<LevelListBean> levelList;
        private List<WorkerListBean> workerList;
        private List<DepartListBean> departList;

        public List<LevelListBean> getLevelList() {
            return levelList;
        }

        public void setLevelList(List<LevelListBean> levelList) {
            this.levelList = levelList;
        }

        public List<WorkerListBean> getWorkerList() {
            return workerList;
        }

        public void setWorkerList(List<WorkerListBean> workerList) {
            this.workerList = workerList;
        }

        public List<DepartListBean> getDepartList() {
            return departList;
        }

        public void setDepartList(List<DepartListBean> departList) {
            this.departList = departList;
        }

        public static class LevelListBean {
            /**
             * id : 4
             * nurseLevelName : 介护1级
             * createTime : 1592707558000
             * orgId : 2
             * remark : 范德萨发生的
             * status : 1
             * itemsList : 吃饭,晨练
             */

            private long id;
            private String nurseLevelName;
            private long createTime;
            private int orgId;
            private String remark;
            private int status;
            private String itemsList;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getNurseLevelName() {
                return nurseLevelName;
            }

            public void setNurseLevelName(String nurseLevelName) {
                this.nurseLevelName = nurseLevelName;
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getItemsList() {
                return itemsList;
            }

            public void setItemsList(String itemsList) {
                this.itemsList = itemsList;
            }
        }

        public static class WorkerListBean {
            /**
             * nurseName : 张医生
             * nurseCode : 1006
             * category : 3
             * staffStatus : 1
             */
            private long id;
            private String nurseName;
            private String nurseCode;
            private int category;
            private int staffStatus;

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
        }

        public static class DepartListBean {
            /**
             * id : 1
             * departName : 内科
             * departCode : nk1001
             * remark : fff
             */

            private long id;
            private String departName;
            private String departCode;
            private String remark;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getDepartName() {
                return departName;
            }

            public void setDepartName(String departName) {
                this.departName = departName;
            }

            public String getDepartCode() {
                return departCode;
            }

            public void setDepartCode(String departCode) {
                this.departCode = departCode;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
