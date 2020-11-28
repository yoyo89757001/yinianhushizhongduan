package com.example.yiliaoyinian.Beans;

public class WGPlay1Bean {

    public WGPlay1Bean(String name, int id, boolean isA) {
        this.name = name;
        this.id = id;
        this.isA = isA;
    }

    public WGPlay1Bean() {

    }

    private String name;
    private int id;
    private boolean isA;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }


}
