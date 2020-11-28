package com.example.yiliaoyinian.Beans;

import java.io.Serializable;

public class JuZhuBean implements Serializable {
    private long L1;
    private String L1Name;
    private long L2;
    private String L2Name;
    private long L3;
    private String L3Name;
    private long L4;
    private String L4Name;


    public long getL1() {
        return L1;
    }

    public void setL1(long l1) {
        L1 = l1;
    }

    public String getL1Name() {
        return L1Name;
    }

    public void setL1Name(String l1Name) {
        L1Name = l1Name;
    }

    public long getL2() {
        return L2;
    }

    public void setL2(long l2) {
        L2 = l2;
    }

    public String getL2Name() {
        return L2Name;
    }

    public void setL2Name(String l2Name) {
        L2Name = l2Name;
    }

    public long getL3() {
        return L3;
    }

    public void setL3(long l3) {
        L3 = l3;
    }

    public String getL3Name() {
        return L3Name;
    }

    public void setL3Name(String l3Name) {
        L3Name = l3Name;
    }

    public long getL4() {
        return L4;
    }

    public void setL4(long l4) {
        L4 = l4;
    }

    public String getL4Name() {
        return L4Name;
    }

    public void setL4Name(String l4Name) {
        L4Name = l4Name;
    }


    public boolean isNill(){
        return L1 != 0 && L1Name != null && L2 != 0 && L2Name != null && L3 != 0 && L3Name != null && L4 != 0 && L4Name != null;
    }
}
