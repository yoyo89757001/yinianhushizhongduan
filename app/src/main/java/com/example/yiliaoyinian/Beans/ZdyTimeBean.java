package com.example.yiliaoyinian.Beans;

public class ZdyTimeBean {
    private int id;
    private String name;
    private boolean isA;

    public ZdyTimeBean(int id, String name, boolean isA) {
        this.id = id;
        this.name = name;
        this.isA = isA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }

    @Override
    public String toString() {
        return "ZdyTimeBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isA=" + isA +
                '}';
    }
}
