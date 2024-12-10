package com.bit.microservices.service_approval.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum WhiteListUserEnum implements EnumGetter{
    USER_INTERNAL("USER INTERNAL"),
    HEAD_POSITION("HEAD POSITION"),

    HEAD_POSITION_ALLOCATION("HEAD POSITION ALLOCATION");
    public final String type;



    WhiteListUserEnum(String type){
        this.type=type;
    }

    private static HashMap<String, WhiteListUserEnum> map= new LinkedHashMap();

    static {
        for(WhiteListUserEnum enumData: WhiteListUserEnum.values()){
            map.put(enumData.type,enumData);
        }
    }


    public static WhiteListUserEnum getValue(String label) throws Exception{
        WhiteListUserEnum status =  map.get(label);
        if(Objects.isNull(status))
            throw new Exception("Enum Tidak Terdaftar");
        return status;
    }
    @Override
    public Boolean valueIsExist(String value) {
        try {
            getValue(value);
            return true;
        }catch (Exception err){
            return false;
        }
    }
}
