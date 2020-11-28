package com.example.yiliaoyinian.Beans;

public class LeiDaBean {


    /**
     * success : true
     * result : {"reportPage":"http://6zn4xi.natappfree.cc:9080/zhzs/realtimeMonitor.html?token=eyJhbGciOiJIUzI1NiJ9.eyJzZXNzaW9uSWQiOiJkMThlN2RiZDIxYTY0MGMxOTU3MWE2YjE5OTA1ZGQyYyIsInVzZXJOYW1lIjoiMTg4ODg4ODg4ODgiLCJleHAiOjE1OTMzNzYzOTUsInVzZXJJZCI6IjEwIn0.sOnYnE-pSBqqD2rjzR1Gq05ZO4sXBHFsO4rffeURaqQ&devId=600194548CD2&day=2020-06-28&time=1593413738394"}
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
         * reportPage : http://6zn4xi.natappfree.cc:9080/zhzs/realtimeMonitor.html?token=eyJhbGciOiJIUzI1NiJ9.eyJzZXNzaW9uSWQiOiJkMThlN2RiZDIxYTY0MGMxOTU3MWE2YjE5OTA1ZGQyYyIsInVzZXJOYW1lIjoiMTg4ODg4ODg4ODgiLCJleHAiOjE1OTMzNzYzOTUsInVzZXJJZCI6IjEwIn0.sOnYnE-pSBqqD2rjzR1Gq05ZO4sXBHFsO4rffeURaqQ&devId=600194548CD2&day=2020-06-28&time=1593413738394
         */

        private String reportPage;

        public String getReportPage() {
            return reportPage;
        }

        public void setReportPage(String reportPage) {
            this.reportPage = reportPage;
        }
    }
}
