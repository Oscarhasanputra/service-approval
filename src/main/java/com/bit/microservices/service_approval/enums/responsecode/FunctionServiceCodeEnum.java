package com.bit.microservices.service_approval.enums.responsecode;

public enum FunctionServiceCodeEnum {
    CREATE("01","Creation"),

    UPDATE("02","UPDATE"),

    DELETE("03","DELETE"),
    GET("04","GET"),
    GET_LIST("05","GET LIST")

    ;


    public final String code;
    public final String label;

    FunctionServiceCodeEnum(String code,String label){
        this.label =label;
        this.code=code;
    }
}
