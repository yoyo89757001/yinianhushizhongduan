package com.example.yiliaoyinian.Beans;

public class ChuangJIBean {

    /**
     * success : true
     * result : {"ezCode":"WSMMRI","gzToken":"b912d57ac25943ac9ed7faad9e15338b","bedCode":"CJ4bgkzfsc","bedMac":"68c63ad08b2d","gzUid":"e38f80e050b748d7bc961250748ebd33","remark":"测试护理组","deviceCode":"CMw5pzlf734","ezSerial":"D44911530","ezToken":"at.4taeljrj6s7x3xr79wfo4mno4bndl5ck-2a3cb321m4-11193af-xlhcp4ly6"}
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
         * ezCode : WSMMRI
         * gzToken : b912d57ac25943ac9ed7faad9e15338b
         * bedCode : CJ4bgkzfsc
         * bedMac : 68c63ad08b2d
         * gzUid : e38f80e050b748d7bc961250748ebd33
         * remark : 测试护理组
         * deviceCode : CMw5pzlf734
         * ezSerial : D44911530
         * ezToken : at.4taeljrj6s7x3xr79wfo4mno4bndl5ck-2a3cb321m4-11193af-xlhcp4ly6
         */

        private String ezCode;
        private String gzToken;
        private String bedCode;
        private String bedMac;
        private String gzUid;
        private String remark;
        private String deviceCode;
        private String ezSerial;
        private String ezToken;

        public String getEzCode() {
            return ezCode;
        }

        public void setEzCode(String ezCode) {
            this.ezCode = ezCode;
        }

        public String getGzToken() {
            return gzToken;
        }

        public void setGzToken(String gzToken) {
            this.gzToken = gzToken;
        }

        public String getBedCode() {
            return bedCode;
        }

        public void setBedCode(String bedCode) {
            this.bedCode = bedCode;
        }

        public String getBedMac() {
            return bedMac;
        }

        public void setBedMac(String bedMac) {
            this.bedMac = bedMac;
        }

        public String getGzUid() {
            return gzUid;
        }

        public void setGzUid(String gzUid) {
            this.gzUid = gzUid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getEzSerial() {
            return ezSerial;
        }

        public void setEzSerial(String ezSerial) {
            this.ezSerial = ezSerial;
        }

        public String getEzToken() {
            return ezToken;
        }

        public void setEzToken(String ezToken) {
            this.ezToken = ezToken;
        }
    }
}
