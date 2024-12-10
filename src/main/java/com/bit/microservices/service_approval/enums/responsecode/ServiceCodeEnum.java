package com.bit.microservices.service_approval.enums.responsecode;

public enum ServiceCodeEnum {
    CONFIG_APPROVAL("001","Config Approval"),
    EVENT_APPROVAL("002","Event Approval"),

    APPROVAL_REQUEST("003","Approval Request");

    public final String code;
    public final String label;

    ServiceCodeEnum(String code,String label){
        this.label =label;
        this.code=code;
    }
}
