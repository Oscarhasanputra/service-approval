package com.bit.microservices.service_approval.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum ApprovalHistoryStatusEnum implements EnumGetter {

    NEW("NEW"),

    WAITING_FOR_APPROVAL("WAITING FOR APPROVAL");
    public final String status;



    ApprovalHistoryStatusEnum(String status){
        this.status=status;
    }

    private static HashMap<String, ApprovalHistoryStatusEnum> map= new LinkedHashMap();

    static {
        for(ApprovalHistoryStatusEnum status: ApprovalHistoryStatusEnum.values()){
            map.put(status.status,status);
        }
    }


    public static ApprovalHistoryStatusEnum getValue(String label) throws Exception{
            ApprovalHistoryStatusEnum status =  map.get(label);
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
