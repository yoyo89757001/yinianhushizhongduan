package com.example.yiliaoyinian.Beans;

import com.alibaba.fastjson.JSONArray;

public class ActionsBean {
    private String subjectId;
    private String model;
    private String actionDefinitionId;
    private String triggerDefinitionId;
    private JSONArray params;
    private String name;
    private String miaoshu;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ActionsBean() {
    }

    public ActionsBean(String subjectId, String model, String actionDefinitionId, String triggerDefinitionId, JSONArray params, String name, String miaoshu, String time) {
        this.subjectId = subjectId;
        this.model = model;
        this.actionDefinitionId = actionDefinitionId;
        this.triggerDefinitionId = triggerDefinitionId;
        this.params = params;
        this.name = name;
        this.miaoshu = miaoshu;
        this.time = time;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getActionDefinitionId() {
        return actionDefinitionId;
    }

    public void setActionDefinitionId(String actionDefinitionId) {
        this.actionDefinitionId = actionDefinitionId;
    }

    public String getTriggerDefinitionId() {
        return triggerDefinitionId;
    }

    public void setTriggerDefinitionId(String triggerDefinitionId) {
        this.triggerDefinitionId = triggerDefinitionId;
    }

    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ActionsBean{" +
                "subjectId='" + subjectId + '\'' +
                ", model='" + model + '\'' +
                ", actionDefinitionId='" + actionDefinitionId + '\'' +
                ", triggerDefinitionId='" + triggerDefinitionId + '\'' +
                ", params=" + params +
                ", name='" + name + '\'' +
                ", miaoshu='" + miaoshu + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
