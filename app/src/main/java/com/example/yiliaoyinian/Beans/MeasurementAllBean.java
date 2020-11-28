package com.example.yiliaoyinian.Beans;

public class MeasurementAllBean {

    private long id;
    private String measureData;
    private long measureTime;
    private int measuredType;
    private float data;
    private int type;//1是正常，2是异常

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public int getMeasuredType() {
        return measuredType;
    }

    public void setMeasuredType(int measuredType) {
        this.measuredType = measuredType;
    }
}
