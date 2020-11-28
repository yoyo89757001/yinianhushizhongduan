package com.example.yiliaoyinian.Beans;

import java.util.List;

public class SeleteLMBean {


    /**
     * code : 1
     * data : [{"createDate":1605007349000,"did":"lumi.158d000485147c,lumi1.54ef44ca1f79,","id":"1326123053181472768","linkageId":"\"L.775802579423645696\"","name":"银行行号","relation":0,"status":0}]
     * success : true
     */

    private int code;
    private boolean success;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * createDate : 1605007349000
         * did : lumi.158d000485147c,lumi1.54ef44ca1f79,
         * id : 1326123053181472768
         * linkageId : "L.775802579423645696"
         * name : 银行行号
         * relation : 0
         * status : 0
         */

        private long createDate;
        private String did;
        private String id;
        private String linkageId;
        private String name;
        private int relation;
        private int status;

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLinkageId() {
            return linkageId;
        }

        public void setLinkageId(String linkageId) {
            this.linkageId = linkageId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
