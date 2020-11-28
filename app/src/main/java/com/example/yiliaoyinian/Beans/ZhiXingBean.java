package com.example.yiliaoyinian.Beans;

import java.util.List;

public class ZhiXingBean {

    /**
     * actionDefinitionId : AD.lumi.curtain.open_degree
     * model : lumi.curtain.aq2
     * actionName : 窗帘打开到指定位置
     * params : [{"paramId":"PD.position","paramName":"打开百分比","paramType":0,"paramEnum":"","paramUnit":"%","defaultValue":"50","minValue":0,"maxValue":100,"options":5}]
     */

    private String actionDefinitionId;
    private String model;
    private String actionName;
    private List<ParamsDTO> params;

    public String getActionDefinitionId() {
        return actionDefinitionId;
    }

    public void setActionDefinitionId(String actionDefinitionId) {
        this.actionDefinitionId = actionDefinitionId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public List<ParamsDTO> getParams() {
        return params;
    }

    public void setParams(List<ParamsDTO> params) {
        this.params = params;
    }

    public static class ParamsDTO {
        /**
         * paramId : PD.position
         * paramName : 打开百分比
         * paramType : 0
         * paramEnum :
         * paramUnit : %
         * defaultValue : 50
         * minValue : 0
         * maxValue : 100
         * options : 5
         */

        private String paramId;
        private String paramName;
        private int paramType;
        private String paramEnum;
        private String paramUnit;
        private String defaultValue;
        private int minValue;
        private int maxValue;
        private int options;

        public String getParamId() {
            return paramId;
        }

        public void setParamId(String paramId) {
            this.paramId = paramId;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public int getParamType() {
            return paramType;
        }

        public void setParamType(int paramType) {
            this.paramType = paramType;
        }

        public String getParamEnum() {
            return paramEnum;
        }

        public void setParamEnum(String paramEnum) {
            this.paramEnum = paramEnum;
        }

        public String getParamUnit() {
            return paramUnit;
        }

        public void setParamUnit(String paramUnit) {
            this.paramUnit = paramUnit;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public int getMinValue() {
            return minValue;
        }

        public void setMinValue(int minValue) {
            this.minValue = minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }

        public int getOptions() {
            return options;
        }

        public void setOptions(int options) {
            this.options = options;
        }
    }
}
