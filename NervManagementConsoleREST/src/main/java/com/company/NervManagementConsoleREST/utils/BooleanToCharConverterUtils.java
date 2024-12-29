package com.company.NervManagementConsoleREST.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToCharConverterUtils implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute != null && attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equalsIgnoreCase(dbData);
    }
}