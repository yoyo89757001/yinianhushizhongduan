package com.example.yiliaoyinian.adapter;

public class PatientsBean {
    private String name;
    private boolean selectType;


    public boolean isSelectType() {
        return selectType;
    }

    public void setSelectType(boolean selectType) {
        this.selectType = selectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
