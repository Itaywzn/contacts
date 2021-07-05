package com.contacts.entities.enums;

import com.contacts.entities.enums.converters.DbConvertibleEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PhoneType {

    PRIVATE(1),
    OFFICE(2),
    HOME(3);

    private final int code;
    private static final Map<Integer, PhoneType> enumsByCode = new HashMap<>();

    PhoneType(int code) {
        this.code = code;
    }


    public int getCode() {
        return this.code;
    }

    public static PhoneType getEnumByCode(Integer code) {
        if (code==null) {
            return null;
        }
        if (enumsByCode.isEmpty()){
            initializeMap();
        }
        return enumsByCode.get(code);
    }

    public static void initializeMap() {
        Arrays.stream(PhoneType.values()).forEach(
                enumInstance -> enumsByCode.put(enumInstance.getCode(), enumInstance));
    }
}
