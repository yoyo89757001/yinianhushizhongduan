package com.example.yiliaoyinian.Beans;

import java.util.List;

public class ServiceTypeBean2 {


    /**
     * success : true
     * result : [{"typeName":"医疗护理","type":1},{"typeName":"照料护理","type":2},{"typeName":"康复护理","type":3},{"typeName":"心理康复","type":4},{"typeName":"法律援助","type":5}]
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
         * typeName : 医疗护理
         * type : 1
         */

        private String typeName;
        private int type;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
