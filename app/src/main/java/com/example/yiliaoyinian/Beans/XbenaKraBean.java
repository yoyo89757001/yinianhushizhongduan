package com.example.yiliaoyinian.Beans;

import java.util.List;

public class XbenaKraBean {


    /**
     * success : true
     * result : [{"id":8,"itemName":"吃药","type":2,"status":1,"itemExplain":"凄凄切切群群群群","remark":"","orgId":2,"itemCode":"2003"}]
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
         * id : 8
         * itemName : 吃药
         * type : 2
         * status : 1
         * itemExplain : 凄凄切切群群群群
         * remark :
         * orgId : 2
         * itemCode : 2003
         */

        private long id;
        private String itemName;
        private int type;
        private int status;
        private String itemExplain;
        private String remark;
        private long orgId;
        private String itemCode;



        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getItemExplain() {
            return itemExplain;
        }

        public void setItemExplain(String itemExplain) {
            this.itemExplain = itemExplain;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getOrgId() {
            return orgId;
        }

        public void setOrgId(long orgId) {
            this.orgId = orgId;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }
    }
}
