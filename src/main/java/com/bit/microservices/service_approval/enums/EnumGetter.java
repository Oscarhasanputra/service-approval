package com.bit.microservices.service_approval.enums;

public interface EnumGetter {
    Boolean valueIsExist(String value);
    static <E extends Enum<E> & EnumGetter> Boolean isValidValue(Class<? extends EnumGetter> enumC, String value) {

        return enumC.getEnumConstants()[0].valueIsExist(value);

    }
}
