package com.example.yiliaoyinian.Beans;

import java.util.List;

public class ServiceTypeBean {//服务类型
   private String typeId;
   private String typeName;
   private String typeName2;

    public ServiceTypeBean() {
    }

    public ServiceTypeBean(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName2() {
        return typeName2;
    }

    public void setTypeName2(String typeName2) {
        this.typeName2 = typeName2;
    }
}
