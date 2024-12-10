package com.bit.microservices.service_approval.enums;

public enum ConfigApprovalSubEventEnum {

    EVENT("Event"),

    SUBEVENT("Subevent");

    public final String fieldType;


    ConfigApprovalSubEventEnum(String fieldType){
        this.fieldType = fieldType;
    }
}
