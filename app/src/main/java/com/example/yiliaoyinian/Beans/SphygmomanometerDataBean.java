package com.example.yiliaoyinian.Beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

//蓝牙测量数据
@Entity
public class SphygmomanometerDataBean {
    @Id
    private Long id;
    private String name;
    private String DoctorName;//护士name
    private String time;
    private int bloodPressure1; //收缩压
    private int bloodPressure2;//舒张压
    private int heartRate;//心率
    private String patientCode;
    private long patientId;
    private String deviceCode;//设备码
    private String patientName;


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBloodPressure1() {
        return bloodPressure1;
    }

    public void setBloodPressure1(int bloodPressure1) {
        this.bloodPressure1 = bloodPressure1;
    }

    public int getBloodPressure2() {
        return bloodPressure2;
    }

    public void setBloodPressure2(int bloodPressure2) {
        this.bloodPressure2 = bloodPressure2;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
