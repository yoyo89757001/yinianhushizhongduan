package com.example.yiliaoyinian.Beans;

import java.util.List;

public class HuanZheBean {


    /**
     * success : true
     * result : {"measureDataList":[{"id":54,"temperature":35.4,"deviceCode":"XCxncnqw26","deviceName":"护理A组","deviceType":"DEVICE_XC","dataCode":"d3eer3s2","userCode":"17408128","createTime":1593497861000,"deleteStatus":0,"dataType":4,"measureData":"腋下体温测量值:35.4","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497847000},{"id":53,"glucose":6.3,"deviceCode":"BGk6fm9z9y","deviceName":"护理A组","deviceType":"DEVICE_BG","dataCode":"w2e3r34","userCode":"17408128","createTime":1593497449000,"deleteStatus":0,"dataType":3,"measureData":"血糖测量值:6.3, 测量时间为:餐前","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497497000},{"id":52,"glucose":15.81,"deviceCode":"BGk6fm9z9y","deviceName":"护理A组","deviceType":"DEVICE_BG","dataCode":"q1w2e3","userCode":"17408128","createTime":1593497308000,"deleteStatus":0,"dataType":3,"measureData":"血糖测量值:12.25, 测量时间为:餐前","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497499000},{"id":48,"diastolic":69,"systolic":103,"heartrateM":99,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"mmt5i4lag4","userCode":"17408128","createTime":1593420015000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为69,收缩压为103","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593420015000},{"id":46,"diastolic":100,"systolic":90,"heartrateM":90,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"w23ju3gwrlnwg","userCode":"17408128","createTime":1593402970000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593402970000},{"id":44,"diastolic":106,"systolic":67,"heartrateM":76,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq87w7esz","userCode":"17408128","createTime":1593307078000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593307078000},{"id":43,"diastolic":105,"systolic":53,"heartrateM":82,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq84v2syn","userCode":"17408128","createTime":1593266002000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593266002000}],"abnormalList":[{"id":15,"measureData":"血糖测量值:15.81, 测量时间为:餐前","measureTime":1593496781000,"deviceCode":"BGk6fm9z9y","deviceName":"血糖","deviceType":"DEVICE_BG","measuredUserName":"患者B","measuredUserCode":"17408128","createTime":1577685602000,"deviceMac":"2AIAMR00958","channelId":2,"type":3,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":3},{"id":18,"measureData":"腋下体温测量值:35.4","measureTime":1593484583000,"deviceCode":"XCxncnqw26","deviceName":"希科一体机","deviceType":"DEVICE_XC","measuredUserCode":"17408128","createTime":1577759810000,"deviceMac":"DN00402Q52057600016","channelId":2,"type":6,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":4},{"id":155,"measureData":"血压测量值:舒张压为72,收缩压为159","measureTime":1593319420000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593222507000,"deviceMac":"BHL0080079","channelId":2,"type":1,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1","dataType":2},{"id":156,"measureData":"血压测量值:舒张压为53,收缩压为111","measureTime":1593318062000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248032000,"deviceMac":"BHL0080079","channelId":2,"type":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1","dataType":2},{"id":157,"measureData":"血压测量值:舒张压为72,收缩压为141","measureTime":1593317969000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248036000,"deviceMac":"BHL0080079","channelId":2,"type":1,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":2}],"nurseLevel":{"id":4,"nurseLevelName":"介护1级","createTime":1592707558000,"orgId":2,"remark":"范德萨发生的","status":1,"itemsList":"吃饭,晨练"},"serviceList":[{"id":11,"serviceName":"医疗护理","serviceContent":"早中晚记得按时吃饭...","serviceType":1,"itemName":"吃饭","patientId":7,"startTime":1593273600000,"endTime":1593360000000,"patientName":"患者B"},{"id":12,"serviceName":"照料护理","serviceContent":"记得吃药。。。。","serviceType":2,"itemName":"吃药","patientId":7,"startTime":1593273600000,"endTime":1593360000000,"patientName":"患者B"},{"id":13,"serviceName":"医疗护理","serviceContent":"每天早起测量血压","serviceType":1,"itemName":"血压","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"},{"id":14,"serviceName":"医疗护理","serviceContent":"每天饭前测量血糖","serviceType":1,"itemName":"血糖","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"},{"id":15,"serviceName":"医疗护理","serviceContent":"每天早上起床测量体温","serviceType":1,"itemName":"体温","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"}],"logList":[{"serviceName":"","serviceContent":"早中晚记得按时吃饭...","itemName":"吃饭","finishTime":1593337179000,"patientName":"患者B","gender":1}],"groupList":[{"id":4,"groupName":"护理A组"}],"patientInfo":{"id":7,"patientName":"患者B","gender":1,"checkInTime":1592956800000,"nurseLevelId":4,"nurseLevelName":"介护1级","buildName":"颐和","floorName":"1F","roomName":"101","bedId":14,"bedName":"1","orgId":2,"doctorName":"周云","nurseDeptName":"护理部","age":86,"illness":"康复护理","patientCode":"17408128"}}
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
         * measureDataList : [{"id":54,"temperature":35.4,"deviceCode":"XCxncnqw26","deviceName":"护理A组","deviceType":"DEVICE_XC","dataCode":"d3eer3s2","userCode":"17408128","createTime":1593497861000,"deleteStatus":0,"dataType":4,"measureData":"腋下体温测量值:35.4","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497847000},{"id":53,"glucose":6.3,"deviceCode":"BGk6fm9z9y","deviceName":"护理A组","deviceType":"DEVICE_BG","dataCode":"w2e3r34","userCode":"17408128","createTime":1593497449000,"deleteStatus":0,"dataType":3,"measureData":"血糖测量值:6.3, 测量时间为:餐前","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497497000},{"id":52,"glucose":15.81,"deviceCode":"BGk6fm9z9y","deviceName":"护理A组","deviceType":"DEVICE_BG","dataCode":"q1w2e3","userCode":"17408128","createTime":1593497308000,"deleteStatus":0,"dataType":3,"measureData":"血糖测量值:12.25, 测量时间为:餐前","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593497499000},{"id":48,"diastolic":69,"systolic":103,"heartrateM":99,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"mmt5i4lag4","userCode":"17408128","createTime":1593420015000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为69,收缩压为103","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593420015000},{"id":46,"diastolic":100,"systolic":90,"heartrateM":90,"deviceCode":"BP5mdmmgn2","deviceName":"护理A组","deviceType":"DEVICE_BP","dataCode":"w23ju3gwrlnwg","userCode":"17408128","createTime":1593402970000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593402970000},{"id":44,"diastolic":106,"systolic":67,"heartrateM":76,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq87w7esz","userCode":"17408128","createTime":1593307078000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593307078000},{"id":43,"diastolic":105,"systolic":53,"heartrateM":82,"deviceCode":"BBPnulgkswt","deviceName":"护理A组","deviceType":"DEVICE_BBP","dataCode":"wq84v2syn","userCode":"17408128","createTime":1593266002000,"deleteStatus":0,"dataType":2,"measureData":"舒张压为95,收缩压为136","nurseGroupId":4,"nurseGroupName":"护理A组","measureTime":1593266002000}]
         * abnormalList : [{"id":15,"measureData":"血糖测量值:15.81, 测量时间为:餐前","measureTime":1593496781000,"deviceCode":"BGk6fm9z9y","deviceName":"血糖","deviceType":"DEVICE_BG","measuredUserName":"患者B","measuredUserCode":"17408128","createTime":1577685602000,"deviceMac":"2AIAMR00958","channelId":2,"type":3,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":3},{"id":18,"measureData":"腋下体温测量值:35.4","measureTime":1593484583000,"deviceCode":"XCxncnqw26","deviceName":"希科一体机","deviceType":"DEVICE_XC","measuredUserCode":"17408128","createTime":1577759810000,"deviceMac":"DN00402Q52057600016","channelId":2,"type":6,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":4},{"id":155,"measureData":"血压测量值:舒张压为72,收缩压为159","measureTime":1593319420000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593222507000,"deviceMac":"BHL0080079","channelId":2,"type":1,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1","dataType":2},{"id":156,"measureData":"血压测量值:舒张压为53,收缩压为111","measureTime":1593318062000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248032000,"deviceMac":"BHL0080079","channelId":2,"type":2,"nurseGroupId":4,"nurseGroupName":"护理A组","bedInfo":"颐和-1F-101-1","dataType":2},{"id":157,"measureData":"血压测量值:舒张压为72,收缩压为141","measureTime":1593317969000,"deviceCode":"BBPnulgkswt","deviceName":"血压计","deviceType":"DEVICE_BBP","measuredUserCode":"17408128","createTime":1593248036000,"deviceMac":"BHL0080079","channelId":2,"type":1,"nurseGroupId":4,"nurseGroupName":"护理A组","abnormalType":1,"bedInfo":"颐和-1F-101-1","dataType":2}]
         * nurseLevel : {"id":4,"nurseLevelName":"介护1级","createTime":1592707558000,"orgId":2,"remark":"范德萨发生的","status":1,"itemsList":"吃饭,晨练"}
         * serviceList : [{"id":11,"serviceName":"医疗护理","serviceContent":"早中晚记得按时吃饭...","serviceType":1,"itemName":"吃饭","patientId":7,"startTime":1593273600000,"endTime":1593360000000,"patientName":"患者B"},{"id":12,"serviceName":"照料护理","serviceContent":"记得吃药。。。。","serviceType":2,"itemName":"吃药","patientId":7,"startTime":1593273600000,"endTime":1593360000000,"patientName":"患者B"},{"id":13,"serviceName":"医疗护理","serviceContent":"每天早起测量血压","serviceType":1,"itemName":"血压","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"},{"id":14,"serviceName":"医疗护理","serviceContent":"每天饭前测量血糖","serviceType":1,"itemName":"血糖","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"},{"id":15,"serviceName":"医疗护理","serviceContent":"每天早上起床测量体温","serviceType":1,"itemName":"体温","patientId":7,"startTime":1593360000000,"endTime":1593619200000,"patientName":"患者B"}]
         * logList : [{"serviceName":"","serviceContent":"早中晚记得按时吃饭...","itemName":"吃饭","finishTime":1593337179000,"patientName":"患者B","gender":1}]
         * groupList : [{"id":4,"groupName":"护理A组"}]
         * patientInfo : {"id":7,"patientName":"患者B","gender":1,"checkInTime":1592956800000,"nurseLevelId":4,"nurseLevelName":"介护1级","buildName":"颐和","floorName":"1F","roomName":"101","bedId":14,"bedName":"1","orgId":2,"doctorName":"周云","nurseDeptName":"护理部","age":86,"illness":"康复护理","patientCode":"17408128"}
         */

        private NurseLevelBean nurseLevel;
        private PatientInfoBean patientInfo;
        private List<MeasureDataListBean> measureDataList;
        private List<AbnormalListBean> abnormalList;
        private List<ServiceListBean> serviceList;
        private List<LogListBean> logList;
        private List<GroupListBean> groupList;

        public NurseLevelBean getNurseLevel() {
            return nurseLevel;
        }

        public void setNurseLevel(NurseLevelBean nurseLevel) {
            this.nurseLevel = nurseLevel;
        }

        public PatientInfoBean getPatientInfo() {
            return patientInfo;
        }

        public void setPatientInfo(PatientInfoBean patientInfo) {
            this.patientInfo = patientInfo;
        }

        public List<MeasureDataListBean> getMeasureDataList() {
            return measureDataList;
        }

        public void setMeasureDataList(List<MeasureDataListBean> measureDataList) {
            this.measureDataList = measureDataList;
        }

        public List<AbnormalListBean> getAbnormalList() {
            return abnormalList;
        }

        public void setAbnormalList(List<AbnormalListBean> abnormalList) {
            this.abnormalList = abnormalList;
        }

        public List<ServiceListBean> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceListBean> serviceList) {
            this.serviceList = serviceList;
        }

        public List<LogListBean> getLogList() {
            return logList;
        }

        public void setLogList(List<LogListBean> logList) {
            this.logList = logList;
        }

        public List<GroupListBean> getGroupList() {
            return groupList;
        }

        public void setGroupList(List<GroupListBean> groupList) {
            this.groupList = groupList;
        }

        public static class NurseLevelBean {
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

        public static class PatientInfoBean {
            /**
             * id : 7
             * patientName : 患者B
             * gender : 1
             * checkInTime : 1592956800000
             * nurseLevelId : 4
             * nurseLevelName : 介护1级
             * buildName : 颐和
             * floorName : 1F
             * roomName : 101
             * bedId : 14
             * bedName : 1
             * orgId : 2
             * doctorName : 周云
             * nurseDeptName : 护理部
             * age : 86
             * illness : 康复护理
             * patientCode : 17408128
             */

            private long id;
            private String patientName;
            private int gender;
            private long checkInTime;
            private int nurseLevelId;
            private String nurseLevelName;
            private String buildName;
            private String floorName;
            private String roomName;
            private long bedId;
            private String bedName;
            private int orgId;
            private String doctorName;
            private String nurseDeptName;
            private int age;
            private String illness;
            private String patientCode;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public long getCheckInTime() {
                return checkInTime;
            }

            public void setCheckInTime(long checkInTime) {
                this.checkInTime = checkInTime;
            }

            public int getNurseLevelId() {
                return nurseLevelId;
            }

            public void setNurseLevelId(int nurseLevelId) {
                this.nurseLevelId = nurseLevelId;
            }

            public String getNurseLevelName() {
                return nurseLevelName;
            }

            public void setNurseLevelName(String nurseLevelName) {
                this.nurseLevelName = nurseLevelName;
            }

            public String getBuildName() {
                return buildName;
            }

            public void setBuildName(String buildName) {
                this.buildName = buildName;
            }

            public String getFloorName() {
                return floorName;
            }

            public void setFloorName(String floorName) {
                this.floorName = floorName;
            }

            public String getRoomName() {
                return roomName;
            }

            public void setRoomName(String roomName) {
                this.roomName = roomName;
            }

            public long getBedId() {
                return bedId;
            }

            public void setBedId(long bedId) {
                this.bedId = bedId;
            }

            public String getBedName() {
                return bedName;
            }

            public void setBedName(String bedName) {
                this.bedName = bedName;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getNurseDeptName() {
                return nurseDeptName;
            }

            public void setNurseDeptName(String nurseDeptName) {
                this.nurseDeptName = nurseDeptName;
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

            public String getPatientCode() {
                return patientCode;
            }

            public void setPatientCode(String patientCode) {
                this.patientCode = patientCode;
            }
        }

        public static class MeasureDataListBean {
            /**
             * id : 54
             * temperature : 35.4
             * deviceCode : XCxncnqw26
             * deviceName : 护理A组
             * deviceType : DEVICE_XC
             * dataCode : d3eer3s2
             * userCode : 17408128
             * createTime : 1593497861000
             * deleteStatus : 0
             * dataType : 4
             * measureData : 腋下体温测量值:35.4
             * nurseGroupId : 4
             * nurseGroupName : 护理A组
             * measureTime : 1593497847000
             * glucose : 6.3
             * diastolic : 69
             * systolic : 103
             * heartrateM : 99
             */

            private long id;
            private float temperature;
            private String deviceCode;
            private String deviceName;
            private String deviceType;
            private String dataCode;
            private String userCode;
            private long createTime;
            private int deleteStatus;
            private int dataType;
            private String measureData;
            private int nurseGroupId;
            private String nurseGroupName;
            private long measureTime;
            private float glucose;
            private int diastolic;
            private int systolic;
            private int heartrateM;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public float getTemperature() {
                return temperature;
            }

            public void setTemperature(float temperature) {
                this.temperature = temperature;
            }

            public String getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getDataCode() {
                return dataCode;
            }

            public void setDataCode(String dataCode) {
                this.dataCode = dataCode;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(int deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public String getMeasureData() {
                return measureData;
            }

            public void setMeasureData(String measureData) {
                this.measureData = measureData;
            }

            public int getNurseGroupId() {
                return nurseGroupId;
            }

            public void setNurseGroupId(int nurseGroupId) {
                this.nurseGroupId = nurseGroupId;
            }

            public String getNurseGroupName() {
                return nurseGroupName;
            }

            public void setNurseGroupName(String nurseGroupName) {
                this.nurseGroupName = nurseGroupName;
            }

            public long getMeasureTime() {
                return measureTime;
            }

            public void setMeasureTime(long measureTime) {
                this.measureTime = measureTime;
            }

            public float getGlucose() {
                return glucose;
            }

            public void setGlucose(float glucose) {
                this.glucose = glucose;
            }

            public int getDiastolic() {
                return diastolic;
            }

            public void setDiastolic(int diastolic) {
                this.diastolic = diastolic;
            }

            public int getSystolic() {
                return systolic;
            }

            public void setSystolic(int systolic) {
                this.systolic = systolic;
            }

            public int getHeartrateM() {
                return heartrateM;
            }

            public void setHeartrateM(int heartrateM) {
                this.heartrateM = heartrateM;
            }
        }

        public static class AbnormalListBean {
            /**
             * id : 15
             * measureData : 血糖测量值:15.81, 测量时间为:餐前
             * measureTime : 1593496781000
             * deviceCode : BGk6fm9z9y
             * deviceName : 血糖
             * deviceType : DEVICE_BG
             * measuredUserName : 患者B
             * measuredUserCode : 17408128
             * createTime : 1577685602000
             * deviceMac : 2AIAMR00958
             * channelId : 2
             * type : 3
             * nurseGroupId : 4
             * nurseGroupName : 护理A组
             * abnormalType : 1
             * bedInfo : 颐和-1F-101-1
             * dataType : 3
             */

            private long id;
            private String measureData;
            private long measureTime;
            private String deviceCode;
            private String deviceName;
            private String deviceType;
            private String measuredUserName;
            private String measuredUserCode;
            private long createTime;
            private String deviceMac;
            private int channelId;
            private int type;
            private long nurseGroupId;
            private String nurseGroupName;
            private int abnormalType;
            private String bedInfo;
            private int dataType;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getMeasureData() {
                return measureData;
            }

            public void setMeasureData(String measureData) {
                this.measureData = measureData;
            }

            public long getMeasureTime() {
                return measureTime;
            }

            public void setMeasureTime(long measureTime) {
                this.measureTime = measureTime;
            }

            public String getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getMeasuredUserName() {
                return measuredUserName;
            }

            public void setMeasuredUserName(String measuredUserName) {
                this.measuredUserName = measuredUserName;
            }

            public String getMeasuredUserCode() {
                return measuredUserCode;
            }

            public void setMeasuredUserCode(String measuredUserCode) {
                this.measuredUserCode = measuredUserCode;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDeviceMac() {
                return deviceMac;
            }

            public void setDeviceMac(String deviceMac) {
                this.deviceMac = deviceMac;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getNurseGroupId() {
                return nurseGroupId;
            }

            public void setNurseGroupId(long nurseGroupId) {
                this.nurseGroupId = nurseGroupId;
            }

            public String getNurseGroupName() {
                return nurseGroupName;
            }

            public void setNurseGroupName(String nurseGroupName) {
                this.nurseGroupName = nurseGroupName;
            }

            public int getAbnormalType() {
                return abnormalType;
            }

            public void setAbnormalType(int abnormalType) {
                this.abnormalType = abnormalType;
            }

            public String getBedInfo() {
                return bedInfo;
            }

            public void setBedInfo(String bedInfo) {
                this.bedInfo = bedInfo;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }
        }

        public static class ServiceListBean {
            /**
             * id : 11
             * serviceName : 医疗护理
             * serviceContent : 早中晚记得按时吃饭...
             * serviceType : 1
             * itemName : 吃饭
             * patientId : 7
             * startTime : 1593273600000
             * endTime : 1593360000000
             * patientName : 患者B
             */

            private long id;
            private String serviceName;
            private String serviceContent;
            private int serviceType;
            private String itemName;
            private long patientId;
            private long startTime;
            private long endTime;
            private String patientName;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getServiceContent() {
                return serviceContent;
            }

            public void setServiceContent(String serviceContent) {
                this.serviceContent = serviceContent;
            }

            public int getServiceType() {
                return serviceType;
            }

            public void setServiceType(int serviceType) {
                this.serviceType = serviceType;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public long getPatientId() {
                return patientId;
            }

            public void setPatientId(long patientId) {
                this.patientId = patientId;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getPatientName() {
                return patientName;
            }

            public void setPatientName(String patientName) {
                this.patientName = patientName;
            }
        }

        public static class LogListBean {
            /**
             * serviceName :
             * serviceContent : 早中晚记得按时吃饭...
             * itemName : 吃饭
             * finishTime : 1593337179000
             * patientName : 患者B
             * gender : 1
             */

            private long startTime;
            private long endTime;
            private String serviceName;
            private String serviceContent;
            private String itemName;
            private long finishTime;
            private String patientName;
            private int gender;
            private String finishUser;

            public String getFinishUser() {
                return finishUser;
            }

            public void setFinishUser(String finishUser) {
                this.finishUser = finishUser;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getServiceContent() {
                return serviceContent;
            }

            public void setServiceContent(String serviceContent) {
                this.serviceContent = serviceContent;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public long getFinishTime() {
                return finishTime;
            }

            public void setFinishTime(long finishTime) {
                this.finishTime = finishTime;
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
        }

        public static class GroupListBean {
            /**
             * id : 4
             * groupName : 护理A组
             */

            private long id;
            private String groupName;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }
        }
    }
}
