package com.contacts.entities.enums.converters;

import com.contacts.entities.enums.PhoneType;

import javax.persistence.AttributeConverter;

public class PhoneTypeConverter implements AttributeConverter<PhoneType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(PhoneType attribute) {
        return attribute.getCode();
    }

    @Override
    public PhoneType convertToEntityAttribute(Integer dbData) {
        return PhoneType.getEnumByCode(dbData);
    }
}
