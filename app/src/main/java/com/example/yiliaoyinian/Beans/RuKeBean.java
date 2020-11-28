package com.example.yiliaoyinian.Beans;

import java.io.Serializable;
import java.util.List;

public class RuKeBean  {


    /**
     * success : true
     * result : [{"id":4,"patientName":"胡翠玲","gender":2,"idCard":"440105193511045140","phone":"1388888888","address":"","remark":"","age":81,"illness":""},{"id":5,"patientName":"莉莉","gender":2,"idCard":"440105193511045140","phone":"","address":"","inDeposit":1000,"remark":"","age":88,"illness":"术后康复，坐月子"},{"id":7,"patientName":"患者B","gender":1,"idCard":"325478455698745662","phone":"13568487758","address":"","inDeposit":10000,"remark":"哒哒哒哒哒哒","age":86,"illness":"康复护理","patientCode":"17408128"},{"id":9,"patientName":"测试患者","gender":2,"idCard":"442801193604102014","phone":"","address":"","remark":"","age":55,"illness":"","patientCode":"14056624"}]
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

    public static class ResultBean implements Serializable{
        /**
         * id : 4
         * patientName : 胡翠玲
         * gender : 2
         * idCard : 440105193511045140
         * phone : 1388888888
         * address :
         * remark :
         * age : 81
         * illness :
         * inDeposit : 1000
         * patientCode : 17408128
         */

        private int id;
        private String patientName;
        private int gender;
        private String idCard;
        private String phone;
        private String address;
        private String remark;
        private int age;
        private String illness;
        private int inDeposit;
        private String patientCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getIllness() {
            return illness;
        }

        public void setIllness(String illness) {
            this.illness = illness;
        }

        public int getInDeposit() {
            return inDeposit;
        }

        public void setInDeposit(int inDeposit) {
            this.inDeposit = inDeposit;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }
    }
}
