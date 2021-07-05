package com.contacts.entities.enums.converters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface DbConvertibleEnum {
    Map<Integer, DbConvertibleEnum> enumsByCode = new HashMap<>();

    public int getCode();

    public DbConvertibleEnum getEnumByCode(Integer code);

    public DbConvertibleEnum[] getEnumValues();

    public default void initializeMap() {
        Arrays.stream(getEnumValues()).forEach(
                dbConvertibleEnum -> enumsByCode.put(dbConvertibleEnum.getCode(), dbConvertibleEnum));
    }


}
