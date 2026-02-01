package com.tp.accessguard_web.model;

import com.tp.accessguard_web.model.enums.PersonStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PersonStatusConvert implements AttributeConverter<PersonStatus, String> {

    @Override
    public String convertToDatabaseColumn(PersonStatus attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public PersonStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PersonStatus.valueOf(dbData.toUpperCase());
    }
}
