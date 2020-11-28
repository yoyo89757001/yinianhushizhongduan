package com.example.yiliaoyinian.Beans;

import java.util.List;

public class ActionListBean {


    /**
     * triggerDefinitionId : TD.lumi.weather.humi_less_than
     * triggerName : 处于指定湿度以下
     * model :  lumi.sensor_ht.agl02
     * params : [{"paramId":"PD.humi","paramName":"湿度","paramType":0,"paramEnum":"","paramUnit":"%","defaultValue":"80","minValue":0,"maxValue":100,"options":12}]
     */

    private String triggerDefinitionId;
    private String triggerName;
    private String model;
    private List<ParamsDTO> params;

    public String getTriggerDefinitionId() {
        return triggerDefinitionId;
    }

    public void setTriggerDefinitionId(String triggerDefinitionId) {
        this.triggerDefinitionId = triggerDefinitionId;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ParamsDTO> getParams() {
        return params;
    }

    public void setParams(List<ParamsDTO> params) {
        this.params = params;
    }

    public static class ParamsDTO {
        /**
         * paramId : PD.humi
         * paramName : 湿度
         * paramType : 0
         * paramEnum :
         * paramUnit : %
         * defaultValue : 80
         * minValue : 0
         * maxValue : 100
         * options : 12
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
