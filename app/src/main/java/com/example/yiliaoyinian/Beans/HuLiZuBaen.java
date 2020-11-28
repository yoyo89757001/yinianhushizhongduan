package com.example.yiliaoyinian.Beans;

import java.util.List;

public class HuLiZuBaen {

    /**
     * success : true
     * result : [{"id":"id","nurseName":"护理员姓名","nurseCode":"护理员编码","idCard":"身份证","gender":"性别，1-男，2-女","phone":"手机号","category":"类别，1-护工，2-护士，3-医生，4-护士长，5-院长","headImg":"头像","entryTime":"入职时间"}]
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
         * id : id
         * nurseName : 护理员姓名
         * nurseCode : 护理员编码
         * idCard : 身份证
         * gender : 性别，1-男，2-女
         * phone : 手机号
         * category : 类别，1-护工，2-护士，3-医生，4-护士长，5-院长
         * headImg : 头像
         * entryTime : 入职时间
         */

        private String id;
        private String nurseName;
        private String nurseCode;
        private String idCard;
        private String gender;
        private String phone;
        private int category;
        private String headImg;
        private String entryTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }
    }
}
