package com.platform.school.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;


@Converter(autoApply = true)
public class FacilitiesConverter implements AttributeConverter<List<String>, String> {


    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return "";
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return List.of();
    }
}
